package com.example.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {
    Button signout_btn, reset_btn, toggle_btn;
    TextView timer_txt;

    private boolean started = false;

    Timer timer;
    TimerTask timerTask;
    double time = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timer = new Timer();

        signout_btn = findViewById(R.id.signOutButton);
        reset_btn = findViewById(R.id.resetButton);
        toggle_btn = findViewById(R.id.toggleButton);
        timer_txt = findViewById(R.id.timerText);

        signout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent i = new Intent(TimerActivity.this, AuthActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        toggle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleTimer();
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
    }

    public void toggleTimer() {
        if (started) {
            // stop
            started = false;
            timerTask.cancel();
        } else {
            // start
            started = true;

            timerTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            time++;
                            timer_txt.setText(getTimerText());
                        }
                    });
                }
            };

            timer.scheduleAtFixedRate(timerTask, 0, 1000);
        }
    }

    public void resetTimer() {
        timerTask.cancel();
        time = 0.0;
        started = false;
        timer_txt.setText("00:00:00");
    }

    private String getTimerText() {
        int r = (int) Math.round(time);

        int sec = ((r % 86400) % 3600) % 60;
        int min = ((r % 86400) % 3600) / 60;
        int hrs = ((r % 86400) / 3600);

        return String.format("%02d", hrs) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
    }
}