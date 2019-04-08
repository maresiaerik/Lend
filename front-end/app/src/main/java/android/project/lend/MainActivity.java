package android.project.lend;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    //ProductManager productManager = new ProductManager();
    UserManager userManager = new UserManager();
    static public UserDataItem USER;
    BottomNavigationView bottom_nav;
    int endFrag =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        USER = userManager.getUser(1);
        Log.d("USERTEST", USER.getFirstName());

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
                    int startFrag=-1;
                    switch (menuItem.getItemId()) {
                        case R.id.navbar_explore:
                            selected_fragment = new ExploreFragment();
                            startFrag=0;
                            break;
                        case R.id.navbar_lendz:
                            selected_fragment = new LendzFragment();
                            startFrag=1;
                            break;
                        case R.id.navbar_home:
                            selected_fragment = new HomeFragment();
                            startFrag=2;
                            break;
                    }
                    FragmentManager manager = getSupportFragmentManager();
                    manager.popBackStack();
                    FragmentTransaction ft = manager.beginTransaction();
                    if(endFrag==startFrag){
                        ft.replace(R.id.fragment_container, selected_fragment);
                        ft.commit();
                    }
                    else if(endFrag>startFrag) {
                        ft.setCustomAnimations(R.anim.in_left, R.anim.out_right);
                        ft.replace(R.id.fragment_container, selected_fragment);
                        ft.commit();
                    }else {
                        ft.setCustomAnimations(R.anim.in_right, R.anim.out_left);
                        ft.replace(R.id.fragment_container, selected_fragment);
                        ft.commit();
                    }
                    endFrag = startFrag;
                    return true;
                }
            };
}
