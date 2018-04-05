package com.example.ken.updish.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Model.User;
import com.example.ken.updish.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    Activity context;
    TextView userDisplay;
    TextView emailDisplay;
    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        context = (Activity)getActivity();

        userDisplay = (TextView)view.findViewById(R.id.txt_userNameContext);
        emailDisplay = (TextView)view.findViewById(R.id.txt_userEmailContext);

        User currentUser = DatabaseHelper.getInstance().getCurrentUser();
        userDisplay.setText(currentUser.getUserName());
        emailDisplay.setText(currentUser.getEmail());

        return view;
    }

}
