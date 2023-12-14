package com.example.unlimited_store.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unlimited_store.R;
import com.example.unlimited_store.dao.UserDAO;
import com.example.unlimited_store.model.User;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    Context context;
    ArrayList<User> list;
    UserDAO userDAO;

    public CustomerAdapter(Context context, ArrayList<User> list, UserDAO userDAO) {
        this.context = context;
        this.list = list;
        this.userDAO = userDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_customer, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Hiển thị dữ liệu lên giao diện quản lí Customer
        holder.tvCustomer.setText(list.get(holder.getAdapterPosition()).getUsername());
        holder.tvPhoneNumber.setText(list.get(holder.getAdapterPosition()).getPhoneNumber());
        holder.tvEmail.setText(list.get(holder.getAdapterPosition()).getEmail());
        holder.tvAdress.setText(list.get(holder.getAdapterPosition()).getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCustomer, tvPhoneNumber, tvEmail, tvAdress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCustomer = itemView.findViewById(R.id.tvCustomer);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvAdress = itemView.findViewById(R.id.tvAdress);
        }
    }
}
