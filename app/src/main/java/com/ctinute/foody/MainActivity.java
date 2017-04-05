package com.ctinute.foody;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.support.v4.view.ViewPager;

import com.ctinute.foody.Adapters.ViewPagerAdapter;
import com.ctinute.foody.CustomView.BottomNavigationViewEx;

import com.ctinute.foody.Fragment.HomeWhatFragment;
import com.ctinute.foody.Fragment.HomeWhereFragment;

public class MainActivity extends FragmentActivity {

    private static final int NUM_MAIN_PAGES = 2;
    ViewPager viewPagerMain;
    ViewPagerAdapter viewPagerMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar appbar = (Toolbar) findViewById(R.id.appbar);
        //setSupportActionBar(appbar);

        // khoi tao thanh dieu huong duoi
        BottomNavigationViewEx navigation = (BottomNavigationViewEx) findViewById(R.id.navigation);
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setTextVisibility(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // khoi tao pager home
        viewPagerMain = (ViewPager) findViewById(R.id.viewPager_home);
        viewPagerMainAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerMainAdapter.addFragment(new HomeWhereFragment(),"where");
        viewPagerMainAdapter.addFragment(new HomeWhatFragment(),"what");
        viewPagerMain.setAdapter(viewPagerMainAdapter);
    }

    @Override
    public void onBackPressed() {
        if (viewPagerMain.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPagerMain.setCurrentItem(viewPagerMain.getCurrentItem() - 1);
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.str_nav_home);
                    return true;
                case R.id.navigation_collection:
                    //mTextMessage.setText(R.string.str_nav_collection);
                    return true;
                case R.id.navigation_search:
                   // mTextMessage.setText(R.string.str_nav_search);
                    return true;
                case R.id.navigation_notification:
                   // mTextMessage.setText(R.string.str_nav_notification);
                    return true;
                case R.id.navigation_account:
                    //mTextMessage.setText(R.string.str_nav_account);
                    return true;
            }
            return false;
        }

    };

}
