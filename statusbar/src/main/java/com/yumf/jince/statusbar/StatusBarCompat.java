package com.yumf.jince.statusbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Class to manage status and navigation bar
 * <p>
 * https://github.com/yuminfeng/statusbarcompat
 */

public class StatusBarCompat {

    private static final int INVALID_VAL = -1;
    private static final int COLOR_DEFAULT = Color.parseColor("#20000000");

    public void compat(Activity activity, int statusColor) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (statusColor != INVALID_VAL) {
                activity.getWindow().setStatusBarColor(statusColor);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = activity.getWindow();
            win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorView = (ViewGroup) win.getDecorView();
            decorView.addView(createStatusBarView(activity, statusColor));
            setRootView(activity, true);
        }

    }

    /**
     * create a view which it's height equal system status bar's height
     *
     * @param context
     * @param color
     * @return
     */
    private View createStatusBarView(Context context, @ColorInt int color) {
        View statusBarView = new View(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams
                (FrameLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(context));
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        return statusBarView;
    }

    /**
     * sets whether or not the root view of layout fitSystemWindows.
     *
     * @param activity
     * @param fitSystemWindows
     */
    private void setRootView(Activity activity, boolean fitSystemWindows) {
        ViewGroup parent = activity.findViewById(Window.ID_ANDROID_CONTENT);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(fitSystemWindows);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

    public void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void setTranslucentNavigation(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * receive the status bar height of the system.
     *
     * @param context
     * @return
     */
    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
