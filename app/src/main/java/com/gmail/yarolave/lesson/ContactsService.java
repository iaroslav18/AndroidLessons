package com.gmail.yarolave.lesson;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

public class ContactsService extends Service {

    public final IBinder binder = new ContactsBinder();

    private static final int photoId = R.drawable.face;
    private static final String name = "Ярослав";
    private static final String[] numbers = {"+79995554422", "+79999999999"};
    private static final String[] emails = {"contact@mail.ru", "second@gmail.com"};
    private static final String description = "Описание";

    public class ContactsBinder extends Binder {
        ContactsService getContactsService() { return ContactsService.this;}
    }

    public ContactsService() {
    }

    @Override
    public IBinder onBind(Intent intent) { return binder; }

    public static class GetContactList extends AsyncTask<Bundle, Void, Bundle> {

        @Override
        public Bundle doInBackground(Bundle... contactListArgs) {
            contactListArgs[0].putInt("photo", photoId);
            contactListArgs[0].putString("name", name);
            contactListArgs[0].putString("number1", numbers[0]);
            return contactListArgs[0];
        }
    }

    public static class GetContactDetails extends AsyncTask<Bundle, Void, Bundle> {

        @Override
        public Bundle doInBackground(Bundle... contactDetailsArgs) {
            contactDetailsArgs[0].putInt("photo", photoId);
            contactDetailsArgs[0].putString("name", name);
            contactDetailsArgs[0].putString("number1", numbers[0]);
            contactDetailsArgs[0].putString("email1", emails[0]);
            contactDetailsArgs[0].putString("number2", numbers[1]);
            contactDetailsArgs[0].putString("email2", emails[1]);
            contactDetailsArgs[0].putString("description", description);
            return contactDetailsArgs[0];
        }
    }
}
