package com.example.unlimited_store.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unlimited_store.R;
import com.example.unlimited_store.activities.LoginActivity;
import com.example.unlimited_store.activities.RegisterActivity;
import com.example.unlimited_store.dao.UserDAO;
import com.example.unlimited_store.database.AppDatabase;
import com.example.unlimited_store.model.User;
import com.google.android.material.textfield.TextInputEditText;


public class ProfileFragment extends Fragment {
    View view;
    UserDAO userDAO;
    String username;

    public ProfileFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        /**
         * Khai báo và ánh xạ
         */
        TextView tvUsername = view.findViewById(R.id.tvUsernameProfile);
        TextInputEditText edtEmail = view.findViewById(R.id.edtEmailProfile);
        TextInputEditText edtPhoneNumber = view.findViewById(R.id.edtPhoneNumberProfile);
        TextInputEditText edtAddress = view.findViewById(R.id.edtAddressProfile);
        Button btnConfirm = view.findViewById(R.id.btnConfirmProfile);

        username = AppDatabase.getUsername();

        /**
         * Khởi tạo lớp userDAO
         */
        userDAO = new UserDAO(requireContext());

        /**
         * Set dữ liệu username, address, email, phone
         */
        tvUsername.setText(username);
        edtAddress.setText(userDAO.getAdressByUsername(username));
        edtEmail.setText(userDAO.getEmailByUsername(username));
        edtPhoneNumber.setText(userDAO.getPhoneNumberByUsername(username));

        /**
         * Nút confirm, cập nhập
         */
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String phoneNumber = edtPhoneNumber.getText().toString();
                String address = edtAddress.getText().toString();
                if (email.equals("") || phoneNumber.equals("") || address.equals("")) {
                    Toast.makeText(getContext(), "Please complete all information!", Toast.LENGTH_SHORT).show();
                } else if (!(phoneNumber.length() == 10)) {
                    Toast.makeText(getContext(), "Phone number must be 10 characters", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getContext(), "Enter valid Email address !", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(username, phoneNumber, email, address);
                    boolean check = userDAO.updateProfile(user);
                    if (check) {
                        Toast.makeText(requireContext(), "Updated profile successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Update profile failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        return view;
    }
}