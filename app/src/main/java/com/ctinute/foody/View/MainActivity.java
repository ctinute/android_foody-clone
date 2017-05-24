package com.ctinute.foody.View;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.ctinute.foody.Object.User;
import com.ctinute.foody.R;
import com.ctinute.foody.View.CustomView.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends TabActivity {

    TabHost tabHostMain;
    public static User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        // khoi tao thanh dieu huong duoi
        BottomNavigationViewEx navigation = (BottomNavigationViewEx) findViewById(R.id.navigation);
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setTextVisibility(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        tabHostMain = getTabHost();
        //tabHostMain.setup();

        TabSpec tab1 = tabHostMain.newTabSpec("Home");
        TabSpec tab2 = tabHostMain.newTabSpec("Collection");
        TabSpec tab3 = tabHostMain.newTabSpec("Search");
        TabSpec tab4 = tabHostMain.newTabSpec("Notification");
        TabSpec tab5 = tabHostMain.newTabSpec("Account");

        tab1.setIndicator("Tab1");
        tab1.setContent(new Intent(this,HomeActivity.class));

        tab2.setIndicator("Tab2");
        tab2.setContent(new Intent(this,CollectionActivity.class));

        tab3.setIndicator("Tab3");
        tab3.setContent(new Intent(this,SearchActivity.class));

        tab4.setIndicator("Tab4");
        tab4.setContent(new Intent(this,NotificationActivity.class));

        tab5.setIndicator("Tab5");
        tab5.setContent(new Intent(this,AccountActivity.class));

        tabHostMain.addTab(tab1);
        tabHostMain.addTab(tab2);
        tabHostMain.addTab(tab3);
        tabHostMain.addTab(tab4);
        tabHostMain.addTab(tab5);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    tabHostMain.setCurrentTab(0);
                    return true;
                case R.id.navigation_collection:
                    tabHostMain.setCurrentTab(1);
                    return true;
                case R.id.navigation_search:
                    tabHostMain.setCurrentTab(2);
                    return true;
                case R.id.navigation_notification:
                    tabHostMain.setCurrentTab(3);
                    return true;
                case R.id.navigation_account:
                    tabHostMain.setCurrentTab(4);
                    return true;
            }
            return false;
        }

    };
}
