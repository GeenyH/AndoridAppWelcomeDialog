package com.geeny.androidappwelcomedialog;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.geeny.androidappwelcomedialog.Utils.ActivityBackStackManager;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setBackgroundDrawable(null);

        mTextView = findViewById(R.id.tv_hello);
        if (checkCurrentActivityStack() && WelcomeDialogController.getInstance().isNeedShow()) {
            WelcomeDialogController.getInstance().show(MainActivity.this, (dialog) -> {
                        new Handler(getMainLooper()).postDelayed(() -> {


                            // 这里理论上是APP进入主界面后要加载的视图和数据，这里简单用文本提示
                            mTextView.setText("数据加载完毕。");


                        }, 3000);
                    }
            );
        }

        // APP中的每个Activity创建时，都应该向ActivityBackStackManager加入Activity，方便之后的管理
        ActivityBackStackManager.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityBackStackManager.getInstance().unRegister(this); // 在整个APP中，每个Activity销毁后，都应该在ActivityBackStackManager中取消掉对应的Activity元素
    }

    //点击返回键返回桌面而不是退出程序
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent home = new Intent(Intent.ACTION_MAIN);
//            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            home.addCategory(Intent.CATEGORY_HOME);
//            startActivity(home);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    // 这种方法为true的情况存在于第一次进入MainActivity时、从其它Activity回到MainActivity时发现MainActivity被系统回收（即ActivityBackStackManagerjfd Activity数小于1）
    // 你可能会问，为什么是小于1，因为我们在AndroidManifest.xml中把MainActivity的android:launchMode设置"singleTop"
    // 也就是说，只要APP回到MainActivity并且需要重新走onCreate方法时下面这个方法就成立，
    // 因为"singleTop"的缘故，无论之前有没有其它的Activity，都会被销毁，只剩下MainActivity
    private boolean checkCurrentActivityStack() {
        return ActivityBackStackManager.getInstance().getActivityBackStacks().size() < 1;
    }
    
//    返回时确认并退出应用
private long lastExitTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastExitTime < 2000) {
            LinkedList<ActivityBackStackManager.ActvityBackStackElement> actvityBackStackElements = ActivityBackStackManager.getInstance().getActivityBackStacks();
            Activity temp;
            for (ActivityBackStackManager.ActvityBackStackElement actvityBackStackElement : actvityBackStackElements) {
                temp = actvityBackStackElement.weakReference.get();
                if (temp != null) {
                    temp.finish();
                }
            }

            System.exit(0);
        } else {
            lastExitTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();
        }
    }
}
