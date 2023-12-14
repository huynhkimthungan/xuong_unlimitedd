package com.example.unlimited_store.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.unlimited_store.R;
import com.example.unlimited_store.activities.LoginActivity;
import com.example.unlimited_store.dao.FeedbackDAO;
import com.example.unlimited_store.database.AppDatabase;
import com.example.unlimited_store.model.Feedback;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedbackUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedbackUserFragment extends Fragment {
    View view;
    ArrayList<Feedback> list;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedbackUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedbackUser.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedbackUserFragment newInstance(String param1, String param2) {
        FeedbackUserFragment fragment = new FeedbackUserFragment();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feedback_user, container, false);

        EditText edtFeedbackUser = view.findViewById(R.id.edtFeedbackUser);

        /**
         * Nút send
         */
        Button btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _feedback = edtFeedbackUser.getText().toString();
                if (_feedback.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter your comment!!!", Toast.LENGTH_SHORT).show();
                } else {
                    Feedback feedback = new Feedback(_feedback, AppDatabase.getUsername());

                    FeedbackDAO feedbackDAO = new FeedbackDAO(getContext());
                    boolean check = feedbackDAO.insertFeedback(feedback);
                    if (check) {
                        Toast.makeText(getContext(), "Thank you for your comment", Toast.LENGTH_SHORT).show();
                        edtFeedbackUser.setText("");
                    } else {
                        Toast.makeText(requireContext(), "Giving feedback failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }
}