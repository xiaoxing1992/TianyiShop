package tianyishop.weiwei.com.tianyishop.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

import tianyishop.weiwei.com.tianyishop.R;

public class SplashActivity extends AppCompatActivity {
    Timer timer = new Timer();
    int max = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                max--;
                if (max < 1) {
                    timer.cancel();
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }
}
