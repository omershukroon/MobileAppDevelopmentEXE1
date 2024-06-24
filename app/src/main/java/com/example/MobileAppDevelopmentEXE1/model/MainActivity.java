package com.example.MobileAppDevelopmentEXE1.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.MobileAppDevelopmentEXE1.R;
import com.example.MobileAppDevelopmentEXE1.logic.GameManager;
import com.google.android.material.button.MaterialButton;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private AppCompatImageView[][] imgMushroomMat;
    private AppCompatImageView[] imgMarioArr;
    private AppCompatImageView[] imgHeartsArr;
    private GameManager gameManager;
    private MaterialButton main_BTN_left;
    private MaterialButton main_BTN_right;
    private static final long DELAY = 1000L;
    private Timer timer;
    private long startTime;
    private boolean timerOn = false;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        gameManager = new GameManager(imgMushroomMat.length, imgMushroomMat[0].length, imgHeartsArr.length);
        initViews();
    }

    private void findView() {
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);

        imgHeartsArr = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};

        imgMarioArr = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_left_mario),
                findViewById(R.id.main_IMG_center_mario),
                findViewById(R.id.main_IMG_right_mario)};

        imgMushroomMat = new AppCompatImageView[][]{
                {
                        findViewById(R.id.main_IMG_left_mushroomI0J0),
                        findViewById(R.id.main_IMG_center_mushroomI0J1),
                        findViewById(R.id.main_IMG_right_mushroomI0J2)
                },
                {
                        findViewById(R.id.main_IMG_left_mushroomI1J0),
                        findViewById(R.id.main_IMG_center_mushroomI1J1),
                        findViewById(R.id.main_IMG_right_mushroomI1J2)
                },

                {
                        findViewById(R.id.main_IMG_left_mushroomI2J0),
                        findViewById(R.id.main_IMG_center_mushroomI2J1),
                        findViewById(R.id.main_IMG_right_mushroomI2J2)
                },


                {
                        findViewById(R.id.main_IMG_left_mushroomI3J0),
                        findViewById(R.id.main_IMG_center_mushroomI3J1),
                        findViewById(R.id.main_IMG_right_mushroomI3J2)
                }

        };
    }

    private void initViews() {
        main_BTN_left.setOnClickListener(v -> BTNClicked(0));
        main_BTN_right.setOnClickListener(v -> BTNClicked(1));
        if (!timerOn) {
            startTime = System.currentTimeMillis();
            timerOn = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> updateMatrixUI());
                }
            },0L,DELAY);
        }
    }
    private void updateMatrixUI(){
        long currentTime = System.currentTimeMillis();
        int seconds = (int)(currentTime - startTime)/1000;
        gameManager.changeMatrixPosition(seconds);
        if(gameManager.checkIfCollision(gameManager.getPlayerIndex())){
            toastAndVibrate("You lose "+ gameManager.getCollisions() + " life!");
        }
        refreshUI();
    }

    private void BTNClicked(int i) {
        gameManager.changePlayerPosition(i);
        if(gameManager.checkIfCollision(gameManager.getPlayerIndex())){
            toastAndVibrate("You lose "+ gameManager.getCollisions() + " life!");
        }
        refreshUI();
    }

    private void refreshUI() {
        if(gameManager.isGameOver()){
            long currentTime = System.currentTimeMillis();
            int sec = (int)(currentTime-startTime) /1000 ;
            changeActivity("Time played: ",sec);
        }

        for (int i = 0; i < gameManager.getNumRows(); i++) {
            for (int j = 0; j < gameManager.getNumCols(); j++) {
                if (i == 0) {
                    if (gameManager.getPacmanArr()[j] == 1) {
                        imgMarioArr[j].setVisibility(View.VISIBLE);
                    } else {
                        imgMarioArr[j].setVisibility(View.INVISIBLE);
                    }
                }

                if (gameManager.getGhostMat()[i][j] == 1) {
                    imgMushroomMat[i][j].setVisibility(View.VISIBLE);
                } else {
                    imgMushroomMat[i][j].setVisibility(View.INVISIBLE);
                }

            }
        }
        if (gameManager.getCollisions() != 0) {
            imgHeartsArr[gameManager.getCollisions() - 1].setVisibility(View.INVISIBLE);
        }
    }

    private void changeActivity(String msg, int sec) {
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra(GameOverActivity.KEY_STATUS,msg);
        intent.putExtra(GameOverActivity.KEY_SCORE,sec);
        startActivity(intent);
        timer.cancel();
        finish();
    }


    private void toastAndVibrate(String text) {
        vibrate();
        toast(text);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }

}