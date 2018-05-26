package com.project.contactmanagement.login.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.project.contactmanagement.login.model.User;

/**
 * Created by Own on 4/10/2018.
 */

public interface LoginPresenter {
    void authFireBase(User user);
    void firebaseAuthWithGoogle(GoogleSignInAccount acct);
}
