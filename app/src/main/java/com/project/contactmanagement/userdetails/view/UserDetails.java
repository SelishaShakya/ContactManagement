package com.project.contactmanagement.userdetails.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.contactmanagement.R;
import com.project.contactmanagement.adduser.model.UserModel;
import com.project.contactmanagement.base.BaseActivity;
import com.project.contactmanagement.retriveuser.view.UserList;
import com.project.contactmanagement.updateuser.view.UpdateUser;
import com.project.contactmanagement.userdetails.presenter.UserDetailsImplementer;
import com.project.contactmanagement.userdetails.presenter.UserDetailsPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Own on 4/11/2018.
 */

public class UserDetails extends BaseActivity implements UserDetailsView {

    UserDetailsPresenter userDetailsPresenter;
    UserModel userModel;
    @BindView(R.id.tv_full_name)
    TextView tvFullname;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    Toolbar toolbar;
    @BindViews({ R.id.btn_user_edit, R.id.btn_user_delete})
    List<Button> buttonViews;

    @Override
    protected int getLayout() {
        return R.layout.user_details;
    }

    @Override
    protected void init() {
        userDetailsPresenter = new UserDetailsImplementer(this, this);
        String userId = getIntent().getExtras().getString("userId");
        userDetailsPresenter.userDetails(userId);
    }


    @OnClick(R.id.btn_user_edit)
    public void editUser() {
        Intent updateUserIntent = new Intent(this, UpdateUser.class);
        updateUserIntent.putExtra("userData",userModel);
        startActivity(updateUserIntent);
    }

    @OnClick(R.id.btn_user_delete)
    public void deleteUser() {
       userDetailsPresenter.deleteUser(userModel.getUserId());
       Intent userListIntent=new Intent(this,UserList.class);
       userListIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       startActivity(userListIntent);
    }


    @Override
    public void getUserDetails(UserModel userModel) {
        setSupportActionBar(toolbar);
        this.userModel=userModel;
//        getSupportActionBar().setTitle(userModel.getFullname());
        tvFullname.setText("Name: "+userModel.getFullname());
        tvAddress.setText("Address: "+userModel.getAddress());
        tvContact.setText("Contact: "+userModel.getContact());
        tvEmail.setText("Email: "+userModel.getEmail());
        ButterKnife.apply(buttonViews, VISIBILITY, View.VISIBLE);

    }
    //custom setter
    public static final ButterKnife.Setter<View, Integer> VISIBILITY = new ButterKnife.Setter<View, Integer>() {
        @Override
        public void set(@NonNull View view, Integer value, int index) {
            view.setVisibility(value);
        }
    };
}
