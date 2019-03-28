package android.project.lend;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    //ProductManager productManager = new ProductManager();
    UserManager userManager = new UserManager();
    static public UserDataItem USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        USER = userManager.getDataList(1).stream().findFirst().get();
        Log.d("USERTEST", USER.getFirstName());

        ProductDataItem productDataItem = new ProductDataItem();

        productDataItem.create();

        productDataItem.setName("MyName");

        productDataItem.update();

        BottomNavigationView bottom_nav = findViewById(R.id.bottom_navigation);

        bottom_nav.setOnNavigationItemSelectedListener(nav_listener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExploreFragment()).commit();
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selected_fragment).commit();
                    return true;
                }
            };
}
