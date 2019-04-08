package android.project.lend;

import android.support.v4.app.Fragment;

public class FragmentHandler {

    private static int currentFragment;

    public static Fragment getCurrentFragment() {
        switch (currentFragment) {
            case 0:
                return new ExploreFragment();
            case 1:
                return new LendzFragment();
            case 2:
                return new HomeFragment();
        }
        return null;
    }

    public static void setCurrentFragment(int newFragment) {
        currentFragment = newFragment;
    }

    public static int getCurrentFragmentId() {
        switch (currentFragment) {
            case 0:
                return R.id.navbar_explore;
            case 1:
                return R.id.navbar_lendz;
            case 2:
                return R.id.navbar_home;
        }
        return currentFragment;
    }
}
