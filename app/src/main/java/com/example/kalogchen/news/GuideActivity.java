package com.example.kalogchen.news;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by kalogchen on 2016/12/14.
 */

public class GuideActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);

    }
}
