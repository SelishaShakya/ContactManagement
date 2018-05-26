package com.project.contactmanagement.userdetails.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.contactmanagement.adduser.model.UserModel;
import com.project.contactmanagement.userdetails.model.UserParser;
import com.project.contactmanagement.userdetails.view.UserDetails;
import com.project.contactmanagement.userdetails.view.UserDetailsView;

import org.json.JSONException;

import io.realm.Realm;

import static android.content.ContentValues.TAG;

/**
 * Created by Own on 4/11/2018.
 */

public class UserDetailsImplementer implements UserDetailsPresenter {

    Context context;
    UserDetailsView userDetailsView;
    Realm mRealm;

    public UserDetailsImplementer(Context context, UserDetailsView userDetailsView) {
        this.context = context;
        this.userDetailsView = userDetailsView;
    }

    @Override
    public void userDetails(final String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mRealm=Realm.getDefaultInstance();
        Log.d("ClickedUserId",userId);
        DocumentReference contact = db.collection("users").document(userId);
        contact.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    UserParser userParser = new UserParser(document);
                    try {
                        userParser.parseUser();
                        userDetailsView.getUserDetails(userParser.userModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.d("fireStore", "error getting documents: ", task.getException());
                    UserModel userModel=mRealm.where(UserModel.class).equalTo("userId",userId).findFirst();
                    userDetailsView.getUserDetails(userModel);
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        UserModel userModel=mRealm.where(UserModel.class).equalTo("userId",userId).findFirst();
                        userDetailsView.getUserDetails(userModel);
                    }
                });

    }

    @Override
    public void deleteUser(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "DocumentSnapshot successfully deleted!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error deleting document", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
