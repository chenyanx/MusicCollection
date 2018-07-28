package com.example.jeremychen.musicapp.utils;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by jeremychen on 2018/7/25.
 */

public class Player {

    private static Player player = new Player();
    private MediaPlayer mediaPlayer;
    public boolean inPlaying = false;

    private Player() {
        mediaPlayer = new MediaPlayer();

    }
    public static Player getInstance()
    {
        return player;
    }

    public void play(final String url)
    {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }

    public void stop()
    {
        mediaPlayer.stop();
    }


}
