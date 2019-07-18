package sanmianti.com.ztools;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.DatePicker;

/**
 * @author sanmianti
 * @description 日期选择器
 * @date 2018/11/8 10:30
 */
public class CusDatePickerDialog implements View.OnClickListener {

    BottomSheetDialog mBottomSheetDialog;
    DatePicker mDatePicker;
    DateSelectorListener mDateSelectorListener;


    public interface DateSelectorListener {
        void onResult(int year, int month, int day);
    }


    public void setmDateSelectorListener(DateSelectorListener dateSelectorListener) {
        this.mDateSelectorListener = dateSelectorListener;
    }

    public void show(Context context) {


        if (mBottomSheetDialog == null) {
            View view = View.inflate(context, R.layout.date_selector_dialog, null);

            view.findViewById(R.id.btn_cancle).setOnClickListener(this);
            view.findViewById(R.id.btn_confirm).setOnClickListener(this);
            mDatePicker = view.findViewById(R.id.datePicker);
            mDatePicker.setMaxDate(System.currentTimeMillis());
            mBottomSheetDialog = new BottomSheetDialog(context);
            mBottomSheetDialog.setContentView(view);
        }

        if (!mBottomSheetDialog.isShowing())
            mBottomSheetDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cancle) {
            //取消
            mBottomSheetDialog.dismiss();
        }
        if (v.getId() == R.id.btn_confirm) {
            //确认
            if (mDateSelectorListener != null) {
                mDateSelectorListener.onResult(mDatePicker.getYear(),
                        mDatePicker.getMonth() + 1, mDatePicker.getDayOfMonth());
            }
            mBottomSheetDialog.dismiss();
        }
    }


}
