package sanmianti.com.ztools.keyboard;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author sanmianti
 * @description 软键盘操作工具类
 * @date 2019年4月17日10:53:50
 */
public class SoftKeboardUtils {

    /**
     * 弹出软键盘
     */
    public void popUpSoftkeyboard(Context context, EditText view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyboard(IBinder windowToken, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.hideSoftInputFromWindow(windowToken, 0);
    }

}
