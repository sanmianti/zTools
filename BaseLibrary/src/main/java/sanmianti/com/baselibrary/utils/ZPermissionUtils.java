package sanmianti.com.baselibrary.utils;

import android.Manifest;

/**
 * @author sanmianti
 * @description 动态权限请求
 * @date 2019/7/30 18:20
 */
public class ZPermissionUtils {

    /**
     * 通过Manifest.permission中权限名获取对应的中文权限名
     *
     * @param permission Manifest.permission中声明的静态权限名
     *
     * @return 中文权限名
     */
    public static String getPermissionName(String permission) {
        switch (permission) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                return "存储";
            case Manifest.permission.READ_CONTACTS:
            case Manifest.permission.WRITE_CONTACTS:
                return "通讯录";
            case Manifest.permission.RECORD_AUDIO:
                return "录音";
            case Manifest.permission.REQUEST_INSTALL_PACKAGES:
                return "安装应用";
            case Manifest.permission.CAMERA:
                return "摄像头";
            case Manifest.permission.SEND_SMS:
            case Manifest.permission.READ_SMS:
                return "短信";
        }
        return "未知";
    }

    /**
     * 权限请求结果回调
     */
    public interface IRequestPermissionCallback {
        /**
         * 权限请求被拒绝
         */
        void onPermissionDenied();

        /**
         * 权限请求被授权
         */
        void onPermissionGranted();
    }
}
