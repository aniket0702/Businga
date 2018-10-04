package com.example.aniket.businga;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notification extends Fragment {

    private List<NotificationsItemDetails> details;
    private RecyclerView rv;
    SwipeRefreshLayout swipeRefreshLayout;
    NotificationDetails notificationDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView =  inflater.inflate(R.layout.fragment_notifications, container, false);

        rv = (RecyclerView)inflatedView.findViewById(R.id.rv_notifications);
        notificationDetails = new NotificationDetails(getContext());
        swipeRefreshLayout = inflatedView.findViewById(R.id.swiperefreshnotifications);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshnotification();
            }
        });
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

    public void refreshnotification()
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.GET,"https://wwwbusingacom.000webhostapp.com/notifications.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Do something when response recieved

                notificationDetails.list.clear();
                String driv[]=response.split("////");
                for(String d:driv) {
                    String det[] = d.split("//");
                    if (det.length == 3) {
                        if (det[1] != "" && det[2] != "") {
                            notificationDetails.add_notification(det[1], det[2]);
                        }
                    }
                }
                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","Errroroor");
                error.printStackTrace();
                swipeRefreshLayout.setRefreshing(false);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("complaint","haha");
                return params;
            }
        };
        queue.add(sr);

    }
}
