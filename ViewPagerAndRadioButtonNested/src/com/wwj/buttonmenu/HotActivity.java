package com.wwj.buttonmenu;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class HotActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot);
		Log.i("wangheng", "HotActivity ---> onCreate");
	}
}
