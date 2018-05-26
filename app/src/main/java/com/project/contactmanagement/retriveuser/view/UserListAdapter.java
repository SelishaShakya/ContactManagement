package com.project.contactmanagement.retriveuser.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.project.contactmanagement.R;
import com.project.contactmanagement.adduser.model.UserModel;
import com.project.contactmanagement.userdetails.view.UserDetails;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    public ArrayList<UserModel> userModels;
    Context context;

    public UserListAdapter(ArrayList<UserModel> userModels, Context context) {
        this.context = context;
        this.userModels = userModels;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_single_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        UserModel userModel = userModels.get(position);
        holder.tvFullname.setText(userModel.getFullname());
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_full_name)
        TextView tvFullname;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Intent userDetailsIntent = new Intent(context, UserDetails.class);
            userDetailsIntent.putExtra("userId",userModels.get(getAdapterPosition()).getUserId());
            context.startActivity(userDetailsIntent);


        }
    }


}