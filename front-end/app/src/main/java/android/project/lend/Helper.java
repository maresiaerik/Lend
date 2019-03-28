package android.project.lend;

import java.util.ArrayList;
import java.util.Random;

public class Helper {

    public ArrayList<ProductData> getProducts(){

        ArrayList<ProductData> productList = new ArrayList<>();

        for(int i = 0; i < 10; i++)
        {
            ProductData productData = new ProductData();

            productData.id = (i + 1);
            productData.name = "Product " + (i + 1);

            Random r = new Random();
            productData.price = r.nextFloat() * (100 - 5) + 5;
            productData.rating = r.nextInt((5-1) + 1 ) + 1;
            productData.image = "https://proxy.duckduckgo.com/iu/?u=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2Fthumb%2F5%2F57%2FFraming_hammer.jpg%2F1200px-Framing_hammer.jpg&f=1";

            productList.add(productData);
        }

        return productList;
    }

    public ArrayList<UserData> getUsers(Integer id){

        ArrayList<UserData> userList = new ArrayList<>();

        UserData userData = new UserData();

        userData.id = 1;
        userData.first_name = "Florian";
        userData.last_name = "Brandsma";
        userData.image_url = "https://secure.i.telegraph.co.uk/multimedia/archive/03597/POTD_chick_3597497k.jpg";
        userData.email_address = "t7brfl00@students.oamk.fi";
        userData.home_address = "Kotkantie 1";
        userData.phone_number = "+3581234567890";
        userData.card_number = "1234 4567 8910";
        userData.card_date = "01/20";
        userData.card_security = "123";

        userList.add(userData);

        return userList;
    }

    public ImageData getImages(Integer id){

        ImageData imageData = new ImageData();

        imageData.id = 1;
        imageData.url = "https://secure.i.telegraph.co.uk/multimedia/archive/03597/POTD_chick_3597497k.jpg";

        return imageData;
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
