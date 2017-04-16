package com.ctinute.foody.Fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.ctinute.foody.Database.DistrictDB;
import com.ctinute.foody.Database.ItemDB;
import com.ctinute.foody.MainActivity;
import com.ctinute.foody.Objects.District;
import com.ctinute.foody.Objects.Street;
import com.ctinute.foody.Objects.WhereItem;
import com.ctinute.foody.R;
import com.ctinute.foody.SelectCityActivity;

import java.util.ArrayList;

public class HomeWhereFragment extends Fragment {

    private boolean useDistrcitFiler;
    private boolean useStreetFilter;
    private int selectedCityId;
    private String selectedCityName;
    private int selectedDistrictId;
    private int selectedStreetId;
    private int selectedTypeFilter;

    SQLiteDatabase database;

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

    TextView textViewTabLocationCurrentCity;

    GridViewMenuAdapter gridViewMenuAdapter;
    ViewPagerSlideAdapter viewPagerSlideAdapter;

    ListView listViewNewest;



    // cac gia tri mau cho grid view menu
    private String[] gridViewMenuLabelList = {"Gần tôi", "Coupon","Đặt chỗ ưu đãi","Đặt giao hàng","E-card","Game & Fun","Bình luận", "Blogs", "Top thành viên", "Video"};
    private int[] gridViewMenuImageList = {R.drawable.ic_nearby,R.drawable.ic_ecoupon,R.drawable.ic_tablenow,R.drawable.ic_deli,R.drawable.ic_ecard,R.drawable.ic_game,R.drawable.ic_comments,R.drawable.ic_blog,R.drawable.ic_topuser,R.drawable.ic_video};
    private int[] viewPagerSlideDrawableList = {R.drawable.slide1,R.drawable.slide2};

    // cac gia tri mau cho type filter (Tab1)
    private String[] listViewNewsestLabelList = {"Mới nhất", "Gần tôi", "Phổ biến", "Du Khách", "Ưu đãi E-Card", "Đặt chỗ", "Ưu đãi thẻ"};
    private int[] listViewNewsestDrawableList = {R.drawable.ic_filter_latest, R.drawable.ic_filter_most_near, R.drawable.ic_filter_top_of_week, R.drawable.ic_filter_tourist ,R.drawable.ic_filter_ecard, R.drawable.ic_filter_most_reservation, R.drawable.ic_filter_bankcard};
    private boolean[] listViewNewsestTagList = {false,false,false,false,false,false,true};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_where, container, false);

        useDistrcitFiler = false;  // loc theo quan/huyen ?
        useStreetFilter = false;   // loc theo duong ?
        selectedCityId = 1;         // mac dinh TP.HCM
        selectedCityName = "TP.HCM";

        layoutInit(view);
        dataInit();
        eventInit(view);

        return view;
    }


    // xu li ket qua tra ve cua activity con
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11){
            // request code tu HomeWhereFragment -> SelectCityActivity
            if (data.getBooleanExtra("isCityChanged",false)){
                useDistrcitFiler = false;
                useStreetFilter = false;
                selectedCityId = data.getIntExtra("selectedCityId",1);  // 1: default value
                selectedCityName = data.getStringExtra("selectedCityName");
                selectedDistrictId = 0;
                selectedStreetId = 0;
                selectedTypeFilter = 0;

                dataInit();
                changeTab(0);
            }
        }
    }


    // khoi tao layout
    private void layoutInit (View view) {
        database = MainActivity.database;

        navBar = (BottomNavigationViewEx) getActivity().getParent().findViewById(R.id.navigation);

        tabHost = (TabHost) view.findViewById(R.id.tabhost);
        tabWidget = tabHost.getTabWidget();
        tabcontent = (FrameLayout) view.findViewById(android.R.id.tabcontent);
        radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup_filter);

        buttonNewest = (RadioButton) view.findViewById(R.id.button_newest);
        buttonCategory = (RadioButton) view.findViewById(R.id.button_category);
        buttonLocation = (RadioButton) view.findViewById(R.id.button_location);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        listViewNewest = (ListView) view.findViewById(R.id.listView_newest);
        listViewDistrict = (ExpandableListView) view.findViewById(R.id.listView_district);

        textViewTabLocationCurrentCity = (TextView) view.findViewById(R.id.tab_location_text_current_city);

        // Tabhost
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

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    // data
    private void dataInit(){
        // Tab 0: RecyclerView chinh
        gridViewMenuAdapter = new GridViewMenuAdapter(getContext(),gridViewMenuLabelList,gridViewMenuImageList);
        viewPagerSlideAdapter = new ViewPagerSlideAdapter(getContext(),viewPagerSlideDrawableList);
        updateWhereItemListData();

        // Tab 1: ListView loc
        ListViewFilterAdapter listViewFilterAdapter = new ListViewFilterAdapter(getContext(), listViewNewsestLabelList, listViewNewsestDrawableList,listViewNewsestTagList);
        listViewNewest.setAdapter(listViewFilterAdapter);
        //TODO: BUG: setSelection not working
        listViewNewest.setSelection(0);

        // Tab 2:

        // Tab 3: Danh sach quan/huyen, duong
        buttonLocation.setText(selectedCityName);
        textViewTabLocationCurrentCity.setText(selectedCityName);
        DistrictDB districtDB = new DistrictDB(this.database);
        ArrayList<District> districtList = districtDB.getDistrictList(selectedCityId);
        ListViewDistrictAdapter listViewDistrictAdapter = new ListViewDistrictAdapter(getContext(),districtList);
        listViewDistrict.setAdapter(listViewDistrictAdapter);
    }


    //
    private void eventInit(View view){
        // TabWidget (chuyen trang)
        buttonNewest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.w("log","button 1");
                changeTab(1);
            }
        });
        buttonCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.w("log","button 2");
                changeTab(2);
            }
        });
        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.w("log","button 3");
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

        // Tab 1
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


        // Tab 3:
        // thay the event mo rong khi nhan -> chon thanh pho
        listViewDistrict.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                useDistrcitFiler = true;
                useStreetFilter = false;
                selectedDistrictId = ((District) (listViewDistrict.getExpandableListAdapter()).getGroup(groupPosition)).getId();
                Log.w("Log","selectedDistrictId change: "+selectedDistrictId);
                updateWhereItemListData();
                changeTab(0);
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
                changeTab(0);
                return true;
            }
        });
        // xu li thay doi tinh/thanh pho
        LinearLayout buttonChangeCity = (LinearLayout) view.findViewById(R.id.button_changeCity);
        buttonChangeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // goi activity thay doi tinh thanh pho
                Intent intent = new Intent(getContext(),SelectCityActivity.class);
                intent.putExtra("selectedCityId",selectedCityId);
                intent.putExtra("selectedCityName",selectedCityName);
                startActivityForResult(intent,11);
            }
        });
    }

    // update du lieu recycler view (Tab 0)
    private void updateWhereItemListData() {
        ArrayList<WhereItem> itemList = null;
        ItemDB itemDB = new ItemDB(database);
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
