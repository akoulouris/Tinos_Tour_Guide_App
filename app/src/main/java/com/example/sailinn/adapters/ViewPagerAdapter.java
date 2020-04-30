package com.example.sailinn.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sailinn.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    static List<Integer> images;
    public ViewPagerAdapter(Context context,List<Integer> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.viewpager_single, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(images.get(position));


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_SHORT).show();
//final Dialog d = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView( R.layout.viewpager_single);
                ImageView imageViewDialog  =  dialog.findViewById(R.id.imageView);
                imageViewDialog .setImageResource(images.get(position));
                Window window = dialog.getWindow();
                window.setLayout(
                        (int)(window.getWindowManager().getDefaultDisplay().getWidth()),
                        (int)(window.getWindowManager().getDefaultDisplay().getHeight() ));

                dialog.show();
              //  Intent intent = new Intent(context, ImageOpeningActivity.class);
              //  intent.putExtra("MY_IMAGE", images[position]);
              //  context.startActivity(intent);

            }
        });


        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }

}


