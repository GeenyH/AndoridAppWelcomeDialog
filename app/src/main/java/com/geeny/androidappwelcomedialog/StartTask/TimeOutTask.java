package com.geeny.androidappwelcomedialog.StartTask;

import android.os.Handler;
import android.os.Looper;

import com.geeny.androidappwelcomedialog.AppStarter;
import com.geeny.androidappwelcomedialog.WelcomeDialogController;

public class TimeOutTask implements StartTaskInterface {

    private static final int TIMEOUT = 6000;  // 我们设定欢迎界面显示的超时时间为6秒

    private AppStarter appStarter;

    public TimeOutTask(AppStarter appStarter) {
        this.appStarter = appStarter;
    }

    @Override
    public void execute(AppStarter.OnTaskListener listener) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                appStarter.setOnTaskListener(null);
                WelcomeDialogController.getInstance().dismiss();  // 超过6秒后关闭欢迎界面
            }
        }, TIMEOUT);
    }

    @Override
    public boolean isNeedDoneBeforeHideWelcomeDialog() {
        return false;  // 关掉欢迎界面时，不需要理睬这个任务是否完成，其它必要的任务完成后就可以关闭欢迎界面了
    }

}
