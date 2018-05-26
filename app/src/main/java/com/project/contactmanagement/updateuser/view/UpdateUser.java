package com.project.contactmanagement.updateuser.view;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.project.contactmanagement.R;
import com.project.contactmanagement.base.BaseActivity;
import com.project.contactmanagement.retriveuser.view.UserList;
import com.project.contactmanagement.adduser.model.UserModel;
import com.project.contactmanagement.updateuser.presenter.UpdateUserImplementor;
import com.project.contactmanagement.updateuser.presenter.UpdateUserPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Own on 4/11/2018.
 */

public class UpdateUser extends BaseActivity implements UpdateUserView {

    UpdateUserPresenter addUserPresenter;
    String userId;
    UserModel userModel;
    @BindView(R.id.et_add_user_full_name)
    EditText etFullName;
    @BindView(R.id.et_add_user_address)
    EditText etAddress;
    @BindView(R.id.et_add_user_contact)
    EditText etContact;
    @BindView(R.id.et_add_user_email)
    EditText etEmail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayout() {
        return R.layout.add_user;
    }

    @Override
    protected void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.add_user));
        userModel=(UserModel)getIntent().getSerializableExtra("userData");
        userId=userModel.getUserId();
        etFullName.setText(userModel.getFullname());
        etAddress.setText(userModel.getAddress());
        etContact.setText(userModel.getContact());
        etEmail.setText(userModel.getEmail());
        addUserPresenter = new UpdateUserImplementor(this, this);
    }


    @OnClick(R.id.btn_submit)
    public void updateUser() {
        String fullName = etFullName.getText().toString();
        String address = etAddress.getText().toString();
        String contact = etContact.getText().toString();
        String email = etEmail.getText().toString();

        if(fullName.isEmpty()){
            etFullName.setError("Required");
            etFullName.requestFocus();
        }else if(address.isEmpty()){
            etAddress.setError("Required");
            etAddress.requestFocus();
        }else if(contact.isEmpty()){
            etContact.setError("Required");
            etContact.requestFocus();
        }else if(email.isEmpty()){
            etEmail.setError("Required");
            etEmail.requestFocus();
        }else{
            userModel = new UserModel(userId,fullName,address,email,contact);
            addUserPresenter.updateUser(userModel);
        }
    }

    @OnClick(R.id.btn_user_list)
    public void openUserList(){
        Intent i = new Intent(this, UserList.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void updateSuccess() {
        Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorUpdating() {
        Toast.makeText(this, "Could not add", Toast.LENGTH_SHORT).show();

    }
}
