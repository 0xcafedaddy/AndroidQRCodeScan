
/*
 * ZBar4Android
 * 作者：秦元培
 * 时间：2013年12月21日
 * 需要解决的问题有：
 * 1、返回内容的正则解析
 * 2、如果锁屏后打开程序会报错
 * 3、没有校正框，画不出来啊，郁闷
 * 4、可能会与其它相机应用冲突，如微信
 * 5、条形码还是读不出来
 */
package com.Android.ZBar4Android;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

public class MainActivity extends Activity
{
	//关于按钮
	private Button BtnAbout;
	//相机
    private Camera mCamera;
    //预览视图
    private CameraPreview mPreview;
    //自动聚焦
    private Handler mAutoFocusHandler;
    //图片扫描器
    private ImageScanner mScanner;
    //弹出窗口
    private PopupWindow mPopupWindow;
    //是否扫描完毕
    private boolean IsScanned = false;
    //是否处于预览状态
    private boolean IsPreview = true;
    //是否显示弹出层
    private boolean IsShowPopup=false;

    //加载iconvlib
    static
    {
        System.loadLibrary("iconv");
    }
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_main);
        //设置屏幕方向为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //自动聚焦线程
        mAutoFocusHandler = new Handler();
        //获取相机实例
        mCamera = getCameraInstance();
		if(mCamera == null)
		{
			//在这里写下获取相机失败的代码
			AlertDialog.Builder mBuilder=new AlertDialog.Builder(this);
			mBuilder.setTitle("ZBar4Android");
			mBuilder.setMessage("ZBar4Android获取相机失败，请重试！");
			mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
			{

				@Override
				public void onClick(DialogInterface mDialogInterface, int mIndex) 
				{
					MainActivity.this.finish();
				}
			});
			AlertDialog mDialog=mBuilder.create();
			mDialog.show();
		}
        //实例化Scanner
        mScanner = new ImageScanner();
        mScanner.setConfig(0, Config.X_DENSITY, 3);
        mScanner.setConfig(0, Config.Y_DENSITY, 3);
       //设置相机预览视图
        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
        preview.addView(mPreview);
        if (IsScanned)
          {
             IsScanned = false;
             mCamera.setPreviewCallback(previewCb);
             mCamera.startPreview();
             IsPreview = true;
             mCamera.autoFocus(autoFocusCB);
          }
        //获取BtnAbout，显示程序信息
        BtnAbout=(Button)findViewById(R.id.BtnAbout);
        BtnAbout.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				//如果弹出层已打开,销毁弹出层
				if(IsShowPopup)
				{
					mPopupWindow.dismiss();
					IsShowPopup=false;
				}
				else
				{
					//否则显示弹出层
					mPopupWindow=new PopupWindow();
					LayoutInflater mInflater=LayoutInflater.from(getApplicationContext());
					View view=mInflater.inflate(R.layout.layout_about, null);
					mPopupWindow.setContentView(view);
					mPopupWindow.setWidth(LayoutParams.WRAP_CONTENT);
					mPopupWindow.setHeight(LayoutParams.WRAP_CONTENT);
					mPopupWindow.showAtLocation(mPreview, 0, 100, 100);
					IsShowPopup=true;
				}
			} 
        });
    }
    //实现Pause方法
    public void onPause()
    {
        super.onPause();
        releaseCamera();
    }
    //获取照相机的方法
    public static Camera getCameraInstance()
    {
        Camera mCamera = null;
        try
        {
            mCamera = Camera.open();
			//没有后置摄像头，尝试打开前置摄像头*******************
			if (mCamera == null)
            {
                Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();
                int cameraCount = Camera.getNumberOfCameras(); 
                for (int camIdx = 0; camIdx < cameraCount; camIdx++)
                {
                    Camera.getCameraInfo(camIdx, mCameraInfo); 
                    if (mCameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
                    {
                        mCamera = Camera.open(camIdx);                        
                    }
            }
        }
	}
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        return mCamera;
    }

    //释放照相机
    private void releaseCamera()
    {
        if (mCamera != null)
        {
            IsPreview = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable()
    {
        public void run()
        {
            if (IsPreview)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    PreviewCallback previewCb = new PreviewCallback()
    {
        public void onPreviewFrame(byte[] data, Camera camera)
        {
            Camera.Parameters parameters = camera.getParameters();
            //获取扫描图片的大小
            Size mSize = parameters.getPreviewSize();
            //构造存储图片的Image
            Image mResult = new Image(mSize.width, mSize.height, "Y800");//第三个参数不知道是干嘛的
            //设置Image的数据资源
            mResult.setData(data);
            //获取扫描结果的代码
            int mResultCode = mScanner.scanImage(mResult);
            //如果代码不为0，表示扫描成功
            if (mResultCode != 0)
            {
            	//停止扫描
                IsPreview = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                //开始解析扫描图片
                SymbolSet Syms = mScanner.getResults();
                for (Symbol mSym : Syms)
                {
                	//mSym.getType()方法可以获取扫描的类型，ZBar支持多种扫描类型,这里实现了条形码、二维码、ISBN码的识别
                    if (mSym.getType() == Symbol.CODE128 || mSym.getType() == Symbol.QRCODE || 
                    	mSym.getType() == Symbol.CODABAR ||	mSym.getType() == Symbol.ISBN10 ||
                    	mSym.getType() == Symbol.ISBN13|| mSym.getType()==Symbol.DATABAR ||
                    	mSym.getType()==Symbol.DATABAR_EXP || mSym.getType()==Symbol.I25)
                    		 
                    {
                    	//添加震动效果，提示用户扫描完成
                        Vibrator mVibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
      				    mVibrator.vibrate(400);
                        Intent intent=new Intent(MainActivity.this,ResultActivity.class);
                        intent.putExtra("ScanResult", "扫描类型:"+GetResultByCode(mSym.getType())+"\n"+ mSym.getData());
                        //这里需要注意的是，getData方法才是最终返回识别结果的方法
                        //但是这个方法是返回一个标识型的字符串，换言之，返回的值中包含每个字符串的含义
                        //例如N代表姓名，URL代表一个Web地址等等，其它的暂时不清楚，如果可以对这个进行一个较好的分割
                        //效果会更好，如果需要返回扫描的图片，可以对Image做一个合适的处理
                        startActivity(intent);
                        IsScanned = true;
                    }
                    else
                    {
                    	//否则继续扫描
                        IsScanned = false;
                        mCamera.setPreviewCallback(previewCb);
                        mCamera.startPreview();
                        IsPreview = true;
                        mCamera.autoFocus(autoFocusCB);
                    }
                }
            }
        }
    };

    //用于刷新自动聚焦的方法
    AutoFocusCallback autoFocusCB = new AutoFocusCallback()
    {
        public void onAutoFocus(boolean success, Camera camera)
        {
            mAutoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };
    
    //根据返回的代码值来返回相应的格式化数据
    public String GetResultByCode(int CodeType)
    {
    	String mResult="";
    	switch(CodeType)
    	{
    	  //条形码
    	  case Symbol.CODABAR:
    		  mResult="条形码";
    		  break;
    	  //128编码格式二维码)
    	  case Symbol.CODE128:
    		  mResult="二维码";
    		  break;
    	  //QR码二维码
    	  case Symbol.QRCODE:
    		  mResult="二维码";
    		  break;
          //ISBN10图书查询
    	  case Symbol.ISBN10:
    		  mResult="图书ISBN号";
    		  break;
    	  //ISBN13图书查询
    	  case Symbol.ISBN13:
    		  mResult="图书ISBN号";
    		  break;
    	}
		return mResult;
    }
 
    
}
