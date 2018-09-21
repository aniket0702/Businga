package com.example.aniket.businga;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BusTrackRVAdapter extends RecyclerView.Adapter<BusTrackRVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView driver_name;
        TextView bus_number;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            driver_name = (TextView)itemView.findViewById(R.id.driver_name);
            bus_number = (TextView)itemView.findViewById(R.id.bus_number);
        }
    }

    List<BusTrackItemDetails> details;

    BusTrackRVAdapter(List<BusTrackItemDetails> persons){
        this.details = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bus_track_item_card, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.driver_name.setText(details.get(i).driverName);
        personViewHolder.bus_number.setText(details.get(i).busNumber);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}