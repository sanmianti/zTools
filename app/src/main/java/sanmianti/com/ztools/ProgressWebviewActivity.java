package sanmianti.com.ztools;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import sanmianti.com.ztools.databinding.ActivityProgressWebviewBinding;

/**
 * 仿微信带进度条的WebView。
 */
public class ProgressWebviewActivity extends BaseActivity {

    ActivityProgressWebviewBinding binding;
    public static final String URL = "url";
    private WebView mWebView;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_progress_webview);
        initData();
        initView();
        initWebView();
    }

    private void initView() {
        binding.webviewExceptionLayer.setOnClickListener(v -> {
            binding.webviewExceptionLayer.setVisibility(View.GONE);
            initWebView();
        });

    }

    private void initData() {
        Intent intent = this.getIntent();
        mUrl = intent.getStringExtra(URL);
        //如果路径为空则跳转至默认页面
        if (mUrl == null || mUrl.length() == 0) {
            mUrl = "http://www.baidu.com";
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        if (mWebView == null) {
            mWebView = new WebView(this);
            binding.webviewContainer.removeAllViewsInLayout();
            binding.webviewContainer.addView(mWebView);
            mWebView.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
            mWebView.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setWebChromeClient(mWebChromeClient);
            mWebView.setWebViewClient(mWebViewClient);
            mWebView.getSettings().setUseWideViewPort(true);
            mWebView.getSettings().setLoadWithOverviewMode(true);
            //支持文件下载
            mWebView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            });
        }
        mWebView.loadUrl(mUrl);
        mWebView.setOnLongClickListener(v -> true);
    }

    /**
     * 加载进度
     */
    WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int progress) {
            if (progress < 100 && binding.webviewProgress.getVisibility() == ProgressBar.GONE) {
                binding.webviewProgress.setVisibility(ProgressBar.VISIBLE);
            }
            binding.webviewProgress.setProgress(progress);
            if (progress == 100) {
                binding.webviewProgress.setVisibility(ProgressBar.GONE);

            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
        }
    };

    /**
     * 异常处理
     */
    WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            //销毁原有Webview
            if (mWebView != null) {
                binding.webviewContainer.removeView(mWebView);
                mWebView.destroy();
                mWebView = null;
            }
            binding.webviewProgress.setVisibility(View.GONE);
            binding.webviewExceptionLayer.setVisibility(View.VISIBLE);
        }

    };

    /**
     * 点击返回键退回上一页
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && KeyEvent.KEYCODE_BACK == keyCode
                && mWebView != null
                && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
