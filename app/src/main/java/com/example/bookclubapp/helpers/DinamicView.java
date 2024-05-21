package com.example.bookclubapp.helpers;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DinamicView {
    Context context;
    public DinamicView(Context context){
        this.context = context;
    }

    public TextInputLayout description(Context context){
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextInputLayout textInputLayout = new TextInputLayout(context);
        textInputLayout.setLayoutParams(params);
        return textInputLayout;
    }

    public TextInputEditText fun(Context context){
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextInputEditText textInputEditText = new TextInputEditText(context);
        textInputEditText.setLayoutParams(params);
        return textInputEditText;
    }
}
