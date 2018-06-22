package com.jimmy.customview.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class WaveView extends View {


    private final Path mPath;
    private final Paint mPaint;
    private float mTheta = 0f;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 拉勾绿
        mPaint.setColor(Color.argb(255, 32, 202, 119));
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 振幅
        int amplitude = 20;

        int height = getHeight();
        // 波长
        int width = getWidth();
        int index = 0;

        // while (index < width) {
        // float endY = (float) (Math.sin((float) index / (float) width * 2f * Math.PI + mTheta) * (float) amplitude + height - amplitude);
        // canvas.drawLine(index, 0, index, endY, mPaint);
        // index++;
        // }

        mPath.reset();
        mPath.moveTo(0, 0);
        while (index <= width) {
            float endY = (float) (Math.sin((float) index / (float) width * 2f * Math.PI + mTheta)
                    * (float) amplitude + height - amplitude);
            mPath.lineTo(index, endY);
            // Log.e("xxx", String.format("(%.4f, %.4f)", (float) index, endY));
            index++;
        }
        mPath.lineTo(index - 1, 0);
        mPath.close();

        canvas.drawPath(mPath, mPaint);

        mTheta += 0.1;
        if (mTheta >= 2f * Math.PI) {
            mTheta -= (2f * Math.PI);
        }
        postInvalidateDelayed(80);
    }
}
