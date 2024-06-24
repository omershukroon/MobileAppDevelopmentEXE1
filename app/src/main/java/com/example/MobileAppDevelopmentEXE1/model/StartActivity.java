package com.example.MobileAppDevelopmentEXE1.model;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.MobileAppDevelopmentEXE1.R;
import com.google.android.material.button.MaterialButton;

public class StartActivity extends AppCompatActivity {

    MaterialButton gameOver_BTN_StartPlay;
    AppCompatImageView start_ING_SuperMario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findview();
        initview();
    }

    private void findview() {
        gameOver_BTN_StartPlay = findViewById(R.id.gameOver_BTN_PlayAgain);
        start_ING_SuperMario = findViewById(R.id.start_ING_SuperMario);
    }


    private void initview() {
        gameOver_BTN_StartPlay.setOnClickListener(v -> changeActivity());
    }

    private void changeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}