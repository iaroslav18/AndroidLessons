package com.gmail.yarolave.lesson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

interface GettingContactsService {
    ContactsService getContactsService();
}

public class MainActivity extends AppCompatActivity implements GettingContactsService {

    private ContactsService contactsService;
    private boolean bound;

    @Override
    public ContactsService getContactsService() {
        return contactsService;
    }

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
        Intent intent = new Intent(this, ContactsService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ContactListFragment()).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }
}
