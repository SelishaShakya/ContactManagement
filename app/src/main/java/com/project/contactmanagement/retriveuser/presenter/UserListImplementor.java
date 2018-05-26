package com.project.contactmanagement.retriveuser.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.contactmanagement.adduser.model.UserModel;
import com.project.contactmanagement.retriveuser.model.UserParser;
import com.project.contactmanagement.retriveuser.view.UserListView;

import org.json.JSONException;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by Own on 4/12/2018.
 */

public class UserListImplementor implements UserListPresenter {


    Context context;
    UserListView userListView;
    Realm mRealm;

    public UserListImplementor(Context context, UserListView userListView) {
        this.context = context;
        this.userListView = userListView;
        mRealm=Realm.getDefaultInstance();

    }

    @Override
    public void getUserList() {
        /*ArrayList<UserModel> userModels=new ArrayList<>(mRealm.where(UserModel.class).findAll());
        userListView.userList(userModels);*/
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
//                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
//                        Log.d("CheckingdocumentId", documentSnapshot.getId());
//                    }
                    UserParser userParser = new UserParser(task);
                    try {
                        userParser.parseUser();
                        userListView.userList(userParser.userModels);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("fireStore", "error getting documents: ", task.getException());
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

        }
}