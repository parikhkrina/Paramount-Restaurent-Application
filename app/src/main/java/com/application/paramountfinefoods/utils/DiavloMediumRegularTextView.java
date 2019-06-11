package com.application.paramountfinefoods.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

public class DiavloMediumRegularTextView extends AppCompatTextView {

    public DiavloMediumRegularTextView(Context context) {
        super(context);
        init(context);
    }

    public DiavloMediumRegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public DiavloMediumRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        /*Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "SEGOEUI.TTF");
        setTypeface(tf);*/
        setCustomFont(this, context);

    }

    public void setCustomFont(TextView textview, Context context) {
        Typeface tf = Util.FontCache.get("Diavlo_MEDIUM_II_37.otf", context);
        if (tf != null) {
            textview.setTypeface(tf);
        }
    }


}
