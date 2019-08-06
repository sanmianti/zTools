package sanmianti.com.baselibrary.baseactivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import sanmianti.com.baselibrary.utils.ZConstants;
import sanmianti.com.baselibrary.utils.ZPermissionUtils;

/**
 * Activity 基类
 *
 * @author sanmianti
 * @date 2018/7/31 14:46
 */
public abstract class BaseActivity extends AppCompatActivity {

    ZPermissionUtils.IRequestPermissionCallback mRequestPermissionCallback;
    String[] mPermissions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ZMainProxy.getMainCallback().setCurrentActivity(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /**
         * 在弹出系统权限请求中断的情况下，permissions及grantResults长度为0。
         * 举个例子：同时发起两个存储权限请求，会有一个请求返回结果为空。详情参见：
         * {@link Activity#onRequestPermissionsResult(int, String[], int[])}
         */
        if (permissions.length == 0 || grantResults.length == 0) {
            return;
        }

        if (ZConstants.REQUEST_CODE_FOR_PERMISSION != requestCode) {
            return;
        }

        boolean hasAllGranted = true;
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                continue;
            }
            hasAllGranted = false;
            /**
             * 根据shouldShowRequestPermissionRationale()函数返回值判断用户是否选择了【不再提示】。
             * 当首次发起权限请求前该方法返回false，当首次权限请求被拒绝，第二次发起权限请求时返回true。
             * 当且仅当发起第N次(N>1)权限请求并且选择不再提示时该方法返回false。
             * **/
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                /**用户选择不再提示**/
                showRationaleDialog(permissions[i]);
            } else {
                /**权限请求失败，但未选中不再提示选项**/
                mRequestPermissionCallback.onPermissionDenied();
            }
            break;
        }
        if (hasAllGranted) {
            mRequestPermissionCallback.onPermissionGranted();
        }
    }

    /**
     * 通用权限请求接口
     * <p>
     * 注意请求权限只能由Activity发出，广播或服务无法请求相关权限。 但可以判断是否拥有某权限。
     *
     * @param permissions 权限集合  For Exmaple{@link Manifest.permission#CAMERA}
     * @param callback    回调接口
     */
    public void requestPermission(@NonNull String[] permissions, @NonNull ZPermissionUtils.IRequestPermissionCallback callback) {

        mRequestPermissionCallback = callback;
        mPermissions = permissions;

        if (permissions.length == 0) {
            callback.onPermissionGranted();
            return;
        }

        boolean isAllGranted = true;
        for (String singlePermission : permissions) {
            isAllGranted = ContextCompat.checkSelfPermission(this, singlePermission) == PackageManager.PERMISSION_GRANTED;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Manifest.permission.REQUEST_INSTALL_PACKAGES.equals(singlePermission)) {
                isAllGranted = this.getPackageManager().canRequestPackageInstalls();
            }
            if (!isAllGranted) {
                break;
            }
        }
        if (isAllGranted) {
            mRequestPermissionCallback.onPermissionGranted();
        } else {
            ActivityCompat.requestPermissions(this, permissions, ZConstants.REQUEST_CODE_FOR_PERMISSION);
        }
    }


    /**
     * 解释获取权限理由，并引导用户手动开启权限
     *
     * @param permission Manifest.permission权限名称
     */
    private void showRationaleDialog(final String permission) {
        String message;

        if (Manifest.permission.REQUEST_INSTALL_PACKAGES.equals(permission)){
            message = "zTools需要获取安装应用权限，否则无法完成更新。请点击去授权，并选择相应权限为允许";
        }else {
            message =  String.format("zTools需要获取%s权限，否则您可能无法使用该功能。请点击去授权，并选择相应权限为允许", ZPermissionUtils.getPermissionName(permission));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("去授权", (dialog, which) -> {
            if (Manifest.permission.REQUEST_INSTALL_PACKAGES.equals(permission)) {
                //安装权限
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                    //小于8.0的系统默认允许
                   mRequestPermissionCallback.onPermissionGranted();
                   return;
                }
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                if (intent.resolveActivity(this.getPackageManager()) != null) {
                    BaseActivity.this.startActivityForResult(intent, ZConstants.REQUEST_CODE_FOR_PERMISSION);
                } else {
                    mRequestPermissionCallback.onPermissionDenied();
                }
            } else {
                // 其它权限
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                if (intent.resolveActivity(this.getPackageManager()) != null) {
                    this.startActivityForResult(intent, ZConstants.REQUEST_CODE_FOR_PERMISSION);
                } else {
                    mRequestPermissionCallback.onPermissionDenied();
                }

            }
        });
        builder.setNegativeButton("取消", (dialog, which) -> mRequestPermissionCallback.onPermissionDenied());
        builder.create().show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ZConstants.REQUEST_CODE_FOR_PERMISSION){
            requestPermission(mPermissions, mRequestPermissionCallback);
        }
    }

}
