package android.project.lend;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Helper {

    public ArrayList<ProductData> getProductData(){

        ArrayList<ProductData> product_list = new ArrayList<>();

        for(int i = 0; i < 10; i++)
        {
            ProductData productData = new ProductData();

            productData.id = (i + 1);
            productData.userId = (1+(i%2));
            productData.name = "Product " + (i + 1);

            Random r = new Random();
            productData.price = (r.nextFloat() * (100 - 5) + 5);
            productData.rating = r.nextInt((5-1) + 1 ) + 1;
            productData.imageUrl = "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2Fthumb%2F5%2F57%2FFraming_hammer.jpg%2F1200px-Framing_hammer.jpg&f=1";
            productData.description = "This is a description.";
            productData.category = "Power Tools";
            productData.status = 1;

            product_list.add(productData);
        }

        return product_list;
    }

    public UserData getUserData(Integer id){

        UserData userData = new UserData();

        userData.id = 1;
        userData.firstName = "Florian";
        userData.lastName = "Brandsma";
        userData.imageUrl = "https://secure.i.telegraph.co.uk/multimedia/archive/03597/POTD_chick_3597497k.jpg";
        userData.emailAddress = "t7brfl00@students.oamk.fi";
        userData.homeAddress = "Kotkantie 1";
        userData.phoneNumber = "+3581234567890";
        userData.cardNumber = "1234 4567 8910";
        userData.cardDate = "01/20";
        userData.cardSecurity = "123";

        return userData;
    }

    public ArrayList<UserData> getUserData(){

        ArrayList<UserData> user_list = new ArrayList<>();

        for(int i = 0; i < 10; i++) {

            UserData userData = new UserData();

            userData.id = (i+1);
            userData.firstName = "Florian";
            userData.lastName = "Brandsma";
            userData.imageUrl = "https://secure.i.telegraph.co.uk/multimedia/archive/03597/POTD_chick_3597497k.jpg";
            userData.emailAddress = "t7brfl00@students.oamk.fi";
            userData.homeAddress = "Kotkantie 1";
            userData.phoneNumber = "+3581234567890";
            userData.cardNumber = "1234 4567 8910";
            userData.cardDate = "01/20";
            userData.cardSecurity = "123";

            user_list.add(userData);
        }

        return user_list;
    }

    public ArrayList<ImageData> getImageData(){

        ArrayList<ImageData> imageList = new ArrayList<>();

        final String[] IMAGES = new String[] {
            "https://secure.i.telegraph.co.uk/multimedia/archive/03597/POTD_chick_3597497k.jpg",
            "http://www.cutestpaw.com/wp-content/uploads/2016/07/Shark-horse-fictional-animal-picture.jpg",
            "https://cms.algoafm.co.za/img/tn_201781185222.jpeg?w=270&h=250&mode=crop&scale=both&anchor=topcenter",
            "https://i2-prod.mirror.co.uk/incoming/article11840943.ece/ALTERNATES/s615/PAY-MATING-BUGS.jpg"
        };

        for(int i = 0; i < IMAGES.length; i++){

            ImageData imageData = new ImageData();

            imageData.id = (i+1);
            imageData.productId = 1;
            imageData.url = IMAGES[i];

            imageList.add(imageData);
        }

        return imageList;
    }

    public ArrayList<LendzData> getLendzData(){

        ArrayList<LendzData> lendzList = new ArrayList<>();

        for(int i = 0; i < 10; i++){

            LendzData lendzData = new LendzData();

            lendzData.id = (i+1);
            lendzData.lenderUserId = (1+(i%2));
            lendzData.productId = (i+1);
            lendzData.startDate = "31/03/2019";
            lendzData.dueDate = "03/04/2019";

            lendzList.add(lendzData);
        }

        return lendzList;
    }

    public class ProductData{

        Integer id;
        Integer userId;
        String name;
        Float price;
        Integer rating;
        String imageUrl;
        String description;
        String category;
        Integer status;
    }

    public class UserData{

        Integer id;
        String firstName;
        String lastName;
        String imageUrl;
        String emailAddress;
        String homeAddress;
        String phoneNumber;
        String cardNumber;
        String cardDate;
        String cardSecurity;
    }

    public class ImageData{

        Integer id;
        Integer productId;
        String url;
    }

    public class LendzData{

        Integer id;
        Integer productId;
        Integer lenderUserId;
        String startDate;
        String dueDate;
    }
}
