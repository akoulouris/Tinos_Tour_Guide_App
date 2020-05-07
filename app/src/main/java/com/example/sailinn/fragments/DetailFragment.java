package com.example.sailinn.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.example.sailinn.adapters.ViewPagerAdapter;

import android.widget.LinearLayout;
import android.widget.ViewFlipper;
import com.example.sailinn.R;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import java.util.List;
import java.util.ArrayList;
import android.transition.Fade;
import android.os.Build;
import com.example.sailinn.fragments.DetailsTransition;

import com.example.sailinn.fragments.ListFragment.Person;

public class DetailFragment   extends Fragment {
    private View  view;
    private List<Integer> images=new ArrayList<>();
    int dotscount=0;
    ImageView[]  dots;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_detail, container, false);


        Toolbar mToolbar = getActivity().findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.GONE);

       Toolbar mToolbark = view.findViewById(R.id.htab_toolbar);
        getActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        );


        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbark);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)  view.findViewById(R.id.htab_collapse_toolbar);
        collapsingToolbar.setTitle("Profile");
      //  mToolbark.setDisplayHomeAsUpEnabled(true);

      /*  collapsingToolbar = view.findViewById(R.id.user_collapsing_toolbar);
        collapsingToolbar.setTitle("Profile");
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);*/


        Bundle args = getArguments();
        int kittenNumber = args.containsKey("image_number") ? args.getInt("image_number") : 1;


       // ImageView image =  view.findViewById(R.id.image);
      //  image.setImageResource(kittenNumber);


        images.add(kittenNumber);
        images.add(kittenNumber);

        ViewPager rv = view.findViewById(R.id.viewPager);
       // rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        ViewPagerAdapter adapter = new ViewPagerAdapter(view.getContext(),images);
        rv.setAdapter(adapter);




        LinearLayout  sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);

        dotscount = adapter.getCount();
          dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(view.getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //params.gravity=Gravity.BOTTOM;
            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.active_dot));

        rv.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.active_dot));

            }
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

  /*  private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.bars));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.beaches));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.carousel1));
    }
*/


}
