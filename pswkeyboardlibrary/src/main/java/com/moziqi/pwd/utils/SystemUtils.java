package com.moziqi.pwd.utils;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;

import java.lang.reflect.Method;

/**
 * Copyright (C), 2018-2018
 * FileName: SystemUtils
 * Author: ziqimo
 * Date: 2018/11/17 下午9:07
 * Description: ${后期安全问题的方式，都放在这里管理}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class SystemUtils {

    /**
     * 禁止截屏
     *
     * @param activity
     */
    public static void noScreenShot(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
    }

    /**
     * 禁止辅助功能的使用
     *
     * @param activity
     */
    public static void noAccessibility(Activity activity) {
        if (Build.VERSION.SDK_INT >= 14) {
            activity.getWindow().getDecorView().setAccessibilityDelegate(new View.AccessibilityDelegate() {
                @Override
                public boolean performAccessibilityAction(View host, int action, Bundle args) {
                    //忽略AccessibilityService传过来的点击事件以达到防止模拟点击的目的
                    if (action == AccessibilityNodeInfo.ACTION_CLICK
                            || action == AccessibilityNodeInfo.ACTION_LONG_CLICK) {
                        return true;
                    }
                    return super.performAccessibilityAction(host, action, args);
                }
            });
        }
    }

    /**
     * 针对单个view限制辅助功能
     *
     * @param view
     */
    public static void noAccessibility(View view) {
        if (Build.VERSION.SDK_INT >= 14) {
            view.setAccessibilityDelegate(new View.AccessibilityDelegate() {
                @Override
                public boolean performAccessibilityAction(View host, int action, Bundle args) {
                    //忽略AccessibilityService传过来的点击事件以达到防止模拟点击的目的
                    if (action == AccessibilityNodeInfo.ACTION_CLICK
                            || action == AccessibilityNodeInfo.ACTION_LONG_CLICK) {
                        return true;
                    }
                    return super.performAccessibilityAction(host, action, args);
                }
            });
        }
    }

    /**
     * 隐藏键盘
     *
     * @param activity
     * @param editText
     */
    public static void hideKeyBoard(Activity activity, EditText editText) {
        // 设置不调用系统键盘
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
