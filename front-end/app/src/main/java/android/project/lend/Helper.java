package android.project.lend;

import java.sql.Array;
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

    public ArrayList<UserData> getUserData(Integer id){

        ArrayList<UserData> user_list = new ArrayList<>();

        UserData user_data = new UserData();

        user_data.id = 1;
        user_data.first_name = "Florian";
        user_data.last_name = "Brandsma";
        user_data.image_url = "https://secure.i.telegraph.co.uk/multimedia/archive/03597/POTD_chick_3597497k.jpg";
        user_data.email_address = "t7brfl00@students.oamk.fi";
        user_data.home_address = "Kotkantie 1";
        user_data.phone_number = "+3581234567890";
        user_data.card_number = "1234 4567 8910";
        user_data.card_date = "01/20";
        user_data.card_security = "123";

        user_list.add(user_data);

        return user_list;
    }

    public ArrayList<ImageData> getImageData(Integer id){

        ArrayList<ImageData> image_list = new ArrayList<>();

        final String[] IMAGES = new String[] {
            "https://secure.i.telegraph.co.uk/multimedia/archive/03597/POTD_chick_3597497k.jpg",
            "http://www.cutestpaw.com/wp-content/uploads/2016/07/Shark-horse-fictional-animal-picture.jpg",
            "https://cms.algoafm.co.za/img/tn_201781185222.jpeg?w=270&h=250&mode=crop&scale=both&anchor=topcenter",
            "https://i2-prod.mirror.co.uk/incoming/article11840943.ece/ALTERNATES/s615/PAY-MATING-BUGS.jpg"
        };

        for(int i = 0; i < IMAGES.length; i++){

            ImageData image_data = new ImageData();

            image_data.id = (i+1);
            image_data.product_id = 1;
            image_data.url = IMAGES[i];
        }

        return image_list;
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
        String first_name;
        String last_name;
        String image_url;
        String email_address;
        String home_address;
        String phone_number;
        String card_number;
        String card_date;
        String card_security;
    }

    public class ImageData{
        Integer id;
        Integer product_id;
        String url;
    }
}
