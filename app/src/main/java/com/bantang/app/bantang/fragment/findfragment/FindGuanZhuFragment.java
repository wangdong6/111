package com.bantang.app.bantang.fragment.findfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bantang.app.bantang.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindGuanZhuFragment extends Fragment {


    public FindGuanZhuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_guan_zhu, container, false);
    }

}
