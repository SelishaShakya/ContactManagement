package com.project.contactmanagement.retriveuser.view;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.contactmanagement.R;
import com.project.contactmanagement.utils.BlurBuilder;
import com.project.contactmanagement.base.BaseFragment;
import com.project.contactmanagement.login.view.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoggedInUserDetails extends BaseFragment {
    @BindView(R.id.logout_btn)
    Button logoutBtn;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    @BindView(R.id.user_email)
    TextView userEmailText;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    GoogleApiClient mGoogleApiClient;
    public static LoggedInUserDetails newInstance(Context context) {
        LoggedInUserDetails f = new LoggedInUserDetails();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    protected int getLayout() {
        return R.layout.logged_in_user_details_fragment;
    }

    @Override
    protected void init(final View view) {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() /* FragmentActivity */, null /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
        ButterKnife.bind(this,view);
        /*final Window window=getActivity().getWindow();
        final View content = getActivity().findViewById(android.R.id.content).getRootView();
        if (content.getWidth() > 0) {
            Bitmap image = BlurBuilder.blur(content);
            window.setBackgroundDrawable(new BitmapDrawable(getActivity().getResources(), image));
        } else {
            content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Bitmap image = BlurBuilder.blur(content);
                    window.setBackgroundDrawable(new BitmapDrawable(getActivity().getResources(), image));
                }
            });
        }*/
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userEmailText.setText(user.getEmail());

    }
    @OnClick(R.id.logout_btn)
    public void signOut() {
        // Firebase sign out
        firebaseAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {


                    }
                });
        Intent intent=new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
    @OnClick(R.id.cancel_btn)
    public void cancel(){
        dismiss();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }


    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }
}
