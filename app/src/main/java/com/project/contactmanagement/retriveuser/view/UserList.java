package com.project.contactmanagement.retriveuser.view;



import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.project.contactmanagement.R;
import com.project.contactmanagement.adduser.model.UserModel;
import com.project.contactmanagement.adduser.view.AddNewUser;
import com.project.contactmanagement.base.BaseActivity;
import com.project.contactmanagement.retriveuser.presenter.UserListImplementor;
import com.project.contactmanagement.retriveuser.presenter.UserListPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class UserList extends BaseActivity implements UserListView{

    UserListPresenter userListPresenter;
    @BindView(R.id.rv_user_list)
    RecyclerView rvUserList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    @Override
    protected int getLayout() {
        return R.layout.user_list;
    }

    @Override
    protected void init() {
        userListPresenter = new UserListImplementor(this,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvUserList.setLayoutManager(layoutManager);
        rvUserList.setHasFixedSize(true);

        userListPresenter.getUserList();
    }

    @Override
    public void userList(ArrayList<UserModel> userModels) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.user_list));
        UserListAdapter earningAdapter = new UserListAdapter(userModels,this);
        rvUserList.setAdapter(earningAdapter);
    }

    @OnClick(R.id.fab)
    public void addUser(){
        Intent intent=new Intent(this, AddNewUser.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_user_info:
                // User chose the "Settings" item, show the app settings UI...
                LoggedInUserDetails loggedInUserDetailsFragment=LoggedInUserDetails.newInstance(this);
//                getSupportFragmentManager().beginTransaction().add(R.id.user_info_fragment_container,loggedInUserDetailsFragment).commit();
                loggedInUserDetailsFragment.show(getSupportFragmentManager(),"Logout Dialog");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_list_menu, menu);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });
        return true;
    }

}
