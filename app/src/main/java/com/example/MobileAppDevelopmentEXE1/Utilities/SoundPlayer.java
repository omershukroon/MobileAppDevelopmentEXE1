package com.example.MobileAppDevelopmentEXE1.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SoundPlayer {

    private Context context;
    private Executor executor;
    private MediaPlayer mediaPlayer;
    private MediaPlayer BackgroundMediaPlayer;

    public SoundPlayer(Context context) {
        this.context = context;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void playBackgroundSound(int soundID) {
        if (BackgroundMediaPlayer == null) {
            executor.execute(() -> {

                BackgroundMediaPlayer = MediaPlayer.create(context, soundID);
                BackgroundMediaPlayer.setLooping(true);
                BackgroundMediaPlayer.setVolume(1.0f, 1.0f);
                BackgroundMediaPlayer.start(); // no need to call prepare(); create() does that for you
            });
        }


    }

    public void playSound(int soundID) {
        if (mediaPlayer == null) {
            executor.execute(() -> {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                MediaPlayer mediaPlayer = MediaPlayer.create(context, soundID);
                mediaPlayer.setLooping(false);
                mediaPlayer.setVolume(0.5f, 0.5f);
                mediaPlayer.start(); // no need to call prepare(); create() does that for you
            });
        }

    }


    public void stopSound() {
        if (mediaPlayer != null) {
            executor.execute(() -> {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            });
        }
        if (BackgroundMediaPlayer != null) {
            executor.execute(() -> {
                BackgroundMediaPlayer.stop();
                BackgroundMediaPlayer.release();
                BackgroundMediaPlayer = null;
            });
        }
    }
}
