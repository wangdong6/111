package com.bantang.app.bantang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bantang.app.bantang.MainActivity;
import com.bantang.app.bantang.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //初始化
        init();
    }

    /**
     * 闪屏
     */
    private void init() {
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    jump();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 跳转，是否是第一次。第一次就去引导页，不是第一次就进主页面
     */
    private void jump() {
   /*     SharedPreferences sp  = getSharedPreferences("args", Context.MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("isFirst", true);
        Intent intent = null;
        if(isFirst){
            intent = new Intent(this,GuideActivity.class);
        }else{
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);*/
        startActivity(new Intent(this, MainActivity.class));
    }
}
