package com.dannextech.apps.u_cleaner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.Serializable;

public class SelectService extends AppCompatActivity {

    private RecyclerView rvServices;
    private ServiceAdapter sAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);

        rvServices = findViewById(R.id.rvServices);

        ServiceModel[] sModel = {new ServiceModel("Cleaning Utensils",100),new ServiceModel("Laundry",500),new ServiceModel("House Cleaning",200),new ServiceModel("Vehicle Washing",100)};
        sAdapter = new ServiceAdapter(sModel);

        rvServices.setHasFixedSize(true);
        rvServices.setLayoutManager(new LinearLayoutManager(this));
        rvServices.setAdapter(sAdapter);
        rvServices.setItemAnimator(new DefaultItemAnimator());


    }

    public void submitService(View v){
        Log.e("Dannex Daniels", "submitService: "+sAdapter.selectedServices);
        startActivity(new Intent(SelectService.this,MakeRequest.class).putExtra("services", (Serializable) sAdapter.selectedServices));
    }
}
