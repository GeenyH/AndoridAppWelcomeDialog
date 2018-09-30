package com.geeny.androidappwelcomedialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;

/**
 * 欢迎界面的管理类
 */
public class WelcomeDialogController {

    private static final int DELAY = 500;
    private static WelcomeDialogController sInstance;
    // 从MainActivity传过来的回调，在欢迎界面关闭时执行。一般来说就是欢迎界面消失进入主界面后加载view和data的操作
    private DialogInterface.OnDismissListener mOnDismissListener;
    private boolean mFinished;
    private Dialog mSplashDialog;
    private final Runnable mDismissRunnable = this::destroy;
    private AppStarter.OnTaskListener mWelcomeDialogShowListener;
    private boolean mIsDispatched;

    public static WelcomeDialogController getInstance() {
        if (sInstance == null) {
            sInstance = new WelcomeDialogController();

            // 关闭欢迎界面的回调，将用于AppStarter里所有任务完成后执行
            sInstance.mWelcomeDialogShowListener = key -> {
                if (!sInstance.mFinished) {
                    sInstance.dismiss();
                }
            };

        }
        return sInstance;
    }

    public boolean isNeedShow() {
        return !mFinished;
    }

    public void show(Activity activity, DialogInterface.OnDismissListener listener) {
        mOnDismissListener = listener;
        if (mSplashDialog == null) {

            // R.style.full_screen_dialog指定dialog不能返回、全屏、没有title、没有背景等一堆限定，让用户在欢迎界面出现时什么都不能操作
            mSplashDialog = new WelcomeDialog(activity, R.style.full_screen_dialog);

            mSplashDialog.setCancelable(false);
        }

        if (!activity.isFinishing()) {
            mSplashDialog.show();
        }
    }

    // 关闭欢迎界面和做一些操作
    public void dismiss() {
        sInstance.mFinished = true;
        dispatchListener();
        delayDismissDialog(DELAY);
    }

    // 执行MainActivity传过来的回调，即在进入主界面后要做的操作
    private void dispatchListener() {
        if (mOnDismissListener != null && !mIsDispatched) {
            mOnDismissListener.onDismiss(mSplashDialog);
            mIsDispatched = true;
        }
    }

    // 过一会关闭掉dialog（算是给dispatchListener做主界面的加载一点时间，提高用户体验）
    private void delayDismissDialog(int time) {
        new Handler(Looper.getMainLooper()).postDelayed(mDismissRunnable, time);  // 调用mDismissRunnable，即destory方法
    }

    public void destroy() {
        try {
            mSplashDialog.dismiss();
        } catch (Exception ignored) {
        }
    }


    public AppStarter.OnTaskListener getListener() {
        return mWelcomeDialogShowListener;
    }
}
