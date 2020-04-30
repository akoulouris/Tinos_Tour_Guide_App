package com.example.sailinn;


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

import com.example.sailinn.fragments.MainFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {
  //  public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        else{super.onBackPressed();}
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
