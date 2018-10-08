package com.example.aniket.businga;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ImportantContacts extends AppCompatActivity {

    private List<ImportantContactsItemDetails> details;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        details = new ArrayList<ImportantContactsItemDetails>();
        setContentView(R.layout.activity_important_contacts);
        rv = (RecyclerView)findViewById(R.id.rv_impcontacts);
        initializeData();
        initializeAdapter();

    }
    private void initializeData(){
        details.add(new ImportantContactsItemDetails("aniket", "38743987"));

    }
    private void initializeAdapter(){
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        ImportantContactsRVAdapter adapter = new ImportantContactsRVAdapter(details, getApplicationContext());
        rv.setAdapter(adapter);
    }

}
