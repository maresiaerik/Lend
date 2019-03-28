package android.project.lend;

import java.util.ArrayList;

public class UserManager extends Helper {

    private ArrayList<UserData> userList;

    public ArrayList<UserDataItem> getDataList(Integer id){

        ArrayList<UserDataItem> userDataItemList = new ArrayList<>();

        userList = getUserData(id);

        for (UserData user : userList) {

            UserDataItem user_data_item = new UserDataItem();

            user_data_item.setId(user.id);
            user_data_item.setFirstName(user.first_name);
            user_data_item.setLastName(user.last_name);
            user_data_item.setImageUrl(user.image_url);
            user_data_item.setEmailAddress(user.email_address);
            user_data_item.setHomeAddress(user.home_address);
            user_data_item.setPhoneNumber(user.phone_number);
            user_data_item.setCardNumber(user.card_number);
            user_data_item.setCardDate(user.card_date);
            user_data_item.setCardSecurity(user.card_security);

            user_data_item.clearChanges();

            userDataItemList.add(user_data_item);
        }

        return userDataItemList;
    }
}
