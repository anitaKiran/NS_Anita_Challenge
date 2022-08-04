package com.nordsecurity.test.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.Window
import android.view.WindowManager

/**
 * Created by Anita Kiran on 6/28/2022.
 */
class Common {

    companion object {

        fun hideStatusBar(activity: Activity){
            activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        fun setStatusBar(activity: Activity){
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}