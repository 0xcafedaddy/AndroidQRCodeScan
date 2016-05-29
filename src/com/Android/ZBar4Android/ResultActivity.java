/*
 * ����ɨ����
 * ���ߣ���Ԫ��
 * ʱ�䣺2013��12��21��
 * �ܽ᣺������һ�����⣬��������������ϰ��·��ؼ���ʱ��������������������д����صķ����������������
 * ˭Ҫ��֪�����������ô������ǵø���˵һ����
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
		//��ȡɨ����
		Intent intent=getIntent();
		Bundle mData=intent.getExtras();
		CharSequence mResult=mData.getCharSequence("ScanResult");
		StringHelper mHelper=new StringHelper(mResult.toString());
		//mResult=mHelper.SplitFormDict();
		mResult=mHelper.toString();
		tv=(TextView)findViewById(R.id.TextResult);
		System.out.println("mResult: "+mResult);
		tv.setText(mResult.toString());
		//����ɨ�����
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
