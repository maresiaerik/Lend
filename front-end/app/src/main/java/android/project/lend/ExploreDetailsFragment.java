package android.project.lend;


import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ExploreDetailsFragment extends Fragment implements DatePickerDialog.OnDateSetListener, IDataController {

    private LendzManager lendzManager;
    private ArrayList<LendzDataItem> lendzDataItemList;
    private ArrayList<ProductDataItem> allItems = null;

    private View view = null;
    private ProductDataItem productDataItem, nextItem, previousItem;
    private DatePickerDialog startCalendar;
    private DatePickerDialog endCalendar;
    private Calendar now = Calendar.getInstance();
    private Button borrowBtn;
    private String startTag = "DatepickerdialogStart", endtag = "DatepickerdialogEnd", finalStartDate, finalEndDate;
    private Integer givenStartYear, givenStartMonth, givenStartDayOfMonth;
    private Button datesBtn;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_explore_details, container, false);

        TextView heading = view.findViewById(R.id.page_title);
        TextView categoryName = view.findViewById(R.id.detailed_category_name);
        TextView description = view.findViewById(R.id.detailed_item_description);

        final GestureDetector gesture = new GestureDetector(getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                openNext();
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                openPrev();
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

        Bundle args = getArguments();
        productDataItem = (ProductDataItem) args.getSerializable("selectedItem");
        allItems = (ArrayList<ProductDataItem>) args.getSerializable("AllItems");

        //Assigning Next + Previous Items
        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).getId().equals(productDataItem.getId())) {
                if (i < allItems.size() - 1) {
                    nextItem = allItems.get(i + 1);
                }
                if (i != 0) {
                    previousItem = allItems.get(i - 1);
                }
                break;
            }
        }

        heading.setText(productDataItem.getName());
        String[] category = getResources().getStringArray(R.array.category_items_dropdown);
        categoryName.setText(category[new Integer(productDataItem.getCategory())]);
        TextView price = view.findViewById(R.id.detailed_price);
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedPrice = df.format(productDataItem.getPrice());
        price.setText(formattedPrice + "€/day");

        description.setText(productDataItem.getDescription());
        ViewPager imageContainer = view.findViewById(R.id.detailed_image_carousel);

        ImageCarousel adapter = new ImageCarousel(getContext(), productDataItem);

        imageContainer.setAdapter(adapter);

        datesBtn = view.findViewById(R.id.detailed_item_dates);
        datesBtn.setEnabled(false);
        datesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartCalendar();
            }
        });

        //Setting Borrow Button Listener
        borrowBtn = view.findViewById(R.id.detailed_item_borrow_button);
        borrowBtn.setEnabled(false);
        borrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent confirmIntent = new Intent(getContext(), ExploreConfirmActivity.class);
                confirmIntent.putExtra("itemData", (Parcelable) productDataItem);
                confirmIntent.putExtra("datesBtn", finalStartDate);
                confirmIntent.putExtra("ImageURL", productDataItem.imageDataItems.get(0).getUrl());
                confirmIntent.putExtra("endDate", finalEndDate);
                confirmIntent.putExtra("userEmail", productDataItem.owner.getEmailAddress());
                confirmIntent.putExtra("userAddress", productDataItem.owner.getHomeAddress());
                confirmIntent.putExtra("userPhone", productDataItem.owner.getPhoneNumber());
                startActivity(confirmIntent);
            }
        });

        lendzManager = new LendzManager(this);

        return view;
    }

    private void openStartCalendar() {

        if ((finalStartDate != null) && finalStartDate.length() > 0) try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date = sdf.parse(finalStartDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            startCalendar = DatePickerDialog.newInstance(
                    this,
                    cal
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        else {
            startCalendar = DatePickerDialog.newInstance(
                    this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        }

        startCalendar.setTitle("Start Date");
        startCalendar.setOkText("Next");
        startCalendar.setMinDate(now);
        startCalendar.setDisabledDays(setDates());
        startCalendar.show(getActivity().getFragmentManager(), startTag);

    }

    private Calendar[] setDates() {

        ArrayList<Calendar> dates = new ArrayList<>();


        for (int i = 0; i < lendzDataItemList.size(); i++) {
            Calendar startDay = Calendar.getInstance();
            Calendar endDay = Calendar.getInstance();
            try {
                startDay.setTime(dateFormat.parse(lendzDataItemList.get(i).getStartDate()));
                endDay.setTime(dateFormat.parse(lendzDataItemList.get(i).getDueDate()));

                while (startDay.compareTo(endDay) < 1) {
                    Calendar betweenDay = Calendar.getInstance();
                    betweenDay.setTime(startDay.getTime());
                    dates.add(betweenDay);
                    startDay.add(Calendar.DAY_OF_MONTH, 1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Calendar[] disabled = new Calendar[dates.size()];
        for (int i = 0; i < dates.size(); i++) {
            Log.d("STARTDATE", dates.get(i).getTime() + "");
            disabled[i] = dates.get(i);
        }


        return disabled;
    }

    private void openEndCalendar() {
        if ((finalEndDate != null) && finalEndDate.length() > 0) try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date = sdf.parse(finalEndDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            startCalendar = DatePickerDialog.newInstance(
                    this,
                    cal
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        else {
            endCalendar = DatePickerDialog.newInstance(
                    this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        }

        Calendar givenDate = now.getInstance();

        try {
            givenDate.setTime(dateFormat.parse(givenStartDayOfMonth + "/" + givenStartMonth + "/" + givenStartYear));
        } catch (ParseException e) {
            Log.d("INSIDEOPENENDCALENDAR", e + " ");
        }


        endCalendar.setMinDate(givenDate);


        Calendar[] startDay = {
                givenDate
        };


        Calendar[] disabled = setDates();


        if (disabled.length > 0) {

            Calendar[] maxSelectableDay = {
                    getNextDisabledDate(disabled)
            };

            if (maxSelectableDay[0] == null) {
                endCalendar.setDisabledDays(disabled);
            } else {
                endCalendar.setMaxDate(maxSelectableDay[0]);
            }
        } else {
            endCalendar.setDisabledDays(disabled);
        }


        endCalendar.setDisabledDays(disabled);
        endCalendar.setHighlightedDays(startDay);
        endCalendar.setTitle("Return Date");
        endCalendar.setOkText("Submit");
        endCalendar.setCancelText("Back");
        endCalendar.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                openStartCalendar();
            }
        });
        endCalendar.show(getActivity().getFragmentManager(), endtag);

    }

    private Calendar getNextDisabledDate(Calendar[] d) {

        List<Calendar> disabled = Arrays.asList(d);

        ArrayList<Calendar> newDisabled = new ArrayList<>();

        Calendar startDate = now.getInstance();

        try {
            startDate.setTime(dateFormat.parse(givenStartDayOfMonth + "/" + givenStartMonth + "/" + givenStartYear));
        } catch (ParseException e) {
            Log.d("INSIDEGETNEXTDISABLEDDATE", e + " ");
        }

        for (int i = 0; i < disabled.size(); i++) {
            if (disabled.get(i).after(startDate)) {
                newDisabled.add(disabled.get(i));
            }
        }
        Log.d("LENGTHOFARRAY", newDisabled.size() + " ");

        if (newDisabled.size() < 1) {
            //return something empty
            Calendar[] r = new Calendar[1];
            r[0] = null;
            return r[0];
        }
        Calendar[] nextDate = new Calendar[newDisabled.size()];

        for (int i = 0; i < newDisabled.size(); i++) {
            Log.d("INDEXTEST", i + " ");

            nextDate[i] = newDisabled.get(i);
        }
        Arrays.sort(nextDate);

        return nextDate[0];

    }


    @Override
    public void onDateSet(DatePickerDialog v, int year, int monthOfYear, int dayOfMonth) {
        if (v.getTag().equals(startTag)) {
            givenStartYear = year;
            givenStartMonth = monthOfYear + 1;
            givenStartDayOfMonth = dayOfMonth;
            finalStartDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            datesBtn.setText(finalStartDate);
            openEndCalendar();

        } else if (v.getTag().equals(endtag)) {
            finalEndDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            datesBtn.setText(finalStartDate + " - " + finalEndDate);
            if (finalStartDate != null && finalEndDate != null) {
                borrowBtn.setEnabled(true);
            }
        }

    }

    @Override
    public void setData() {

        lendzDataItemList = lendzManager.getLendzListByProduct(productDataItem.getId());
        datesBtn.setEnabled(true);
        datesBtn.setText("Select dates");
    }

    private void openNext() {
        if (nextItem != null) {
            ExploreDetailsFragment detailedItemView = new ExploreDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedItem", nextItem);
            bundle.putSerializable("AllItems", allItems);
            detailedItemView.setArguments(bundle);
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.popBackStack();
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.in_right, R.anim.out_left);
            ft.replace(R.id.fragment_container, detailedItemView);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    private void openPrev() {
        if (previousItem != null) {
            ExploreDetailsFragment detailedItemView = new ExploreDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedItem", previousItem);
            bundle.putSerializable("AllItems", allItems);
            detailedItemView.setArguments(bundle);
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.popBackStack();
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.in_left, R.anim.out_right);
            ft.replace(R.id.fragment_container, detailedItemView);
            ft.addToBackStack(null);
            ft.commit();
        }
    }
}



