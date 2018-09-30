package com.geeny.androidappwelcomedialog;

import android.app.Application;

import com.geeny.androidappwelcomedialog.StartTask.GetConfigStartTaskImpl;
import com.geeny.androidappwelcomedialog.StartTask.ShowAdsStartTaskImpl;
import com.geeny.androidappwelcomedialog.StartTask.TimeOutTask;
import com.geeny.androidappwelcomedialog.Utils.DeviceRelatedUtil;

/**
 * APP一启动，将调用Application类
 */
public class WelcomeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DeviceRelatedUtil.init(this);

        // 添加启动任务到AppStarter里
        AppStarter.getInstance().add(new ShowAdsStartTaskImpl());
        AppStarter.getInstance().add(new GetConfigStartTaskImpl());
        AppStarter.getInstance().add(new TimeOutTask(AppStarter.getInstance()));

        AppStarter.getInstance().start(WelcomeDialogController.getInstance().getListener());  // 执行启动任务

    }
}
