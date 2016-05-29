/*
 * 返回扫描结果
 * 作者：秦元培
 * 时间：2013年12月21日
 * 总结：这里有一个问题，就是在这个界面上按下返回键的时候程序会立即报错，试着重写过相关的方法都解决不了问题
 * 谁要是知道这个问题怎么解决，记得给我说一声啊
 */
package com.Android.ZBar4Android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity 
{

	private TextView tv;
	private Button BtnBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_result);
		//获取扫描结果
		Intent intent=getIntent();
		Bundle mData=intent.getExtras();
		CharSequence mResult=mData.getCharSequence("ScanResult");
		StringHelper mHelper=new StringHelper(mResult.toString());
		//mResult=mHelper.SplitFormDict();
		mResult=mHelper.toString();
		tv=(TextView)findViewById(R.id.TextResult);
		System.out.println("mResult: "+mResult);
		tv.setText(mResult.toString());
		//返回扫描界面
		BtnBack=(Button)findViewById(R.id.BtnBack);
		BtnBack.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				Intent intent=new Intent(ResultActivity.this,MainActivity.class);
				startActivity(intent);
			}
			
		});
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
	}
	
}
