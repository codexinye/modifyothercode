package com.wwj.buttonmenu;

import java.util.ArrayList;

import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnCheckedChangeListener{
	
	private TabHost mTabHost;
	private RadioGroup radioGroup;
	private ViewPager mViewPager = null;
	private LocalActivityManager mLocalActivityManager = null;
	private ArrayList<View> mViewPagerViews = null;
	private ArrayList<RadioButton> mRadioButtonList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		mLocalActivityManager = new LocalActivityManager(this, true);
		mLocalActivityManager.dispatchCreate(savedInstanceState);
		
		mViewPagerViews = new ArrayList<View>(); 
		mRadioButtonList = new ArrayList<RadioButton>();
		// 实例化TabHost
		mTabHost = this.getTabHost();

		Intent homeIntent = new Intent(this, HomeActivity.class);
		Intent subscribeIntent = new Intent(this, SubscribeActivity.class);
		Intent hotIntent = new Intent(this, HotActivity.class);
		Intent financialIntent = new Intent(this, FinancialActivity.class);
		Intent searchIntent = new Intent(this, SearchActivity.class);
		

		mRadioButtonList.add((RadioButton)this.findViewById(R.id.homeRadioButton));
		mRadioButtonList.add((RadioButton)this.findViewById(R.id.subscribeRadioButton));
		mRadioButtonList.add((RadioButton)this.findViewById(R.id.hotRadioButton));
		mRadioButtonList.add((RadioButton)this.findViewById(R.id.financialRadioButton));
		mRadioButtonList.add((RadioButton)this.findViewById(R.id.searchRadioButton));
		
		
		// 添加选项卡
		mTabHost.addTab(mTabHost.newTabSpec("ONE").setIndicator("ONE")
				.setContent(homeIntent));
		mTabHost.addTab(mTabHost.newTabSpec("TWO").setIndicator("TWO")
				.setContent(subscribeIntent));
		mTabHost.addTab(mTabHost.newTabSpec("THREE").setIndicator("THREE")
				.setContent(hotIntent));
		mTabHost.addTab(mTabHost.newTabSpec("FOUR").setIndicator("FOUR")
				.setContent(financialIntent));
		mTabHost.addTab(mTabHost.newTabSpec("FIVE").setIndicator("FIVE")
				.setContent(searchIntent));
		
		radioGroup = (RadioGroup) findViewById(R.id.main_radio);
		//注册监听器
		radioGroup.setOnCheckedChangeListener(this);
		
		mViewPager = (ViewPager) findViewById(R.id.viewPager);

		mViewPagerViews.add(getView("HOME", homeIntent));
		mViewPagerViews.add(getView("SUBSCRIBE", subscribeIntent));
		mViewPagerViews.add(getView("HOT", hotIntent));
		mViewPagerViews.add(getView("FINANCIAL", financialIntent));
		mViewPagerViews.add(getView("SEARCH", searchIntent));
		
		mViewPager.setAdapter(new MyViewPagerAdapter());
		mViewPager.setCurrentItem(0);
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(mViewPager.getCurrentItem() == 0 && ev.getY() < 320){
			return mViewPagerViews.get(0).dispatchTouchEvent(ev);
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId) {
			case R.id.homeRadioButton:
				mTabHost.setCurrentTabByTag("ONE");
				mViewPager.setCurrentItem(0);
				break;
			case R.id.subscribeRadioButton:
				mTabHost.setCurrentTabByTag("TWO");
				mViewPager.setCurrentItem(1);
				break;
			case R.id.hotRadioButton:
				mTabHost.setCurrentTabByTag("THREE");
				mViewPager.setCurrentItem(2);
				break;
			case R.id.financialRadioButton:
				mTabHost.setCurrentTabByTag("FOUR");
				mViewPager.setCurrentItem(3);
				break;
			case R.id.searchRadioButton:
				mTabHost.setCurrentTabByTag("FIVE");
				mViewPager.setCurrentItem(4);
				break;			
			}
	}
	// 根据Intent和id得到View
	private View getView(String id,Intent intent){
		return mLocalActivityManager.startActivity(id, intent).getDecorView();
	}

	class MyViewPagerAdapter extends PagerAdapter{

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager)container).removeView(mViewPagerViews.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(mViewPagerViews.get(position), 0);
			return mViewPagerViews.get(position);

		}

		@Override
		public int getCount() {
			return mViewPagerViews.size();
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
			mTabHost.setCurrentTab(arg0);
			Log.i("wngheng", "onPageSelected : " + arg0);
			mRadioButtonList.get(arg0).setChecked(true);
		}
	} 
}
