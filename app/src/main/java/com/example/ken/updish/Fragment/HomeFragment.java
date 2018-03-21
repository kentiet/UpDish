package com.example.ken.updish.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.ken.updish.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Activity context;
    private ArrayList<String> allPostList = new ArrayList<>();
    private ArrayList<String> allPostDescription = new ArrayList<>();
    private ArrayList<String> allPostDate = new ArrayList<>();
    private ArrayList<String> allPostUser = new ArrayList<>();
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
        customPostAdapter = new CustomPostAdapter(context, allPostList, allPostDescription, allPostDate, allPostUser);
        listPost = (ListView)view.findViewById(R.id.listViewMain);
        listPost.setAdapter(customPostAdapter);

        listPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //start activity with each title data clicked
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("postTitle", allPostList.get(i));
                intent.putExtra("postDesc", allPostDescription.get(i));
                intent.putExtra("postDate", allPostDate.get(i));
                intent.putExtra("postUser", allPostUser.get(i));
                startActivity(intent);
            }
        });
        return view;
    }
    private void addItems()
    {
        //Data from Database will replace the items
        allPostList.add("I like this dish so much <3");
        allPostList.add("This delicious pizza is the one I am always looking for");
        allPostList.add("Cutest ice cream I have ever seen");
        allPostList.add("I like this dish so much <3");
        allPostList.add("This delicious pizza is the one I am always looking for");
        allPostList.add("Cutest ice cream I have ever seen");

        allPostDescription.add("One day I tried to look for my dog. I smelled a great food coming out of this place. So I stopped looking for my dog and went inside to eat. Great place !");
        allPostDescription.add("TestDescription2TestDescription2TestDescription2TestDescription2");
        allPostDescription.add("TestDescription3TestDescription3TestDescription3TestDescription3");
        allPostDescription.add("TestDescription2TestDescription2TestDescription2TestDescription2");
        allPostDescription.add("TestDescription5");
        allPostDescription.add("TestDescription6");

        allPostDate.add("Posted on 03/06/18");
        allPostDate.add("Posted on 04/06/18");
        allPostDate.add("Posted on 19/06/18");
        allPostDate.add("Posted on 03/09/18");
        allPostDate.add("Posted on 03/08/16");
        allPostDate.add("Posted on 01/01/10");

        allPostUser.add("Username");
        allPostUser.add("Username2");
        allPostUser.add("Username3");
        allPostUser.add("Username4");
        allPostUser.add("Username5");
        allPostUser.add("Username6");
    }


}
