package com.dannextech.apps.u_cleaner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MakeRequestAdapter extends RecyclerView.Adapter<MakeRequestAdapter.ViewHolder> {
    private MakeRequestModel[] payments;
    private Context context;

    public MakeRequestAdapter(MakeRequestModel[] payments) {
        this.payments = payments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.payment_details,viewGroup,false);

        return new MakeRequestAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String name = payments[i].getName();
        int price = payments[i].getPrice();

        viewHolder.sTitle.setText(name);
        viewHolder.sPrice.setText(String.valueOf(price));
    }

    @Override
    public int getItemCount() {
        return payments.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sTitle, sPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sPrice = itemView.findViewById(R.id.tvPayPrice);
            sTitle = itemView.findViewById(R.id.tvPayTitle);
        }
    }
}
