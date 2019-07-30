package sanmianti.com.ztools;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import sanmianti.com.baselibrary.baseactivity.BaseActivity;
import sanmianti.com.ztools.databinding.ActivityOpenThirdAppBinding;

/**
 * 根据包名打开第三方应用
 *
 * @author sanmianti
 * @date 2018/7/31 16:38
 */
public class OpenThirdAppActivity extends BaseActivity {

    ActivityOpenThirdAppBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_open_third_app);
        initView();
    }

    private void initView(){
        binding.openWechat.setOnClickListener(v -> openThirdApp("com.tencent.mm"));
        binding.openAlipay.setOnClickListener(v -> openThirdApp("com.eg.android.AlipayGphone"));
    }

    /**
     * 根据包名打开指定应用
     * @param packageName 应用包名
     */
    private void openThirdApp(String packageName){
        PackageManager packageManager = getPackageManager();
        Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(packageName);
        if (launchIntentForPackage != null) {
            startActivity(launchIntentForPackage);
        } else {
            showToast("手机未安装该应用");
        }
    }

}
