package com.bantang.app.bantang.fragment.titlefragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bantang.app.bantang.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JiaJuFragment extends Fragment {


    public JiaJuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jia_ju, container, false);
    }

}
