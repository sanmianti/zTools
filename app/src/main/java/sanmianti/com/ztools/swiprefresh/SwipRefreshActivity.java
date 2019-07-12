package sanmianti.com.ztools.swiprefresh;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sanmianti.com.ztools.BaseActivity;
import sanmianti.com.ztools.R;
import sanmianti.com.ztools.databinding.ActivitySwipRefreshBinding;

/**
 * 下拉刷新测试用例
 *
 * @author sanmianti
 * @date 2019年7月12日09:47:02
 */
public class SwipRefreshActivity extends BaseActivity implements
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    ActivitySwipRefreshBinding binding;
    SwipRefreshListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_swip_refresh);
        initView();
    }

    private void initView() {
        binding.swipRefreshPull.setOnRefreshListener(this);
        binding.swipRefreshOpen.setOnClickListener(this);
        binding.swipRefreshStop.setOnClickListener(this);
        binding.swipRefreshDisable.setOnClickListener(this);
        binding.swipRefreshChangeColor.setOnClickListener(this);
        binding.swipRefreshEnable.setOnClickListener(this);
        mAdapter = new SwipRefreshListAdapter(getDemoData());
        binding.swipRefreshRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.swipRefreshRecyclerview.setAdapter(mAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.swip_refresh_open:
                //显示刷新动画
                binding.swipRefreshPull.setRefreshing(true);
                break;
            case R.id.swip_refresh_stop:
                //隐藏刷新动画
                binding.swipRefreshPull.setRefreshing(false);
                break;
            case R.id.swip_refresh_disable:
                //停用刷新功能
                binding.swipRefreshPull.setEnabled(false);
                break;
            case R.id.swip_refresh_enable:
                //启用刷新功能
                binding.swipRefreshPull.setEnabled(true);
                break;
            case R.id.swip_refresh_change_color:
                //修改刷新动画颜色
                binding.swipRefreshPull.setColorSchemeColors(Color.RED);
                break;
            default:
                break;
        }
    }

    /**
     * 数据刷新
     */
    @Override
    public void onRefresh() {
        //执行耗时操作
        new Handler().postDelayed(() -> {
            //刷新完成
            mAdapter.setDatas(getDemoData());
            mAdapter.notifyDataSetChanged();
            binding.swipRefreshPull.setRefreshing(false);
            showToast("刷新完成");
        }, 2000);
    }

    /**
     * 模拟数据
     *
     * @return 模拟数据
     */
    private List<Integer> getDemoData() {
        List<Integer> nums = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            nums.add(random.nextInt(100));
        }

        return nums;
    }
}
