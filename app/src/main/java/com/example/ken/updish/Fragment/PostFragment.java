package com.example.ken.updish.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ken.updish.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {

    Activity context;
    private TextView mTextMessage;

    public PostFragment() {
        // Required empty public constructor
        Log.e("Updish", "Test fragment constructor", null);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        context = (Activity)getActivity();

        Button btn_new = (Button)view.findViewById(R.id.btn_Newpost);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Clicked",Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}
