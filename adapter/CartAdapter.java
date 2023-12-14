package com.example.unlimited_store.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unlimited_store.R;
import com.example.unlimited_store.activities.CartActivity;
import com.example.unlimited_store.dao.CartDAO;
import com.example.unlimited_store.dao.ProductDAO;
import com.example.unlimited_store.database.AppDatabase;
import com.example.unlimited_store.model.Cart;
import com.example.unlimited_store.model.Product;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    ArrayList<Cart> list;
    CartDAO cartDAO;

    int currentQuantity = 1;
    int total = 0;
    String totalText = "";
    boolean topping = false;
    boolean cream = false;


    public CartAdapter(Context context, ArrayList<Cart> list, CartDAO cartDAO) {
        this.context = context;
        this.list = list;
        this.cartDAO = cartDAO;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_cart_rcv, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        //-----------------------------------Set dữ liệu lên item----------------------------------------------
        /**
         * Name
         */
        String name = cartDAO.getProductName(list.get(holder.getAdapterPosition()).getIdProduct());
        holder.tvName.setText(name);

        /**
         * Price
         */
        int price = cartDAO.getPrice(list.get(holder.getAdapterPosition()).getIdProduct());
        String priceText = moneyText(price);
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

        int topping = list.get(holder.getAdapterPosition()).getTopping();
        String toppingText = topping == 7000 ? "topping " : "";

        holder.tvNote.setText(creamText + toppingText);

        /**
         * Quantity, Total
         */
        int quantity = list.get(holder.getAdapterPosition()).getQuantity();
        holder.tvQuantity.setText(quantity + "");

        total = list.get(holder.getAdapterPosition()).getTotal(price, topping, cream);
        totalText = moneyText(total);
        holder.tvTotal.setText(totalText);

        /**
         * Image
         */
        String image = cartDAO.getImagePath(list.get(holder.getAdapterPosition()).getIdProduct());

        //set hình ảnh được lòa từ internet lên rcv--lưu ý buộc có wifi
        Glide
                .with(holder.itemView.getContext())//lấy context
                .load(image)//lấy link ảnh
                .centerCrop()//chỉnh ảnh đúng khung
                .into(holder.ivProduct);//gắn vào imageView


        //-------------------------------------Chức năng----------------------------------------------
        /**
         * Delete
         */
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idCart = list.get(holder.getAdapterPosition()).getIdCart();
                int position = holder.getAdapterPosition();
                deleteCart(idCart, position);
            }
        });

        /**
         * Update
         */
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idProduct = list.get(holder.getAdapterPosition()).getIdProduct();
                int state = list.get(holder.getAdapterPosition()).getState();
                int topping = list.get(holder.getAdapterPosition()).getTopping();
                int extraCream = list.get(holder.getAdapterPosition()).getExtraCream();
                int quantity = list.get(holder.getAdapterPosition()).getQuantity();
                int idCart = list.get(holder.getAdapterPosition()).getIdCart();
                total = list.get(holder.getAdapterPosition()).getTotal(price, topping, cream);
                updateCart(new Cart(idCart, quantity, state, total, topping, extraCream, idProduct, AppDatabase.getUsername()));
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
     * Phương thức deleteCart
     */
    private void deleteCart(int idCart, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning");
        builder.setIcon(R.drawable.baseline_warning_24);
        builder.setMessage("Are you sure you want to delete?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = cartDAO.deleteCart(idCart);
                if (check) {
                    Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(cartDAO.getCartByUsername(AppDatabase.getUsername()));
                    notifyItemRemoved(position);
                    CartActivity.updateTotal();
                } else {
                    Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
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
     * Phương thức updateCart
     */
    private void updateCart(Cart cart) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_cart, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.MyDialogAnimation;

        //Khai báo và ánh xạ
        ImageView ivProduct = view.findViewById(R.id.ivProduct);
        ImageView ibAddQuantity = view.findViewById(R.id.ibAddQuantity);
        ImageView ibSubQuantity = view.findViewById(R.id.ibSubQuantity);
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvTotal = view.findViewById(R.id.tvPrice);
        TextView tvQuantity = view.findViewById(R.id.tvQuantity);
        RadioButton rdoHot = view.findViewById(R.id.rdoHot);
        RadioButton rdoIced = view.findViewById(R.id.rdoIced);
        CheckBox chkTopping = view.findViewById(R.id.chkTopping);
        CheckBox chkExtraCream = view.findViewById(R.id.chkExtraCream);
        Button btnConfirm = view.findViewById(R.id.btnConfirm);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        //----------------------------------Set dữ liệu lên dialog-----------------------------------
        /**
         * Image
         */
        String image = cartDAO.getImagePath(cart.getIdProduct());

        //Set hình ảnh được load từ internet lên rcv--lưu ý buộc có wifi
        Glide
                .with(context)//lấy context
                .load(image)//lấy link ảnh
                .centerCrop()//chỉnh ảnh đúng khung
                .into(ivProduct);//gắn vào imageView


        /**
         * Trả về false cho topping và cream, vì biến còn lưu lại trạng thái true của item trước đó, dẫn đến kết quả không chính xác
         */

        topping = false;
        cream = false;

        /**
         * Set dữ liệu:  name, price,, total, quanntity, hot, iced, topping, extraCream
         */
        total = cart.getTotal(cartDAO.getPrice(cart.getIdProduct()), cart.getTopping(), cart.getExtraCream());
        totalText = moneyText(total);
        tvTotal.setText(totalText + "");
        tvName.setText(cartDAO.getProductName(cart.getIdProduct()));
        tvQuantity.setText(cart.getQuantity() + "");

        rdoHot.setChecked(cart.getState() == 1 ? true : false);
        rdoIced.setChecked(cart.getState() == 2 ? true : false);

        if (cart.getTopping() == 7000) {
            chkTopping.setChecked(true);
            topping = true;
        }

        if (cart.getExtraCream() == 5000) {
            chkExtraCream.setChecked(true);
            cream = true;
        }


        /**
         * ------------------------------------Thêm giảm số lượng--------------------------------------
         */

        //Biến currentQuantity phục vụ cho addQuantity, subQuantity
        currentQuantity = cart.getQuantity();

        //Nút thêm số lượng
        ibAddQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuantity++;
                //Kiểm tra chkbox
                if (cream && topping) {
                    total = currentQuantity * cartDAO.getPrice(cart.getIdProduct()) + (currentQuantity * 12000);
                } else if (cream) {
                    total = currentQuantity * cartDAO.getPrice(cart.getIdProduct()) + (currentQuantity * 5000);
                } else if (topping) {
                    total = currentQuantity * cartDAO.getPrice(cart.getIdProduct()) + (currentQuantity * 7000);
                } else {
                    total = currentQuantity * cartDAO.getPrice(cart.getIdProduct());
                }
                totalText = moneyText(total);
                tvTotal.setText(totalText + "");
                tvQuantity.setText(currentQuantity + "");
            }
        });

        //Nút giảm số lượng
        ibSubQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Không cho xuống 0
                if (currentQuantity <= 1) {
                    Toast.makeText(context, "Please choose a quantity greater than 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                currentQuantity--;

                //Kiểm tra chkbox
                if (cream && topping) {
                    total = currentQuantity * cartDAO.getPrice(cart.getIdProduct()) + (currentQuantity * 12000);
                } else if (cream) {
                    total = currentQuantity * cartDAO.getPrice(cart.getIdProduct()) + (currentQuantity * 5000);
                } else if (topping) {
                    total = currentQuantity * cartDAO.getPrice(cart.getIdProduct()) + (currentQuantity * 7000);
                } else {
                    total = currentQuantity * cartDAO.getPrice(cart.getIdProduct());
                }
                totalText = moneyText(total);
                tvTotal.setText(totalText + "");
                tvQuantity.setText(currentQuantity + "");
            }
        });


        /**
         * ------------------------------Xử lí chkExtraCream và chkTopping------------------------------
         */
        CompoundButton.OnCheckedChangeListener checkboxChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Trường hợp chkTopping và chkExtraCream được chọn
                if (chkTopping.isChecked() && topping == false && chkExtraCream.isChecked() && cream == false) {
                    total = total + (currentQuantity * 12000);
                    topping = true;
                    cream = true;
                }
                //Trường hợp chkExtraCream được chọn
                else if (chkExtraCream.isChecked() && cream == false) {
                    total = total + (currentQuantity * 5000);
                    cream = true;
                }
                //Trường hợp chkTopping được chọn
                else if (chkTopping.isChecked() && topping == false) {
                    total = total + (currentQuantity * 7000);
                    topping = true;
                }
                //Trường hợp chkExtraCream bỏ chọn
                else if (!chkExtraCream.isChecked() && cream == true) {
                    total = total - (currentQuantity * 5000);
                    cream = false;
                }
                //Trường hợp chkTopping bỏ chọn
                else if (!chkTopping.isChecked() && topping == true) {
                    total = total - (currentQuantity * 7000);
                    topping = false;
                }
                totalText = moneyText(total);
                tvTotal.setText(totalText + "");
            }
        };
        chkExtraCream.setOnCheckedChangeListener(checkboxChangeListener);
        chkTopping.setOnCheckedChangeListener(checkboxChangeListener);

        /**
         * Nút confirm
         */
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.valueOf(tvQuantity.getText().toString());
                int idCart = cart.getIdCart();
                int idProduct = cart.getIdProduct();
                int topping = chkTopping.isChecked() ? 7000 : 0;
                int extraCream = chkExtraCream.isChecked() ? 5000 : 0;

                if (!rdoHot.isChecked() && !rdoIced.isChecked()) {
                    Toast.makeText(context, "Please choose your drink state(Hot, Iced)", Toast.LENGTH_SHORT).show();
                    return;
                }
                int state = rdoHot.isChecked() ? 1 : rdoIced.isChecked() ? 2 : 0;

                Cart cart = new Cart(idCart, quantity, state, total, topping, extraCream, idProduct, AppDatabase.getUsername());
                boolean check = cartDAO.updateCart(cart);
                if (check) {
                    Toast.makeText(context, "Order confirmation successful!", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(cartDAO.getCartByUsername(AppDatabase.getUsername()));
                    notifyDataSetChanged();
                    CartActivity.updateTotal();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Order confirmation failed!!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        /**
         * Nút Cancel
         */
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    /**
     * Phương thức chuyển tiền 3000 sang 3.000 VND
     */
    public static String moneyText(int moneyNumber) {
        String moneyText = String.valueOf(moneyNumber);
        int dots = (moneyText.length() - 1) / 3;
//        if (moneyText.length() % 3 == 0) {
//            dots = moneyText.length() / 3 - 1;
//        }
        int lengthArray = moneyText.length() + dots;
        String[] array = new String[lengthArray];
        int count = 0;
        int index = moneyText.length() - 1;

        for (int i = lengthArray - 1; i >= 0; i--) {
            count++;
            if (count == 4) {
                array[i] = ".";
                count = 0;
            } else {
                if (index >= 0) {
                    array[i] = moneyText.charAt(index--) + "";
                } else {
                    break;
                }
            }
        }

        String result = "";
        for (int i = 0; i < array.length; i++) {
            result += array[i];
        }
        return result + " VND";
    }
}
