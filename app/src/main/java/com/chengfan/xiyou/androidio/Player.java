package com.chengfan.xiyou.androidio;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * =============================================================================
 * Copyright (c) 2017 Administrator All rights reserved.
 * Packname com.fjg.open.opensourceproject.utils
 * Created by Administrator.
 * Created time 2017/12/25 0025 下午 5:05.
 * Version   1.0;
 * Describe :  音频播放控制器
 * History:
 * ==============================================================================
 */

public class Player implements MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener {

    public MediaPlayer mediaPlayer;
    private float mSpeed = 1f;  //播放速率
    public Player() {
        init();
    }

    private void init() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (mMediaPlayerFinish != null) {
                    mMediaPlayerFinish.onFinish();
                }
                return false;
            }
        });
    }


    public void prepare() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.prepare();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void playUrl(String url) {
        if (mediaPlayer == null) {
            init();
        }
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play(String url) {
        if (mediaPlayer == null) {
            init();
        }
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void setPlayerSpeed(float speed) {
        this.mSpeed = speed;
    }

    public void changePlayerSpeed(float speed) {
        // this checks on API 23 and up
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
            } else {
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
                mediaPlayer.pause();
            }
            this.mSpeed = speed;
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        int duration = mediaPlayer.getDuration();
        //tv_total.setText(DateUtil.formatterTime(duration));
        mp.start();
        changePlayerSpeed(mSpeed);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mMediaPlayerFinish != null) {
            mMediaPlayerFinish.onFinish();
        }
    }

    public interface MediaPlayerFinish {
        void onFinish();
    }

    private MediaPlayerFinish mMediaPlayerFinish;

    public void setMediaPlayerFinish(MediaPlayerFinish mediaPlayerFinish) {
        this.mMediaPlayerFinish = mediaPlayerFinish;
    }
}
