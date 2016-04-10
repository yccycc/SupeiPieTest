package com.yctech.supeipietest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

public class PieView extends GLSurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private Context mContext;
    private CircleHandler mHandler = new CircleHandler();
    private static int mForCircleChange;
    private String mDealPecent = "50%";

    public PieView(Context context) {
        super(context,null);
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(30);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mHandler.sendEmptyMessageDelayed(0, 500);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (true)
                {
                    long t1 = System.currentTimeMillis();
                    drawPie();
                    long gap = System.currentTimeMillis() - t1;
                    Log.i("bitchgap",gap+"");
                    if(gap < 50)
                    {
                        try {
                            Thread.sleep(50-gap);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    private void drawPie() {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.BLACK);
        Bitmap circle = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.circle);
        Bitmap circleLight = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.circle_light);
        Bitmap[] circles = {circle,circleLight};
        long temp = System.currentTimeMillis();
        canvas.drawBitmap(circles[mForCircleChange % 2], 0, 0, null);

        canvas.drawText(mDealPecent, 100, 100, mPaint);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    static class CircleHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mForCircleChange++;
            sendEmptyMessageDelayed(0, 500);
        }
    }
}
