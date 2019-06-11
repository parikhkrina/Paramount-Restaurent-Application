package com.application.paramountfinefoods.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class DominoBoldTextView extends AppCompatTextView {

    private void setCustomFont(DominoBoldTextView textView, Context context) {
        Typeface tf = Util.FontCache.get("Domino-Bold.ttf", context);
        if (tf != null) {
            textView.setTypeface(tf);
        }
    }


    public DominoBoldTextView(Context context) {
        super(context);
        init(context);

    }

    public DominoBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DominoBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    private void init(Context context) {

        setCustomFont(this, context);
    }
}
