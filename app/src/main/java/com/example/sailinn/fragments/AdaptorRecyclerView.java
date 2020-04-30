package com.example.sailinn.fragments;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sailinn.R;
import com.example.sailinn.fragments.ListFragment.Person;

import java.util.List;

public class AdaptorRecyclerView extends RecyclerView.Adapter<AdaptorRecyclerView.PersonViewHolder> {
    private static ClickListener clickListener;
    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        CardView cv;
        TextView personName;
       // TextView personAge;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
           // personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
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

   static  List<Person> persons;



    AdaptorRecyclerView(List<Person> persons){
        this.persons = persons;
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_single, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder,final  int i) {
        personViewHolder.personName.setText(persons.get(i).name);
       // personViewHolder.personAge.setText(persons.get(i).age);
        personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
        ViewCompat.setTransitionName(personViewHolder.personPhoto, String.valueOf(i) + "_image");

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static int getItem(int position) {
        return persons.get(position).photoId;
        //return persons.size();
    }

}

