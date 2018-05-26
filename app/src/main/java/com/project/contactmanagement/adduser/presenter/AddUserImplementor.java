package com.project.contactmanagement.adduser.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.contactmanagement.adduser.model.UserModel;
import com.project.contactmanagement.adduser.view.AddUserView;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

/**
 * Created by Own on 4/11/2018.
 */

public class AddUserImplementor implements AddUserPresenter {

    Context context;
    AddUserView appUserView;
    Realm mRealm;

    public AddUserImplementor(Context context, AddUserView appUserView) {
        this.context = context;
        this.appUserView = appUserView;
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void addUser(final UserModel userModel) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        final Map<String, Object> user = new HashMap<>();
        user.put("full name", userModel.fullname);
        user.put("address", userModel.address);
        user.put("email", userModel.email);
        user.put("contact", userModel.contact);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("fireStore", "DocumentSnapshot added with ID: " + documentReference.getId());
                        userModel.setUserId(documentReference.getId());
                        mRealm.beginTransaction();
                        mRealm.insertOrUpdate(userModel);
                        mRealm.commitTransaction();
                        appUserView.addSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("fireStore", "Error adding document", e);
                        appUserView.errorAdding();
                    }
                });
        /*if(NetworkUtils.isOnline(context)){
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("fireStore", "DocumentSnapshot added with ID: " + documentReference.getId());
                            userModel.setUserId(documentReference.getId());
                            mRealm.beginTransaction();
                            mRealm.insertOrUpdate(userModel);
                            mRealm.commitTransaction();
                            appUserView.addSuccess();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("fireStore", "Error adding document", e);
                            appUserView.errorAdding();
                        }
                    });
        }else{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
            String offlineuserId= "user"+sdf.format(Calendar.getInstance().getTime());
            userModel.setUserId(offlineuserId);
            mRealm.beginTransaction();
            mRealm.insertOrUpdate(userModel);
            mRealm.commitTransaction();
        }*/



}
}
