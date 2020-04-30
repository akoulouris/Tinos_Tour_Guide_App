package com.example.sailinn.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RelativeLayout;

import com.example.sailinn.R;
import com.example.sailinn.model.MainMenu;

import java.util.ArrayList;
import java.util.List;


public class CustomGrid  extends BaseAdapter{
    private Context mContext;
    private List<MainMenu> mMainMenu = new ArrayList<>();
    private float _relativeGridHeight;
   // private final String[] web;
  // private final int[] Imageid;

   // public CustomGrid(Context c,String[] web,int[] Imageid ) {
   public CustomGrid(Context c,List<MainMenu> mainMenu ,float relativeGridHeight) {
        mContext = c;
       mMainMenu =mainMenu;
       _relativeGridHeight=relativeGridHeight;
       // this.Imageid = Imageid;
       // this.web = web;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mMainMenu.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

          //  View view = inflater.inflate(R.layout.grid_single, parent, false);
            //view= new View(mContext);
           // TextView textView = (TextView) view.findViewById(R.id.grid_text);
          //  ImageView imageView = (ImageView)view.findViewById(R.id.grid_image);
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);
            RelativeLayout grid_RelativeLayout= grid.findViewById(R.id. grid_cardview);
            grid_RelativeLayout.getLayoutParams().height=Math.round((_relativeGridHeight*40)/100);
            // Set the name of the 'NicePlace'
           TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            textView.setText(mMainMenu.get(position).getTitle());
            // Set the image
           ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
           imageView.setImageResource(mMainMenu.get(position).getImageUrl());
          // textView.setText(web[position]);
          //imageView.setImageResource(Imageid[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }


}
