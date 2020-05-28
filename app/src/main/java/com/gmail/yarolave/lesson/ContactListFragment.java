package com.gmail.yarolave.lesson;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactListFragment extends Fragment implements View.OnClickListener, ContactResult {

    private ContactsService service;
    private GettingContactsService gettingContactsService;
    private Context currentContext;

    private SettingTitle title;
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        currentContext = context;
        if (context instanceof GettingContactsService) {
            gettingContactsService = (GettingContactsService) context;
            service = gettingContactsService.getContactsService();
        }
    }

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
        view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        CardView card = (CardView) view.findViewById(R.id.card1);
        card.setOnClickListener(this);
        if (currentContext instanceof SettingTitle) {
            title = (SettingTitle) currentContext;
            title.setContactListTitle();
        }
        service.getContactList(this);
        return view;
    }

    @Override
    public void getContact(Bundle bundle) {
        if (view != null) {
            setResources(view, bundle);
        }
    }

    private void setResources(final View view, final Bundle bundle) {
        view.post(new Runnable() {
            @Override
            public void run() {
                ImageView photo = view.findViewById(R.id.avatar);
                photo.setImageResource(bundle.getInt("photo"));
                TextView name = view.findViewById(R.id.name);
                name.setText(bundle.getString("name"));
                TextView number = view.findViewById(R.id.number);
                number.setText(bundle.getString("number1"));
            }
        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        gettingContactsService = null;
        currentContext = null;
    }
}
