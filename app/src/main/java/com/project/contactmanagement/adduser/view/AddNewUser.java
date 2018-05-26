package com.project.contactmanagement.adduser.view;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.project.contactmanagement.R;
import com.project.contactmanagement.adduser.model.UserModel;
import com.project.contactmanagement.adduser.presenter.AddUserImplementor;
import com.project.contactmanagement.adduser.presenter.AddUserPresenter;
import com.project.contactmanagement.base.BaseActivity;
import com.project.contactmanagement.retriveuser.view.UserList;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;

import static com.project.contactmanagement.utils.TextViewUtils.isEmailValid;

/**
 * Created by Own on 4/11/2018.
 */

public class AddNewUser extends BaseActivity implements AddUserView {

    AddUserPresenter addUserPresenter;
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
    Realm mRealm;

    @Override
    protected int getLayout() {
        return R.layout.add_user;
    }

    @Override
    protected void init() {
        addUserPresenter = new AddUserImplementor(this, this);
        mRealm=Realm.getDefaultInstance();
    }


    @OnClick(R.id.btn_submit)
    public void addUser() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.add_user));
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
        }else if(!isEmailValid(email)) {
            etEmail.setError("Invalid Email");
            etEmail.requestFocus();
        }else {
            UserModel userModel = new UserModel(fullName,address,email,contact);
            addUserPresenter.addUser(userModel);
        }
    }


    @OnClick(R.id.btn_user_list)
    public void openUserList(){
        Intent i = new Intent(this, UserList.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void addSuccess() {
        Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, UserList.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void errorAdding() {
        Toast.makeText(this, "Could not add", Toast.LENGTH_SHORT).show();

    }
}
