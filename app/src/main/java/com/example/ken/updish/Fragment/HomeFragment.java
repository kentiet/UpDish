package com.example.ken.updish.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ken.updish.Activity.DetailActivity;
import com.example.ken.updish.Activity.MainActivity;
import com.example.ken.updish.Adapter.CustomPostAdapter;

import com.example.ken.updish.BackgroundWorker.PostListBackgroundWorker;
import com.example.ken.updish.Database.DatabaseHelper;
import com.example.ken.updish.Listener.PostDetailsClickListener;

import com.example.ken.updish.Database.DatabaseHelper;

import com.example.ken.updish.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Activity context;
    private CustomPostAdapter customPostAdapter;
    private ListView listPost;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e("Updish", "Home fragment onCreateView", null);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = (Activity)getActivity();

        // Using database for Adapter
        customPostAdapter = new CustomPostAdapter(context);
        listPost = (ListView)view.findViewById(R.id.listViewMain);
        listPost.setAdapter(customPostAdapter);

        // Listener
        PostDetailsClickListener pdcl = new PostDetailsClickListener(context);
        listPost.setOnItemClickListener(pdcl);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("Home FragMent", "Detached", null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Home FragMent", "Attached ", null);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Home FragMent", "Resumed ", null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("Home FragMent", "Activity created ", null);
    }
}
