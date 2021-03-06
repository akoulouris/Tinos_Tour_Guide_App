package com.example.sailinn.fragments;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.example.sailinn.R;
import android.widget.AdapterView;
import android.widget.GridView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.example.sailinn.adapters.CustomGrid;
import com.example.sailinn.interfaces.OnBackPressed;
import com.example.sailinn.model.Beach;
import com.example.sailinn.model.MainMenu;
import com.example.sailinn.viewmodels.MainViewModel;

import android.animation.ObjectAnimator;




public class MainFragment extends Fragment implements OnBackPressed {
    //Variables
    private ViewFlipper  viewflipper;
    private BottomNavigationView bottomNavigationView;
    private View  view;
    private MainViewModel mMAinViewModel;
    private CustomGrid adapter;
    GridView grid;

    Animation mainLogoAnim;
    Animation mainMenuAnim;
    ObjectAnimator objectanimator;
    String[] web = {
            "Beaches",
            "Dinner",
            "Bars",
            "Facebook"
            /*"Flickr",
            "Pinterest"
*/

    } ;

    int[] imageId = {
            R.drawable.beachblack,
            R.drawable.foodblack,
            R.drawable.drinkblack,
            R.drawable.barimag
          /*  R.drawable.logosi,
            R.drawable.logosi*/

    };

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



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_main, container, false);
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        //Animations
        mainLogoAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.main_logo_animation);
        mainMenuAnim= AnimationUtils.loadAnimation(view.getContext(), R.anim.main_menu_animation);

        //ToolBar
        Toolbar mToolbar = getActivity().findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.GONE);

        //ViewModel
        mMAinViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mMAinViewModel.init();
        mMAinViewModel.getMainMenu().observe(this, new Observer<List<MainMenu>>() {
            @Override
            public void onChanged(@Nullable List<MainMenu> mainMenu) {
                adapter.notifyDataSetChanged();
            }
        });

          //Get elements height
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        view.findViewById(R.id.bottom_navigation).measure(display.getWidth(), display.getHeight());
        float navBottomHeight2 =  view.findViewById(R.id.bottom_navigation).getMeasuredHeight();
        float relativeGridHeight2 =  display.getHeight()-navBottomHeight2-Math.round((display.getHeight()*40)/100); //view height

        //Set up flipper
        viewflipper = view.findViewById(R.id.flipper);
        viewflipper.startFlipping();

        //Set elements Height  %
        viewflipper.getLayoutParams().height=Math.round((display.getHeight()*40)/100);
        view.findViewById(R.id.logomain).getLayoutParams().height=Math.round((display.getHeight()*40)/100);

        //Completely transparent Status Bar
        getActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );


        Beach _beach = new Beach("");
         // initialize bottomNavigationView AND  click event
      /* bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_Me) {
                    Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
                    View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_window_settings, null);
                    int width = Math.round((display.getWidth()*98)/100); // LinearLayout.LayoutParams.MATCH_PARENT;
                    int height = Math.round((display.getHeight()*40)/100);
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height,true);
                    popupWindow.setBackgroundDrawable(null);
                    //Toast.makeText(view.getContext(), "Hello toast!", Toast.LENGTH_SHORT).show();
                    popupWindow.setAnimationStyle(R.style.Animation);
                    popupWindow.showAtLocation(popupView, Gravity.BOTTOM,0, 0);
                    dimBehind(popupWindow);
                    return true;
                }
                else if (item.getItemId() == R.id.action_Favorite){
                    Toast.makeText(view.getContext(), "Hello toast!", Toast.LENGTH_SHORT).show();

                  //  android.support.v4.app.FragmentTransaction trasection = getFragmentManager().beginTransaction();

                  android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                  //  trasection.replace(R.id.fEVORITE_fragment,new ListFragment());
                  //  trasection.addToBackStack(null);
                    //trasection.commit();

                  // ft.hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.headlines_fragment));
                    ft.replace(R.id.headlines_fragment,new ListFragment());
                   ft.addToBackStack(null);
                   ft.commit();
                    return true;
                }
                return true;
            }
        });*/


       // String url = "http://127.0.0.1:5000/";


       // try {
       //     url obj =  new url(url);
       //     httpurlconnection con =  (httpurlconnection) obj.openconnection();
       //     con.setrequestmethod("get");
       //     int responcecode = con.getresponsecode();
       //     int s =1;
       // } catch (malformedurlexception | protocolexception e) {
       //     e.printstacktrace();
      //  } catch (ioexception e) {
       //     e.printstacktrace();
       // }

       // adapter = new CustomGrid(view.getContext(), web, imageId);
        adapter = new CustomGrid(view.getContext(),mMAinViewModel.getMainMenu().getValue(),relativeGridHeight2);

        grid=(GridView)view.findViewById(R.id.grid);
        grid.setAdapter(null);
        grid.setAdapter(adapter);

        Bundle args = getArguments();
        String Origin = args.containsKey("Origin") ? args.getString("Origin") : "";
        if (Origin=="FirstLoad") {
           // Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();

            view.findViewById(R.id.relativeGrid).measure(display.getWidth(), display.getHeight());
            view.findViewById(R.id.flipper).measure(display.getWidth(), display.getHeight());
            view.findViewById(R.id.bottom_navigation).measure(display.getWidth(), display.getHeight());
            view.findViewById(R.id.grid).measure(display.getWidth(), display.getHeight());
            view.findViewById(R.id.logomain).measure(display.getWidth(), display.getHeight());
            view.findViewById(R.id.header).measure(display.getWidth(), display.getHeight());

            //
            float GridHeight =  view.findViewById(R.id.grid).getMeasuredHeight(); // view width
            float FliperHeight =  view.findViewById(R.id.flipper).getMeasuredHeight(); // view width
            float navBottomHeight =  view.findViewById(R.id.bottom_navigation).getMeasuredHeight();
            float logomainHeight =  view.findViewById(R.id.logomain).getMeasuredHeight();// view width
            float headerHeight =  view.findViewById(R.id.header).getMeasuredHeight();// view width


            float relativeGridHeight =  display.getHeight()-navBottomHeight-FliperHeight;
           // view.findViewById(R.id.grid).setY(relativeGridHeight);
           // RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)  view.findViewById(R.id.logomain).getLayoutParams();
           // lp.addRule(RelativeLayout.CENTER_VERTICAL,0);
          //  view.findViewById(R.id.logomain).setLayoutParams(lp);
          //  view.findViewById(R.id.logomain).setY(0);
           //  objectanimator = ObjectAnimator.ofFloat(grid, "translationY", -911);

          //  float mid = (headerHeight/2) - (GridHeight/2);
          // ObjectAnimator objectanimator = ObjectAnimator.ofFloat(grid, "Y",mid);
          //  objectanimator.setDuration(1500);
          // objectanimator.start();


            //Set animation to elements
            grid.setAnimation(mainMenuAnim);
            view.findViewById(R.id.logomain).setAnimation(mainLogoAnim);

           /* float midMainLogo = (relativeGridHeight/2) - (logomainHeight/2);
            ObjectAnimator objectanimatorMainLogo = ObjectAnimator.ofFloat(view.findViewById(R.id.logomain), "Y",midMainLogo);
            objectanimatorMainLogo.setDuration(1500);
            objectanimatorMainLogo.start();
*/



            args.putString("Origin","");

        }
            else
            {
               // objectanimator.end();
            }





        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               // Toast.makeText(view.getContext(), "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                if (position == 0){
                     args.putString("Origin", "Beach");
                }
                else if (position == 1){
                    args.putString("Origin", "Lunch");
                }
                else {
                    args.putString("Origin", "");
                }
                ListFragment listFragment = new ListFragment();
                listFragment.setArguments(args);
                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.headlines_fragment,  listFragment);
                ft.addToBackStack(null);
               ft.commit();
            }
        });
       // new GetBeach(this, _beach, view).execute("http://192.168.1.63:5000/");

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {




    }

    public void  onBackPressed() {
      /*  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       // MyActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();*/
    }

    private class GetBeach extends AsyncTask<String, Void, String>
    {

        private MainFragment _mainfragmnet;
        Beach _beach;
        View _view;


        public GetBeach(MainFragment mainfragmnet, Beach beach, View view)
        {
            this._mainfragmnet = mainfragmnet;
            this._beach = beach;
            this._view = view;
        }


        protected  String doInBackground(String... urls)
         {
             HttpURLConnection urlConnection= null;
             try {
                 URL url = new URL(urls[0]);
                 urlConnection = (HttpURLConnection) url.openConnection();
                 urlConnection.setRequestMethod("GET");
                 urlConnection.connect();
                 InputStream in = urlConnection.getInputStream();
                 /* InputStream in = new BufferedInputStream(urlConnection.getInputStream());*/
                 //readStream(in);
                 BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf8"));
                 StringBuffer sb = new StringBuffer();
                 String data = "";
                 String line = "";

                 while ((line = br.readLine()) != null) {
                     sb.append(line);
                 }

                 data = sb.toString();

                 return data;
             } catch(IOException ie) {
                 ie.printStackTrace();
                 return null;
             } catch (Exception e) {
                 // This will catch any exception, because they are all descended from Exception
                 System.out.println("Error " + e.getMessage());
                 return null;
             } finally {
                 urlConnection.disconnect();
             }

         }


        protected void onPostExecute(String DataBeach)
        {


        }




    }



}
