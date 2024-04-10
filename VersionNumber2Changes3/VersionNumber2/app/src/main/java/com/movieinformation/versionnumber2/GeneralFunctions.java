package com.movieinformation.versionnumber2;

import android.content.Context;
import android.util.TypedValue;

public class GeneralFunctions {

    public int getColorId(Context context, int type) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(type, typedValue, true);
        return context.getColor(typedValue.resourceId);
    }
}
