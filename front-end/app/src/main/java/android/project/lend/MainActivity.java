package android.project.lend;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IDataController {

    //ProductManager productManager = new ProductManager();
    UserManager userManager;
    static public UserDataItem USER;
    static public String[] PRODUCT_STATUS = new String[] {"Available","Lendzed","Returned"};

    public static Context mainActivityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.mainActivityContext = getApplicationContext();

        userManager = new UserManager(this, null);

        ProductDataItem productDataItem = new ProductDataItem();

        productDataItem.create();

        productDataItem.setName("MyName");

        productDataItem.update();

        BottomNavigationView bottom_nav = findViewById(R.id.bottom_navigation);

        bottom_nav.setOnNavigationItemSelectedListener(nav_listener);

        bottom_nav.setSelectedItemId(FragmentManager.getCurrentFragmentId());

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FragmentManager.getCurrentFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener nav_listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selected_fragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.navbar_explore:
                            selected_fragment = new ExploreFragment();
                            break;
                        case R.id.navbar_lendz:
                            selected_fragment = new LendzFragment();
                            break;
                        case R.id.navbar_home:
                            selected_fragment = new HomeFragment();
                            break;
                    }
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selected_fragment).commit();
                    return true;
                }
            };

    @Override
    public void setData() {

        Log.d("TESTRESPONSE","Logged in");

        USER = userManager.getUser(1);
    }
}
