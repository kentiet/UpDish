package com.example.ken.updish.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ken.updish.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {


    public PostFragment() {
        // Required empty public constructor
        Log.e("Updish", "Inside PostFragment constructor" );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

}
