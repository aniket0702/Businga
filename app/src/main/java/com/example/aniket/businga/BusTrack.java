package com.example.aniket.businga;

import android.content.Context;
import android.content.Intent;
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

public class BusTrack extends Fragment {

    private List<BusTrackItemDetails> details;
    private RecyclerView rv;
    BusDetails bus;

    SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_bus_track, container, false);
        rv = (RecyclerView)inflatedView.findViewById(R.id.rv);
        swipeRefreshLayout = inflatedView.findViewById(R.id.swiperefreshbus);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDrivers();

            }
        });
        bus=new BusDetails(getContext());
        initializeData();
        initializeAdapter();
        return inflatedView;
    }

    private void initializeData(){
        details =BusDetails.list;

    }

    private void initializeAdapter(){
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        BusTrackRVAdapter adapter = new BusTrackRVAdapter(details,getContext());
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void refreshDrivers(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest sr = new StringRequest(Request.Method.POST,"https://wwwbusingacom.000webhostapp.com/get_driver.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Do something when response recieved

                String driv[]=response.split(";");

                for(String d:driv)
                {
                    String det[]=d.split(",");
                    bus.updateBus(det[0],det[1],det[2],Integer.parseInt(det[3]));
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
