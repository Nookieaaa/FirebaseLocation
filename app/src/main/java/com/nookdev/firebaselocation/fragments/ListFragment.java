package com.nookdev.firebaselocation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nookdev.firebaselocation.DataUpdateManager;
import com.nookdev.firebaselocation.R;
import com.nookdev.firebaselocation.activities.MapActivity;
import com.nookdev.firebaselocation.adapter.UserListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ListFragment extends Fragment {
    private Unbinder mUnbinder;
    private UserListAdapter mAdapter;
    private boolean mTwoPane = false;

    @BindView(R.id.recyclerview_users)
    RecyclerView mRecyclerView;

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        mUnbinder = ButterKnife.bind(this,v);

        mAdapter = new UserListAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setCallback(user -> {
            if(!mTwoPane){
                Intent intent = new Intent(getActivity(), MapActivity.class);
                intent.putExtras(user.toBundle());
                startActivity(intent);
            }
            DataUpdateManager.getInstance().setCurrentSelectedUser(user.getName());
        });

        return v;
    }

    public void setTwoPane(boolean twoPane){
        mTwoPane = twoPane;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter.release();
        mUnbinder.unbind();
    }
}
