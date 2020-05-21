package com.gmail.yarolave.lesson;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class ContactDetailsFragment extends Fragment implements DetailsResult {

    private int paramId;
    private ContactsService service;
    private GettingContactsService gettingContactsService;

    private SettingTitle title;
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GettingContactsService) {
            gettingContactsService = (GettingContactsService) context;
            service = gettingContactsService.getContactsService();
        }
        if (context instanceof SettingTitle) {
            title.setContactDetailsTitle();
        }
    }

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
        service.getContactDetails(this);
        if (getArguments() != null) {
            paramId = getArguments().getInt("paramId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact_details, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setTitle("Детали контакта");
        return view;
    }

    @Override
    public void getDetails(Bundle bundle) {
        setResources(view, bundle);
    }

    private void setResources(final View view, final Bundle bundle) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (view != null) {
                    ImageView photo = view.findViewById(R.id.avatar);
                    photo.setImageResource(bundle.getInt("photo"));
                    TextView name = view.findViewById(R.id.name);
                    name.setText(bundle.getString("name"));
                    TextView number1 = view.findViewById(R.id.firstNumber);
                    number1.setText(bundle.getString("number1"));
                    TextView number2 = view.findViewById(R.id.secondNumber);
                    number2.setText(bundle.getString("number2"));
                    TextView email1 = view.findViewById(R.id.firstEmail);
                    email1.setText(bundle.getString("email1"));
                    TextView email2 = view.findViewById(R.id.secondEmail);
                    email2.setText(bundle.getString("email2"));
                    TextView description = view.findViewById(R.id.description);
                    description.setText(bundle.getString("description"));
                }
            }
        });
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
    }
}
