
package com.Android.ZBar4Android;

import java.io.IOException;
import android.util.Log;

import android.view.SurfaceView;
import android.view.SurfaceHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;

//������ZBar��Ŀ��SDK�ṩ�����������޸�
@SuppressLint("ViewConstructor")
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback 
{
    
	private SurfaceHolder mHolder;
    private Camera mCamera;
    private PreviewCallback mPreviewCallBack;
    private AutoFocusCallback mAutoFocusCallBack;

    public CameraPreview(Context context, Camera camera,
                         PreviewCallback previewCb,
                         AutoFocusCallback autoFocusCb) {
        super(context);
        mCamera = camera;
        mPreviewCallBack = previewCb;
        mAutoFocusCallBack = autoFocusCb;

        /* 
         * �Զ��۽�
         * Ҫ��API�汾>9
         */
        Camera.Parameters parameters = camera.getParameters();
        for (String f : parameters.getSupportedFocusModes()) {
            if (f == Parameters.FOCUS_MODE_CONTINUOUS_PICTURE) {
                parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                mAutoFocusCallBack = null;
                break;
            }
        }


        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);

        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            Log.d("DBG", "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // Camera preview released in activity
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        /*
         * If your preview can change or rotate, take care of those events here.
         * Make sure to stop the preview before resizing or reformatting it.
         */
        if (mHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
        }

        try {
            // Hard code camera surface rotation 90 degs to match Activity view in portrait
            mCamera.setDisplayOrientation(90);

            mCamera.setPreviewDisplay(mHolder);
            mCamera.setPreviewCallback(mPreviewCallBack);
            mCamera.startPreview();
            mCamera.autoFocus(mAutoFocusCallBack);
        } catch (Exception e){
            Log.d("DBG", "Error starting camera preview: " + e.getMessage());
        }
    }
    
    /*
     * ����У׼��
     * �޸ģ���Ԫ��
     * ʱ�䣺2013��11��22��
     * 
     */
	@Override
	protected void onDraw(Canvas mCanvas) 
	{
       //���ﲻ��д�ˣ�
	}
}
