package com.project.contactmanagement.updateuser.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.contactmanagement.adduser.model.UserModel;
import com.project.contactmanagement.updateuser.view.UpdateUserView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Own on 4/11/2018.
 */

public class UpdateUserImplementor implements UpdateUserPresenter {

    Context context;
    UpdateUserView appUserView;

    public UpdateUserImplementor(Context context, UpdateUserView appUserView) {
        this.context = context;
        this.appUserView = appUserView;
    }

    @Override
    public void updateUser(UserModel userModel) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("full name", userModel.fullname);
        user.put("address", userModel.address);
        user.put("email", userModel.email);
        user.put("contact", userModel.contact);


            DocumentReference contact = db.collection("users").document(userModel.userId);
            contact.update("address", userModel.address);
            contact.update("email", userModel.email);
            contact.update("contact", userModel.contact);
            contact.update("full name", userModel.fullname)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Updated Successfully",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }




}
