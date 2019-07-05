package com.coolweather.android;

import android.app.Fragment;
import android.support.v4.content.res.ConfigurationHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;


public class MainActivity extends AppCompatActivity {

    public Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //LitePal.initialize(this);

        setContentView(R.layout.activity_main);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 按下BACK，同时没有重复
//
//            if(fragment instanceof ChooseAreaFragment)
//            {
//                ((ChooseAreaFragment)fragment).handleBack();
//                Log.d("query","onBackPressed is used");
//
//                return  true;
//            }

        }

        return super.onKeyDown(keyCode, event);
    }

}
