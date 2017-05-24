package com.ctinute.foody.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.ctinute.foody.Object.Category;
import com.ctinute.foody.Object.District;
import com.ctinute.foody.Object.ItemWhere;
import com.ctinute.foody.Object.Street;
import com.ctinute.foody.Object.Type;
import com.ctinute.foody.R;
import com.ctinute.foody.View.Adapters.GridViewMenuAdapter;
import com.ctinute.foody.View.Adapters.ListViewCategoryAdapter;
import com.ctinute.foody.View.Adapters.ListViewDistrictAdapter;
import com.ctinute.foody.View.Adapters.ListViewTypeAdapter;
import com.ctinute.foody.View.Adapters.RecyclerViewWhereAdapter;
import com.ctinute.foody.View.Adapters.ViewPagerSlideAdapter;
import com.ctinute.foody.View.CustomView.BottomNavigationViewEx;
import com.ctinute.foody.View.Listener.EndlessRecyclerOnScrollListener;
import com.ctinute.foody.View.Listener.ImageLoaderListener;
import com.ctinute.foody.View.SelectCityActivity;
import com.ctinute.foody.Webservice.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class HomeWhereFragment extends Fragment {

    private String selectedCityName;
    private int selectedCityId;
    private int selectedDistrictId;
    private int selectedStreetId;
    private int selectedCategoryId;


    TabHost tabHost;
    TabWidget tabWidget;
    FrameLayout tabcontent;

    RadioGroup radioGroup;
    RadioButton buttonFilter;
    RadioButton buttonCategory;
    RadioButton buttonLocation;
    BottomNavigationViewEx navBar;
    ExpandableListView listViewDistrict;
    RecyclerView recyclerView;


    TextView textViewTabLocationCurrentCity;

    GridViewMenuAdapter gridViewMenuAdapter;
    ViewPagerSlideAdapter viewPagerSlideAdapter;

    ListView listViewFilter;
    ListView listViewCategory;



    // cac gia tri mau cho grid view menu
    private String[] gridViewMenuLabelList = {"Gần tôi", "Coupon","Đặt chỗ ưu đãi","Đặt giao hàng","E-card","Game & Fun","Bình luận", "Blogs", "Top thành viên", "Video"};
    private int[] gridViewMenuImageList = {R.drawable.ic_nearby,R.drawable.ic_ecoupon,R.drawable.ic_tablenow,R.drawable.ic_deli,R.drawable.ic_ecard,R.drawable.ic_game,R.drawable.ic_comments,R.drawable.ic_blog,R.drawable.ic_topuser,R.drawable.ic_video};
    private int[] viewPagerSlideDrawableList = {R.drawable.slide1,R.drawable.slide2};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_where, container, false);

        selectedCityId = 1;         // mac dinh TP.HCM
        selectedDistrictId = 0;
        selectedStreetId = 0;
        selectedCityName = "TP.HCM";
        selectedCategoryId = 1;

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
                changeCity(data.getIntExtra("selectedCityId",1), data.getStringExtra("selectedCityName"));
            }
        }
    }


    // khoi tao layout
    private void layoutInit (View view) {

        navBar = (BottomNavigationViewEx) getActivity().getParent().findViewById(R.id.navigation);

        tabHost = (TabHost) view.findViewById(R.id.tabhost);
        tabWidget = tabHost.getTabWidget();
        tabcontent = (FrameLayout) view.findViewById(android.R.id.tabcontent);
        radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup_filter);

        buttonFilter = (RadioButton) view.findViewById(R.id.button_newest);
        buttonCategory = (RadioButton) view.findViewById(R.id.button_category);
        buttonLocation = (RadioButton) view.findViewById(R.id.button_location);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        listViewFilter = (ListView) view.findViewById(R.id.listView_newest);
        listViewCategory = (ListView) view.findViewById(R.id.listview_category);
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
    }

    // data
    private void dataInit(){
        // Tab 1: ListView loc
        //initTypeTab();
        changeType(0);

        // Tab 2:
        //initCategoryTab();
        changeCategory(0);

        // Tab 3: Danh sach quan/huyen, duong
        changeCity(selectedCityId,selectedCityName);

        // Tab 0: RecyclerView chinh
        gridViewMenuAdapter = new GridViewMenuAdapter(getContext(),gridViewMenuLabelList,gridViewMenuImageList);
        viewPagerSlideAdapter = new ViewPagerSlideAdapter(getContext(),viewPagerSlideDrawableList);
        updateWhereItemListData();
    }

    //
    private void eventInit(View view){
        // TabWidget (chuyen trang)
        buttonFilter.setOnClickListener(new View.OnClickListener() {
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
        listViewFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeType(position);
            }
        });

        // Tab 2:
        listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeCategory(position);
            }
        });

        // Tab 3:
        //
        textViewTabLocationCurrentCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCity(selectedCityId,selectedCityName);
            }
        });
        // thay the event mo rong khi nhan -> chon thanh pho
        listViewDistrict.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                changeDistrict(groupPosition);
                return false;
            }
        });
        listViewDistrict.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                changeStreet(groupPosition,childPosition);
                return false;
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
        RequestParams params = new RequestParams();
        params.put("cityid", selectedCityId);
        params.put("districtid", selectedDistrictId);
        params.put("streetid", selectedStreetId);
        params.put("categoryid", selectedCategoryId);
        params.put("offset", 0);
        // goi REST api de lay du lieu
        RestClient.get("itemwhere", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // parse du lieu tu JSON -> object
                ArrayList<ItemWhere> items = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        items.add(new ItemWhere(response.getJSONObject(i),getContext(), new ImageLoaderListener(){
                            @Override
                            public void onLoading() {
                            }
                            @Override
                            public void onFailed() {
                                if (recyclerView.getAdapter() != null)
                                    recyclerView.getAdapter().notifyDataSetChanged();
                            }
                            @Override
                            public void onSuccess() {
                                if (recyclerView.getAdapter() != null)
                                    recyclerView.getAdapter().notifyDataSetChanged();
                            }
                        }));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // gan du lieu vao recylerview qua adapter
                RecyclerViewWhereAdapter recyclerViewWhereAdapter = new RecyclerViewWhereAdapter(getContext(),items,gridViewMenuAdapter,viewPagerSlideAdapter);
                recyclerView.setAdapter(recyclerViewWhereAdapter);
                if (recyclerView.getLayoutManager() == null)
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                // tweaks cho recycler view
                recyclerView.setHasFixedSize(true);
                recyclerView.setDrawingCacheEnabled(true);

                // load them nooi dung khi cuon
                recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
                    @Override
                    public void onLoadMore(int current_page) {
                        Log.w("event","LoadMore - size:"+recyclerView.getAdapter().getItemCount());
                        RequestParams params = new RequestParams();
                        params.put("cityid", selectedCityId);
                        params.put("districtid", selectedDistrictId);
                        params.put("streetid", selectedStreetId);
                        params.put("categoryid", selectedCategoryId);
                        params.put("offset", current_page*10);
                        RestClient.get("itemwhere", params, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                ArrayList<ItemWhere> items = new ArrayList<
                                        >();
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        items.add(new ItemWhere(response.getJSONObject(i),getContext(), new ImageLoaderListener(){
                                            @Override
                                            public void onLoading() {
                                            }

                                            @Override
                                            public void onFailed() {
                                                if (recyclerView.getAdapter() != null)
                                                    recyclerView.getAdapter().notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onSuccess() {
                                                if (recyclerView.getAdapter() != null)
                                                    recyclerView.getAdapter().notifyDataSetChanged();
                                            }
                                        }));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                Log.w("event","Append:"+items.size());
                                ((RecyclerViewWhereAdapter) recyclerView.getAdapter()).appendData(items);
                                Log.w("event","Appended:"+recyclerView.getAdapter().getItemCount());
                            }
                        });
                    }
                });
            }
        });

        changeTab(0);
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

    // thay doi Type
    private void changeType(final int index) {
        if (listViewFilter.getAdapter()==null){
            // adapter chua co -> load du lieu, tao adapter, thay doi index
            RestClient.get("type", null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    ArrayList<Type> types = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            types.add(new Type(response.getJSONObject(i),getContext()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ListViewTypeAdapter listViewTypeAdapter = new ListViewTypeAdapter(getContext(), types);
                    listViewFilter.setAdapter(listViewTypeAdapter);

                    listViewTypeAdapter.setSelectedIndex(index);
                    buttonFilter.setText(((Type) listViewTypeAdapter.getItem(index)).getName());

                    if (index > 0)
                        Toast.makeText(getContext(), "Tính năng lọc chưa hoàn thiện", Toast.LENGTH_SHORT).show();

                    changeTab(0);
                }
            });
        }
        else {
            // adapter da co -> chi thay doi index
            ListViewTypeAdapter listViewTypeAdapter = (ListViewTypeAdapter) listViewFilter.getAdapter();
            listViewTypeAdapter.setSelectedIndex(index);
            buttonFilter.setText(((Type) listViewTypeAdapter.getItem(index)).getName());

            if (index > 0)
                Toast.makeText(getContext(), "Tính năng lọc chưa hoàn thiện", Toast.LENGTH_SHORT).show();

            changeTab(0);
        }
    }

    // thay doi danh muc
    private void changeCategory(final int index){
        // adapter chua co -> load du lieu, tao adapter, thay doi index
        if (listViewCategory.getAdapter() == null){
            RestClient.get("category", null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    ArrayList<Category> categories = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            categories.add(new Category(response.getJSONObject(i), getContext(), new ImageLoaderListener(){
                                @Override
                                public void onLoading() {
                                }
                                @Override
                                public void onFailed() {
                                    if (listViewCategory.getAdapter()!=null)
                                        ((ListViewCategoryAdapter) listViewCategory.getAdapter()).notifyDataSetChanged();
                                }
                                @Override
                                public void onSuccess() {
                                    if (listViewCategory.getAdapter()!=null)
                                        ((ListViewCategoryAdapter) listViewCategory.getAdapter()).notifyDataSetChanged();
                                }
                            }));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ListViewCategoryAdapter listViewCategoryAdapter = new ListViewCategoryAdapter(getContext(),categories);
                    listViewCategory.setAdapter(listViewCategoryAdapter);
                    listViewCategoryAdapter.setSelectedIndex(index);
                    buttonCategory.setText(((Category) listViewCategoryAdapter.getItem(index)).getName());
                    selectedCategoryId = ((Category) listViewCategoryAdapter.getItem(index)).getId();
                }
            });
        }
        else {
            // adapter da co -> chi thay doi index
            ListViewCategoryAdapter listViewCategoryAdapter = (ListViewCategoryAdapter) listViewCategory.getAdapter();
            listViewCategoryAdapter.setSelectedIndex(index);
            buttonCategory.setText(((Category) listViewCategoryAdapter.getItem(index)).getName());
            selectedCategoryId = ((Category) listViewCategoryAdapter.getItem(index)).getId();

            updateWhereItemListData();
        }
    }

    // thay doi thanh pho
    private void changeCity(int cityId, String cityName){
        // reset cac field da chon
        selectedDistrictId = 0;
        selectedStreetId = 0;
        selectedCityId = cityId;
        selectedCityName = cityName;
        buttonLocation.setText(selectedCityName);
        textViewTabLocationCurrentCity.setText(selectedCityName);

        // cap nhat ds quan huyen
        RequestParams params = new RequestParams();
        params.add("cityid",String.valueOf(selectedCityId));
        RestClient.get("district", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<District> districts = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        districts.add(new District(response.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ListViewDistrictAdapter listViewDistrictAdapter = new ListViewDistrictAdapter(getContext(),districts);
                listViewDistrict.setAdapter(listViewDistrictAdapter);
            }
        });

        // reset cac flter
        changeType(0);
        changeCategory(0);
    }

    // thay doi quan/huyen
    private void changeDistrict(int index){
        selectedStreetId = 0;
        ((ListViewDistrictAdapter) listViewDistrict.getExpandableListAdapter()).setSelectedGroupIndex(index);
        District selectedDistrict = (District) (listViewDistrict.getExpandableListAdapter()).getGroup(index);
        selectedDistrictId = selectedDistrict.getId();
        buttonLocation.setText(selectedDistrict.getName());
        updateWhereItemListData();
    }

    // thay doi duong
    private void changeStreet(int districtIndex, int streetIndex) {
        ((ListViewDistrictAdapter) listViewDistrict.getExpandableListAdapter()).setSelectedChildIndex(districtIndex,streetIndex);
        Street selectedStreet = (Street) (listViewDistrict.getExpandableListAdapter()).getChild(districtIndex,streetIndex);
        selectedStreetId = selectedStreet.getId();
        buttonLocation.setText(selectedStreet.getName());
        updateWhereItemListData();
    }

}
