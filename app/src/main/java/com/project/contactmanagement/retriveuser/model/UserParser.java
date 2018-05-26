package com.project.contactmanagement.retriveuser.model;

import android.support.annotation.NonNull;
import android.util.Log;

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

    @NonNull Task<QuerySnapshot> task;
    public ArrayList<UserModel> userModels;

    public UserParser(@NonNull Task<QuerySnapshot> task) {
        this.task = task;
        userModels = new ArrayList<>();
    }

    public void parseUser() throws JSONException {
        for (DocumentSnapshot document : task.getResult()) {
            JSONObject jsonObject = new JSONObject(document.getData());
            UserModel userModel = new UserModel(document.getId(),jsonObject.getString("full name"),
                    jsonObject.getString("address"),jsonObject.getString("email"),
                    jsonObject.getString("contact")
            );
            userModels.add(userModel);
        }
    }


}
