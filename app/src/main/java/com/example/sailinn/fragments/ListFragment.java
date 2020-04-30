package com.example.sailinn.fragments;

import android.content.Context;
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
import android.widget.ViewFlipper;
import com.example.sailinn.R;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import java.util.List;
import java.util.ArrayList;

public   class ListFragment  extends Fragment {
    private View  view;
    private List<Person> persons;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_list, container, false);


        Toolbar mToolbar = getActivity().findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.VISIBLE);



        RecyclerView rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        initializeData();

        AdaptorRecyclerView adapter = new AdaptorRecyclerView(persons);
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
    public  void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.bars));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.beaches));
       persons.add(new Person("Lillie Watts", "35 years old", R.drawable.carousel1));
    }

    public class Person {
        String name;
        String age;
        int photoId;

        Person(String name, String age, int photoId) {
            this.name = name;
            this.age = age;
            this.photoId = photoId;
        }
    }
}
