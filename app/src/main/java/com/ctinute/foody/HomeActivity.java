package com.ctinute.foody;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;

import com.ctinute.foody.Adapters.ViewPagerAdapter;
import com.ctinute.foody.Fragment.HomeWhatFragment;
import com.ctinute.foody.Fragment.HomeWhereFragment;

public class HomeActivity extends AppCompatActivity {

    RadioButton buttonWhere;
    RadioButton buttonWhat;

    ViewPager viewPagerMain;
    ViewPagerAdapter viewPagerMainAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar appbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        buttonWhere = (RadioButton) findViewById(R.id.button_where);
        buttonWhat = (RadioButton) findViewById(R.id.button_what);

        // khoi tao pager home
        viewPagerMain = (ViewPager) findViewById(R.id.viewPager_home);
        viewPagerMainAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerMainAdapter.addFragment(new HomeWhereFragment(),"where");
        viewPagerMainAdapter.addFragment(new HomeWhatFragment(),"what");
        viewPagerMain.setAdapter(viewPagerMainAdapter);

        viewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                switch (position){
                    case 0:
                        buttonWhere.setChecked(true);
                        break;
                    case 1:
                        buttonWhat.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {}
        });

        buttonWhere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewPagerMain.setCurrentItem(0,true);
            }
        });
        buttonWhat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewPagerMain.setCurrentItem(1,true);
            }
        });


        // cac gia tri mac dinh
        buttonWhere.setChecked(true);
        viewPagerMain.setCurrentItem(0);
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
}
