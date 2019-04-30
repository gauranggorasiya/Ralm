package wait.list.manager.custom_view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import wait.list.manager.MyApplication;


public class TextViewFontAwesome extends android.support.v7.widget.AppCompatTextView {

    public TextViewFontAwesome(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewFontAwesome(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewFontAwesome(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = MyApplication.getTypefaceFa();
            setTypeface(tf);
        }
    }

}