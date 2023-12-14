package com.example.unlimited_store.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unlimited_store.R;
import com.example.unlimited_store.dao.CartDAO;
import com.example.unlimited_store.dao.ProductDAO;
import com.example.unlimited_store.model.Product;

import java.util.ArrayList;

public class ProductManAdapter extends RecyclerView.Adapter<ProductManAdapter.ViewHolder> {
    Context context;
    ArrayList<Product> list;
    ProductDAO productDAO;

    public ProductManAdapter(Context context, ArrayList<Product> list, ProductDAO productDAO) {
        this.context = context;
        this.list = list;
        this.productDAO = productDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_productman_rcv, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /**
         * Set name, price
         */
        holder.tvProductManName.setText(list.get(holder.getAdapterPosition()).getName());
        String priceText = CartAdapter.moneyText(list.get(holder.getAdapterPosition()).getPrice());
        holder.tvProductManPrice.setText(priceText);

        /*
         * Set image
         */
        Glide
                .with(holder.itemView.getContext())//lấy context
                .load(list.get(position).getImage())//lấy link ảnh
                .centerCrop()//chỉnh ảnh đúng khung
                .into(holder.ivProductMan);//gắn vào imageView

        /**
         * Nút delete
         */
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idProduct = list.get(holder.getAdapterPosition()).getIdProduct();
                int position = holder.getAdapterPosition();
                deleteProduct(idProduct, position);
            }
        });

        /**
         * Nút Update
         */
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                updateProduct(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductMan;
        TextView tvProductManName, tvProductManPrice;
        Button btnUpdate, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductMan = itemView.findViewById(R.id.ivProductMan);
            tvProductManName = itemView.findViewById(R.id.tvProductManName);
            tvProductManPrice = itemView.findViewById(R.id.tvProductManPrice);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    /**
     * Phương thức xóa sản phẩm
     */
    private void deleteProduct(int idProduct, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning");
        builder.setIcon(R.drawable.warning);
        builder.setMessage("Are you sure you want to delete? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    int id = list.get(position).getIdProduct();
                    boolean check = productDAO.deleteProduct(id);
                    if (check) {
                        Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show();
                        list.clear();
                        list.addAll(productDAO.getAllProduct());
                        notifyItemRemoved(position);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }catch (Exception e){
                    Toast.makeText(context, "The product is being traded or stored in the purchase history\n" +
                            "Currently cannot be deleted!", Toast.LENGTH_LONG).show();
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

    /**
     * Phương thức cập nhập sản phẩm
     */
    private void updateProduct(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_product, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.MyDialogAnimation;

        /**
         * Khai báo và ánh xạ
         */
        EditText edtDALName = view.findViewById(R.id.edtDALName);
        EditText edtDALPrice = view.findViewById(R.id.edtDALPrice);
        EditText edtDALDescription = view.findViewById(R.id.edtDALDescription);
        EditText edtDALImage = view.findViewById(R.id.edtDALImage);
        Button btnSave = view.findViewById(R.id.btnDALSave);
        Button btnCancel = view.findViewById(R.id.btnDALCancel);

        /**
         * Set name, price, description, image
         */
        edtDALName.setText(list.get(position).getName());
        edtDALPrice.setText(list.get(position).getPrice() + "");
        edtDALDescription.setText(list.get(position).getDescription());
        edtDALImage.setText(list.get(position).getImage());

        /**
         * Nút Save
         */
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lấy dữ liệu người dùng nhập vào
                String name = edtDALName.getText().toString();
                String price = edtDALPrice.getText().toString();
                String description = edtDALDescription.getText().toString();
                String image = edtDALImage.getText().toString();

                //Tạo 1 đối tượng sách mới
                Product product = new Product();
                product.setIdProduct(list.get(position).getIdProduct());
                product.setName(name);
                product.setPrice(Integer.parseInt(price));
                product.setImage(image);

                boolean check = productDAO.updateProduct(product);
                if (check) {
                    Toast.makeText(context, "Updated successfully!", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(productDAO.getAllProduct());
                    notifyDataSetChanged();

                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }

            }
        });

        /**
         * Nút Canel trong dialog
         */
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
