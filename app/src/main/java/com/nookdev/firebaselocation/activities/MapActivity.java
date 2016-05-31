package com.nookdev.firebaselocation.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.google.android.gms.maps.SupportMapFragment;
import com.nookdev.firebaselocation.MapManager;
import com.nookdev.firebaselocation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MapActivity extends AppCompatActivity {
    Unbinder mUnbinder;
    MapManager mMapManager;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.map_fragment)
    FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mUnbinder = ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null)
            ab.setDisplayHomeAsUpEnabled(true);
        SupportMapFragment smf = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        smf.getMapAsync(googleMap -> {
            mMapManager = new MapManager(googleMap);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
