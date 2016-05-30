package com.nookdev.firebaselocation;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nookdev.firebaselocation.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

    private List<User> mData;

    public UserListAdapter() {
        mData = new ArrayList<>();
        mData.add(new User("user1",null));
        mData.add(new User("user2",null));
        mData.add(new User("user3",null));
        mData.add(new User("user4",null));
        mData.add(new User("user5",null));
        mData.add(new User("user6",null));
        mData.add(new User("user7",null));
        mData.add(new User("user8",null));
        mData.add(new User("user9",null));
        mData.add(new User("user10",null));
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
    }

    protected class UserListViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.user_list_item_id)
        TextView id;

        @BindView(R.id.user_list_item_name)
        TextView name;

        public UserListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
