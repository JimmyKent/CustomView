package com.jimmy.customview.widgets;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class CardiogramView extends View {
    private Paint mPaint;// 画笔
    private Path mPath;// 路径对象

    private int screenW, screenH;// 屏幕宽高
    private float x, y;// 路径初始坐标
    private float initScreenW;// 屏幕初始宽度
    private float initX;// 初始X轴坐标
    private float initY;// 初始Y轴坐标

    private float transX, moveX;// 画布移动的距离

    private boolean isCanvasMove;// 画布是否需要平移

    private Random random;

    public CardiogramView(Context context) {
        super(context);
    }

    public CardiogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        /*
         * 实例化画笔并设置属性
         */
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(2);
        /*该方法用来设置我们画笔的笔触风格，上面的例子中我使用
         * 的是ROUND，表示是圆角的笔触，那么什么叫笔触呢，其实很
         * 简单，就像我们现实世界中的笔，如果你用圆珠笔在纸上戳一点，
         * 那么这个点一定是个圆，即便很小，它代表了笔的笔触形状，如果我们把
         * 一支铅笔笔尖削成方形的，那么画出来的线条会是一条弯曲的“矩形”，这就是笔
         * 触的意思。除了ROUND，Paint.Cap还提供了另外两种类型：SQUARE和
         * BUTT，具体大家自己去try~~*/
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //这个方法用于设置结合处的形态，就像上面的代码中我们虽说是花了一条心电线，但是这条线其实是由无数条小线拼接成的，拼接处的形状就由该方法指定。
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        //该方法为我们绘制的图形添加一个阴影层效果：
        //radius表示阴影的扩散半径，而dx和dy表示阴影平面上的偏移值，shadowColor就不说了阴影颜色，最后提醒一点setShadowLayer同样不支持HW哦
        mPaint.setShadowLayer(7, 0, 0, Color.WHITE);

        mPath = new Path();
        transX = 0;
        isCanvasMove = true;

        random = new Random();
    }

    //视图大小发生变化的时候回调
    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {

        /*
         * 获取屏幕宽高
         */
        screenW = w;
        screenH = h;

        /*
         * 设置起点坐标
         */
        x = 0;
        initY = (screenH / 2) + (screenH / 4) + (screenH / 10);
        y = initY;
        // 屏幕初始宽度
        initScreenW = screenW;

        // 初始X轴坐标
        initX = screenW / 2;

        moveX = (screenW / 24);

        mPath.moveTo(x, y);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.e("jgc", "onDraw");
        canvas.drawColor(Color.BLACK);

        mPath.lineTo(x, y);
        //XXX 向左平移画布
        canvas.translate(-transX, 0);

        // 计算坐标
        calCoors();

        // 绘制路径
        canvas.drawPath(mPath, mPaint);
        invalidate();
    }

    /**
     * 计算坐标
     */
    private void calCoors() {
        if (x < initX) {
            x += 60;
        } else {
            if (isCanvasMove) {
                transX += 4;
            }
            x += 4;
            if (y > initY + 20) {
                y -= random.nextInt(10);
            } else
                /*if (y < initY - 20)*/ {
                y += random.nextInt(10);
            }
        }
    }


}
