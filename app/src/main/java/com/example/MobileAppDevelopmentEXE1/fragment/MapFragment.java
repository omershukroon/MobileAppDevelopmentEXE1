package com.example.MobileAppDevelopmentEXE1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.MobileAppDevelopmentEXE1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {
    private GoogleMap myMap;

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map_fragment, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    myMap = googleMap;
                    LatLng defaultLocation = new LatLng(32.109333, 34.855499);
                    myMap.addMarker(new MarkerOptions().position(defaultLocation).title("Default Location Marker"));
                    myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
                }
            });
        }
        return view;
    }

    public void zoom(double latitude, double longitude) {
        if (myMap != null) {
            LatLng newLocation = new LatLng(latitude, longitude);
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 15));
            myMap.addMarker(new MarkerOptions().position(newLocation).title("Selected Location"));
        }
    }
}