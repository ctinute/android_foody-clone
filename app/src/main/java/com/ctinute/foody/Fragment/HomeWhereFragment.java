package com.ctinute.foody.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.ctinute.foody.Adapters.GridViewMenuAdapter;
import com.ctinute.foody.Adapters.ListViewDistrictAdapter;
import com.ctinute.foody.Adapters.RecyclerViewAdapter;
import com.ctinute.foody.Adapters.ViewPagerSlideAdapter;
import com.ctinute.foody.CustomView.BottomNavigationViewEx;
import com.ctinute.foody.CustomView.ExpandableHeightGridView;
import com.ctinute.foody.CustomView.ExpandableHeightListView;
import com.ctinute.foody.Database.DistrictDBHelper;
import com.ctinute.foody.Database.ItemDB;
import com.ctinute.foody.MainActivity;
import com.ctinute.foody.Objects.District;
import com.ctinute.foody.R;

import java.util.ArrayList;

import static android.view.View.GONE;

public class HomeWhereFragment extends Fragment {

    TabHost tabHost;
    TabWidget tabWidget;
    FrameLayout tabcontent;

    RadioGroup radioGroup;
    RadioButton buttonNewest;
    RadioButton buttonCategory;
    RadioButton buttonLocation;

    BottomNavigationViewEx navBar;

    private String[] gridViewLabelList = {
            "Gần tôi", "Coupon","Đặt chỗ ưu đãi","Đặt giao hàng","E-card","Game & Fun","Bình luận", "Blogs", "Top thành viên", "Video"};
    private int[] gridViewImageList = {
            R.drawable.ic_nearby,R.drawable.ic_ecoupon,R.drawable.ic_tablenow,R.drawable.ic_deli,R.drawable.ic_ecard,R.drawable.ic_game,R.drawable.ic_comments,R.drawable.ic_blog,R.drawable.ic_topuser,R.drawable.ic_video};

    private int[] viewPagerSlideDrawableList = {R.drawable.slide1,R.drawable.slide2};

    private ExpandableHeightGridView gridViewMenu;
    private ExpandableHeightListView listViewItem;

    ExpandableListView listViewDistrict;

    public HomeWhereFragment() {
    }

    public static HomeWhereFragment newInstance() {
        return new HomeWhereFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_where, container, false);

        tabHost = (TabHost) view.findViewById(R.id.tabhost);
        tabHost.setup();

        //Tab mac dinh
        TabHost.TabSpec spec = tabHost.newTabSpec("Tab default");
        spec.setContent(R.id.tab_default);
        spec.setIndicator("Tab default");
        tabHost.addTab(spec);

        //Tab moi nhat
        spec = tabHost.newTabSpec("Tab newest");
        spec.setContent(R.id.tab_newest);
        spec.setIndicator("Tab newest");
        tabHost.addTab(spec);

        // tab danh muc
        spec = tabHost.newTabSpec("Tab category");
        spec.setContent(R.id.tab_category);
        spec.setIndicator("Tab category");
        tabHost.addTab(spec);

        // tab dia diem
        spec = tabHost.newTabSpec("Tab location");
        spec.setContent(R.id.tab_location);
        spec.setIndicator("Tab location");
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);


        tabWidget = tabHost.getTabWidget();
        tabcontent = (FrameLayout) view.findViewById(android.R.id.tabcontent);
        navBar = (BottomNavigationViewEx) getActivity().getParent().findViewById(R.id.navigation);
        radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup_filter);
        buttonNewest = (RadioButton) view.findViewById(R.id.button_newest);
        buttonCategory = (RadioButton) view.findViewById(R.id.button_category);
        buttonLocation = (RadioButton) view.findViewById(R.id.button_location);

        addEvent();

        // TAB: mac dinh
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ItemDB itemDB = new ItemDB(MainActivity.database);
        LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        GridViewMenuAdapter gridViewMenuAdapter = new GridViewMenuAdapter(getContext(),gridViewLabelList,gridViewImageList);
        ViewPagerSlideAdapter viewPagerSlideAdapter = new ViewPagerSlideAdapter(getContext(),viewPagerSlideDrawableList);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),itemDB.getItemList(),gridViewMenuAdapter,viewPagerSlideAdapter);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setHasFixedSize(true);
        //recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        //recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        // TAB: danh sach tanh pho:
        listViewDistrict = (ExpandableListView) view.findViewById(R.id.listView_district);
        DistrictDBHelper districtDBHelper = new DistrictDBHelper(MainActivity.database);
        ArrayList<District> districtList = districtDBHelper.getDistrictList(1);
        ListViewDistrictAdapter listViewDistrictAdapter = new ListViewDistrictAdapter(getContext(),districtList);
        listViewDistrict.setAdapter(listViewDistrictAdapter);

        // thay the event mo rong khi nhan
        listViewDistrict.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // TODO: chon quan/huyen
                return true;
            }
        });


        Button buttonCancel1 = (Button) view.findViewById(R.id.button_cancel1);
        buttonCancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(0);
            }
        });
        Button buttonCancel2 = (Button) view.findViewById(R.id.button_cancel2);
        buttonCancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(0);
            }
        });
        Button buttonCancel3 = (Button) view.findViewById(R.id.button_cancel3);
        buttonCancel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(0);
            }
        });

        return view;
    }


    private void hideNav(){
        navBar.setVisibility(View.VISIBLE);
    }
    private void showNav(){
        navBar.setVisibility(GONE);
    }

    //change state tab with id is a current tab
    private void changeTab(int tabIndex) {
        Log.w("log","Current tab: "+tabHost.getCurrentTab()+"\nIndex: "+tabIndex);
        if (tabHost.getCurrentTab() == tabIndex) {
            // dong filter tab -> main tab
            hideNav();
            tabHost.setCurrentTab(0);
            radioGroup.clearCheck();
        } else {
            // mo filter tab
            showNav();
            tabHost.setCurrentTab(tabIndex);
        }
    }

    //setting for click tab widget
    private void addEvent() {
        buttonNewest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("log","button 1");
                changeTab(1);
            }
        });

        buttonCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("log","button 2");
                changeTab(2);
            }
        });

        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("log","button 3");
                changeTab(3);
            }
        });
    }
}
