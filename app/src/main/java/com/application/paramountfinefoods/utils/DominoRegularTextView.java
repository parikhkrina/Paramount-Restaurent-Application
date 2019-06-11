package com.application.paramountfinefoods.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class DominoRegularTextView  extends AppCompatTextView {

    public DominoRegularTextView(Context context) {
        super(context);
        init(context);
    }

    public DominoRegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public DominoRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        /*Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "SEGOEUI.TTF");
        setTypeface(tf);*/
        setCustomFont(this, context);

    }

    public void setCustomFont(DominoRegularTextView textview, Context context) {
        Typeface tf = Util.FontCache.get("Domine-Regular.ttf", context);
        if (tf != null) {
            textview.setTypeface(tf);
        }
    }

}
