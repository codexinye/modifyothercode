package com.wwj.buttonmenu;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

/**
 * 
 * @author wangheng
 * 
 */
public class HomeActivity extends Activity {
	
	private Timer mTimer = null;
	private Handler mHandler = new Handler();
	
	private LinearLayout mLinearLayout = null;
	private ViewPager mViewPager = null;
	private int[] mImages = {
			R.drawable.feature_guide_0,R.drawable.feature_guide_1,R.drawable.feature_guide_2,
			R.drawable.feature_guide_3,R.drawable.feature_guide_4,R.drawable.feature_guide_5
			};
	private ImageView[] mViewArray = new ImageView[mImages.length];
	private ImageView[] mIndicationArray = new ImageView[mImages.length];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home2);
		Log.i("wangheng", "HomeActivity ---> onCreate");
		mLinearLayout = (LinearLayout) findViewById(R.id.homeIndicationLinearLayout);
		mViewPager = (ViewPager) findViewById(R.id.homeViewPager);

		initViews();
	}
	@Override
	protected void onDestroy() {
		if(mTimer != null){
			mTimer.cancel();
			mTimer = null;
		}
		super.onDestroy();
	}
	private void initViews() {
		int len = mViewArray.length;
		for(int i = 0;i < len;i++){
			ImageView iv01 = new ImageView(HomeActivity.this);
			ViewGroup.LayoutParams params01 = 
					new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);
			iv01.setLayoutParams(params01);
			iv01.setImageResource(mImages[i]);
			iv01.setScaleType(ScaleType.FIT_XY);
			mViewPager.addView(iv01);
			mViewArray[i] = iv01;
			iv01.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.i("wangheng", "===========>hashcode:" + v.hashCode());
				}
			});
			
			ImageView iv02 = new ImageView(HomeActivity.this);
			LinearLayout.LayoutParams params02 = 
					new LinearLayout.LayoutParams(15,15);
			params02.leftMargin = 5;
			params02.rightMargin = 5;
			iv02.setLayoutParams(params02);
			if(i == 0){
				iv02.setBackgroundResource(R.drawable.page_indicator_focused);
			}else{
				iv02.setBackgroundResource(R.drawable.page_indicator);				
			}
			mLinearLayout.addView(iv02);
			mIndicationArray[i] = iv02;
		}
		
		mViewPager.setAdapter(new MyViewPagerAdapter());
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						int index = mViewPager.getCurrentItem();
						if(index < mViewArray.length - 1){
							index ++;
						}else{
							index = 0;
						}
						mViewPager.setCurrentItem(index);
						int len = mIndicationArray.length;
						for(int i = 0;i < len;i++){
							if(i == index){
								mIndicationArray[i].setBackgroundResource(R.drawable.page_indicator_focused);
							}else{
								mIndicationArray[i].setBackgroundResource(R.drawable.page_indicator);
							}
						}
					}
				});
			}
		}, 5000, 5000);
	}
	
	class MyViewPagerAdapter extends PagerAdapter{

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager)container).removeView(mViewArray[position]);
		}

		@Override
		public Object instantiateItem(View container, int position) {
			if(mViewArray[position].getParent() == null){
				((ViewPager) container).addView(mViewArray[position], 0);
			}
			
			return mViewArray[position];

		}

		@Override
		public int getCount() {
			return mViewArray.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		@Override
		public Parcelable saveState() {
			return null;
		}
		
	}
	class MyOnPageChangeListener implements OnPageChangeListener{
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int arg0) {
			mViewPager.setCurrentItem(arg0);
			int len = mIndicationArray.length;
			for(int i = 0;i < len;i++){
				if(i == arg0){
					mIndicationArray[i].setBackgroundResource(R.drawable.page_indicator_focused);
				}else{
					mIndicationArray[i].setBackgroundResource(R.drawable.page_indicator);
				}
			}
		}
	}
	
	
}
