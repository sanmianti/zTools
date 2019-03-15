package com.sanmianti.qrcode.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sanmianti.qrcode.R;
import com.sanmianti.qrcode.utils.CodeUtils;
import com.sanmianti.qrcode.utils.ImageUtil;
import com.sanmianti.qrcode.utils.ZXingLibrary;

/**
 * 二维码扫描主界面
 *
 * @author sanmianti
 * @date 2018/8/2 11:43
 */
public class CaptureActivity extends AppCompatActivity {

    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;
    public static final int START_ACTIVITY_FOR_RESULT_CODE = 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZXingLibrary.DisplayUtil.initDisplayOpinion(this);
        setContentView(R.layout.capture_activity);
        initView();
    }

    private void initView() {

        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();

        ImageView titleBarBack = findViewById(R.id.title_bar_back);
        TextView titleBarAlubm = findViewById(R.id.title_bar_album);

        titleBarBack.setOnClickListener(v -> finish());
        titleBarAlubm.setOnClickListener(v -> getPicFromAlbum());


    }

    /**
     * 打开相册选择解析图片
     */
    private void getPicFromAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            onResult(true, result);
        }

        @Override
        public void onAnalyzeFailed() {
            onResult(false, "扫描失败");
        }
    };

    /**
     * 解析本地图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || requestCode != REQUEST_IMAGE) {
            return;
        }
        CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, data.getData()), new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                onResult(true, result);
            }

            @Override
            public void onAnalyzeFailed() {
                Toast.makeText(CaptureActivity.this, "图片解析失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 扫描结果回调
     *
     * @param success 是否成功
     * @param result  扫描结果
     */
    private void onResult(boolean success, String result) {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        if (success) {
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
        } else {
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
        }
        bundle.putString(CodeUtils.RESULT_STRING, result);
        resultIntent.putExtras(bundle);
        CaptureActivity.this.setResult(RESULT_OK, resultIntent);
        CaptureActivity.this.finish();
    }


}