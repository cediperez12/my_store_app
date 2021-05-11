package com.iced.mystore.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;
import butterknife.BindView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.iced.mystore.R;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.splash_txt_layout)
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){



        Animation splashAnimation = new AlphaAnimation(0,1);
        splashAnimation.setDuration(3000);

        Thread splashInitThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);

                    Intent intent = new Intent(MainActivity.this,DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        splashInitThread.start();
    }
}