package sanmianti.com.baselibrary.utils;

import android.widget.Toast;

import sanmianti.com.baselibrary.baseactivity.ZMainProxy;

/**
 * @author sanmianti
 * @description  Toast弹出工具类
 * @date 2019/8/6 23:09
 */
public class ZToastUtils {

    public static void showToast(String toastStr ){
        if (ZMainProxy.getMainCallback().getCurrentActivity() ==  null){
            return;
        }
        Toast.makeText( ZMainProxy.getMainCallback().getCurrentActivity(), toastStr, Toast.LENGTH_SHORT).show();
    }

}
