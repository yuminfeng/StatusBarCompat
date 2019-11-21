package com.yumf.jince.statusbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.ColorUtils;
import android.util.Log;
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

    private static final int ACTION_STATUS_BAR_COLOR = 1;
    private static final int ACTION_SYSTEM_UI_TRANSPARENT = 2;
    private static final int ACTION_SYSTEM_UI_HIDE_OR_SHOW = 3;
    private static final int ACTION_SYSTEM_BAR_TRANSPARENT = 4;

    private Activity mActivity;
    private Builder mBuilder;

    public StatusBarCompat(Activity activity) {
        this.mActivity = activity;
    }

    private StatusBarCompat(Activity activity, Builder builder) {
        this.mActivity = activity;
        this.mBuilder = builder;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public void apply() {
        if (mBuilder.type == ACTION_STATUS_BAR_COLOR) {
            setStatusBarColor(mBuilder.statusColor);
        } else if (mBuilder.type == ACTION_SYSTEM_UI_TRANSPARENT) {
            setStatusBarTransparent(mBuilder.applyNavigation);
        } else if (mBuilder.type == ACTION_SYSTEM_UI_HIDE_OR_SHOW) {
            setFullScreen(mBuilder.systemUIShow);
        } else if (mBuilder.type == ACTION_SYSTEM_BAR_TRANSPARENT) {
            setTranslucentStatus(true);
        }
    }

    /**
     * set systemUI hide or not
     * <p>
     * LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER: window不允许和刘海屏重叠
     * LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT: 默认情况下，全屏window不会使用到刘海区域，非全屏window可正常使用刘海区域
     * LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES: 允许window扩展到刘海区
     *
     * @param fullScreen
     */
    public void setFullScreen(boolean fullScreen) {

        if (fullScreen) {
            Window window = mActivity.getWindow();
            View decorView = window.getDecorView();
            // Set the IMMERSIVE flag.
            // Set the content to appear under the system bars so that the content
            // doesn't resize when the system bars hide and show.
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(option);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                window.setAttributes(lp);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                decorView.requestApplyInsets();
            }
        } else {
            Window window = mActivity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * set status bar transparent .
     *
     * @param applyNav whether also apply to system navigation bar
     */
    @Deprecated
    private void setStatusBarTransparent(boolean applyNav) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = mActivity.getWindow();
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.setStatusBarColor(Color.TRANSPARENT);
            if (applyNav) {
                option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                window.setNavigationBarColor(Color.TRANSPARENT);
            }
            decorView.setSystemUiVisibility(option);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
    }

    /**
     * set status bar color .
     *
     * @param statusColor
     */
    private void setStatusBarColor(@ColorInt int statusColor) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mActivity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(statusColor);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decorView = window.getDecorView();
                int vis = decorView.getSystemUiVisibility();
                if (isLightColor(statusColor)) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; //black
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; //white
                }
                decorView.setSystemUiVisibility(vis);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = mActivity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            decorView.addView(createStatusBarView(mActivity, statusColor));
            setRootView(mActivity, true);
        }

    }

    /**
     * calculate the color is light or dark.
     *
     * @param color
     * @return
     */
    private boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    /**
     * create a view which it's height equal system status bar's height.
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
        ViewGroup parent = activity.findViewById(android.R.id.content);
        View child = parent.getChildAt(0);
        if (child instanceof ViewGroup) {
            child.setFitsSystemWindows(fitSystemWindows);
        }
    }

    /**
     * set status bar translucent or not.
     *
     * @param on
     */
    private void setTranslucentStatus(boolean on) {
        Window win = mActivity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * set navigation bar translucent or not.
     *
     * @param on
     */
    private void setTranslucentNavigation(boolean on) {
        Window win = mActivity.getWindow();
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

    public static class Builder {

        @ColorInt
        private int statusColor;

        private int type;

        private boolean applyNavigation;

        private boolean systemUIShow;

        public Builder statusColor(@ColorInt int statusColor) {
            this.statusColor = statusColor;
            this.type = ACTION_STATUS_BAR_COLOR;
            return this;
        }

        public Builder statusBarTransparent(boolean applyNav) {
            this.applyNavigation = applyNav;
            this.type = ACTION_SYSTEM_UI_TRANSPARENT;
            return this;
        }

        public Builder statusBarTransparent() {
            this.type = ACTION_SYSTEM_BAR_TRANSPARENT;
            return this;
        }

        public Builder fullScreen(boolean fullscreen) {
            this.systemUIShow = fullscreen;
            this.type = ACTION_SYSTEM_UI_HIDE_OR_SHOW;
            return this;
        }

        public StatusBarCompat build(Activity activity) {
            return new StatusBarCompat(activity, this);
        }
    }
}
