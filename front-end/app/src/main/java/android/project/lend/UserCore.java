package android.project.lend;

import android.util.Log;

public class UserCore {

    private Integer id;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private String emailAddress;
    private String homeAddress;
    private String phoneNumber;
    private String cardNumber;
    private String cardDate;
    private String cardSecurity;

    public Boolean changed;
    public Boolean changedFirstName;
    public Boolean changedLastName;
    public Boolean changedImageUrl;
    public Boolean changedEmailAddress;
    public Boolean changedHomeAddress;
    public Boolean changedPhoneNumber;
    public Boolean changedCardNumber;
    public Boolean changedCardDate;
    public Boolean changedCardSecurity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {

        if(this.firstName == firstName) return;

        changed = true;
        changedFirstName = true;

        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {

        if(this.lastName == lastName) return;

        changed = true;
        changedLastName = true;

        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {

        if(this.imageUrl == imageUrl) return;

        changed = true;
        changedImageUrl = true;

        this.imageUrl = imageUrl;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {

        if(this.emailAddress == emailAddress) return;

        changed = true;
        changedEmailAddress = true;

        this.emailAddress = emailAddress;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {

        if(this.homeAddress == homeAddress) return;

        changed = true;
        changedHomeAddress = true;

        this.homeAddress = homeAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {

        if(this.phoneNumber == phoneNumber) return;

        changed = true;
        changedPhoneNumber = true;

        this.phoneNumber = phoneNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {

        if(this.cardNumber == cardNumber) return;

        changed = true;
        changedCardNumber = true;

        this.cardNumber = cardNumber;
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {

        if(this.cardDate == cardDate) return;

        changed = true;
        changedCardNumber = true;

        this.cardDate = cardDate;
    }

    public String getCardSecurity() {
        return cardSecurity;
    }

    public void setCardSecurity(String cardSecurity) {

        if(this.cardSecurity == cardSecurity) return;

        changed = true;
        changedCardSecurity = true;

        this.cardSecurity = cardSecurity;
    }

    public void create()
    {

    }

    public void update()
    {
        if(!changed) return;

        Log.d("USERCHANGED", "Something was changed!");

        if(changedFirstName){}
        if(changedLastName){}
        if(changedEmailAddress){}
        if(changedHomeAddress){}
        if(changedPhoneNumber){}
        if(changedCardNumber){}
        if(changedCardDate){}
        if(changedCardSecurity){}
        if(changedImageUrl){}

        clearChanges();
    }

    public void delete()
    {
        Log.d("USERDELETED", this.firstName + " was deleted");
    }

    public void clearChanges()
    {
        changed = false;
        changedFirstName = false;
        changedLastName = false;
        changedEmailAddress = false;
        changedHomeAddress = false;
        changedPhoneNumber = false;
        changedCardNumber = false;
        changedCardDate = false;
        changedCardSecurity = false;
        changedImageUrl = false;
    }
}
