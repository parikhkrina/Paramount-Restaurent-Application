package com.application.paramountfinefoods.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class DiavloBoldTextView extends AppCompatTextView {


    private void setCustomFont(DiavloBoldTextView textView, Context context) {
        Typeface tf = Util.FontCache.get("Diavlo_BOLD_II_37.otf", context);
        if (tf != null) {
            textView.setTypeface(tf);
        }
    }


    public DiavloBoldTextView(Context context) {
        super(context);
        init(context);

    }

    public DiavloBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DiavloBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    private void init(Context context) {

        setCustomFont(this, context);
    }
}
