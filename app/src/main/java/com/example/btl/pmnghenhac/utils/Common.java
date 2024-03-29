package com.example.btl.pmnghenhac.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.WindowManager;

import com.example.btl.pmnghenhac.R;
import com.example.btl.pmnghenhac.activities.MainActivity;

public class Common {
    public static Bitmap getDefaultBg(Context mContext) {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.default_wallpaper);
        return bitmap;
    };
    public static void setStatusBarTranslucent(boolean makeTranslucent, Activity activity) {
        if (makeTranslucent) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    public static String miliSecondToString(int totalTimeInSec) {
        int min = totalTimeInSec / 60;
        int sec = totalTimeInSec - min * 60;
        return String.format("%02d:%02d", min, sec);
    }

    public static void updateMainActivity(){
        MainActivity mainActivity = (MainActivity) AppController.getInstance().getMainActivity();
        if(mainActivity != null){
            mainActivity.showCurrentSong();
        }
    }
}
