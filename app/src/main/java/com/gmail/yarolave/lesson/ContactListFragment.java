package com.gmail.yarolave.lesson;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactListFragment extends Fragment implements View.OnClickListener {

    public ContactListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        CardView card = (CardView) view.findViewById(R.id.card1);
        card.setOnClickListener(this);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setTitle("Список контактов");
        return view;
    }

    @Override
    public void onClick(View view) {
        CardView card = (CardView) view.findViewById(R.id.card1);
        ContactDetailsFragment details = ContactDetailsFragment.newInstance(card.getId());
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, details);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
