package com.dannextech.apps.u_cleaner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MakeRequest extends AppCompatActivity {

    private RecyclerView rvPayments;
    private TextView tvTotal;
    private Button btMakeRequest;
    private MakeRequestAdapter pAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);

        final Map<String, String> services = (Map<String, String>) getIntent().getSerializableExtra("services");
        Log.e("Dannex Daniels", "onCreate: "+services.size());
        final MakeRequestModel[] pModel = new MakeRequestModel[services.size()];

        rvPayments = findViewById(R.id.rvPayments);
        tvTotal = findViewById(R.id.tvTotal);
        btMakeRequest = findViewById(R.id.btnMakeRequest);

        //get current date in the format 01-Jan-2019
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        int min = 1000;
        int max = 10000;
        final String serviceID = "CLEAN"+(new Random().nextInt((max - min) + 1) + min);

        int counter = 0;
        int total = 0;
        for (Map.Entry<String, String> entry : services.entrySet()) {
            String key = entry.getKey();
            int value = Integer.parseInt(entry.getValue());

            pModel[counter] = new MakeRequestModel(key,value);
            counter++;
            total += value;
        }

        tvTotal.setText("Sh. "+total);
        Log.e("Dannex Daniels", "onCreate: "+pModel[0].getName());
        pAdapter = new MakeRequestAdapter(pModel);

        rvPayments.setHasFixedSize(true);
        rvPayments.setLayoutManager(new LinearLayoutManager(this));
        rvPayments.setAdapter(pAdapter);
        rvPayments.setItemAnimator(new DefaultItemAnimator());

        btMakeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Map.Entry<String, String> entry : services.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    makeRequest(serviceID,key,value,"0712341234","0756785678",formattedDate,"new","cash","no");
                }
            }
        });
    }

    public void makeRequest(final String receipt, final String service, final String charges, final String client, final String worker, final String rDate, final String status, final String pay_mode, final String paid) {
        //post my location
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://10.0.2.2/ucleaner/makerequest.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        JSONObject jsonObject1 = null;
                        Log.e("ResponseResult", response);
                        try {
                            jsonObject1 = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            if (jsonObject1.getString("status").equals("OK")){
                                Toast.makeText(MakeRequest.this, "Request has been Sent",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MakeRequest.this, FindWorker.class));
                            }else{
                                Toast.makeText(MakeRequest.this, "Request Failed to be Sent",Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.ResponseResult", error.toString());
                        Log.d("Error.ResponseResult", error.toString());
                        Toast.makeText(MakeRequest.this, "Error occurred : "+error.getMessage(),Toast.LENGTH_LONG).show();
                        //sendDriveRequest(jsonObject);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("request_receipt", receipt);
                params.put("service", service);
                params.put("charges", charges);
                params.put("client", client));
                params.put("worker", worker);
                params.put("request_date", rDate);
                params.put("status", status);
                params.put("pay_mode", pay_mode);
                params.put("paid?", paid);

                return params;
            }
        };
        queue.add(postRequest);
    }

}
}
