package com.example.aniket.businga;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NotificationRVAdapter extends RecyclerView.Adapter<NotificationRVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView header;
        TextView body;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_notification);
            header = (TextView)itemView.findViewById(R.id.notification_header);
            body = (TextView)itemView.findViewById(R.id.notification_body);
        }
    }

    List<NotificationsItemDetails> details;

    NotificationRVAdapter(List<NotificationsItemDetails> persons){
        this.details = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_item_card, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.header.setText(details.get(i).header);
        personViewHolder.body.setText(details.get(i).body);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}