package sanmianti.com.ztools.keyboard;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import sanmianti.com.baselibrary.baseactivity.BaseActivity;
import sanmianti.com.ztools.R;
import sanmianti.com.ztools.databinding.ActivitySoftkeyboardTestBinding;

/**
 * @author sanmianti
 * @description 软键盘操作工具测试页面
 * @date 2019年4月17日10:53:58
 */
public class SoftkeyboardTestActivity extends BaseActivity implements View.OnClickListener {

    ActivitySoftkeyboardTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_softkeyboard_test);
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        binding.hideSoftkeyboard.setOnClickListener(this);
        binding.popupSoftkeyboard.setOnClickListener(this);

        binding.inputOptionGo.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                showToast("Go");
                return true;
            }
            return false;
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hide_softkeyboard:
                new SoftKeboardUtils().hideSoftKeyboard(v.getWindowToken(), this);
                break;
            case R.id.popup_softkeyboard:
                new SoftKeboardUtils().popUpSoftkeyboard(this, binding.inputOptionGo);
                break;
            default:
                break;
        }
    }
}
