package com.nookdev.firebaselocation.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nookdev.firebaselocation.FirebaseManager;
import com.nookdev.firebaselocation.R;
import com.nookdev.firebaselocation.UpdateManager;
import com.nookdev.firebaselocation.interfaces.IUpdate;
import com.nookdev.firebaselocation.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

    private List<User> mData;

    public UserListAdapter() {

        UpdateManager.getInstance().addConsumer((dataSnapshot, action) -> {
            switch (action){
                case IUpdate.ADD:{
                    String s = dataSnapshot.toString();
                    break;
                }
            }
            String s = dataSnapshot.toString();
        });
    }

    @Override
    public UserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item,parent,false);
        return new UserListViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mData!=null ? mData.size() : 0;
    }

    @Override
    public void onBindViewHolder(UserListViewHolder holder, int position) {
        User user = mData.get(holder.getAdapterPosition());
        holder.id.setText(String.valueOf(position));
        holder.name.setText(user.getName());
        holder.row.setOnClickListener(v -> FirebaseManager.saveUser(mData.get(position)));
    }

    protected class UserListViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.user_list_item_id)
        TextView id;

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
