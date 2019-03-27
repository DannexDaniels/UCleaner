package com.dannextech.apps.u_cleaner;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private ServiceModel[] services;

    private Context context;

    private ServiceDbHelper dbHelper;

    private String serviceID = null;

    private ColorGenerator generator = ColorGenerator.MATERIAL;

    Map<String, String> selectedServices;

    public ServiceAdapter(ServiceModel[] services) {
        this.services = services;

        int min = 1000;
        int max = 10000;
        serviceID = "CLEAN"+(new Random().nextInt((max - min) + 1) + min);

        selectedServices = new HashMap<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.service_details,viewGroup,false);
        dbHelper = new ServiceDbHelper(context);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        String name = services[i].getName();
        int price = services[i].getPrice();

        viewHolder.sTitle.setText(name);
        viewHolder.sPrice.setText(String.valueOf(price));



        //Get the first letter of list item
        String letter = String.valueOf(name.charAt(0));
        //Create a new TextDrawable for our image's background
        final TextDrawable drawable = TextDrawable.builder().buildRound(letter,generator.getRandomColor());
        viewHolder.letter.setImageDrawable(drawable);
        //Toast.makeText(context,"Size is "+ position,Toast.LENGTH_LONG).show();
        final String[] pos = new String[services.length];
        viewHolder.sTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos[i] == null){
                    Log.e("Dannex Daniels", "onClick: first time selection - "+viewHolder.sTitle.getText().toString() );
                   pos[i] = "sel";
                }else if (pos[i] == "sel"){
                    Log.e("Dannex Daniels", "onClick: deselecting - "+viewHolder.sTitle.getText().toString());
                    pos[i] = "not_sel";
                }else if (pos[i] == "not_sel"){
                    Log.e("Dannex Daniels", "onClick: reselecting - "+viewHolder.sTitle.getText().toString() );
                    pos[i] = "sel";
                }


                if (pos[i].equals("not_sel")){
                    viewHolder.letter.setImageDrawable(drawable);
                    selectedServices.remove(viewHolder.sTitle.getText().toString());
                }else {
                    viewHolder.letter.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    selectedServices.put(viewHolder.sTitle.getText().toString(),viewHolder.sPrice.getText().toString());
                }

                getSelectedServices();
            }
        });
    }

    public Map<String,String> getSelectedServices(){
        Log.e("Dannex Daniels", "getSelectedServices: "+selectedServices);
        return selectedServices;
    }
    private void deleteUnSelectedDb(String service) {
        //gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Defne 'where' part of query
        String selection = ServiceDbContract.Service.COL_SERVICE_NAME + " LIKE ?";
        //Specify argument in place holder
        String[] selectioAgs = {service};
        //Isue SQL statement
        db.delete(ServiceDbContract.Service.TABLE_NAME,selection,selectioAgs);
    }

    private void saveSelectedDb(String sTitle, String sPrice) {

        SharedPreferences.Editor editor = context.getSharedPreferences("driver", MODE_PRIVATE).edit();
        editor.putString("service_id", serviceID);

        //get current date in the format 01-Jan-2019
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        //gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Creates a new map of values, where columns are the keys
        ContentValues values = new ContentValues();
        values.put(ServiceDbContract.Service.COL_SERVICE_NAME, sTitle);
        values.put(ServiceDbContract.Service.COL_SERVICE_ID, serviceID);
        values.put(ServiceDbContract.Service.COL_SERVICE_PRICE,sPrice);
        values.put(ServiceDbContract.Service.COL_SERVICE_DATE,formattedDate);
        //insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ServiceDbContract.Service.TABLE_NAME,null,values);
    }


    @Override
    public int getItemCount() {
        return services.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sTitle, sPrice;
        ImageView letter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            letter = itemView.findViewById(R.id.ivServiceLetter);
            sPrice = itemView.findViewById(R.id.tvServicePrice);
            sTitle = itemView.findViewById(R.id.tvServiceTitle);
        }
    }
}
