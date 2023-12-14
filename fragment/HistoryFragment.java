package com.example.unlimited_store.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.unlimited_store.R;
import com.example.unlimited_store.adapter.HistoryAdapter;
import com.example.unlimited_store.dao.HistoryDAO;
import com.example.unlimited_store.database.AppDatabase;
import com.example.unlimited_store.model.History;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    RecyclerView rcvHistory;
    HistoryDAO historyDAO;
    ArrayList<History> listHistory;
    HistoryAdapter historyAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listHistory = new ArrayList<>();
        historyDAO = new HistoryDAO(requireContext());
        listHistory.addAll(historyDAO.getHistoryByUsername(AppDatabase.getUsername()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Hiển thị lịch sử sản phẩm
         */
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        rcvHistory = view.findViewById(R.id.rcvList);
        rcvHistory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        historyAdapter = new HistoryAdapter(requireContext(), listHistory, historyDAO);
        rcvHistory.setAdapter(historyAdapter);
        return view;
    }
}