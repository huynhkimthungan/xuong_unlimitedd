package com.example.unlimited_store.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unlimited_store.R;
import com.example.unlimited_store.adapter.ProductAdapter;
import com.example.unlimited_store.dao.ProductDAO;
import com.example.unlimited_store.model.Product;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ImageView ivCoffee, ivJuice, ivBeerWine, ivTea;
    SearchView svProduct;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    ProductDAO productDAO;
    ArrayList<Product> list;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        productDAO = new ProductDAO(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        /**
         * Ánh xạ
         */
        ivCoffee = view.findViewById(R.id.ivCoffee);
        ivJuice = view.findViewById(R.id.ivJuice);
        ivBeerWine = view.findViewById(R.id.ivBeerWine);
        ivTea = view.findViewById(R.id.ivTea);
        recyclerView = view.findViewById(R.id.rcvList);
        svProduct = view.findViewById(R.id.svProduct);

        /**
         * Hiện thị tất cả sẩn phẩm lên RecyclerView
         */
        productDAO = new ProductDAO(getContext());
        list = new ArrayList<>();
        list = productDAO.getAllProduct();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        productAdapter = new ProductAdapter(getContext(), list, productDAO);
        recyclerView.setAdapter(productAdapter);

        /**
         * Sự kiện ivCoffee
         */
        ivCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClick(v, ivCoffee);

                list.clear();
                list.addAll(productDAO.getAllCoffee());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                recyclerView.setLayoutManager(gridLayoutManager);
                productAdapter = new ProductAdapter(requireContext(), list, productDAO);
                recyclerView.setAdapter(productAdapter);
            }
        });

        /**
         * Sự kiện ivJuice
         */
        ivJuice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClick(v, ivJuice);

                list.clear();
                list.addAll(productDAO.getAllJuice());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                recyclerView.setLayoutManager(gridLayoutManager);
                productAdapter = new ProductAdapter(requireContext(), list, productDAO);
                recyclerView.setAdapter(productAdapter);
            }
        });

        /**
         * Sự kiện ivTea
         */
        ivTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClick(v, ivTea);

                list.clear();
                list.addAll(productDAO.getAllTea());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                recyclerView.setLayoutManager(gridLayoutManager);
                productAdapter = new ProductAdapter(requireContext(), list, productDAO);
                recyclerView.setAdapter(productAdapter);
            }
        });

        /**
         * Sự kiện ivBeerWine
         */
        ivBeerWine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClick(v, ivBeerWine);

                list.clear();
                list.addAll(productDAO.getAllBeer());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                recyclerView.setLayoutManager(gridLayoutManager);
                productAdapter = new ProductAdapter(requireContext(), list, productDAO);
                recyclerView.setAdapter(productAdapter);
            }
        });

        /**
         * Search product
         */
        svProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                productAdapter = new ProductAdapter(getContext(), list1, productDAO);
                recyclerView.setAdapter(productAdapter);
                return false;
            }
        });
        return view;
    }

    public void onImageClick(View view, ImageView ivImage) {
        // Thực hiện animation khi click
        Animation rotateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_animation);
        ivImage.startAnimation(rotateAnimation);
    }
}

