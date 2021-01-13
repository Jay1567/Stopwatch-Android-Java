package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button start, stop, reset;
    TextView txtWatch;
    boolean isClockRunning = false;
    long startTime = 0;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.btnStart);
        stop = findViewById(R.id.btnStop);
        reset = findViewById(R.id.btnReset);
        txtWatch = findViewById(R.id.txtWatch);
        handler = new Handler();
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            long currentTime = SystemClock.uptimeMillis() - startTime;

            int mil = (int)currentTime%1000 /10;    //Showing 2 out of 3 digits
            int sec = (int) currentTime/1000;
            int min = sec / 60;
            sec = sec%60;

            txtWatch.setText(String.format("%02d:%02d.%02d", min, sec, mil));
            handler.postDelayed(this, 0);
        }
    };

    public void startClock(View view){
        if(isClockRunning) {
            Toast.makeText(this,"Stopwatch is already running..", Toast.LENGTH_SHORT).show();
            return;
        }

        isClockRunning = true;
        startTime = SystemClock.uptimeMillis();
        handler.postDelayed(run, 0);


    }

    public void pauseClock(View view){
        isClockRunning = false;
        handler.removeCallbacks(run);
    }

    public void resetClock(View view){
        if(isClockRunning){
            Toast.makeText(this,"Stopwatch is Running. Stop before resetting.", Toast.LENGTH_SHORT).show();
            return;
        }

        startTime = 0;
        txtWatch.setText("00:00.00");
    }
}