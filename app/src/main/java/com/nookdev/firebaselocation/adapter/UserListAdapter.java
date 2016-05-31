package com.nookdev.firebaselocation.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nookdev.firebaselocation.DataUpdateManager;
import com.nookdev.firebaselocation.R;
import com.nookdev.firebaselocation.interfaces.IUpdate;
import com.nookdev.firebaselocation.interfaces.OnItemSelectedCallback;
import com.nookdev.firebaselocation.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> implements IUpdate {
    private OnItemSelectedCallback mCallback;

    public UserListAdapter() {
        DataUpdateManager.getInstance().addConsumer(this);
    }

    @Override
    public UserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item,parent,false);
        return new UserListViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return DataUpdateManager.getInstance().getSize();
    }

    @Override
    public void onBindViewHolder(UserListViewHolder holder, int position) {
        User user = DataUpdateManager.getInstance().getUserAt(position);
        holder.name.setText(user.getName());
        holder.row.setOnClickListener(v-> {
            if(mCallback!=null)
                mCallback.onItemSelected(user);
        });
    }

    @Override
    public void onItemAdded(int position) {
        notifyItemInserted(position);
    }

    @Override
    public void onItemRemoved(int position) {
        notifyItemRemoved(position);
    }

    public void setCallback(OnItemSelectedCallback callback) {
        mCallback = callback;
    }

    public void release() {
        DataUpdateManager.getInstance().removeConsumer(this);
        mCallback = null;
    }

    protected class UserListViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.user_list_item_name)
        TextView name;

        @BindView(R.id.user_list_item)
        LinearLayout row;

        public UserListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
