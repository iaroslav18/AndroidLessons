package com.gmail.yarolave.lesson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;


public class MainActivity extends AppCompatActivity {

    public Bundle contactListArgs, contactDetailsArgs;
    private ContactsService contactsService;
    private boolean bound;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            ContactsService.ContactsBinder contactsBinder = (ContactsService.ContactsBinder) binder;
            contactsService = contactsBinder.getContactsService();
            bound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getContactsResources();
            ContactListFragment contactListFragment = ContactListFragment.newInstance(contactListArgs);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, contactListFragment).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ContactsService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }

    private void getContactsResources() {
        ContactsService.GetContactList getContactList = new ContactsService.GetContactList();
        contactListArgs = getContactList.doInBackground();
        ContactsService.GetContactDetails getContactDetails = new ContactsService.GetContactDetails();
        contactDetailsArgs = getContactDetails.doInBackground();
    }
}
