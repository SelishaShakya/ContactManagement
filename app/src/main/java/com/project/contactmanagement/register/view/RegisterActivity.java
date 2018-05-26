package com.project.contactmanagement.register.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.contactmanagement.R;
import com.project.contactmanagement.base.BaseActivity;
import com.project.contactmanagement.login.model.User;
import com.project.contactmanagement.register.presenter.RegisterImplementor;
import com.project.contactmanagement.register.presenter.RegisterPresenter;
import com.project.contactmanagement.retriveuser.view.UserList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.project.contactmanagement.utils.TextViewUtils.isEmailValid;

public class RegisterActivity extends BaseActivity implements RegisterView {
    //defining view objects

    @BindView(R.id.et_register_email)
    EditText etEmail;
    @BindView(R.id.et_register_login)
    EditText etPassword;
    ProgressDialog progressDialog;
    RegisterPresenter registerPresenter;

    //defining firebaseauth object

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        registerPresenter = new RegisterImplementor(this,this);
        progressDialog = new ProgressDialog(this);
    }


    @OnClick(R.id.btn_register)
    public void registerUser(){

        //getting email and password from edit texts
        String email = etEmail.getText().toString().trim();
        String password  = etPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(email.isEmpty()){
            etEmail.setError("Required");
            etEmail.requestFocus();
        }else if(password.isEmpty()){
            etPassword.setError("Required");
            etPassword.requestFocus();
        }else if(password.length()<7) {
            etPassword.setError("Must be of 6 characters");
            etPassword.requestFocus();
        }else if(!isEmailValid(email)) {
            etEmail.setError("Invalid Email");
            etEmail.requestFocus();
        }else{
            User user = new User(email,password);
            registerPresenter.registerUser(user);
            progressDialog.setMessage("Registering Please Wait...");
            progressDialog.show();
        }
    }

    @Override
    public void success(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        Intent intent=new Intent(this, UserList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public void error(String s) {
        progressDialog.dismiss();
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }
}
