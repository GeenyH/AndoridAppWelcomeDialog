package com.geeny.androidappwelcomedialog;

import com.geeny.androidappwelcomedialog.StartTask.StartTaskInterface;

import java.util.ArrayList;
import java.util.List;

public class AppStarter {

    private static AppStarter sInstance = new AppStarter();
    private final List<StartTaskInterface> mStaterTasks = new ArrayList<>();  // 存储所有的启动任务
    private OnTaskListener mWelcomeDialogShowListener;  // 关闭欢迎界面的回调

    private int mCountOfNeedDoneTask;  // 必要完成的启动任务个数
    // 这个回调用于每一个必要完成的启动任务执行完成后调用，工作大致是记录完成的个数，当全部完成时，调用
    final OnTaskListener neeWaitTasksListener = new OnTaskListener() {
        private int mCountOfFinishedTask;  // 完成并调用了回调的任务个数

        @Override
        public synchronized void onFinished(String key) {
            mCountOfFinishedTask++;
            if (mCountOfFinishedTask == mCountOfNeedDoneTask && mWelcomeDialogShowListener != null) {
                mWelcomeDialogShowListener.onFinished(null);  // 当必要完成的启动任务全部执行完毕后，便可以关闭欢迎界面了
            }
        }
    };

    public static AppStarter getInstance() {
        if (sInstance == null) {
            sInstance = new AppStarter();
        }
        return sInstance;
    }

    public OnTaskListener getOnTaskListener() {
        return mWelcomeDialogShowListener;
    }

    public void setOnTaskListener(OnTaskListener onTaskListener) {
        this.mWelcomeDialogShowListener = onTaskListener;
    }

    // 增加一个启动任务
    public void add(StartTaskInterface task) {
        mStaterTasks.add(task);
    }

    public void start(OnTaskListener listener) {

        mWelcomeDialogShowListener = listener;

        // 计算下欢迎界面关闭前必需完成的任务数
        for (StartTaskInterface t : mStaterTasks) {
            if (t.isNeedDoneBeforeHideWelcomeDialog()) {
                mCountOfNeedDoneTask += 1;
            }
        }

        for (StartTaskInterface t : mStaterTasks) {
            if (t.isNeedDoneBeforeHideWelcomeDialog()) {
                t.execute(neeWaitTasksListener);  // 需要在欢迎界面关闭前完成的任务执行完成后，将会回调neeWaitTasksListener
            } else {
                t.execute(null);  // 在欢迎界面关闭不一定要完成的任务直接执行
            }
        }
    }

    public interface OnTaskListener {
        void onFinished(String key);
    }
}
