package android.project.lend;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import static android.app.Activity.RESULT_CANCELED;
import static java.lang.Float.parseFloat;

public class HomeNewItemFragment extends Fragment {

    private View view;
    private String itemTitle, itemDescription, itemCategory;
    private EditText inputPriceText;
    private Float itemPrice;
    private Button priceBtn, cancelBtn, submitBtn;
    private Boolean titleCheck, categoryCheck, descriptionCheck, priceCheck;
    private ImageView newImage1, newImage2, newImage3, newImage4;
    private final String imageDirectory = "/Lend";
    private int gallery = 1, camera = 2, currentPic = 0;
    private String currentPhotoPath;
    private Uri photoURI;
    private int PERMISSION_ALL = 1;
    private String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_new_item, container, false);

        //Set Price Button For Dialog
        priceBtn = view.findViewById(R.id.price_btn);
        priceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceSelector();
            }
        });

        //Set Submit Button
        submitBtn = view.findViewById(R.id.new_item_submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewProduct();
            }
        });

        //Set Cancel Button
        cancelBtn = view.findViewById(R.id.new_item_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //Getting Image Views
        newImage1 = view.findViewById(R.id.new_image_1);
        newImage2 = view.findViewById(R.id.new_image_2);
        newImage3 = view.findViewById(R.id.new_image_3);
        newImage4 = view.findViewById(R.id.new_image_4);

        //Setting OnClick Listeners To Image Views
        newImage1.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             showPictureDialog();
                                             currentPic = 1;
                                         }
                                     }
        );
        newImage2.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             showPictureDialog();
                                             currentPic = 2;
                                         }
                                     }
        );
        ;
        newImage3.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             showPictureDialog();
                                             currentPic = 3;
                                         }
                                     }
        );
        newImage4.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             showPictureDialog();
                                             currentPic = 4;
                                         }
                                     }
        );


        return view;
    }

    //Checking Permissions
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    //Save New Product
    private void setNewProduct() {
        titleCheck = categoryCheck = descriptionCheck = priceCheck = false;
        ProductDataItem newProduct = new ProductDataItem();

        newProduct.create();

        //Get Title
        EditText inputTitle = view.findViewById(R.id.new_item_title);
        itemTitle = inputTitle.getText().toString();
        if (itemTitle.length() <= 0) {
            Toast.makeText(getContext(), "Please select valid title", Toast.LENGTH_SHORT).show();
        } else {
            titleCheck = true;
            newProduct.setName(itemTitle);
        }

        //Get Category
        Spinner categorySpinner = view.findViewById(R.id.category_spinner);
        itemCategory = categorySpinner.getSelectedItem().toString();
        if (titleCheck) {
            if (itemCategory.equals("Category")) {
                Toast.makeText(getContext(), "Please select valid category", Toast.LENGTH_SHORT).show();
            } else {
                categoryCheck = true;
                newProduct.setCategory(itemCategory);
            }
        }

        //Get Description
        EditText inputDescription = view.findViewById(R.id.new_item_description);
        itemDescription = inputDescription.getText().toString();
        if (categoryCheck) {
            if (itemDescription.length() <= 0) {
                Toast.makeText(getContext(), "Please select valid description", Toast.LENGTH_SHORT).show();
            } else {
                descriptionCheck = true;
                newProduct.setDescription(itemDescription);
            }
        }
        //Get Price
        if (descriptionCheck) {
            if (inputPriceText == null || inputPriceText.length() < 0) {
                Toast.makeText(getContext(), "Please select valid price", Toast.LENGTH_SHORT).show();
            } else {
                itemPrice = parseFloat(inputPriceText.getText().toString());
                priceCheck = true;
                newProduct.setPrice(itemPrice);
            }
        }
        //Confirm Final Check + Update Product
        if (priceCheck) {
            Toast.makeText(getContext(), "Item Added", Toast.LENGTH_SHORT).show();
            newProduct.update();
        }

    }

    //Creating Add Picture Dialog Box And Checking Permissions
    private void showPictureDialog() {
        if (!hasPermissions(getContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
            showPictureDialog();
        } else {
            final Dialog pictureDialog = new Dialog(getContext());
            pictureDialog.setContentView(R.layout.picture_input_layout);
            pictureDialog.show();
            //Get Dialog Camera Icon
            pictureDialog.findViewById(R.id.contact_message).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takePhotoFromCamera();
                    pictureDialog.dismiss();
                }

            });
            //Get Dialog Gallery Icon
            pictureDialog.findViewById(R.id.message).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choosePhotoFromGallery();
                    pictureDialog.dismiss();
                }
            });
        }
    }

    //Start Gallery Intent
    public void choosePhotoFromGallery() {
        Toast.makeText(getContext(), "Opening Gallery", Toast.LENGTH_SHORT).show();
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, gallery);

    }

    //Start Camera Intent
    public void takePhotoFromCamera() {
        Toast.makeText(getContext(), "Starting Camera", Toast.LENGTH_SHORT).show();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            File photoFile = null;
            try {

                photoFile = createImageFile();

            } catch (IOException ex){

            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getContext(),
                        "android.project.lend",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, camera);
            }
        }
    }

    //Start Activity Result Listener
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == gallery) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentURI);
                    Toast.makeText(getContext(), "Image Saved", Toast.LENGTH_SHORT).show();
                    saveSelector(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == camera) {
            Uri imageUri = null;
            if (data != null) {
                imageUri = data.getData();
            }
            if (imageUri == null && photoURI != null) {
                imageUri = photoURI;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                    saveSelector(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Create Image File
    private File createImageFile() throws IOException {
        File image = null;
        File storageDir = new File(
                Environment.getExternalStorageDirectory() + imageDirectory);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        try {
            String imageFileName = "JPEG_" + Calendar.getInstance().getTimeInMillis() + "_";
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
            currentPhotoPath = image.getAbsolutePath();
            galleryAddPic();
        } catch (IOException ex) {
        }
        return image;
    }


    //Save Image To Gallery
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
    }

    //Save Bitmap Icon And Save To Relevant ImageView
    private void saveSelector(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(0);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 800, 600, true);
        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

        switch (currentPic) {
            case 1:
                newImage1.setImageBitmap(rotatedBitmap);
                break;
            case 2:
                newImage2.setImageBitmap(rotatedBitmap);
                break;
            case 3:
                newImage3.setImageBitmap(rotatedBitmap);
                break;
            case 4:
                newImage4.setImageBitmap(rotatedBitmap);
                break;
        }
    }

    //Open Price Dialog Box
    @SuppressLint("ClickableViewAccessibility")
    private void priceSelector() {
        final Dialog priceDialog = new Dialog(getContext());
        priceDialog.setContentView(R.layout.price_input_layout);
        priceDialog.show();

        //Get Input Price Text
        inputPriceText = priceDialog.findViewById(R.id.price_input);

        //Get Dialog Cancel Button
        final Button priceCancel = priceDialog.findViewById(R.id.new_item_price_cancel);
        priceCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceDialog.cancel();
            }

        });

        //Get Dialog Submit Button
        final Button priceSubmit = priceDialog.findViewById(R.id.new_item_price_submit);
        priceSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String inputPriceTextString = inputPriceText.getText().toString();
                if (inputPriceTextString.length() > 0 && parseFloat(inputPriceTextString) > 0) {
                    priceBtn.setText("€ " + inputPriceTextString + "/day");
                    priceDialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Please enter valid price", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Set Listener For Price Input
        inputPriceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView priceCalc = priceDialog.findViewById(R.id.price_calc_text);
                if (s.toString().length() > 0) {
                    double input = Double.parseDouble(s.toString());
                    double usersProfit = Math.floor(input * 0.95);
                    priceCalc.setText("You'll receive €" + usersProfit + " per day");
                } else {
                    priceCalc.setText("");
                }
            }

        });

    }


}
