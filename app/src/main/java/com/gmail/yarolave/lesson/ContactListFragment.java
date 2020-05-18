package com.gmail.yarolave.lesson;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactListFragment extends Fragment implements View.OnClickListener {

    public ContactListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContactsService.GetContactList getContactList = new ContactsService.GetContactList();
        setArguments(getContactList.doInBackground());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        CardView card = (CardView) view.findViewById(R.id.card1);
        card.setOnClickListener(this);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setTitle("Список контактов");
        setResources(view);
        return view;
    }

    private void setResources(View view) {
        if (getArguments() != null) {
            ImageView photo = view.findViewById(R.id.avatar);
            photo.setImageResource(getArguments().getInt("photo"));
            TextView name = view.findViewById(R.id.name);
            name.setText(getArguments().getString("name"));
            TextView number = view.findViewById(R.id.number);
            number.setText(getArguments().getString("number1"));
        }
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
