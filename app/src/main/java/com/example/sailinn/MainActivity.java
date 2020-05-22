package com.example.sailinn;


import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
//import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
//import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.sailinn.fragments.DetailFragment;
import com.example.sailinn.fragments.FavoriteFragment;
import com.example.sailinn.fragments.ListFragment;
import com.example.sailinn.fragments.MainFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    private int revealX;
    private int revealY;
    View rootLayout;
    public static void dimBehind(PopupWindow popupWindow) {
        View container;
        if (popupWindow.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                container = (View) popupWindow.getContentView().getParent();
            } else {
                container = popupWindow.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();
            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.8f;
        wm.updateViewLayout(container, p);
    }



    //  public class MainActivity extends FragmentActivity {
  private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent = getIntent();

        rootLayout = findViewById(R.id.root_layout);

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y))
        {
            rootLayout.setVisibility(View.INVISIBLE);
            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);
            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                       revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }

        } else {
            rootLayout.setVisibility(View.VISIBLE);
        }
       // getSupportActionBar().hide();
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }*/
       // getSupportActionBar().hide();
        Toolbar mToolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString("Origin", "FirstLoad");

        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(args);

        ft.replace(R.id.headlines_fragment, mainFragment);
        ft.addToBackStack(null);
        ft.commit();


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_Me) {
                    Display display = getWindowManager().getDefaultDisplay();
                    LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popup_window_settings, null);
                   // View popupView = LayoutInflater.from(inflate(R.layout.popup_window_settings, null);
                    int width = Math.round((display.getWidth()*98)/100); // LinearLayout.LayoutParams.MATCH_PARENT;
                   int height = Math.round((display.getHeight()*40)/100);
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height,true);
                   popupWindow.setBackgroundDrawable(null);
                   //Toast.makeText(view.getContext(), "Hello toast!", Toast.LENGTH_SHORT).show();
                   popupWindow.setAnimationStyle(R.style.Animation);
                   popupWindow.showAtLocation(popupView, Gravity.BOTTOM,0, 0);
                   dimBehind(popupWindow);
                    return false;
                }
                else if (item.getItemId() == R.id.action_Favorite){
                    //Toast.makeText(view.getContext(), "Hello toast!", Toast.LENGTH_SHORT).show();
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.headlines_fragment,new FavoriteFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;
                }
                else if (item.getItemId() == R.id.action_home){
                   android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    //  trasection.replace(R.id.fEVORITE_fragment,new ListFragment());
                    //  trasection.addToBackStack(null);
                    //trasection.commit();
                    // ft.hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.headlines_fragment));
                    Bundle args = new Bundle();
                    args.putString("Origin", "");

                    MainFragment mainFragment = new MainFragment();
                    mainFragment.setArguments(args);
                    ft.replace(R.id.headlines_fragment,  mainFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;
                }
                return true;
            }
        });

       // setSupportActionBar(mToolbar);
     //  getSupportActionBar().setDisplayShowTitleEnabled(true);
      // getSupportActionBar().setDisplayShowHomeEnabled(true);
      //  getSupportActionBar().setIcon(R.drawable.logosi);
       // getSupportActionBar().setIcon(R.drawable.images);
    }

    @Override
    public void onBackPressed() {
       // MainFragment mainFragment = new MainFragment();
       // mainFragment.onBackPressed();
       //
        Fragment f = getVisibleFragment();
        if(f instanceof MainFragment) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you ready to return to real world?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            System.exit(1);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }
        else if (f instanceof  FavoriteFragment){
            bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
            super.onBackPressed();

        }
        else{super.onBackPressed();}
    }

    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(1100);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }
    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }
}
