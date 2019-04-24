package android.project.lend;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements IDataController {

    //ProductManager productManager = new ProductManager();
    static UserManager userManager;
    static public UserDataItem USER;
    BottomNavigationView bottom_nav;
    int endFrag = -1;
    static public String[] PRODUCT_STATUS = new String[]{"Available", "Lendzed", "Returned"};
    static public String BASE_URL = "https://lend-app.herokuapp.com/";
    Dialog dialog;

    public static Context mainActivityContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dialog = new Dialog(this, R.style.LoadingDialog);
        dialog.setContentView(R.layout.loading);
        dialog.setCancelable(false);
        dialog.show();

        MainActivity.mainActivityContext = getApplicationContext();

        userManager = new UserManager(this, null);

        ProductDataItem productDataItem = new ProductDataItem();

        productDataItem.create();

        productDataItem.setName("MyName");

        productDataItem.update();

        bottom_nav = findViewById(R.id.bottom_navigation);

        bottom_nav.setOnNavigationItemSelectedListener(nav_listener);

        bottom_nav.setSelectedItemId(FragmentHandler.getCurrentFragmentId());

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FragmentHandler.getCurrentFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener nav_listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selected_fragment = null;
                    int startFrag = -1;
                    switch (menuItem.getItemId()) {
                        case R.id.navbar_explore:
                            selected_fragment = new ExploreFragment();
                            startFrag = 0;
                            break;
                        case R.id.navbar_lendz:
                            selected_fragment = new LendzFragment();
                            startFrag = 1;
                            break;
                        case R.id.navbar_home:
                            selected_fragment = new HomeFragment();
                            startFrag = 2;
                            break;
                    }
                    FragmentManager manager = getSupportFragmentManager();
                    manager.popBackStack();
                    FragmentTransaction ft = manager.beginTransaction();
                    if (endFrag == startFrag) {
                        return true;
                    } else if (endFrag > startFrag) {
                        ft.setCustomAnimations(R.anim.in_left, R.anim.out_right);
                        ft.replace(R.id.fragment_container, selected_fragment);
                        ft.commit();
                    } else {
                        if (endFrag >= 0 && ( USER == null || USER.getId() == null)) {
                            loginRequired();
                            return false;
                        } else {
                            ft.setCustomAnimations(R.anim.in_right, R.anim.out_left);
                            ft.replace(R.id.fragment_container, selected_fragment);
                            ft.commit();
                        }
                    }
                    endFrag = startFrag;
                    return true;
                }
            };

    private void loginRequired() {
        Intent login = new Intent(this, HomeUserLoginActivity.class);
        startActivity(login);
    }

    @Override
    public void setData() {
        SharedPreferences data = getSharedPreferences("USER", 0);

        if(data.contains("id")) {
            Helper helper = new Helper();
            Helper.UserData user = helper.new UserData(
                    data.getInt("id", 0),
                    data.getString("firstname",""),
                    data.getString("lastname",""),
                    data.getString("url",""),
                    data.getString("email",""),
                    data.getString("addressET",""),
                    data.getString("phone",""),
                    data.getString("card_num",""),
                    data.getString("card_date",""),
                    data.getString("card_sec",""),
                    data.getString("passwordET","")
            );

            MainActivity.USER = new UserDataItem();
            MainActivity.USER.create(user);
        }
        dialog.dismiss();
    }

}

