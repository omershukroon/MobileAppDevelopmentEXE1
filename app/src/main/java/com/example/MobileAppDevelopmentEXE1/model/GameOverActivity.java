package com.example.MobileAppDevelopmentEXE1.model;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MobileAppDevelopmentEXE1.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class GameOverActivity extends AppCompatActivity {

    public MaterialTextView game_over_TEXT;
    public MaterialButton gameOver_BTN_PlayAgain;
    public static final String KEY_STATUS = "KEY_STATUS";
    public static final String KEY_SCORE = "KEY_SCORE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        findview();
        initview();
    }


    private void findview() {
        game_over_TEXT =findViewById(R.id.game_over_TEXT);
        gameOver_BTN_PlayAgain = findViewById(R.id.gameOver_BTN_PlayAgain);
    }

    private void initview() {
        Intent previousIntent = getIntent();
        int score = previousIntent.getIntExtra(KEY_SCORE,0);
        String status = previousIntent.getStringExtra(KEY_STATUS);
        game_over_TEXT.setText(status + score);
        gameOver_BTN_PlayAgain.setOnClickListener(v -> changeActivity());
    }

    private void changeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}