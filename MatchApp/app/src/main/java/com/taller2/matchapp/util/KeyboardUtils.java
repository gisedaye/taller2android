package com.taller2.matchapp.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * Created by federicofarina on 6/23/16.
 */
public class KeyboardUtils {

    public static void hideKeyboard(Context context, TextView tv) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);
    }
}
