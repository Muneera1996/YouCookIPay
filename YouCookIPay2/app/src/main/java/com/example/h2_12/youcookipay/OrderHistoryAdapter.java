package com.example.h2_12.youcookipay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {
    private Context context;
    private ArrayList<OrderHistory> historyList;

    public OrderHistoryAdapter(Context context, ArrayList<OrderHistory> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.custom_order_history,parent,false);
        return new OrderHistoryViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        final OrderHistory orderHistory=historyList.get(position);
        holder.text1.setText(orderHistory.getOrderId());
        holder.text2.setText(orderHistory.getChefId());
        holder.text3.setText(orderHistory.getUserId());
        holder.text4.setText(orderHistory.getMealId());
        holder.text5.setText(orderHistory.getMealName());
        holder.text6.setText(orderHistory.getMealClassification());
        holder.text7.setText(orderHistory.getMealType());
        holder.text8.setText(orderHistory.getMealCategory());
        holder.text9.setText(orderHistory.getPortionPrice());
        holder.text10.setText(orderHistory.getQuantity());
        holder.text11.setText(orderHistory.getTotalCost());
        holder.text12.setText(orderHistory.getServiceCharges());
        holder.text13.setText(orderHistory.getDeliveryCharges());
        holder.text14.setText(orderHistory.getGrandTotal());
        holder.text15.setText(orderHistory.getUserName());
        holder.text16.setText(orderHistory.getUserEmail());
        holder.text17.setText(orderHistory.getUserContact());
        holder.text18.setText(orderHistory.getDate());
        holder.text19.setText(orderHistory.getTime());
        holder.text20.setText(orderHistory.getDeliveryOption());
        holder.text21.setText(orderHistory.getStreet());
        holder.text22.setText(orderHistory.getCity());
        holder.text23.setText(orderHistory.getArea());
        holder.text24.setText(orderHistory.getPaymentMethod());
        holder.text25.setText(orderHistory.getTransactionId());
        holder.text26.setText(orderHistory.getOrderStatus());
        holder.text27.setText(orderHistory.getChefName());
        }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView text1,text2,text3,text4,text5,text6,text9,text7,text8,text10,text11,text12,text13,text14,text15,text16,text17,text18,text19,text20,text21,text22,text23,text24,text25,text26,text27;
        public OrderHistoryViewHolder(View itemView) {
            super(itemView);
            text1=itemView.findViewById(R.id.order_id);
            text2=itemView.findViewById(R.id.chef_id);
            text3=itemView.findViewById(R.id.user_id);
            text4=itemView.findViewById(R.id.meal_id);
            text5=itemView.findViewById(R.id.meal_name);
            text6=itemView.findViewById(R.id.meal_classification);
            text7=itemView.findViewById(R.id.meal_type);
            text8=itemView.findViewById(R.id.meal_category);
            text9=itemView.findViewById(R.id.portion_price);
            text10=itemView.findViewById(R.id.quantity);
            text11=itemView.findViewById(R.id.total_quantity_cost);
            text12=itemView.findViewById(R.id.service_charges);
            text13=itemView.findViewById(R.id.delivery_charges);
            text14=itemView.findViewById(R.id.grand_total);
            text15=itemView.findViewById(R.id.user_name);
            text16=itemView.findViewById(R.id.user_email);
            text17=itemView.findViewById(R.id.user_contact);
            text18=itemView.findViewById(R.id.date);
            text19=itemView.findViewById(R.id.time);
            text20=itemView.findViewById(R.id.delivery_option);
            text21=itemView.findViewById(R.id.street);
            text22=itemView.findViewById(R.id.city);
            text23=itemView.findViewById(R.id.area);
            text24=itemView.findViewById(R.id.payment_method);
            text25=itemView.findViewById(R.id.transaction_id);
            text26=itemView.findViewById(R.id.order_status);
            text27=itemView.findViewById(R.id.chef_name);


        }
    }

}
