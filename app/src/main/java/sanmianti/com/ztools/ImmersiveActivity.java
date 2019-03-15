package sanmianti.com.ztools;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import sanmianti.com.ztools.databinding.ActivityImmersiveBinding;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.View.SYSTEM_UI_FLAG_LOW_PROFILE;

/**
 * 沉浸式用户体验，我的理解就是给用户一种身临其境的感受，尽量减少状态栏，虚拟按钮等系统UI对用户注意力的干扰。
 * <p>
 * 官方参考文档：
 * https://developer.android.com/training/system-ui/
 *
 * @author sanmianti
 * @date 2018/8/3 22:52
 */

public class ImmersiveActivity extends BaseActivity {

    ActivityImmersiveBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_immersive);
        initView();
    }

    private void initView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("沉浸式用户体验");
        }
        binding.dimSystemBar.setOnClickListener(v -> dimSystemBars());
        binding.revelSystemBar.setOnClickListener(v -> revelSystemBars());
        binding.hideStatusBar.setOnClickListener(v -> hideStatusBar());
        binding.behindStatusBar.setOnClickListener(v -> behindStatusbar());
        binding.hideNavigationBar.setOnClickListener(v -> hideNavigationBar());
        binding.behindNavigationBar.setOnClickListener(v -> behindNavigationBar());
        binding.leanBackFullscreen.setOnClickListener(v -> leanBackFullscreen());
        binding.stickyFullscreen.setOnClickListener(v -> stickyFullscreen());
        binding.googleImmersive.setOnClickListener(v ->
                startActivity(new Intent(ImmersiveActivity.this, GoogleImmersiveActivity.class)));
        binding.googleStickyImmersive.setOnClickListener(v ->
                startActivity(new Intent(ImmersiveActivity.this, GoogleImmersiveStickyActivity.class)));

    }

    /**
     * 沉浸式用户体验之弱化导航栏及状态栏。
     */
    private void dimSystemBars() {
        View decorView = this.getWindow().getDecorView();
        int uiOptions = SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 沉浸式用户体验之隐藏状态栏。
     * 谷歌官方建议在隐藏状态栏的时候也应隐藏顶部菜单栏，以便带来更佳的沉浸式用户体验。
     */
    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * 沉浸式用户体验之全屏但显示状态栏。即应用位于状态栏底部。
     */
    private void behindStatusbar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * 沉浸式用户体验之隐藏导航栏（虚拟按键）。当用户点击屏幕时会重新显示。
     * <p>
     * 谷歌官方建议隐藏导航栏的时候也应该隐藏状态栏，以便提供更好的沉浸式用户体验。
     */
    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 沉浸式用户体验之应用位于导航栏底部。
     */
    private void behindNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 沉浸式用户体验之触屏恢复式全屏，即处于全屏状态下点击屏幕后状态栏及导航栏即可恢复。
     * <p>
     * 适用于观看视频等场景。
     */
    private void leanBackFullscreen() {
        View decorView = getWindow().getDecorView();
        int uiOptions = SYSTEM_UI_FLAG_FULLSCREEN
                | SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 沉浸式用户体验之触屏不可恢复式全屏。
     * <p>
     * 适用于游戏，画图等场景。
     */
    private void stickyFullscreen() {
        View decorView = getWindow().getDecorView();
        int uiOptions = SYSTEM_UI_FLAG_IMMERSIVE
                | SYSTEM_UI_FLAG_FULLSCREEN
                | SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 显示系统栏：Status Bar and Navigation Bar
     * <p>
     * setSystemUiVisibility()方法传0将会消除之前所有设置
     */
    private void revelSystemBars() {
        View decorView = this.getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);
    }
}
