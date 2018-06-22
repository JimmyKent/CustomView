package com.jimmy.customview;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private WindowManager wm;
    private Stack<View> viewStack = new Stack<>();
    private View view1, view2;
    private LayoutParams lp, lp2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.long_view, null);
        view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.long_view2, null);

        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int flag = LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCH_MODAL
                | LayoutParams.FLAG_LAYOUT_NO_LIMITS | LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        lp = new LayoutParams(LayoutParams.WRAP_CONTENT, 800,
                LayoutParams.TYPE_APPLICATION_SUB_PANEL, flag, PixelFormat.RGBA_8888);
        lp2 = new LayoutParams(LayoutParams.WRAP_CONTENT, 800,
                LayoutParams.TYPE_APPLICATION_SUB_PANEL, flag, PixelFormat.RGBA_8888);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, WaveViewAty.class));
                viewStack.push(view1);
                wm.addView(view1, lp);
                wm.addView(view2, lp2);

            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View v1 = viewStack.pop();
                wm.removeView(view2);
                wm.addView(v1, lp);
            }
        });



        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                wm.addView(view1, lp);
            }
        }, 100);*/

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        wm.addView(view1, lp);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
