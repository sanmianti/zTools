package sanmianti.com.ztools.swiprefresh;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sanmianti.com.ztools.R;

/**
 * 下拉刷新测试用例列表adapter
 *
 * @author sanmianti
 * @date 2019年7月12日09:47:02
 */
public class SwipRefreshListAdapter extends RecyclerView.Adapter {

    List<Integer> datas;


    SwipRefreshListAdapter(List<Integer> datas){
        this.datas = datas;
    }

    public void setDatas(List<Integer> datas){
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.swip_refresh_list_item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).textView.setText(String.valueOf(datas.get(position)));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public MyViewHolder(TextView itemView) {
            super(itemView);
            this.textView = itemView;
        }
    }

}
