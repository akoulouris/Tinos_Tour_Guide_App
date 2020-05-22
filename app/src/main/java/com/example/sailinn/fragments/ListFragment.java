package com.example.sailinn.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.example.sailinn.viewmodels.ListViewModel;

import android.widget.Toast;
import java.util.List;
import java.util.ArrayList;

public   class ListFragment  extends Fragment {
    private View  view;
   // private List<Person> persons;
   // private List<ListItem> listItems;
    private ListViewModel mListViewModel;
    private AdaptorRecyclerView adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_list, container, false);

        getActivity().getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        Bundle args = getArguments();
        String Origin = args.containsKey("Origin") ? args.getString("Origin") : "";

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

        //ViewModel
        mListViewModel = ViewModelProviders.of(this).get(ListViewModel.class);


        //Set adaptor
        if (Origin=="Beach") {
            mListViewModel.initBeach();
            mListViewModel.getListBeach().observe(this, new Observer<List<ListItem>>() {
                @Override
                public void onChanged(@Nullable List<ListItem> listItem) {
                    adapter.notifyDataSetChanged();
                }
            });
            adapter = new AdaptorRecyclerView(mListViewModel.getListBeach().getValue(),getActivity());
        }
        else if (Origin=="Lunch") {
            mListViewModel.initLunch();
            mListViewModel.getListLunch().observe(this, new Observer<List<ListItem>>() {
                @Override
                public void onChanged(@Nullable List<ListItem> listItem) {
                    adapter.notifyDataSetChanged();
                }
            });
            adapter = new AdaptorRecyclerView(mListViewModel.getListLunch().getValue(),getActivity());
        }
        else {
            mListViewModel.init();
            mListViewModel.getList().observe(this, new Observer<List<ListItem>>() {
                @Override
                public void onChanged(@Nullable List<ListItem> listItem) {
                    adapter.notifyDataSetChanged();
                }
            });
            adapter = new AdaptorRecyclerView(mListViewModel.getList().getValue(),getActivity());
        }


        rv.setAdapter(adapter);

         //Set set On Item Click
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

   /* public class Person {
        public  String name;
        public  String age;
        public  int photoId;

        public  Person(String name, String age, int photoId) {
            this.name = name;
            this.age = age;
            this.photoId = photoId;
        }
    }*/
}
