package sanmianti.com.ztools;

import android.app.Activity;
import android.app.Application;

import sanmianti.com.baselibrary.baseactivity.ZMainProxy;

/**
 * @author sanmianti
 * @description  主项目Application
 * @date 2019/8/6 23:19
 */
public class ZToolsApplication  extends Application {

    private Activity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        ZMainProxy.setMainCallback(new ZMainProxy.IMainCallback() {
            @Override
            public Activity getCurrentActivity() {
                return currentActivity;
            }

            @Override
            public void setCurrentActivity(Activity activity) {
                ZToolsApplication.this.currentActivity = activity;
            }
        });

    }
}
