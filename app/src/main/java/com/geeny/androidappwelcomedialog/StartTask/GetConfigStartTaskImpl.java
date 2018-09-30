package com.geeny.androidappwelcomedialog.StartTask;

import android.os.Handler;

import com.geeny.androidappwelcomedialog.AppStarter;

/**
 * 启动APP时得到配置信息任务（调用API或读取本地数据）
 */
public class GetConfigStartTaskImpl implements StartTaskInterface {
    @Override
    public void execute(AppStarter.OnTaskListener listener) {
        new Handler().postDelayed(() -> {
            if (listener != null) {
                listener.onFinished("GetConfigFinished");
            }
        }, 3000);  // 假设花了3秒完成请求API得到APP的配置变量
    }

    @Override
    public boolean isNeedDoneBeforeHideWelcomeDialog() {
        return true;
    }
}
