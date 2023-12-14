package com.example.unlimited_store.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unlimited_store.R;
import com.example.unlimited_store.activities.CartActivity;
import com.example.unlimited_store.dao.HistoryDAO;
import com.example.unlimited_store.database.AppDatabase;
import com.example.unlimited_store.model.History;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private final Context context;
    ArrayList<History> list;
    HistoryDAO historyDAO;
    int total = 0;
    String totalText = "";

    public HistoryAdapter(Context context, ArrayList<History> list, HistoryDAO historyDAO) {
        this.context = context;
        this.list = list;
        this.historyDAO = historyDAO;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_cart_rcv, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        /**
         * -----------------------------Set dữ liệu lên item----------------------------------------------
         */

        /**
         * Name
         */
        String name = historyDAO.getProductName(list.get(holder.getAdapterPosition()).getIdProduct());
        holder.tvName.setText(name);

        /**
         * Price
         */
        int price = historyDAO.getPrice(list.get(holder.getAdapterPosition()).getIdProduct());
        String priceText = CartAdapter.moneyText(price);
        holder.tvPrice.setText(priceText + "");

        /**
         * State
         */
        //Quy tắc: 1 là hot, 2 là iced
        int state = list.get(holder.getAdapterPosition()).getState();
        String stateText = state == 1 ? "Hot" : state == 2 ? "Iced" : "";
        holder.tvState.setText(stateText);

        /**
         * Note (cream, topping)
         */
        int cream = list.get(holder.getAdapterPosition()).getExtraCream();
        String creamText = cream == 5000 ? "cream " : "";

        //1 là không có Topping, 2 là có Topping
        int topping = list.get(holder.getAdapterPosition()).getTopping();
        String toppingText = topping == 7000 ? "topping " : "";

        holder.tvNote.setText(creamText + toppingText);

        /**
         * Quantity
         */
        int quantity = list.get(holder.getAdapterPosition()).getQuantity();
        holder.tvQuantity.setText(quantity + "");

        /**
         * Total
         */
        total = list.get(holder.getAdapterPosition()).getTotal();
        totalText = CartAdapter.moneyText(total);
        holder.tvTotal.setText(totalText);

        /**
         * Image
         */
        String image = historyDAO.getImagePath(list.get(holder.getAdapterPosition()).getIdProduct());

        //set hình ảnh được lòa từ internet lên rcv--lưu ý buộc có wifi
        Glide
                .with(holder.itemView.getContext())//lấy context
                .load(image)//lấy link ảnh
                .centerCrop()//chỉnh ảnh đúng khung
                .into(holder.ivProduct);//gắn vào imageView

        /**
         * -------------------------------CHỨC NĂNG----------------------------------
         */

        /**
         * Delete
         */
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                int idHistory = list.get(holder.getAdapterPosition()).getIdHistory();
                deleteHistory(idHistory, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct, ivDelete;
        TextView tvName, tvPrice, tvQuantity, tvTotal, tvState, tvNote;
        RelativeLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvState = itemView.findViewById(R.id.tvState);
            tvNote = itemView.findViewById(R.id.tvNote);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            item = itemView.findViewById(R.id.item);
        }
    }

    /**
     * Phương thức xóa item history
     */
    private void deleteHistory(int idHistory, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning");
        builder.setIcon(R.drawable.baseline_warning_24);
        builder.setMessage("Are you sure you want to delete?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                boolean check = historyDAO.deleteHistory(idHistory);
                if (check) {
                    Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(historyDAO.getHistoryByUsername(AppDatabase.getUsername()));
                    notifyItemRemoved(position);
                } else {
                    Toast.makeText(context, "Delete failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.MyDialogAnimation;
        alertDialog.show();
    }
}
