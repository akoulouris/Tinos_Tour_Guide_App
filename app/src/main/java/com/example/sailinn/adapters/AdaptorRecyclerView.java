package com.example.sailinn.adapters;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sailinn.R;
import com.example.sailinn.fragments.ListFragment.Person;
import com.example.sailinn.helper.FavDB;
import com.example.sailinn.model.ListItem;

import java.util.ArrayList;
import java.util.List;

public class AdaptorRecyclerView extends RecyclerView.Adapter<AdaptorRecyclerView.ViewHolder> {
    private static ClickListener clickListener;
    public static List<ListItem> ListItems;
    private FavDB favDB;
    private Context context;

    public AdaptorRecyclerView(List<ListItem> ListItems, Context context){
        this.ListItems = ListItems;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        favDB = new FavDB(context);
        //create table on first
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_single, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v);

        return pvh;
    }

    @Override
    public int getItemCount() {
        return ListItems.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder personViewHolder,final  int i) {
        final ListItem ListItem = ListItems.get(i);
        personViewHolder.personName.setText(ListItems.get(i).getTitle());
        // personViewHolder.personAge.setText(persons.get(i).age);
        personViewHolder.personPhoto.setImageResource(ListItems.get(i).getImageResourse());
        ViewCompat.setTransitionName(personViewHolder.personPhoto, String.valueOf(i) + "_image");
        readCursorData(ListItem, personViewHolder);
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        CardView cv;
      TextView personName;
       // TextView personAge;
        ImageView personPhoto;
     //   private FavDB favDB;
     CheckBox favBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
           // personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            favBtn = itemView.findViewById(R.id.favBtn);
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                   ListItem ListItem = ListItems.get(position);
                if (ListItem.getFavStatus().equals(("0"))){
                    ListItem.setFavStatus("1");
                    favDB.insertIntoTheDatabase(ListItem.getTitle(),ListItem.getImageResourse(),
                            ListItem.getKey_id(),ListItem.getFavStatus());
                    favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                }
                else
                {
                    ListItem.setFavStatus("0");
                    favDB.remove_fav(ListItem.getKey_id());
                    favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
                }
                  //  likeClick(coffeeItem, favBtn, likeCountTextView);
                }
            });
        }

        @Override
        public void onClick(View v) {
           // clickListener.onItemClick(getAdapterPosition(), v);
            clickListener.onItemClick(getAdapterPosition(), v,getItem(getAdapterPosition()));

        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        AdaptorRecyclerView.clickListener = clickListener;
    }


    public interface ClickListener {
        //void onItemClick(PersonViewHolder personViewHolder, int position);
        void onItemClick(int position, View v,int photoId);
        void onItemLongClick(int position, View v);
    }

   //static  List<Person> persons;







    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static int getItem(int position) {
        return ListItems.get(position).getImageResourse();
        //return persons.size();
    }
    private void createTableOnFirstStart() {
        favDB.insertEmpty();
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void readCursorData(ListItem ListItem, ViewHolder viewHolder) {
        Cursor cursor = favDB.read_all_data(ListItem.getKey_id());
       SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                String item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
                ListItem.setFavStatus(item_fav_status);

                //check fav status
                if (item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                } else if (item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

    }

}

