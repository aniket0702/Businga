package com.example.aniket.businga;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;

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
        final int id = det.bus_id;
        personViewHolder.track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_location(id);
            }
        });
    }

    public void get_location(final int id){
        RequestQueue queue = Volley.newRequestQueue(mctx);
        StringRequest sr = new StringRequest(Request.Method.POST,"https://wwwbusingacom.000webhostapp.com/get_locations.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Do something when response recieved
                Log.i(TAG, "onResponse: "+ response);
                if(response.equals("No Entries found") == true || response.equals("0//0") == true){
                    Log.i(TAG, "onResponse: Entered the false block");
                    Toast.makeText(mctx, "The bus is currently not running", Toast.LENGTH_SHORT).show();
                }
                else{
                    String stringLocations[] = response.split("//");
                    double locations[] = new double[]{Double.parseDouble(stringLocations[0]), Double.parseDouble(stringLocations[1])};
                    Log.i(TAG, "onResponse: " + String.valueOf(locations[0]));
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + stringLocations[0] +">,<"+stringLocations[1]+">?q=<" + stringLocations[0] +">,<"+stringLocations[1]+">(Bus)"));
                    mctx.startActivity(intent);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("bus_no",String.valueOf(id));
                return params;
            }
        };
        queue.add(sr);

    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}