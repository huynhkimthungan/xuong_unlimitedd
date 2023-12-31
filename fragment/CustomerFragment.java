package com.example.unlimited_store.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unlimited_store.R;
import com.example.unlimited_store.adapter.CustomerAdapter;
import com.example.unlimited_store.dao.UserDAO;
import com.example.unlimited_store.model.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerFragment extends Fragment {
    View view;
    private RecyclerView rcvCustomer;
    UserDAO userDAO;
    ArrayList<User> list;
    CustomerAdapter customerAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CustomerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Customer.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerFragment newInstance(String param1, String param2) {
        CustomerFragment fragment = new CustomerFragment();
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
         * Hiển thị RecyclerView
         */
        view = inflater.inflate(R.layout.fragment_customer, container, false);
        rcvCustomer = view.findViewById(R.id.rcvCustomer);

        userDAO = new UserDAO(getContext());
        list = new ArrayList<>();
        list.addAll(userDAO.getAllCustomer());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvCustomer.setLayoutManager(linearLayoutManager);

        customerAdapter = new CustomerAdapter(getContext(), list, userDAO);
        rcvCustomer.setAdapter(customerAdapter);
        return view;
    }
}