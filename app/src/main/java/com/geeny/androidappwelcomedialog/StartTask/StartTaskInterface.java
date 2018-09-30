package com.geeny.androidappwelcomedialog.StartTask;

import com.geeny.androidappwelcomedialog.AppStarter;

/**
 * 启动时的任务接口定义
 */
public interface StartTaskInterface {
    void execute(AppStarter.OnTaskListener listener);  // 启动任务的方法，方法中当任务结束时将回调listener

    // 隐藏欢迎界面前，是否需要确保这个任务已经完成
    // 如果为true，则此任务是必要执行的，在没完成任务前欢迎界面将一直显示（直到超时）
    boolean isNeedDoneBeforeHideWelcomeDialog();
}
