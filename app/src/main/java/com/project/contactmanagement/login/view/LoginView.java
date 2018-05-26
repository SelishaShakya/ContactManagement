package com.project.contactmanagement.login.view;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Own on 4/10/2018.
 */

public interface LoginView {
    void error(String s);

    void showAppropriateOptions(FirebaseUser user);
}
