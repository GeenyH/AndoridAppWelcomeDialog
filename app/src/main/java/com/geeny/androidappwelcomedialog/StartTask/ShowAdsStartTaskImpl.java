package com.geeny.androidappwelcomedialog.StartTask;

import com.geeny.androidappwelcomedialog.AppStarter;

/**
 * 显示广告的任务
 */
public class ShowAdsStartTaskImpl implements StartTaskInterface {
    @Override
    public void execute(AppStarter.OnTaskListener listener) {
        new android.os.Handler().postDelayed(() -> {
            if (listener != null) {
                listener.onFinished("ShowAdsFinished");  // task完成后回调并传入标识，执行结束操作
            }
        }, 2000);  // 假设展示广告时间为5秒
    }

    @Override
    public boolean isNeedDoneBeforeHideWelcomeDialog() {
        return true;  // 这个任务没完成前，不要关闭欢迎界面
    }
}
