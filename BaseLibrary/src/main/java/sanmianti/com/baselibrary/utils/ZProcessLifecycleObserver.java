package sanmianti.com.baselibrary.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

/**
 * @author sanmianti
 * @description 应用进程生命活动周期监听
 * @date 2019/8/6 23:01
 */
public class ZProcessLifecycleObserver  implements LifecycleObserver {
    /**
     * 应用被创建，只会在应用启动时调用一次
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onAppCreate() {
        ZToastUtils.showToast("应用首次启动");
    }

    /**
     * 应用切换至前台
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onMoveToForeground() {
        ZToastUtils.showToast("应用切换至前台");
    }


    /**
     * 应用切至后台或者熄屏
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onMoveToBackground() {
        ZToastUtils.showToast("应用切至后台或者熄屏");
    }

}
