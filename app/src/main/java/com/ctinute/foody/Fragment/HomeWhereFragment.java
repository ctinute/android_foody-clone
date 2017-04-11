package com.ctinute.foody.Fragment;

import android.app.ExpandableListActivity;
import android.content.ClipData;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.ctinute.foody.Adapters.GridViewMenuAdapter;
import com.ctinute.foody.Adapters.ListViewDistrictAdapter;
import com.ctinute.foody.Adapters.ListViewFilterAdapter;
import com.ctinute.foody.Adapters.RecyclerViewAdapter;
import com.ctinute.foody.Adapters.ViewPagerSlideAdapter;
import com.ctinute.foody.CustomView.BottomNavigationViewEx;
import com.ctinute.foody.Database.DistrictDBHelper;
import com.ctinute.foody.Database.ItemDB;
import com.ctinute.foody.MainActivity;
import com.ctinute.foody.Objects.District;
import com.ctinute.foody.Objects.Street;
import com.ctinute.foody.Objects.WhereItem;
import com.ctinute.foody.R;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class HomeWhereFragment extends Fragment {

    private boolean useDistrcitFiler = false;  // loc theo quan/huyen ?
    private boolean useStreetFilter = false;   // loc theo duong ?
    private int selectedCityId = 1;         // mac dinh TP.HCM
    private int selectedDistrictId;
    private int selectedStreetId;
    private int selectedTypeFilter;

    TabHost tabHost;
    TabWidget tabWidget;
    FrameLayout tabcontent;

    RadioGroup radioGroup;
    RadioButton buttonNewest;
    RadioButton buttonCategory;
    RadioButton buttonLocation;
    BottomNavigationViewEx navBar;
    ExpandableListView listViewDistrict;
    RecyclerView recyclerView;

    GridViewMenuAdapter gridViewMenuAdapter;
    ViewPagerSlideAdapter viewPagerSlideAdapter;

    // cac gia tri cho grid view menu
    private String[] gridViewMenuLabelList = {"Gần tôi", "Coupon","Đặt chỗ ưu đãi","Đặt giao hàng","E-card","Game & Fun","Bình luận", "Blogs", "Top thành viên", "Video"};
    private int[] gridViewMenuImageList = {R.drawable.ic_nearby,R.drawable.ic_ecoupon,R.drawable.ic_tablenow,R.drawable.ic_deli,R.drawable.ic_ecard,R.drawable.ic_game,R.drawable.ic_comments,R.drawable.ic_blog,R.drawable.ic_topuser,R.drawable.ic_video};
    private int[] viewPagerSlideDrawableList = {R.drawable.slide1,R.drawable.slide2};

    // cac gia tri cho type filter (Tab1)
    private String[] listViewNewsestLabelList = {"Mới nhất", "Gần tôi", "Phổ biến", "Du Khách", "Ưu đãi E-Card", "Đặt chỗ", "Ưu đãi thẻ"};
    private int[] listViewNewsestDrawableList = {R.drawable.ic_filter_latest, R.drawable.ic_filter_most_near, R.drawable.ic_filter_top_of_week, R.drawable.ic_filter_tourist ,R.drawable.ic_filter_ecard, R.drawable.ic_filter_most_reservation, R.drawable.ic_filter_bankcard};
    private boolean[] listViewNewsestTagList = {false,false,false,false,false,false,true};



    public HomeWhereFragment() {
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

        //
        tabWidget = tabHost.getTabWidget();
        tabcontent = (FrameLayout) view.findViewById(android.R.id.tabcontent);
        navBar = (BottomNavigationViewEx) getActivity().getParent().findViewById(R.id.navigation);
        radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup_filter);
        buttonNewest = (RadioButton) view.findViewById(R.id.button_newest);
        buttonCategory = (RadioButton) view.findViewById(R.id.button_category);
        buttonLocation = (RadioButton) view.findViewById(R.id.button_location);

        // Chuyen trang khi nhan cac nut phia tren
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
        // tro ve trang chinh khi nhan cac nut huy
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



        // TAB: mac dinh
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        // do du lieu, tao view thong qua adapter
        gridViewMenuAdapter = new GridViewMenuAdapter(getContext(),gridViewMenuLabelList,gridViewMenuImageList);
        viewPagerSlideAdapter = new ViewPagerSlideAdapter(getContext(),viewPagerSlideDrawableList);
        updateWhereItemListData();


        // TAB: moi nhat
        final ListView listViewNewest = (ListView) view.findViewById(R.id.listView_newest);
        ListViewFilterAdapter listViewFilterAdapter = new ListViewFilterAdapter(getContext(), listViewNewsestLabelList, listViewNewsestDrawableList,listViewNewsestTagList);
        listViewNewest.setAdapter(listViewFilterAdapter);
        // event - chon filter
        listViewNewest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Nothing selected", Toast.LENGTH_SHORT).show();
            }
        });
        listViewNewest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.w("log","Listview filter selected index: "+ position);
                // dieu chinh hien thi cho selected item cu
                View selectedItemViewOld = listViewNewest.getAdapter().getView(selectedTypeFilter,view,parent);
                ImageView iconOld = (ImageView) selectedItemViewOld.findViewById(R.id.listView_filter_item_icon);
                TextView labelOld = (TextView) selectedItemViewOld.findViewById(R.id.listView_filter_item_text);
                //TODO: BUG: set color not working
                iconOld.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorBackgroundDarkerr));
                labelOld.setTextColor(ContextCompat.getColor(getContext(),R.color.textColorMain));
                // dieu chinh hien thi cho selected item moi
                View selectedItemView = listViewNewest.getAdapter().getView(position,view,parent);
                ImageView icon = (ImageView) selectedItemView.findViewById(R.id.listView_filter_item_icon);
                TextView label = (TextView) selectedItemView.findViewById(R.id.listView_filter_item_text);
                icon.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorAccent));
                label.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                listViewNewest.setSelection(position);
                selectedTypeFilter = position;
                // cap nhat tab label
                buttonNewest.setText(label.getText());
                // TODO: cap nhat filter danh sach quan
                Toast.makeText(getContext(), "Tính năng chưa hoàn thiện", Toast.LENGTH_SHORT).show();
                changeTab(0);
            }
        });
        //TODO: BUG: setSelection not working
        listViewNewest.setSelection(0);


        // TAB: danh sach tanh pho:
        listViewDistrict = (ExpandableListView) view.findViewById(R.id.listView_district);
        DistrictDBHelper districtDBHelper = new DistrictDBHelper(MainActivity.database);
        ArrayList<District> districtList = districtDBHelper.getDistrictList(1);
        final ListViewDistrictAdapter listViewDistrictAdapter = new ListViewDistrictAdapter(getContext(),districtList);
        listViewDistrict.setAdapter(listViewDistrictAdapter);

        // thay the event mo rong khi nhan -> chon thanh pho
        listViewDistrict.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                useDistrcitFiler = true;
                useStreetFilter = false;
                selectedDistrictId = ((District) (listViewDistrict.getExpandableListAdapter()).getGroup(groupPosition)).getId();
                Log.w("Log","selectedDistrictId change: "+selectedDistrictId);
                updateWhereItemListData();
                return true;
            }
        });
        listViewDistrict.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                useDistrcitFiler = false;
                useStreetFilter = true;
                selectedStreetId = ((Street) (listViewDistrict.getExpandableListAdapter()).getChild(groupPosition,childPosition)).getId();
                Log.w("Log","selectedStreetId change: "+selectedStreetId);
                updateWhereItemListData();
                return true;
            }
        });
        return view;
    }

    // update du lieu recycler view
    private void updateWhereItemListData() {
        ArrayList<WhereItem> itemList = null;
        ItemDB itemDB = new ItemDB(MainActivity.database);
        if(!useDistrcitFiler && !useStreetFilter){
            itemList = itemDB.getItemList(selectedCityId, 0);
        }
        else {
            if(useStreetFilter){
                itemList = itemDB.getItemList(selectedStreetId, 2);
                useDistrcitFiler = false;
            }
            if (useDistrcitFiler) {
                itemList = itemDB.getItemList(selectedDistrictId, 1);
                useStreetFilter = false;
            }
        }
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),itemList,gridViewMenuAdapter,viewPagerSlideAdapter);
        recyclerView.setAdapter(recyclerViewAdapter);
        changeTab(0);
        // tweaks cho recycler view
        recyclerView.setHasFixedSize(true);
        //recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        //recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }


    // an hien nav bar
    private void showNav(){
        navBar.setVisibility(View.VISIBLE);
    }
    private void hideNav(){
        navBar.setVisibility(View.GONE);
    }

    // thay doi tab page
    private void changeTab(int tabIndex) {
        Log.w("log","Current tab: "+tabHost.getCurrentTab()+"\nIndex: "+tabIndex);
        if (tabIndex == 0){
            showNav();
            tabHost.setCurrentTab(0);
            radioGroup.clearCheck();
        }
        else {
            if (tabHost.getCurrentTab() == tabIndex) {
                // dong filter tab -> main tab
                showNav();
                tabHost.setCurrentTab(0);
                radioGroup.clearCheck();
            } else {
                // mo filter tab
                hideNav();
                tabHost.setCurrentTab(tabIndex);
            }
        }
    }

}
