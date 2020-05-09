package com.example.sailinn.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sailinn.R;
import com.example.sailinn.helper.FavDB;
import com.example.sailinn.model.FavItem;

import java.util.ArrayList;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private Context context;
    private List<FavItem> favItemList = new ArrayList<>();
    private FavDB favDB;

    public FavAdapter(Context context, List<FavItem> favItemList) {
        this.context = context;
        this.favItemList = favItemList;
    }

    @NonNull
    @Override
    public  ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_single, viewGroup, false);
        favDB = new FavDB(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder  holder, int position) {
        holder.favTextView.setText(favItemList.get(position).getItem_title());
        holder.favImageView.setImageResource(favItemList.get(position).getItem_image());
        holder.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
        if (favItemList.isEmpty()) {
           // holder.FavoriteRecyclerView.setVisibility(View.GONE);
           // holder.emptyTextView.setVisibility(View.VISIBLE);
        }
        else {
          //  holder.FavoriteRecyclerView.setVisibility(View.VISIBLE);
           // holder.emptyTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return favItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView emptyTextView;
        TextView favTextView;
        Button favBtn;
        ImageView favImageView;
        RecyclerView FavoriteRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favTextView = (TextView)itemView.findViewById(R.id.person_name);
            favImageView = (ImageView)itemView.findViewById(R.id.person_photo);
            favBtn = itemView.findViewById(R.id.favBtn);
            emptyTextView=itemView.findViewById(R.id.empty_view);
            FavoriteRecyclerView=itemView.findViewById(R.id.FavoriteRecyclerView);
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final FavItem favItem =favItemList.get(position);
                    favDB.remove_fav(favItem.getKey_id());
                    removeItem(position);
                }

            });
        }
    }

    private void removeItem(int position) {
        favItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,favItemList.size());
    }
}