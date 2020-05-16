package com.gmail.yarolave.lesson;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactDetailsFragment extends Fragment {

    private int paramId;


    public ContactDetailsFragment() {
        // Required empty public constructor
    }

    public static ContactDetailsFragment newInstance(int paramId) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("paramId", paramId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        setArguments(mainActivity.getContactDetails());
        if (getArguments() != null) {
            paramId = getArguments().getInt("paramId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_details, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setTitle("Детали контакта");
        setResources(view);
        return view;
    }

    private void setResources(View view) {
        if (getArguments() != null) {
            ImageView photo = view.findViewById(R.id.avatar);
            photo.setImageResource(getArguments().getInt("photo"));
            TextView name = view.findViewById(R.id.name);
            name.setText(getArguments().getString("name"));
            TextView number1 = view.findViewById(R.id.firstNumber);
            number1.setText(getArguments().getString("number1"));
            TextView number2 = view.findViewById(R.id.secondNumber);
            number2.setText(getArguments().getString("number2"));
            TextView email1 = view.findViewById(R.id.firstEmail);
            email1.setText(getArguments().getString("email1"));
            TextView email2 = view.findViewById(R.id.secondEmail);
            email2.setText(getArguments().getString("email2"));
            TextView description = view.findViewById(R.id.description);
            description.setText(getArguments().getString("description"));
        }
    }
}
