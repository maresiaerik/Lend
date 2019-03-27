package android.project.lend;

import android.util.Log;

public class UserCore {

    private Integer id;
    private String first_name;
    private String last_name;
    private String image_url;
    private String email_address;
    private String home_address;
    private String phone_number;
    private String card_number;
    private String card_date;
    private String card_security;

    public Boolean changed;
    public Boolean changed_first_name;
    public Boolean changed_last_name;
    public Boolean changed_image_url;
    public Boolean changed_email_address;
    public Boolean changed_home_address;
    public Boolean changed_phone_number;
    public Boolean changed_card_number;
    public Boolean changed_card_date;
    public Boolean changed_card_security;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {

        if(this.first_name == first_name) return;

        changed = true;
        changed_first_name = true;

        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {

        if(this.last_name == last_name) return;

        changed = true;
        changed_last_name = true;

        this.last_name = last_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {

        if(this.image_url == image_url) return;

        changed = true;
        changed_image_url = true;

        this.image_url = image_url;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {

        if(this.email_address == email_address) return;

        changed = true;
        changed_email_address = true;

        this.email_address = email_address;
    }

    public String getHome_address() {
        return home_address;
    }

    public void setHome_address(String home_address) {

        if(this.home_address == home_address) return;

        changed = true;
        changed_home_address = true;

        this.home_address = home_address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {

        if(this.phone_number == phone_number) return;

        changed = true;
        changed_phone_number = true;

        this.phone_number = phone_number;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {

        if(this.card_number == card_number) return;

        changed = true;
        changed_card_number = true;

        this.card_number = card_number;
    }

    public String getCard_date() {
        return card_date;
    }

    public void setCard_date(String card_date) {

        if(this.card_date == card_date) return;

        changed = true;
        changed_card_number = true;

        this.card_date = card_date;
    }

    public String getCard_security() {
        return card_security;
    }

    public void setCard_security(String card_security) {

        if(this.card_security == card_security) return;

        changed = true;
        changed_card_security = true;

        this.card_security = card_security;
    }

    public void create()
    {

    }

    public void update()
    {
        if(!changed) return;

        Log.d("USERCHANGED", "Something was changed!");

        if(changed_first_name){}
        if(changed_last_name){}
        if(changed_email_address){}
        if(changed_home_address){}
        if(changed_phone_number){}
        if(changed_card_number){}
        if(changed_card_date){}
        if(changed_card_security){}
        if(changed_image_url){}

        clearChanges();
    }

    public void delete()
    {
        Log.d("USERDELETED", this.first_name + " was deleted");
    }

    public void clearChanges()
    {
        changed = false;
        changed_first_name = false;
        changed_last_name = false;
        changed_email_address = false;
        changed_home_address = false;
        changed_phone_number = false;
        changed_card_number = false;
        changed_card_date = false;
        changed_card_security = false;
        changed_image_url = false;
    }
}
