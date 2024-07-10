package com.example.MobileAppDevelopmentEXE1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.MobileAppDevelopmentEXE1.Interfaces.MoveCallback;
import com.example.MobileAppDevelopmentEXE1.Utilities.MoveDetector;
import com.example.MobileAppDevelopmentEXE1.Utilities.SharePreferencesManager;
import com.example.MobileAppDevelopmentEXE1.Utilities.SoundPlayer;
import com.example.MobileAppDevelopmentEXE1.logic.GameManager;
import com.example.MobileAppDevelopmentEXE1.model.FragmentActivity;
import com.example.MobileAppDevelopmentEXE1.model.Player;
import com.example.MobileAppDevelopmentEXE1.model.PlayerList;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_CHARACTER = "KEY_CHARACTER";
    public static final String KEY_GAMEMODE = "KEY_GAMEMODE";
    public static final String KEY_PLAYER_NAME = "KEY_PLAYER_NAME";
    public static final String KEY_LATITUDE = "KEY_LATITUDE";
    public static final String KEY_LONGITUDE = "KEY_LONGITUDE";
    private AppCompatImageView[][] imgMushroomMat;
    private AppCompatImageView[][] imgCoinsMat;
    private AppCompatImageView[] imgMarioArr;
    private AppCompatImageView[] imgHeartsArr;
    private MaterialTextView main_TEXT_score;
    private MaterialButton main_BTN_left;
    private MaterialButton main_BTN_right;
    private GameManager gameManager;
    private SoundPlayer soundPlayer;
    private MoveDetector moveDetector;
    private Player player;
    private PlayerList playerList;
    private SharePreferencesManager preferencesManager;
    Gson gson = new Gson();
    private static final long DELAY = 1000L;
    private Timer timer;
    private long startTime;
    private boolean timerOn = false;
    private int gameMode;
    private int character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        SharePreferencesManager.init(this);
        preferencesManager = SharePreferencesManager.getInstance();
        playerList = new PlayerList();
        playerList.getPlayerList().addAll(preferencesManager.loadPlayerList());
        Intent previousIntent = getIntent();
        character = previousIntent.getIntExtra(KEY_CHARACTER, 1);
        gameMode = previousIntent.getIntExtra(KEY_GAMEMODE, 0);
        player = new Player().setCharacterIcon(previousIntent.getIntExtra(KEY_CHARACTER, R.drawable.mario));
        player.setName(previousIntent.getStringExtra(KEY_PLAYER_NAME));
        player.setLatitude(previousIntent.getDoubleExtra(KEY_LATITUDE, 0.0));
        player.setLongitude(previousIntent.getDoubleExtra(KEY_LONGITUDE, 0.0));
        gameManager = new GameManager(imgMushroomMat.length, imgMushroomMat[0].length, imgHeartsArr.length);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundPlayer = new SoundPlayer(this);
        soundPlayer.playBackgroundSound(R.raw.ground_theme);
        if(gameMode == 1){
            moveDetector.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundPlayer.stopSound();
        if (timer != null) {
            timer.cancel();
            timerOn = false;
        }
        if(moveDetector != null){
            moveDetector.stop();
        }
    }

    private void findView() {
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_TEXT_score = findViewById(R.id.main_TEXT_score);

        imgHeartsArr = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };

        imgMarioArr = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_mario_0),
                findViewById(R.id.main_IMG_mario_1),
                findViewById(R.id.main_IMG_mario_2),
                findViewById(R.id.main_IMG_mario_3),
                findViewById(R.id.main_IMG_mario_4)
        };

        imgMushroomMat = new AppCompatImageView[][]{
                {findViewById(R.id.main_IMG_left_mushroomI0J0), findViewById(R.id.main_IMG_center_mushroomI0J1), findViewById(R.id.main_IMG_right_mushroomI0J2), findViewById(R.id.main_IMG_right_mushroomI0J3), findViewById(R.id.main_IMG_right_mushroomI0J4)},
                {findViewById(R.id.main_IMG_left_mushroomI1J0), findViewById(R.id.main_IMG_center_mushroomI1J1), findViewById(R.id.main_IMG_right_mushroomI1J2), findViewById(R.id.main_IMG_right_mushroomI1J3), findViewById(R.id.main_IMG_right_mushroomI1J4)},
                {findViewById(R.id.main_IMG_left_mushroomI2J0), findViewById(R.id.main_IMG_center_mushroomI2J1), findViewById(R.id.main_IMG_right_mushroomI2J2), findViewById(R.id.main_IMG_right_mushroomI2J3), findViewById(R.id.main_IMG_right_mushroomI2J4)},
                {findViewById(R.id.main_IMG_left_mushroomI3J0), findViewById(R.id.main_IMG_center_mushroomI3J1), findViewById(R.id.main_IMG_right_mushroomI3J2), findViewById(R.id.main_IMG_right_mushroomI3J3), findViewById(R.id.main_IMG_right_mushroomI3J4)},
                {findViewById(R.id.main_IMG_left_mushroomI4J0), findViewById(R.id.main_IMG_center_mushroomI4J1), findViewById(R.id.main_IMG_right_mushroomI4J2), findViewById(R.id.main_IMG_right_mushroomI4J3), findViewById(R.id.main_IMG_right_mushroomI4J4)}
        };

        imgCoinsMat = new AppCompatImageView[][]{
                {findViewById(R.id.main_IMG_left_coinI0J0), findViewById(R.id.main_IMG_center_coinI0J1), findViewById(R.id.main_IMG_right_coinI0J2), findViewById(R.id.main_IMG_right_coinI0J3), findViewById(R.id.main_IMG_right_coinI0J4)},
                {findViewById(R.id.main_IMG_left_coinI1J0), findViewById(R.id.main_IMG_center_coinI1J1), findViewById(R.id.main_IMG_right_coinI1J2), findViewById(R.id.main_IMG_right_coinI1J3), findViewById(R.id.main_IMG_right_coinI1J4)},
                {findViewById(R.id.main_IMG_left_coinI2J0), findViewById(R.id.main_IMG_center_coinI2J1), findViewById(R.id.main_IMG_right_coinI2J2), findViewById(R.id.main_IMG_right_coinI2J3), findViewById(R.id.main_IMG_right_coinI2J4)},
                {findViewById(R.id.main_IMG_left_coinI3J0), findViewById(R.id.main_IMG_center_coinI3J1), findViewById(R.id.main_IMG_right_coinI3J2), findViewById(R.id.main_IMG_right_coinI3J3), findViewById(R.id.main_IMG_right_coinI3J4)},
                {findViewById(R.id.main_IMG_left_coinI4J0), findViewById(R.id.main_IMG_center_coinI4J1), findViewById(R.id.main_IMG_right_coinI4J2), findViewById(R.id.main_IMG_right_coinI4J3), findViewById(R.id.main_IMG_right_coinI4J4)}
        };
    }

    private void initViews() {
        if (gameMode == 0) {
            main_BTN_left.setOnClickListener(v -> BTNClicked(0));
            main_BTN_right.setOnClickListener(v -> BTNClicked(1));
        } else {
            main_BTN_left.setVisibility(View.INVISIBLE);
            main_BTN_right.setVisibility(View.INVISIBLE);
            initMoveDetector();
        }
        if (character == R.drawable.luigi) {
            setPlayerCharacter();
        }
        main_TEXT_score.setText(String.valueOf(gameManager.getScore()));
        if (!timerOn) {
            startTime = System.currentTimeMillis();
            timerOn = true;
            timer = new Timer();
            startTimer(timer);
        }
    }

    private void startTimer(Timer timer) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> updateMatrixUI());
            }
        }, 0L, DELAY);
    }

    private void setPlayerCharacter() {
        for (AppCompatImageView imgMario : imgMarioArr) {
            imgMario.setImageResource(R.drawable.luigi);
        }
    }

    private void updateMatrixUI() {
        long currentTime = System.currentTimeMillis();
        int seconds = (int) (currentTime - startTime) / 1000;
        gameManager.changeMatrixPosition(seconds);
        if (gameManager.checkIfCollision(gameManager.getPlayerIndex())) {
            if (soundPlayer != null) {
                soundPlayer.playSound(R.raw.super_mario_lost_life_sound_effect);
            }
            toastAndVibrate("You lose " + gameManager.getCollisions() + " life!");
        }
        if (gameManager.checkIfGetCoin(gameManager.getPlayerIndex())) {
            if (soundPlayer != null) {
                soundPlayer.playSound(R.raw.super_mario_coin_sound_effect);
            }
        }
        refreshUI();
    }

    private void BTNClicked(int i) {
        gameManager.changePlayerPosition(i);
        if (gameManager.checkIfCollision(gameManager.getPlayerIndex())) {
            if (soundPlayer != null) {
                soundPlayer.playSound(R.raw.super_mario_lost_life_sound_effect);
            }
            toastAndVibrate("You lose " + gameManager.getCollisions() + " life!");
        }
        if (gameManager.checkIfGetCoin(gameManager.getPlayerIndex())) {
            if (soundPlayer != null) {
                soundPlayer.playSound(R.raw.super_mario_coin_sound_effect);
            }
        }
        refreshUI();
    }

    private void refreshUI() {
        if (gameManager.isGameOver()) {
            changeActivity();
        }
        main_TEXT_score.setText(String.valueOf(gameManager.getScore()));

        for (int i = 0; i < gameManager.getNumRows(); i++) {
            for (int j = 0; j < gameManager.getNumCols(); j++) {
                if (i == 0) {
                    imgMarioArr[j].setVisibility(gameManager.getMariosArr()[j] == 1 ? View.VISIBLE : View.INVISIBLE);
                }
                imgMushroomMat[i][j].setVisibility(gameManager.getMushroomsMat()[i][j] == 1 ? View.VISIBLE : View.INVISIBLE);
                imgCoinsMat[i][j].setVisibility(gameManager.getCoinsMat()[i][j] == 1 ? View.VISIBLE : View.INVISIBLE);
            }
        }
        if (gameManager.getCollisions() != 0) {
            imgHeartsArr[gameManager.getCollisions() - 1].setVisibility(View.INVISIBLE);
        }
    }

    private void changeActivity() {
        player.setScore(gameManager.getScore());
        playerList.addPlayer(player);
        savePlayerList();
        Intent intent = new Intent(this, FragmentActivity.class);
        startActivity(intent);
        timer.cancel();
        finish();
    }

    private void savePlayerList() {
        preferencesManager.savePlayerList(playerList.getPlayerList());
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
        if (v != null) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }

    private void initMoveDetector() {
        moveDetector = new MoveDetector(this, new MoveCallback() {
            @Override
            public void moveX() {
                BTNClicked(moveDetector.getMoveDirectionX());
            }
        });
    }
}