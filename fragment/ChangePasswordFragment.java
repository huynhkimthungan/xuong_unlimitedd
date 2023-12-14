package com.example.unlimited_store.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.unlimited_store.R;
import com.example.unlimited_store.activities.LoginActivity;
import com.example.unlimited_store.activities.RegisterActivity;
import com.example.unlimited_store.dao.UserDAO;
import com.example.unlimited_store.database.AppDatabase;
import com.google.android.material.textfield.TextInputEditText;


public class ChangePasswordFragment extends Fragment {

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        /**
         * Khai báo và ánh xạ
         */
        Button btnChangePassword = view.findViewById(R.id.btnChangePassword);
        TextInputEditText edtOldPassword = view.findViewById(R.id.edtOldPassword);
        TextInputEditText edtNewPassword = view.findViewById(R.id.edtNewPassword);
        TextInputEditText edtConfirmPassword = view.findViewById(R.id.edtConfirmPassword);

        /**
         * Nút changePassword
         */
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = edtOldPassword.getText().toString();
                String newPassword = edtNewPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();

                if(oldPassword.length() == 0 || newPassword.length() == 0 || confirmPassword.length() == 0){
                    Toast.makeText(requireContext(), "Please complete all information!", Toast.LENGTH_SHORT).show();
                }else{
                    UserDAO userDAO = new UserDAO(requireContext());
                    String username = AppDatabase.getUsername();
                    boolean check = userDAO.isMatched(username, oldPassword);
                    if(check){
                        if (RegisterActivity.isValid(newPassword)){
                            if(newPassword.equals(confirmPassword)){
                                Toast.makeText(requireContext(), "Changed password successfully", Toast.LENGTH_SHORT).show();
                                userDAO.updatePassword(newPassword, username, userDAO.getRoleByUsername(username));
                                edtNewPassword.setText("");
                                edtOldPassword.setText("");
                                edtConfirmPassword.setText("");
                            }else{
                                Toast.makeText(requireContext(), "Password confirmation is incorrect!!!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(requireContext(), "Password must be \ngreater than 8 characters\n including numbers\n capital letters, and special characters\nEx: PuPuChaCha@9389", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(requireContext(), "Old password is incorrect. Please login again!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;    }
}