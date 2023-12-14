package com.example.unlimited_store.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unlimited_store.R;
import com.example.unlimited_store.activities.LoginActivity;
import com.example.unlimited_store.dao.CartDAO;
import com.example.unlimited_store.dao.ProductDAO;
import com.example.unlimited_store.dao.UserDAO;
import com.example.unlimited_store.database.AppDatabase;
import com.example.unlimited_store.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    ArrayList<Product> list;
    ProductDAO productDAO;
    CartDAO cartDAO;


    public ProductAdapter(Context context, ArrayList<Product> list, ProductDAO productDAO) {
        this.context = context;
        this.list = list;
        this.productDAO = productDAO;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product_rcv, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        /**
         * Set dữ liệu lên item
         */
        holder.tvCoffeeName.setText(list.get(holder.getAdapterPosition()).getName());
        int price = list.get(holder.getAdapterPosition()).getPrice();
        String priceText = CartAdapter.moneyText(price);
        holder.tvCoffeePrice.setText(priceText);

        /**
         * Khởi tạo userDAO
         */
        UserDAO userDAO = new UserDAO(context);

        /**
         * Image
         */
        //set hình ảnh được lòa từ internet lên rcv--lưu ý buộc có wifi
        Glide
                .with(holder.itemView.getContext())//lấy context
                .load(list.get(position).getImage())//lấy link ảnh
                .centerCrop()//chỉnh ảnh đúng khung
                .into(holder.ivCoffee);//gắn vào imageView

        /**
         * ivAddCart
         */
        holder.ivAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiểm tra đã đăng nhập chưa?
                if(!AppDatabase.getLoginState()){
                    Toast.makeText(context, "Please login before adding products!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Nếu username là admin không thể thêm vào Cart
                else if (userDAO.getRoleByUsername(AppDatabase.getUsername()) == 1) {
                    Toast.makeText(context, "You are not allowed to access!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Thêm dữ liệu vào Cart
                cartDAO = new CartDAO(context);
                cartDAO.addCart(list.get(holder.getAdapterPosition()).getIdProduct(), 1, AppDatabase.getUsername());
                Toast.makeText(context, "Add to cart successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * Dialog hiển thị thông tin sản phẩm
         */
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String image = list.get(holder.getAdapterPosition()).getImage();
                String name = list.get(holder.getAdapterPosition()).getName();
                int price = list.get(holder.getAdapterPosition()).getPrice();
                String description = list.get(holder.getAdapterPosition()).getDescription();
                Product product = new Product(image,name,price, description);
                showDetailedProduct(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCoffee, ivAddCart;
        TextView tvCoffeeName, tvCoffeePrice;
        LinearLayout item;

        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCoffee = itemView.findViewById(R.id.imgProduct);
            tvCoffeeName = itemView.findViewById(R.id.tvProductName);
            tvCoffeePrice = itemView.findViewById(R.id.tvProductPrice);
            item = itemView.findViewById(R.id.item);
            ivAddCart = itemView.findViewById(R.id.ivAddCart);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    /**
     * Phương thức hiển thị chi tiết sản phẩm
     */
    private void showDetailedProduct(Product product){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_show_product, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.MyDialogAnimation;

        /**
         * Khai báo và ánh xạ
         */
        ImageView ivProduct = view.findViewById(R.id.ivProduct);
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvPrice = view.findViewById(R.id.tvPrice);
        TextView tvDescription = view.findViewById(R.id.tvDescription);
        ImageView btnCancel = view.findViewById(R.id.btnCancel);

        /**
         * Set image
         */
        Glide
                .with(context)//lấy context
                .load(product.getImage())//lấy link ảnh
                .centerCrop()//chỉnh ảnh đúng khung
                .into(ivProduct);//gắn vào imageView

        /**
         * Set name, price, description
         */
        tvName.setText(product.getName());
        String priceText = CartAdapter.moneyText(product.getPrice());
        tvPrice.setText(priceText);
        tvDescription.setText(product.getDescription());

        /**
         * Nút cancel
         */
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
