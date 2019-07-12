package sanmianti.com.ztools;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sanmianti.qrcode.activity.CaptureActivity;
import com.sanmianti.qrcode.utils.CodeUtils;

import sanmianti.com.ztools.keyboard.SoftkeyboardTestActivity;
import sanmianti.com.ztools.swiprefresh.SwipRefreshActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initView() {
        findViewById(R.id.open_third_app).setOnClickListener(this);
        findViewById(R.id.status_bar).setOnClickListener(this);
        findViewById(R.id.progress_webview).setOnClickListener(this);
        findViewById(R.id.qr_code).setOnClickListener(this);
        findViewById(R.id.date_picker).setOnClickListener(this);
        findViewById(R.id.softkeyboard).setOnClickListener(this);
        findViewById(R.id.pulldownRefresh).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_third_app:
                startActivity(new Intent(MainActivity.this,
                        OpenThirdAppActivity.class));
                break;
            case R.id.status_bar:
                startActivity(new Intent(MainActivity.this,
                        ImmersiveActivity.class));
                break;
            case R.id.progress_webview:
                Intent intent = new Intent(MainActivity.this,
                        ProgressWebviewActivity.class);
                intent.putExtra(ProgressWebviewActivity.URL,
                        "https://blog.csdn.net/u012719153");
                startActivity(intent);
                break;
            case R.id.qr_code:
                startActivityForResult(new Intent(MainActivity.this,
                        CaptureActivity.class), CaptureActivity.START_ACTIVITY_FOR_RESULT_CODE);
                break;
            case R.id.date_picker:
                CusDatePickerDialog pickerDialog = new CusDatePickerDialog();
                pickerDialog.setmDateSelectorListener((year, month, day) -> {
                       showToast(year + "-" + month + "-" + day);
                });
                pickerDialog.show(this);
                break;
            case R.id.softkeyboard:
                startActivity(new Intent(MainActivity.this, SoftkeyboardTestActivity.class));
                break;
            case R.id.pulldownRefresh:
                startActivity(new Intent(MainActivity.this, SwipRefreshActivity.class));
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != CaptureActivity.START_ACTIVITY_FOR_RESULT_CODE
                || resultCode != Activity.RESULT_OK) {
            return;
        }
        String scanResult = data.getStringExtra(CodeUtils.RESULT_STRING);
        if (scanResult == null || scanResult.length() == 0) {
            showToast("扫描结果为空");
            return;
        }

        //普通文本
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("扫描结果：");
        builder.setMessage(scanResult);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("复制", (dialog, which) -> {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText("扫描结果", scanResult);
            if (cm != null) {
                cm.setPrimaryClip(mClipData);
                showToast("复制成功");
            } else {
                showToast("复制失败");
            }
        });
        builder.show();

    }
}
