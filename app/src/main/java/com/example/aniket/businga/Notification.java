package com.example.aniket.businga;

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

public class Notification extends Fragment {

    private List<NotificationsItemDetails> details;
    private RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView =  inflater.inflate(R.layout.fragment_notifications, container, false);

        rv = (RecyclerView)inflatedView.findViewById(R.id.rv_notifications);

        initializeData();
        initializeAdapter();
        return inflatedView;

    }
    private void initializeData(){
        details = NotificationDetails.list;
    }
    private void initializeAdapter(){
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        NotificationRVAdapter adapter = new NotificationRVAdapter(details, getContext());
        rv.setAdapter(adapter);
    }
}
