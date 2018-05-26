package com.project.contactmanagement.login.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.contactmanagement.R;
import com.project.contactmanagement.register.view.RegisterActivity;
import com.project.contactmanagement.base.BaseActivity;
import com.project.contactmanagement.login.model.User;
import com.project.contactmanagement.login.presenter.LoginImplementor;
import com.project.contactmanagement.login.presenter.LoginPresenter;
import com.project.contactmanagement.retriveuser.view.UserList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.project.contactmanagement.utils.TextViewUtils.isEmailValid;

public class LoginActivity extends BaseActivity implements LoginView {

    private ProgressDialog progressDialog;

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    @Inject
    User user;
    //defining firebaseauth object
    private static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.et_login_email)
    EditText etEmail;
    @BindView(R.id.et_login_password)
    EditText etPassword;
    @BindView(R.id.google_sign_in_button)
    com.google.android.gms.common.SignInButton googleSignInButton;
    @Inject
    LoginPresenter loginPresenter;
    private static final String LOGIN_PREFS = "user_login_preference";

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        loginPresenter = new LoginImplementor(this, this);
        progressDialog = new ProgressDialog(this);
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    @OnClick(R.id.ll_sign_up)
    public void signUp() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }


    @OnClick(R.id.btn_login)
    public void checkLogin() {
        final String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Required");
            etEmail.requestFocus();
        } else if (password.isEmpty()) {
            etPassword.setError("Required");
            etPassword.requestFocus();
        }else if(!isEmailValid(email)) {
            etEmail.setError("Invalid Email");
            etEmail.requestFocus();
        } else {
            User user = new User(email, password);
            loginPresenter.authFireBase(user);
            showProgressDialog();
        }


    }

    @OnClick(R.id.google_sign_in_button)
    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        showProgressDialog();
    }


    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait!");
            progressDialog.setIndeterminate(true);
        }
        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void error(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        hideProgressDialog();
    }

    @Override
    public void showAppropriateOptions(FirebaseUser user) {
        hideProgressDialog();
        Intent intent = new Intent(LoginActivity.this, UserList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("FirebaseUser",user);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                loginPresenter.firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // User already logged in
            Intent intent = new Intent(this, UserList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}
