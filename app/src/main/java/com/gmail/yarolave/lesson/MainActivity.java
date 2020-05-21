package com.gmail.yarolave.lesson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

interface GettingContactsService {
    ContactsService getContactsService();
}

interface SettingTitle {
    void setContactListTitle();
    void setContactDetailsTitle();
}

public class MainActivity extends AppCompatActivity implements GettingContactsService,
        SettingTitle {

    private ContactsService contactsService;
    private boolean bound;
    private Intent intent;

    @Override
    public ContactsService getContactsService() {
        return contactsService;
    }

    @Override
    public void setContactListTitle() {
        setTitle("Список контактов");
    }

    @Override
    public void setContactDetailsTitle() {
        setTitle("Детали контакта");
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            ContactsService.ContactsBinder contactsBinder = (ContactsService.ContactsBinder) binder;
            contactsService = contactsBinder.getContactsService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, ContactsService.class);
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
