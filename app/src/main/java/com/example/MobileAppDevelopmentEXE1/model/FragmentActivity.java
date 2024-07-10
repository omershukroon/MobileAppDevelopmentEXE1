package com.example.MobileAppDevelopmentEXE1.model;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MobileAppDevelopmentEXE1.R;
import com.example.MobileAppDevelopmentEXE1.fragment.ButtonFragment;
import com.example.MobileAppDevelopmentEXE1.fragment.ListFragment;
import com.example.MobileAppDevelopmentEXE1.fragment.MapFragment;

public class FragmentActivity extends AppCompatActivity implements ListFragment.OnPlayerSelectedListener {

    private ListFragment listFragment;
    private MapFragment mapFragment;
    private ButtonFragment buttonFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragment);
        findView();
        initView();
    }

    private void findView() {

    }

    private void initView() {
        listFragment = new ListFragment();
        listFragment.setOnPlayerSelectedListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_FRAME_list, listFragment).commit();
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_FRAME_map, mapFragment).commit();
        buttonFragment = new ButtonFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_FRAME_buttons, buttonFragment).commit();
    }

    @Override
    public void onPlayerSelected(Player player) {
        if (mapFragment != null) {
            mapFragment.zoom(player.getLatitude(), player.getLongitude());
        }
    }
}