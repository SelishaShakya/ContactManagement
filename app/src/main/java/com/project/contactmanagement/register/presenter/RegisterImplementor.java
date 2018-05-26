package com.project.contactmanagement.register.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.contactmanagement.login.model.User;
import com.project.contactmanagement.register.view.RegisterActivity;
import com.project.contactmanagement.register.view.RegisterView;

import java.util.concurrent.Executor;

/**
 * Created by Own on 4/10/2018.
 */

public class RegisterImplementor implements RegisterPresenter {

    Context context;
    RegisterView registerView;
    private FirebaseAuth firebaseAuth;

    public RegisterImplementor(Context context, RegisterView registerView) {
        this.context = context;
        this.registerView = registerView;
    }

    @Override
    public void registerUser(User user) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(user.username, user.password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            registerView.success("Successfully registered");
                        } else {
                            registerView.error("Registration Error");
                        }
                    }
                });
    }
}
