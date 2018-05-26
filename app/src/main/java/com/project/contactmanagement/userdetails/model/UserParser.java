package com.project.contactmanagement.userdetails.model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.contactmanagement.adduser.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Own on 4/12/2018.
 */

public class UserParser {

    @NonNull DocumentSnapshot documentSnapshot;
    public UserModel userModel;


    public UserParser(DocumentSnapshot documentSnapshot) {
        this.documentSnapshot = documentSnapshot;
    }

    public void parseUser() throws JSONException {
            JSONObject jsonObject = new JSONObject(documentSnapshot.getData());
            userModel = new UserModel(documentSnapshot.getId(),jsonObject.getString("full name"),
                    jsonObject.getString("address"),jsonObject.getString("email"),
                    jsonObject.getString("contact")
            );


    }


}
