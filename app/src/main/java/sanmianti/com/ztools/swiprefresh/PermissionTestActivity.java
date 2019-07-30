package sanmianti.com.ztools.swiprefresh;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import sanmianti.com.baselibrary.baseactivity.BaseActivity;
import sanmianti.com.baselibrary.utils.ZConstants;
import sanmianti.com.baselibrary.utils.ZPermissionUtils;
import sanmianti.com.ztools.R;
import sanmianti.com.ztools.databinding.ActivityPermissionTestBinding;

public class PermissionTestActivity extends BaseActivity implements View.OnClickListener {

    ActivityPermissionTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_permission_test);
        initView();
    }

    private void initView() {
        binding.permissionStorage.setOnClickListener(this);
        binding.permissionConnect.setOnClickListener(this);
        binding.permissionMicrophoneCamera.setOnClickListener(this);
        binding.permissionInstall.setOnClickListener(this);
        binding.permissionRevoke.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.permission_storage:
                /**存储权限**/
                requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new ZPermissionUtils.IRequestPermissionCallback() {
                    @Override
                    public void onPermissionDenied() {
                        showToast("权限请求被拒绝");
                    }

                    @Override
                    public void onPermissionGranted() {
                        showToast("成功获取权限");
                    }
                });
                break;
            case R.id.permission_connect:
                requestPermission(new String[]{Manifest.permission.READ_CONTACTS},
                        new ZPermissionUtils.IRequestPermissionCallback() {
                    @Override
                    public void onPermissionDenied() {
                        showToast("权限请求被拒绝");
                    }

                    @Override
                    public void onPermissionGranted() {
                        showToast("成功获取权限");
                    }
                });
                break;
            case R.id.permission_microphone_camera:
                requestPermission(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA},
                        new ZPermissionUtils.IRequestPermissionCallback() {
                    @Override
                    public void onPermissionDenied() {
                        showToast("权限请求被拒绝");
                    }

                    @Override
                    public void onPermissionGranted() {
                        showToast("成功获取权限");
                    }
                });
                break;
            case R.id.permission_install:
                requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.REQUEST_INSTALL_PACKAGES},
                        new ZPermissionUtils.IRequestPermissionCallback() {
                    @Override
                    public void onPermissionDenied() {
                        showToast("权限请求被拒绝");
                    }

                    @Override
                    public void onPermissionGranted() {
                        showToast("成功获取权限");
                    }
                });
                break;
            case R.id.permission_revoke:
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                if (intent.resolveActivity(this.getPackageManager()) != null) {
                    this.startActivityForResult(intent, ZConstants.REQUEST_CODE_FOR_APPLICATION_DETAILS_SETTINGS);
                }
                break;
            default:
                break;
        }
    }
}
