package com.project.contactmanagement.login.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.ProviderQueryResult;
import com.project.contactmanagement.login.model.User;
import com.project.contactmanagement.login.view.LoginActivity;
import com.project.contactmanagement.login.view.LoginView;

/**
 * Created by Own on 4/10/2018.
 */

public class LoginImplementor implements LoginPresenter {

    Context context;
    LoginView loginView;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private FirebaseAuth firebaseAuth;

    public LoginImplementor(Context context, LoginView loginView) {
        this.context = context;
        this.loginView = loginView;
        firebaseAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void authFireBase(final User user) {

        firebaseAuth.fetchProvidersForEmail(user.username).addOnCompleteListener(
                new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "checking to see if user exists in firebase or not");
                            ProviderQueryResult result = task.getResult();

                            if (result != null && result.getProviders() != null
                                    && result.getProviders().size() > 0) {
                                Log.d(TAG, "User exists, trying to login using entered credentials");
                                performLogin(user.username, user.password);
                            } else {
                                loginView.error("User doesn't exist, creating account");
                            }
                        } else {
                            loginView.error("There is a problem, please try again later.");
                        }
                    }
                });
    }

    @Override
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            loginView.showAppropriateOptions(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            loginView.error("Authentication failed.");
                        }

                    }
                });
    }


    private void performLogin(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "login success");
                            Toast.makeText(context,
                                    "Login Success.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            loginView.showAppropriateOptions(user);

                        } else {
                            loginView.error("Authentication failed.");
                        }
                    }
                });
    }
}
