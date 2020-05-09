package com.example.sailinn.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.view.WindowManager;

import com.example.sailinn.R;
import com.example.sailinn.adapters.AdaptorRecyclerView;
import com.example.sailinn.model.ListItem;

import android.widget.Toast;
import java.util.List;
import java.util.ArrayList;

public   class ListFragment  extends Fragment {
    private View  view;
   // private List<Person> persons;
    private List<ListItem> listItems;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_list, container, false);

        //clear Completely transparent Status Bar
        getActivity().getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        getActivity().getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        );

        //Display Toolbar and set Title
        Toolbar mToolbar = getActivity().findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.VISIBLE);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mToolbar.setTitle("Beaches");

        //hide the bottom navigation bar
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);

        RecyclerView rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        initializeData();

        AdaptorRecyclerView adapter = new AdaptorRecyclerView(listItems,getActivity());
        rv.setAdapter(adapter);



        adapter.setOnItemClickListener(new AdaptorRecyclerView.ClickListener() {


            public void onItemClick(int position, View v,int s) {
               // Toast.makeText(view.getContext(), "You Clicked at " + position, Toast.LENGTH_SHORT).show();

                Bundle args = new Bundle();
                args.putInt("image_number", s);

                DetailFragment details = new DetailFragment();
                details.setArguments(args);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    details.setSharedElementEnterTransition(new DetailsTransition());
                    details.setEnterTransition(new Fade());
                    setExitTransition(new Fade());
                    details.setSharedElementReturnTransition(new DetailsTransition());
                }


                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addSharedElement(v.findViewById(R.id.person_photo),"kittenImage")
                        .replace(R.id.headlines_fragment, details)
                        .addToBackStack(null)
                        .commit();

               // Log.d(TAG, "onItemClick position: " + position);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Toast.makeText(view.getContext(), "You Clicked at " + position, Toast.LENGTH_SHORT).show();
               // Log.d(TAG, "onItemLongClick pos = " + position);
            }
        });

        return view;
    }

    public  void initializeData2(){
      // persons = new ArrayList<>();
       // persons.add(new Person("Emma Wilson", "23 years old", R.drawable.bars));
      //  persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.beaches));
      //  persons.add(new Person("Lillie Watts", "35 years old", R.drawable.carousel1));
    }


    public  void initializeData(){
        listItems = new ArrayList<>();
        listItems.add(new ListItem(R.drawable.bars,"Emma Wilson","0","0"));
        listItems.add(new ListItem(R.drawable.beaches,"Lavery Maiss","1","0" ));
        listItems.add(new ListItem(R.drawable.carousel1,"Lillie Watts", "2", "0"));
    }

    public class Person {
        public  String name;
        public  String age;
        public  int photoId;

        public  Person(String name, String age, int photoId) {
            this.name = name;
            this.age = age;
            this.photoId = photoId;
        }
    }
}
