package com.example.MobileAppDevelopmentEXE1.model;


import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.MobileAppDevelopmentEXE1.MainActivity;
import com.example.MobileAppDevelopmentEXE1.R;
import com.google.android.material.button.MaterialButton;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class StartActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private MaterialButton start_BTN_StartGame;
    private MaterialButton start_BTN_RecordsTable;
    private AppCompatImageView start_ING_SuperMario;
    private MaterialButton start_BTN_mario_onClick;
    private MaterialButton start_BTN_luigis_onClick;
    private MaterialButton start_BTN_mario;
    private MaterialButton start_BTN_luigis;
    private EditText start_name;
    private RadioButton radioButtonArrowa;
    private RadioButton radioButtonSensors;
    private static String playersName;
    private double latitude;
    private double longitude;
    private int isMarioOrLuigis = 0;
    private int gameMode = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findview();
        initview();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestLocationPermission();
    }

    private void findview() {
        start_BTN_StartGame = findViewById(R.id.start_BTN_StartGame);
        start_BTN_RecordsTable = findViewById(R.id.start_BTN_RecordsTable);
        start_ING_SuperMario = findViewById(R.id.start_ING_SuperMario);
        start_BTN_mario_onClick = findViewById(R.id.start_BTN_mario_onClick);
        start_BTN_luigis_onClick = findViewById(R.id.start_BTN_luigis_onClick);
        start_BTN_mario = findViewById(R.id.start_BTN_mario);
        start_BTN_luigis = findViewById(R.id.start_BTN_luigis);
        start_name = findViewById(R.id.start_name);
        radioButtonArrowa = findViewById(R.id.radioButtonArrowa);
        radioButtonSensors = findViewById(R.id.radioButtonSensors);
    }


    private void initview() {
        start_BTN_StartGame.setOnClickListener(v -> changeActivity());
        start_BTN_RecordsTable.setOnClickListener(v -> changeActivityToFregment());
        radioButtonArrowa.setOnClickListener(v -> setGameMode(0));
        radioButtonSensors.setOnClickListener(v -> setGameMode(1));
        start_BTN_mario.setOnClickListener(v -> showPlayerChoice(R.drawable.mario));
        start_BTN_luigis.setOnClickListener(v -> showPlayerChoice(R.drawable.luigi));

    }

    private void changeActivityToFregment() {
        Intent intent = new Intent(this, FragmentActivity.class);
        startActivity(intent);
        finish();
    }

    private void setGameMode(int i) {
        gameMode = i;
    }

    private void showPlayerChoice(int choice) {
        if (choice == R.drawable.mario) {
            isMarioOrLuigis = choice;
            start_BTN_mario_onClick.setVisibility(View.VISIBLE);
            start_BTN_luigis_onClick.setVisibility(View.INVISIBLE);

        } else {
            isMarioOrLuigis = choice;
            start_BTN_luigis_onClick.setVisibility(View.VISIBLE);
            start_BTN_mario_onClick.setVisibility(View.INVISIBLE);

        }

    }

    private void changeActivity() {
        playersName = start_name.getText().toString();
        if (isMarioOrLuigis == 0 || gameMode < 0 || playersName.isEmpty()) {
            toast("You have to enter name and chose character and game mode!");
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.KEY_PLAYER_NAME, playersName);
            intent.putExtra(MainActivity.KEY_CHARACTER, isMarioOrLuigis);
            intent.putExtra(MainActivity.KEY_GAMEMODE, gameMode);
            intent.putExtra(MainActivity.KEY_LATITUDE, latitude);
            intent.putExtra(MainActivity.KEY_LONGITUDE, longitude);
            startActivity(intent);
            finish();
        }

    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getUserLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { // Added method to handle permission result
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            } else {
                                Toast.makeText(StartActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
