package com.sanmianti.qrcode.utils;

import android.content.Context;
import android.util.DisplayMetrics;


/**
 * Created by aaron on 16/9/7.
 */

public class ZXingLibrary {

    public static void initDisplayOpinion(Context context) {
        if (context == null) {
            return;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(context, dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(context, dm.heightPixels);
    }

    /**
     * Created by aaron on 16/8/3.
     */
    public static class DisplayUtil
    {

        public static int screenWidthPx; //屏幕宽 px
        public static int screenhightPx; //屏幕高 px
        public static float density;//屏幕密度
        public static int densityDPI;//屏幕密度
        public static float screenWidthDip;//  dp单位
        public static float screenHightDip;//  dp单位



        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        public static int dip2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        public static int px2dip(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }

        public static void initDisplayOpinion(Context context) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            density = dm.density;
            densityDPI = dm.densityDpi;
            screenWidthPx = dm.widthPixels;
            screenhightPx = dm.heightPixels;
            screenWidthDip = DisplayUtil.px2dip(context, dm.widthPixels);
            screenHightDip = DisplayUtil.px2dip(context, dm.heightPixels);
        }
    }
}
