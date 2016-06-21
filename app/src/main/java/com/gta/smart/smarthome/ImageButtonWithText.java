package com.gta.smart.smarthome;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/5/19.
 */
public class ImageButtonWithText extends RelativeLayout {
    private View view;
    private ImageButton imageButton;
    private TextView textView;
    private Context context;
//    private LinearLayout imagebtnwithtext;
    public ImageButtonWithText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.imagebtn_with_text, this, true);
        imageButton = (ImageButton) view.findViewById(R.id.img_btn);
        textView = (TextView) view.findViewById(R.id.textview);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageButtonWithText);
        CharSequence text = typedArray.getText(R.styleable.ImageButtonWithText_android_text);
        if (text != null) {
            textView.setText(text);
        }

        float size = typedArray.getDimensionPixelSize(R.styleable.ImageButtonWithText_android_textSize, 8);
        if (size != 8) {
            textView.setTextSize(size);
        }
        ColorStateList colorStateList = typedArray.getColorStateList(R.styleable.ImageButtonWithText_android_textColor);
        if (null != colorStateList) {
            textView.setTextColor(colorStateList);
        }

        Drawable drawable = typedArray.getDrawable(R.styleable.ImageButtonWithText_android_src);
        if (null != drawable) {
            imageButton.setImageDrawable(drawable);
        }
        typedArray.recycle();
//        imagebtnwithtext = (LinearLayout) findViewById(R.id.imagebtnwithtext);
//        imagebtnwithtext.setOnClickListener(this);
//        textView.setOnClickListener(this);
//        imageButton.setOnClickListener(this);
    }

//    public interface OnImageBtnWidthTextClickListener{
//        void onImageBtnWithTextClicked(View view);
//    }
//    private OnImageBtnWidthTextClickListener onImageBtnWidthTextClickListener;
//
//    public void setOnImageBtnWithTextClicked(OnImageBtnWidthTextClickListener onClicked){
//        onImageBtnWidthTextClickListener = onClicked;
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (onImageBtnWidthTextClickListener != null) {
//            onImageBtnWidthTextClickListener.onImageBtnWithTextClicked(v);
//        }
//    }

//    public void setImageButtonSrc(int drawable) {
////        imageButton.setImageDrawable(drawable);
//        imageButton.setImageResource(drawable);
//    }
//
    public void setBackgroundBitmap(/*int resource*/Bitmap bm) {
//        imageButton.setBackgroundResource(resource);
        imageButton.setImageBitmap(bm);
    }
//
//
    public void setTextViewText(String text) {
        textView.setText(text);
    }
//    public void setTextColor(int color) {
//        textView.setTextColor(color);
//    }
//    public void setTextSize(int size) {
//        textView.setTextSize(size);
//    }
}
