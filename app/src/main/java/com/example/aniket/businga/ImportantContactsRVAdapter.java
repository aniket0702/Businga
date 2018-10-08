package com.example.aniket.businga;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ImportantContactsRVAdapter extends RecyclerView.Adapter<ImportantContactsRVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView header;
        TextView body;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_impcontacts);
            header = (TextView)itemView.findViewById(R.id.contact_name);
            body = (TextView)itemView.findViewById(R.id.contact_number);
        }
    }

    List<ImportantContactsItemDetails> details;
    Context mctx;
    ImportantContactsRVAdapter(List<ImportantContactsItemDetails> persons, Context mctx){
        this.mctx = mctx;
        this.details = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.important_contact_item_card, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.header.setText(details.get(i).name);
        personViewHolder.body.setText(details.get(i).phoneNumber);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}