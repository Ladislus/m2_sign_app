package com.example.test_sign;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MaWebView extends WebView {
    public MaWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        BaseInputConnection test = new BaseInputConnection(this,false);
        outAttrs.imeOptions = EditorInfo.IME_ACTION_DONE;
        return test;
    }
}
