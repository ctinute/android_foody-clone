package com.ctinute.foody.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ctinute.foody.R;

public class HomeWhatFragment extends Fragment {


    public HomeWhatFragment() {
        // Required empty public constructor
    }

    public static HomeWhatFragment newInstance() {
        return new HomeWhatFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_what, container, false);
    }

}
