package com.example.ken.updish.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ken.updish.Activity.MainActivity;
import com.example.ken.updish.Adapter.CustomPostAdapter;
import com.example.ken.updish.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Activity context;
    private ArrayList<String> allPostList = new ArrayList<>();
    private CustomPostAdapter customPostAdapter;
    private ListView listPost;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Required empty public constructor
        context = (Activity)getActivity();

        // Inflate the layout for this fragment
        addItems();
        customPostAdapter = new CustomPostAdapter(context, allPostList);
        listPost = (ListView)view.findViewById(R.id.listViewMain);
        listPost.setAdapter(customPostAdapter);

        return view;
    }


    private void addItems()
    {
        allPostList.add("I like this dish so much <3");
        allPostList.add("This delicious pizza is the one I am always looking for");
        allPostList.add("Cutest ice cream I have ever seen");
        allPostList.add("I like this dish so much <3");
        allPostList.add("This delicious pizza is the one I am always looking for");
        allPostList.add("Cutest ice cream I have ever seen");
    }

}
