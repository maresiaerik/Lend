package android.project.lend;

import java.util.ArrayList;

public class UserManager extends Helper {

    private ArrayList<UserData> userList;

    private void getUserData(Integer id){

        userList = getUsers(id);
    }

    private UserDataItem getUser(Integer id){

        ArrayList<UserDataItem> userDataItemList = new ArrayList<>();
        UserDataItem userDataItem = new UserDataItem();

        getUserData(id);

        for (UserData user : userList) {

            userDataItem.setId(user.id);
            userDataItem.setFirst_name(user.first_name);
            userDataItem.setLast_name(user.last_name);
            userDataItem.setImage_url(user.image_url);
            userDataItem.setEmail_address(user.email_address);
            userDataItem.setHome_address(user.home_address);
            userDataItem.setPhone_number(user.phone_number);
            userDataItem.setCard_number(user.card_number);
            userDataItem.setCard_date(user.card_date);
            userDataItem.setCard_security(user.card_security);

            userDataItem.clearChanges();
        }

        return userDataItem;
    }
}
