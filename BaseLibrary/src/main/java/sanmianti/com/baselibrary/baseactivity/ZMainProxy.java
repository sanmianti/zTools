package sanmianti.com.baselibrary.baseactivity;

import android.app.Activity;

/**
 * @author sanmianti
 * @description 用于获取主项目相关函数的代理类
 * @date 2019/8/6 23:37
 */
public class ZMainProxy {

   private static IMainCallback mainCallback;

    /**
     * 设置主项目实现的回调接口
     */
    public static void setMainCallback(IMainCallback callback){
        mainCallback =  callback;
    }

    /**
     * 获取主项目实现实现的回调接口
     */
    public static IMainCallback getMainCallback(){
        return mainCallback;
    }

    /**
     * 主项目中实现该接口，提供相关功能
     */
    public  interface IMainCallback{
        public Activity getCurrentActivity();
        public void setCurrentActivity(Activity activity);
    }
}
