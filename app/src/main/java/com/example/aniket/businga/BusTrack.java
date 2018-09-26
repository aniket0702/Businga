package com.example.aniket.businga;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BusTrack extends Fragment {

    private List<BusTrackItemDetails> details;
    private RecyclerView rv;
    BusDetails bus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_bus_track, container, false);
        rv = (RecyclerView)inflatedView.findViewById(R.id.rv);

        bus=new BusDetails(getContext());
        initializeData();
        initializeAdapter();
        return inflatedView;
    }

    private void initializeData(){
        details =BusDetails.list;
        /*details.add(new BusTrackItemDetails("driver1", "9414562357","RJ13 DR 5678",1));
        details.add(new BusTrackItemDetails("driver2", "7894561230","RJ13 DR 5678",3));
        details.add(new BusTrackItemDetails("driver3", "4512879630","RJ13 DR 5678",2));*/
    }

    private void initializeAdapter(){
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        BusTrackRVAdapter adapter = new BusTrackRVAdapter(details,getContext());
        rv.setAdapter(adapter);
    }

}
