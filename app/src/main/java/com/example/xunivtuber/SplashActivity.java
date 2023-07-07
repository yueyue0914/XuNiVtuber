package com.example.xunivtuber;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 5000; // 5秒

    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler = new Handler();

        // 设置定时器，延迟跳转到下一个页面
        mRunnable = new Runnable() {
            @Override
            public void run() {
                goToNextPage();
            }
        };
        mHandler.postDelayed(mRunnable, SPLASH_DELAY);

        Button skipButton = findViewById(R.id.skip_button);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(mRunnable); // 移除定时器
                goToNextPage();
            }
        });
    }

    private void goToNextPage() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // 关闭当前活动
    }
}
