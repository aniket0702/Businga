package com.example.aniket.businga;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BusTrackRVAdapter extends RecyclerView.Adapter<BusTrackRVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView name,mobile,bus_no,bus_id;
        Button track;

        PersonViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            mobile=itemView.findViewById(R.id.mobile);
            bus_no=itemView.findViewById(R.id.bus_no);
            bus_id=itemView.findViewById(R.id.bus_id);
            track=itemView.findViewById(R.id.track);
        }
    }

    List<BusTrackItemDetails> details;
    Context mctx;

    BusTrackRVAdapter(List<BusTrackItemDetails> persons,Context mctx){
        this.details = persons;
        this.mctx=mctx;
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
        BusTrackItemDetails det=details.get(i);
        personViewHolder.name.setText(det.name);
        personViewHolder.mobile.setText(det.mobile);
        personViewHolder.bus_no.setText(det.bus_no);
        personViewHolder.bus_id.setText(String.valueOf(det.bus_id));

        personViewHolder.track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mctx,"Taking to maps...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}