package com.example.unlimited_store.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unlimited_store.R;
import com.example.unlimited_store.dao.FeedbackDAO;
import com.example.unlimited_store.model.Feedback;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    Context context;
    ArrayList<Feedback> list;
    FeedbackDAO feedbackDAO;

    public FeedbackAdapter(Context context, ArrayList<Feedback> list, FeedbackDAO feedbackDAO) {
        this.context = context;
        this.list = list;
        this.feedbackDAO = feedbackDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_feedback_admin,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvFeedback.setText(list.get(holder.getAdapterPosition()).getContent());
        holder.tvUsername.setText(list.get(holder.getAdapterPosition()).getUsername());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFeedback;
        TextView tvUsername;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFeedback = itemView.findViewById(R.id.tvFeedback);
            tvUsername = itemView.findViewById(R.id.tvUsername);
        }
    }
}
