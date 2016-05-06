package com.leichao.studyforit.common.widget.titlebar;

import android.app.Activity;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.leichao.studyforit.common.debug.Debug;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 可以修改statusbar的颜色，做到沉浸式状态栏的效果
 * Created by leichao on 2016/5/6.
 */
public class StatusBarManager {

    /**
     * 设置状态栏的背景颜色
     */
    public static void setBackgroundColor(Activity activity, int statusColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上版本
            Window window = activity.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(statusColor);

            ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
                ViewCompat.setFitsSystemWindows(mChildView, true);
            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4以上版本
            Window window = activity.getWindow();
            ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

            //First translucent status bar.
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight(activity);

            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
                //如果已经为 ChildView 设置过了 marginTop, 再次调用时直接跳过
                if (lp != null && lp.topMargin < statusBarHeight && lp.height != statusBarHeight) {
                    //不预留系统空间
                    ViewCompat.setFitsSystemWindows(mChildView, false);
                    lp.topMargin += statusBarHeight;
                    mChildView.setLayoutParams(lp);
                }
            }

            View statusBarView = mContentView.getChildAt(0);
            if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height == statusBarHeight) {
                //避免重复调用时多次添加 View
                statusBarView.setBackgroundColor(statusColor);
                return;
            }
            statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            statusBarView.setBackgroundColor(statusColor);
            //向 ContentView 中添加假 View
            mContentView.addView(statusBarView, 0, lp);
        }
    }

    /**
     * 是否将状态栏的文字设置为黑色
     */
    public static boolean setTextColorDark(Activity activity, boolean darkmode) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0以上版本
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            result = true;
        } else if (setMeiZuStatusBarDark(activity, darkmode)) {
            result = true;
        } else if (setXiaoMiStatusBarDark(activity, darkmode)) {
            result = true;
        }
        return result;
    }

    /**
     * 获得状态栏的高度
     */
    public static int getStatusBarHeight(Activity activity){
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 是否将魅族手机状态栏文字颜色更改为黑色，true为黑色，false为白色
     */
    public static boolean setMeiZuStatusBarDark(Activity activity, boolean darkmode) {
        boolean result = false;
        try {
            Window window = activity.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkmode) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
            result = true;
        } catch (Exception e) {
            Debug.e("MeiZu", "setMeiZuStatusBarDark: failed");
        }
        return result;
    }

    /**
     * 是否将小米手机状态栏文字颜色更改为黑色，true为黑色，false为白色
     */
    public static boolean setXiaoMiStatusBarDark(Activity activity, boolean darkmode) {
        boolean result = false;
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            result = true;
        } catch (Exception e) {
            Debug.e("XiaoMi", "setXiaoMiStatusBarDark: failed");
        }
        return result;
    }

}
