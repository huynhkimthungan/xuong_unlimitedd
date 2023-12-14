package com.example.unlimited_store.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unlimited_store.R;
import com.example.unlimited_store.adapter.ProductManAdapter;
import com.example.unlimited_store.dao.ProductDAO;
import com.example.unlimited_store.model.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsManagementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsManagementFragment extends Fragment {
    private RecyclerView rcvProductMan;
    SearchView svProductMan;
    View view;
    ImageView ivAddProduct;
    ProductDAO productDAO;
    ArrayList<Product> list;
    ProductManAdapter productManAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductsManagementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Products.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsManagementFragment newInstance(String param1, String param2) {
        ProductsManagementFragment fragment = new ProductsManagementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Hiện thị sản phẩm lên RecyclerView
         */
        view = inflater.inflate(R.layout.fragment_products_management, container, false);
        rcvProductMan = view.findViewById(R.id.rcvProductMan);
        ivAddProduct = view.findViewById(R.id.ivAddProduct);

        productDAO = new ProductDAO(getContext());
        list = new ArrayList<>();
        list = productDAO.getAllProduct();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvProductMan.setLayoutManager(linearLayoutManager);

        productManAdapter = new ProductManAdapter(getContext(), list, productDAO);
        rcvProductMan.setAdapter(productManAdapter);

        /**
         * Sự kiện thêm sản phẩm
         */
        ivAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        /**
         * Search product
         */
        svProductMan = view.findViewById(R.id.svProductMan);
        svProductMan.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Product> list1 = new ArrayList<>();
                for (Product product : productDAO.getAllProduct()) {//phải gọi hàm
                    if (product.getName().toLowerCase().contains(newText.toLowerCase())) {
                        list1.add(product);
                    }
                }
                productManAdapter = new ProductManAdapter(getContext(), list1, productDAO);
                rcvProductMan.setAdapter(productManAdapter);
                return false;
            }
        });

        return view;
    }

    /**
     * Phương thức thêm sản phẩm
     */
    private void addProduct(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater1 = ((Activity) getActivity()).getLayoutInflater();
        View view = inflater1.inflate(R.layout.dialog_add_product, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.MyDialogAnimation;

        //Ánh xạ
        EditText edtName = view.findViewById(R.id.edtDALName);
        EditText edtPrice = view.findViewById(R.id.edtDALPrice);
        EditText edtDescription = view.findViewById(R.id.edtDALDescription);
        EditText edtImage = view.findViewById(R.id.edtDALImage);
        Spinner spnType = view.findViewById(R.id.spnType);

        Button btnCancel = view.findViewById(R.id.btnDALCancel);
        Button btnSave = view.findViewById(R.id.btnDALSave);

        //Đưa các option lên spinner
        String[] data = {"Coffee", "Juice", "Tea", "Beer & Wine"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, data);
        // Thiết lập loại dropdown cho Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Gán Adapter cho Spinner
        spnType.setAdapter(adapter);

        //Bắt sự kiện cho btnThemSachDAL
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String name = edtName.getText().toString();
                    String priceText = edtPrice.getText().toString();
                    String description = edtDescription.getText().toString();
                    String image = edtImage.getText().toString();
                    String type = spnType.getSelectedItem().toString();

                    Glide.with(view.getContext()).load(image);

                    //Kiểm tra nhập liệu
                    if (name.equals("") || priceText.equals("") || image.equals("") || description.equals("")) {
                        Toast.makeText(getContext(), "Please complete all information!", Toast.LENGTH_SHORT).show();
                    } else {
                        //Tạo 1 đối tượng sách mới
                        int price = Integer.valueOf(edtPrice.getText().toString());
                        Product product = new Product(image, name, price, description, type);

                        //gọi hàm them sách từ sachDAO
                        boolean check = productDAO.insertProduct(product);
                        if (check) {
                            Toast.makeText(getContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(productDAO.getAllProduct());
                            productManAdapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Add failed!!!", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(requireContext(), "Please check the information again!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}

