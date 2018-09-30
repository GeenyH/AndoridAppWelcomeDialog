package com.geeny.androidappwelcomedialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.geeny.androidappwelcomedialog.Utils.DeviceRelatedUtil;

public class WelcomeDialog extends Dialog {
    WelcomeDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_welcome);

        // 动态地将欢迎界面的图片根据地屏幕参数撑到铺满整个屏幕
        ImageView iv = findViewById(R.id.iv_welcome_picture);
        iv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Matrix m = new Matrix();
                float scale;
                Drawable drawable = ContextCompat.getDrawable(iv.getContext(), R.drawable.welcome);
                final int dwidth = drawable.getIntrinsicWidth();
                final int dheight = drawable.getIntrinsicHeight();

                final int vwidth = iv.getMeasuredWidth();
                final int vheight = iv.getMeasuredHeight();

                if (dwidth * vheight > vwidth * dheight) {
                    scale = (float) vheight / (float) dheight;
                } else {
                    scale = (float) vwidth / (float) dwidth;
                }
                m.setScale(scale, scale);
                iv.setImageMatrix(m);

                iv.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = DeviceRelatedUtil.getInstance().getDisplayWidth();
        lp.height = DeviceRelatedUtil.getInstance().getDisplayHeight();
        getWindow().setAttributes(lp);
    }
}
