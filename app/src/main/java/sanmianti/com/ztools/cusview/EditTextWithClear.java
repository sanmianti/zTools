package sanmianti.com.ztools.cusview;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import sanmianti.com.ztools.R;


/**
 * @author sanmianti
 * @description 带一键清除功能的自定义输入框
 * @date 2019年4月17日11:39:30
 */
public class EditTextWithClear extends android.support.v7.widget.AppCompatEditText {

    private Drawable imageDel;

    public EditTextWithClear(Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        imageDel = getContext().getResources().getDrawable(R.drawable.outline_close_black_18dp);
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (length() < 1)
                    setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null, null, null);
                else
                    setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null, imageDel, null);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imageDel != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getX();
            int eventY = (int) event.getY();
            Rect rect = new Rect();
            getLocalVisibleRect(rect);
            rect.left = rect.right - 100;
            if (rect.contains(eventX, eventY)) {
                setText("");
            }
            performClick();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

}
