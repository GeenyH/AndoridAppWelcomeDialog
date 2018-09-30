package com.geeny.androidappwelcomedialog.Utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 关于设备的工具类，比如得到屏幕的分辨率等数值
 */
public class DeviceRelatedUtil {
    private static DeviceRelatedUtil sInstance;
    private Context mContext;
    private DisplayMetrics mDisplayMetrics;

    private DeviceRelatedUtil(Context context) {
        this.mContext = context;
        mDisplayMetrics = this.mContext.getResources().getDisplayMetrics();
    }

    public static DeviceRelatedUtil getInstance() {
        return sInstance;
    }

    public static void init(Context context) {
        if (sInstance == null) {
            sInstance = new DeviceRelatedUtil(context);
        }
    }

    public int getDisplayHeight() {
        return mDisplayMetrics.heightPixels;
    }

    public int getDisplayWidth() {
        return mDisplayMetrics.widthPixels;
    }
}
