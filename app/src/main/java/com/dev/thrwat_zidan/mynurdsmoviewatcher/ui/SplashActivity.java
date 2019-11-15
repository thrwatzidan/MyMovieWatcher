package com.dev.thrwat_zidan.mynurdsmoviewatcher.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.dev.thrwat_zidan.mynurdsmoviewatcher.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)) {
            finish();
            finishAffinity();

            Log.i("ISEXIT_3", "Exit Call");
        }else{

            Log.i("ISEXIT_3", "Fail Call");



            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity();
                }
            }, 7000);

        }
    }



    public void startActivity() {
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
//        if(Build.VERSION.SDK_INT>20){
//            ActivityOptions options =
//                    ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this);
//            startActivity(i,options.toBundle());
//        }else {
        startActivity(i);
        finish();

        // }
    }
}
