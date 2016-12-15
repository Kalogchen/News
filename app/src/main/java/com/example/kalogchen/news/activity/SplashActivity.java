package com.example.kalogchen.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.example.kalogchen.news.R;
import com.example.kalogchen.news.utils.SharedPreferencesUtils;

public class SplashActivity extends Activity {

    RelativeLayout rlSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rlSplash = (RelativeLayout) findViewById(R.id.rl_splash);

        splashAnim();

    }

    /**
     * 闪屏页动画
     */

    private void splashAnim() {

        //动画集合
        AnimationSet animSet = new AnimationSet(false);

        //旋转动画
        RotateAnimation rotateAnim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setDuration(2000);//动画持续时间
        rotateAnim.setFillAfter(true);//保持动画完成后的效果

        //缩放动画
        ScaleAnimation scaleAnim = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnim.setDuration(2000);//动画持续时间
        scaleAnim.setFillAfter(true);//保持动画完成后的效果

        //渐变动画
        AlphaAnimation alphaAnim = new AlphaAnimation(0, 1);
        alphaAnim.setDuration(2000);//动画持续时间
        alphaAnim.setFillAfter(true);//保持动画完成后的效果

        //添加动画到集合
        animSet.addAnimation(rotateAnim);
        animSet.addAnimation(scaleAnim);
        animSet.addAnimation(alphaAnim);

        //给动画设置监听，动画播放完后就进入新手引导页
        animSet.setAnimationListener(new Animation.AnimationListener() {
            //动画开始执行
            @Override
            public void onAnimationStart(Animation animation) {
            }

            //动画执行结束
            @Override
            public void onAnimationEnd(Animation animation) {
                intoGuideOrHome();
            }

            //动画重复执行
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        //给控件设置动画
        rlSplash.startAnimation(animSet);

    }

    /**
     * 判断闪屏页过后是进入引导页还是主页面
     */
    private void intoGuideOrHome() {

        boolean intoGuide = SharedPreferencesUtils.getBoolean(this, "into_guide_page", false);

        if (intoGuide) {
            //已经进入过引导页后，再进来直接进入主页面
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        }else {
            //没有进入过引导页，直接进入引导页
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
        }
        finish();
    }

}
