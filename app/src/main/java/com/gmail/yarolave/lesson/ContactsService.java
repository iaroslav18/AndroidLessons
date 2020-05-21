package com.gmail.yarolave.lesson;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

interface ContactResult {
    void getContact(Bundle bundle);
}

interface DetailsResult {
    void getDetails(Bundle bundle);
}

public class ContactsService extends Service {

    public final IBinder binder = new ContactsBinder();
    private final ExecutorService executor;

    private static final int photoId = R.drawable.face;
    private static final String name = "Ярослав";
    private static final String[] numbers = {"+79995554422", "+79999999999"};
    private static final String[] emails = {"contact@mail.ru", "second@gmail.com"};
    private static final String description = "Описание";

    public class ContactsBinder extends Binder {
        ContactsService getContactsService() { return ContactsService.this;}
    }

    public ContactsService() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public IBinder onBind(Intent intent) { return binder; }

    public void getContactList(ContactResult callback) {
        final WeakReference<ContactResult> weakReference = new WeakReference<ContactResult>(callback);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putInt("photo", photoId);
                bundle.putString("name", name);
                bundle.putString("number1", numbers[0]);
                ContactResult local = weakReference.get();
                if (local != null) {
                    local.getContact(bundle);
                }
            }
        });
    }

    public void getContactDetails(DetailsResult callback) {
        final WeakReference<DetailsResult> weakReference = new WeakReference<DetailsResult>(callback);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putInt("photo", photoId);
                bundle.putString("name", name);
                bundle.putString("number1", numbers[0]);
                bundle.putString("email1", emails[0]);
                bundle.putString("number2", numbers[1]);
                bundle.putString("email2", emails[1]);
                bundle.putString("description", description);
                DetailsResult local = weakReference.get();
                if (local != null) {
                    local.getDetails(bundle);
                }
            }
        });
    }
}
