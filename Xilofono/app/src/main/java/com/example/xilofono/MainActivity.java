package com.example.xilofono;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void play_1(View view){
        MediaPlayer mp= MediaPlayer.create(this, R.raw.note1);
        mp.start();
    }

    public void play_2(View view){
        MediaPlayer mp= MediaPlayer.create(this, R.raw.note2);
        mp.start();
    }

    public void play_3(View view){
        MediaPlayer mp= MediaPlayer.create(this, R.raw.note3);
        mp.start();
    }

    public void play_4(View view){
        MediaPlayer mp= MediaPlayer.create(this, R.raw.note4);
        mp.start();
    }

    public void play_5(View view){
        MediaPlayer mp= MediaPlayer.create(this, R.raw.note5);
        mp.start();
    }

    public void play_6(View view){
        MediaPlayer mp= MediaPlayer.create(this, R.raw.note6);
        mp.start();
    }

    public void play_7(View view){
        MediaPlayer mp= MediaPlayer.create(this, R.raw.note7);
        mp.start();
    }

    public void play_8(View view){
        MediaPlayer mp= MediaPlayer.create(this, R.raw.note1);
        mp.start();
    }

}
