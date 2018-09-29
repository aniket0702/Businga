package com.example.aniket.businga;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class BusDetails
{
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mctx;

    final String pref_name="busdetails";
    final String KEY_NAME="name";
    final String KEY_MBL="mobile";
    final String KEY_BUSN="bus_no";
    final String KEY_BUSI="bus_id";
    final String KEY_SIZE="size";

    static List<BusTrackItemDetails> list=new ArrayList<>();

    BusDetails(Context mctx)//String name, String mobile, String bus_no, int bus_id)
    {
        this.mctx = mctx;
    }

    int getSize()
    {
        return pref.getInt(KEY_SIZE,-1);
    }

    void incSize()
    {
        if(pref.getInt(KEY_SIZE,-1)<3 && pref.getInt(KEY_SIZE,-1)>=0)
            editor.putInt(KEY_SIZE,pref.getInt(KEY_SIZE,-1)+1);
    }




    void decSize()
    {
        if(pref.getInt(KEY_SIZE,-1)>0)
            editor.putInt(KEY_SIZE,pref.getInt(KEY_SIZE,-1)-1);
    }

    void addBus(String name,String mobile,String bus_no,int bus_id)
    {

        list.add(new BusTrackItemDetails(name,mobile,bus_no,bus_id));
        incSize();

        editor.apply();
    }

    void updateBus(String name,String mobile,String bus_no,int bus_id)
    {


        List<BusTrackItemDetails> listtemp=new ArrayList<>();
        for(BusTrackItemDetails det:list)
        {
            if(det.bus_id!=bus_id)
                listtemp.add(det);
        }
        list.clear();
        list=listtemp;
        list.add(new BusTrackItemDetails(name,mobile,bus_no,bus_id));

    }

    void deleteBus(int bus_id)
    {
        List<BusTrackItemDetails> listtemp=new ArrayList<>();
        for(BusTrackItemDetails det:list)
        {
            if(det.bus_id!=bus_id)
                listtemp.add(det);
        }
        list.clear();
        list=listtemp;
        decSize();

    }

}
