package com.leichao.studyforit.common.widget.titlebar;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.leichao.studyforit.R;

/**
 *
 * Created by leichao on 2016/5/3.
 */
public class ToolbarManager {

    public static void setToolbar(final Activity activity, String title) {
        setToolbar(activity, R.drawable.toolbar_back, title, -1, new OnLeftIconListener() {
            @Override
            public void onLeftIconClicked() {
                activity.finish();
            }
        });
    }

    public static void setToolbar(final Activity activity, String title, final OnToolbarListener listener) {
        setToolbar(activity, R.drawable.toolbar_back, title, -1, listener);
    }

    public static void setToolbar(final Activity activity, int leftIconResId, String title, final OnToolbarListener listener) {
        setToolbar(activity, leftIconResId, title, -1, listener);
    }

    public static void setToolbar(Activity activity, int leftIconResId, String title, int menuId, final OnToolbarListener listener) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        TextView titleTv = (TextView) activity.findViewById(R.id.toolbar_title);
        if (toolbar != null && titleTv != null) {
            toolbar.setTitle("");
            if (leftIconResId > 0) {
                toolbar.setNavigationIcon(R.drawable.toolbar_back);
                if (listener != null) {
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onLeftIconClicked();
                        }
                    });
                }
            }
            if (title != null) {
                titleTv.setText(title);
            }
            if (menuId > 0) {
                toolbar.getMenu().clear();
                toolbar.inflateMenu(menuId);
                if (listener != null) {
                    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            return listener.onMenuItemClick();
                        }
                    });
                }
            }
        }
    }

    public static Toolbar getToolbar(Activity activity) {
        return (Toolbar) activity.findViewById(R.id.toolbar);
    }

    public static TextView getTitleTv(Activity activity) {
        return (TextView) activity.findViewById(R.id.toolbar_title);
    }

    public static void setBackgroudColor(Activity activity, int colorResId) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setBackgroundResource(colorResId);
        }
    }

    public static void setTitleColor(Activity activity, int colorResId) {
        TextView titleTv = (TextView) activity.findViewById(R.id.toolbar_title);
        if (titleTv != null) {
            titleTv.setTextColor(ContextCompat.getColor(activity, colorResId));
        }
    }

}
