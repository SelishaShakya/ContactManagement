package com.project.contactmanagement.application;

import android.app.Application;
import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;
import com.project.contactmanagement.application.component.AppComponent;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Own on 4/10/2018.
 */

public class MainApplication extends Application {

    private AppComponent component;

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    public AppComponent getComponent(){
        return  component;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(0) // Must be bumped when the schema changes
//                .migration(new MyMigration()) // Migration to run instead of throwing an exception
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }


}
