package com.stkj.freeshare.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public final class RadarSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private final SurfaceHolder mHolder;
    private Thread mThread;
    private boolean mAnimating;
    private View mCenterView;
    private int d;
    private int mCx;
    private int mCy;
    private Paint mPaint;
    private int mRadius[];
    private float density;
    private Paint.Style mStyle;
    private int mAlpha;
    private int mRed;
    private int mGreen;
    private int mBlue;

    private Bitmap mBackgound;
    private Paint mBackgoundPaint;
    private RectF mBackgroundDst;

    // 背景色
    // private int mTransparentColor;

    public RadarSurfaceView(Context context) {
        super(context);
        mHolder = getHolder();
        init(context);
    }

    public RadarSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        init(context);
    }

    public RadarSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mHolder = getHolder();
        init(context);
    }

    public void setStyle(Paint.Style style, int alpha, int read, int green,
                         int blue) {
        mStyle = style;
        mAlpha = alpha;
        mRed = read;
        mGreen = green;
        mBlue = blue;
        mPaint.setStyle(mStyle);
    }

    private void init(Context context) {
        mHolder.addCallback(this);
        mAnimating = true;
        d = 0;
        mCx = 0;
        mCy = 0;
        density = 1.0F;
        mStyle = Paint.Style.STROKE;
        mAlpha = 255;
        mRed = 0xd4;
        mGreen = 0xd4;
        mBlue = 0xd4;
        setWillNotDraw(false);
        getHolder().addCallback(this);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(mStyle);
        mPaint.setStrokeWidth(2.0F);
        density = context.getResources().getDisplayMetrics().density;
        int radius = (int) (100F * density);
        mRadius = new int[4];
        mRadius[0] = radius;
        mRadius[1] = radius * 2;
        mRadius[2] = radius * 3;
        mRadius[3] = radius * 4;

        // mTransparentColor = Color.rgb(0xa, 0xb, 0xc);
        // 背景元素
        mBackgoundPaint = new Paint();
        mBackgoundPaint.setAntiAlias(true);
        mBackgroundDst = new RectF();
    }

    public int getInnerRaidus() {
        return mRadius[0];
    }

    private void drawCircle(Canvas canvas) {

        if (mCenterView != null) {
            int ai[] = new int[2];
            mCenterView.getLocationInWindow(ai);
            int ai1[] = new int[2];
            getLocationInWindow(ai1);
            mCx = (ai[0] + mCenterView.getWidth() / 2) - ai1[0];
            mCy = (ai[1] + mCenterView.getHeight() / 2) - ai1[1];
        } else {
            mCx = canvas.getWidth() / 2;
            mCy = canvas.getHeight() / 2;
        }
        if (mAnimating) {
            int color = Color.argb((int) ((double) mAlpha * (1.0D - (double) d
                    / (3D * (double) mRadius[0]))), mRed, mGreen, mBlue);
            mPaint.setColor(color);
            canvas.drawCircle(mCx, mCy, mRadius[0] + d, mPaint);
            color = Color
                    .argb((int) ((double) mAlpha * (1.0D - (double) (d + 1 * mRadius[0])
                            / (3D * (double) mRadius[0]))), mRed, mGreen, mBlue);
            mPaint.setColor(color);
            canvas.drawCircle(mCx, mCy, mRadius[1] + d, mPaint);
            color = Color
                    .argb((int) ((double) mAlpha * (1.0D - (double) (d + 2 * mRadius[0])
                            / (3D * (double) mRadius[0]))), mRed, mGreen, mBlue);
            mPaint.setColor(color);
            canvas.drawCircle(mCx, mCy, mRadius[2] + d, mPaint);
        }

    }

    public void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawCircle(canvas);
        if (mHolder.getSurface().isValid()) {
            this.postInvalidateDelayed(33);
            d = (int) ((float) d + 3f * density) % (mRadius[0]);
        }
    }

    public void setBackgoundBitmap(Bitmap bmp) {
        mBackgound = bmp;
        invalidate();
    }

    /**
     * 画背景，如果没有设置背景就白色color
     *
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        if (mBackgound != null && !mBackgound.isRecycled()) {
            mBackgroundDst.left = getLeft();
            mBackgroundDst.top = getTop();
            mBackgroundDst.right = getRight();
            mBackgroundDst.bottom = getBottom();
            canvas.drawBitmap(mBackgound, null, mBackgroundDst, mBackgoundPaint);
        } else {
            canvas.drawColor(-1);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // 回收背景
        if (mBackgound != null) {
            mBackgound.recycle();
        }
    }

    public void setAlignView(View view) {
        mCenterView = view;
        postInvalidate();
    }
}