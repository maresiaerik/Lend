package android.project.lend;

import java.util.ArrayList;

public class UserManager extends Helper implements IManager {

    private IDataController dataController;
    public Boolean loaded = false;

    private ArrayList<UserData> userList = new ArrayList<>();

    private IManager parentManager;

    public UserManager(){}

    public UserManager(IDataController dataController, IManager parentManager){

        this.dataController = dataController;
        this.parentManager = parentManager;

        getUserData(this, userList);
    }

    public ArrayList<UserDataItem> getUserList(){

        ArrayList<UserDataItem> userDataItemList = new ArrayList<>();

        for (UserData user : userList) {

            UserDataItem userDataItem = setUser(user);

            userDataItemList.add(userDataItem);
        }

        return userDataItemList;
    }

    public UserDataItem getUser(Integer id){

        for(UserData user : userList){

            if(user.id == id)
                return setUser(user);
        }

        return null;
    }

    public void addUser(){

    }

    private UserDataItem setUser(UserData user)
    {
        UserDataItem userDataItem = new UserDataItem();

        userDataItem.setId(user.id);
        userDataItem.setFirstName(user.firstName);
        userDataItem.setLastName(user.lastName);
        userDataItem.setImageUrl(user.imageUrl);
        userDataItem.setEmailAddress(user.emailAddress);
        userDataItem.setHomeAddress(user.homeAddress);
        userDataItem.setPhoneNumber(user.phoneNumber);
        userDataItem.setCardNumber(user.cardNumber);
        userDataItem.setCardDate(user.cardDate);
        userDataItem.setCardSecurity(user.cardSecurity);

        userDataItem.clearChanges();

        return userDataItem;
    }

    @Override
    public void setLoaded(Boolean loaded) {

        this.loaded = loaded;

        if(parentManager != null)
            parentManager.checkStatus();
        else
            checkStatus();
    }

    @Override
    public void checkStatus() {

        if(!loaded) return;

        dataController.setData();
    }
}
