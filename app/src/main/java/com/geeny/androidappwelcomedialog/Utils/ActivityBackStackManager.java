package com.geeny.androidappwelcomedialog.Utils;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

// 用于存储和管理打开和销毁的Activity栈
public class ActivityBackStackManager {

    private static final ActivityBackStackManager ourInstance = new ActivityBackStackManager();

    private LinkedList<ActvityBackStackElement> mActvityBackStackElements = new LinkedList<>();

    private ActivityBackStackManager() {
    }

    public static ActivityBackStackManager getInstance() {
        return ourInstance;
    }

    public void register(Activity activity) {
        ActvityBackStackElement actvityBackStackElement = new ActvityBackStackElement();
        actvityBackStackElement.weakReference = new WeakReference<>(activity);
        if (!mActvityBackStackElements.contains(actvityBackStackElement)) {
            mActvityBackStackElements.addFirst(actvityBackStackElement);
        }
    }

    public void unRegister(Activity activity) {
        ActvityBackStackElement actvityBackStackElement = new ActvityBackStackElement();
        actvityBackStackElement.weakReference = new WeakReference<>(activity);
        mActvityBackStackElements.remove(actvityBackStackElement);
    }

    @NonNull
    public LinkedList<ActvityBackStackElement> getActivityBackStacks() {
        return mActvityBackStackElements;
    }

    // 我们自己定义的Actvity栈的元素。用弱引用存入一个Activity。
    public static class ActvityBackStackElement {
        public WeakReference<? extends Activity> weakReference;

        // 重写equals和hash方法的意义在于当我们删除Activity栈里面的一个元素时，是根据Activity的相关属性来找到对应的BackStack元素并删除，
        // 所以删除的元素符合的条件在于Activity，而不是BackStack元素的hash值，所以需要重写equals和hash方法

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ActvityBackStackElement actvityBackStackElement = (ActvityBackStackElement) o;

            if (weakReference != null && actvityBackStackElement.weakReference != null) {
                Activity activity = weakReference.get();
                if (activity != null) {
                    return activity.equals(actvityBackStackElement.weakReference.get());
                }
            }

            return false;
        }

        @Override
        public int hashCode() {
            if (weakReference != null) {
                Activity activity = weakReference.get();
                if (activity != null) {
                    return activity.hashCode();
                }
            }

            return 0;
        }
    }
}
