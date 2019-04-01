package android.project.lend;

import java.util.ArrayList;
import java.util.Random;

public class Helper {

    public ArrayList<ProductData> getProductData(){

        ArrayList<ProductData> product_list = new ArrayList<>();

        for(int i = 0; i < 10; i++)
        {
            ProductData product_data = new ProductData();

            product_data.id = (i + 1);
            product_data.name = "Product " + (i + 1);

            Random r = new Random();
            product_data.price = r.nextFloat() * (100 - 5) + 5;
            product_data.rating = r.nextInt((5-1) + 1 ) + 1;
            product_data.image = "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2Fthumb%2F5%2F57%2FFraming_hammer.jpg%2F1200px-Framing_hammer.jpg&f=1";

            product_list.add(product_data);
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

            UserData user_data = new UserData();

            user_data.id = (i+1);
            user_data.firstName = "Florian";
            user_data.lastName = "Brandsma";
            user_data.imageUrl = "https://secure.i.telegraph.co.uk/multimedia/archive/03597/POTD_chick_3597497k.jpg";
            user_data.emailAddress = "t7brfl00@students.oamk.fi";
            user_data.homeAddress = "Kotkantie 1";
            user_data.phoneNumber = "+3581234567890";
            user_data.cardNumber = "1234 4567 8910";
            user_data.cardDate = "01/20";
            user_data.cardSecurity = "123";

            user_list.add(user_data);
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
            lendzData.startDate = "31/03/2019";
            lendzData.dueDate = "03/04/2019";

            lendzList.add(lendzData);
        }

        return lendzList;
    }

    public class ProductData{

        int id;
        String name;
        float price;
        int rating;
        String image;
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
