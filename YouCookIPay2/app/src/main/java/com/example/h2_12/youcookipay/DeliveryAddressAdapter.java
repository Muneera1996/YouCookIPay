package com.example.h2_12.youcookipay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DeliveryAddressAdapter extends RecyclerView.Adapter<DeliveryAddressAdapter.DeliveryAddressViewHolder>{
    private Context context;
    private ArrayList<String> street;
    private ArrayList<String> areas;
    private ArrayList<String> cities;
    private ArrayList<String> codes;

    public DeliveryAddressAdapter(Context context,ArrayList<String> street, ArrayList<String> areas, ArrayList<String> cities, ArrayList<String> codes) {
        this.context=context;
        this.street=street;
        this.areas=areas;
        this.cities=cities;
        this.codes=codes;
    }

    @NonNull
    @Override
    public DeliveryAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.custom_previous_address,parent,false);
        return new DeliveryAddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryAddressViewHolder holder, int position) {

            holder.text1.setText(Integer.toString(position+1));
            String address=street.get(position) + " " + areas.get(position)+ " "+cities.get(position)+ " " + codes.get(position);
            holder.text2.setText(address);

    }

    @Override
    public int getItemCount() {
        return street.size();
    }

    public class DeliveryAddressViewHolder extends RecyclerView.ViewHolder {
        TextView text1,text2;

        public DeliveryAddressViewHolder(View itemView) {
            super(itemView);
            text1=itemView.findViewById(R.id.place_holder);
            text2=itemView.findViewById(R.id.address);
        }
    }


    }
