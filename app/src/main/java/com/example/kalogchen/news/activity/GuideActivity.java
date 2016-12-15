package com.example.kalogchen.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.kalogchen.news.R;
import com.example.kalogchen.news.utils.SharedPreferencesUtils;

import java.util.ArrayList;

/**
 * Created by kalogchen on 2016/12/14.
 */

public class GuideActivity extends Activity {

    private ViewPager vpGuide;

    //图片数组
    private ArrayList<ImageView> ivList;

    //引导页的圆点
    private LinearLayout llPoint;

    //把图片路径加载到数组里
    int[] ivId = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};

    //引导页两个圆点间距
    private int pointWidth;

    //引导页红点
    private View redPoint;

    //开始体验按钮
    private Button btStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);

        vpGuide = (ViewPager) findViewById(R.id.vg_guide);
        llPoint = (LinearLayout) findViewById(R.id.ll_point);
        redPoint =  findViewById(R.id.red_point);
        btStart = (Button) findViewById(R.id.bt_start );

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //记录用户已经进入过引导页
                SharedPreferencesUtils.setBoolean(GuideActivity.this, "into_guide_page", true);

                //跳转到主页面
                Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        initView();

        vpGuide.setAdapter(new GuideAdapter());

        vpGuide.addOnPageChangeListener(new guidePageListener());
    }

    /**
     * 初始化界面
     */
    private void initView() {
        ivList = new ArrayList<ImageView>();


        //初始化引导页
        for (int i=0; i<ivId.length; i++) {
            ImageView ivGuide = new ImageView(this);
            //设置引导页图片
            ivGuide.setBackgroundResource(ivId[i]);
            //将要分页显示的view装入集合中
            ivList.add(ivGuide);
        }

        //初始化引导页下面的圆点
        for (int i=0; i<ivId.length; i++) {
            View point = new View(this);
            //设置引导页下面的圆点
            point.setBackgroundResource(R.drawable.gray_point_shape);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            //设置圆点间的距离
            if (i>0) {
                params.leftMargin = 10;
            }
            //设置圆点大小
            point.setLayoutParams(params);
            //将圆点添加到线性布局中
            llPoint.addView(point);

        }

        //获取视图树，在onCreate里布局没有马上画出来，只有当布局布置完成后才去计算圆点间的距离    measure（测量大小） layout（界面位置）   onDraw（绘制界面）
        llPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //当layout执行结束后调用此方法
            @Override
            public void onGlobalLayout() {
                //计算相邻两个圆点的距离
                pointWidth = llPoint.getChildAt(1).getLeft() - llPoint.getChildAt(0).getLeft();
            }
        });


    }


    /**
     * ViewPager适配器
     */
    class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            //返回要显示的view的个数
            return ivId.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //当前视图添加到container中
            container.addView(ivList.get(position));
            //返回当前view
            return ivList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //从当前container中删除指定位置（position）的View
            container.removeView(ivList.get(position));
        }
    }

    /**
     * 引导页滑动监听
     */
    class guidePageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //计算红点要移动的距离,当移动到另一页的时候，positionOffset为0.所以要加上pointWidth * position
            float moveWidth = pointWidth * positionOffset + pointWidth * position;
            //获取红点的布局参数
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) redPoint.getLayoutParams();
            //通过重新设置红点的左边距让红点进行移动
            params.leftMargin = (int) moveWidth;
            //设置红点布局参数
            redPoint.setLayoutParams(params);

        }

        @Override
        public void onPageSelected(int position) {
            //当移动到最后一个引导页的时候显示“开始体验”按钮，否则不显示
            if (position == ivId.length - 1) {
                btStart.setVisibility(View.VISIBLE);
            }else {
                btStart.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
