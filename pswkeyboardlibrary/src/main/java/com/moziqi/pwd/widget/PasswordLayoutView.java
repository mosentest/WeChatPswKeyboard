package com.moziqi.pwd.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moziqi.pwd.R;

/**
 * Copyright (C), 2018-2018
 * FileName: PasswordLayoutView
 * Author: ziqimo
 * Date: 2018/11/17 下午9:30
 * Description: ${密码输入框}
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class PasswordLayoutView extends LinearLayout {


    private TextView[] tvList;      //用数组保存6个TextView，为什么用数组？

    private ImageView[] imgList;      //用数组保存6个TextView，为什么用数组？


    private OnPasswordInputFinish onPasswordInputFinish;

    private PasswordLayoutListener mPasswordLayoutListener;


    public PasswordLayoutView(Context context) {
        this(context, null);
    }

    public PasswordLayoutView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_password_view, this, true);
        setOrientation(HORIZONTAL);
        //@drawable/pwd_input_area_bg
        setBackgroundDrawable(getResources().getDrawable(R.drawable.pwd_input_area_bg));
        initView(view);
    }


    private void initView(View view) {


        tvList = new TextView[6];

        imgList = new ImageView[6];

        tvList[0] = (TextView) view.findViewById(R.id.tv_pass1);
        tvList[1] = (TextView) view.findViewById(R.id.tv_pass2);
        tvList[2] = (TextView) view.findViewById(R.id.tv_pass3);
        tvList[3] = (TextView) view.findViewById(R.id.tv_pass4);
        tvList[4] = (TextView) view.findViewById(R.id.tv_pass5);
        tvList[5] = (TextView) view.findViewById(R.id.tv_pass6);


        imgList[0] = (ImageView) view.findViewById(R.id.img_pass1);
        imgList[1] = (ImageView) view.findViewById(R.id.img_pass2);
        imgList[2] = (ImageView) view.findViewById(R.id.img_pass3);
        imgList[3] = (ImageView) view.findViewById(R.id.img_pass4);
        imgList[4] = (ImageView) view.findViewById(R.id.img_pass5);
        imgList[5] = (ImageView) view.findViewById(R.id.img_pass6);

//        for (int i = 0; i < 6; i++) {
//            imgList[i].setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mPasswordLayoutListener != null) {
//                        mPasswordLayoutListener.onclick();
//                    }
//                }
//            });
//            tvList[i].setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mPasswordLayoutListener != null) {
//                        mPasswordLayoutListener.onclick();
//                    }
//                }
//            });
//        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    public void show(int currentIndex, String text) {
        tvList[currentIndex].setText(text);

        tvList[currentIndex].setVisibility(View.INVISIBLE);
        imgList[currentIndex].setVisibility(View.VISIBLE);
    }

    public void del(int currentIndex) {

        tvList[currentIndex].setText("");

        tvList[currentIndex].setVisibility(View.VISIBLE);
        imgList[currentIndex].setVisibility(View.INVISIBLE);

    }

    public void setOnPasswordInputFinish(OnPasswordInputFinish onPasswordInputFinish) {
        this.onPasswordInputFinish = onPasswordInputFinish;
    }


    public void setClickListener(PasswordLayoutListener clickListener) {
        this.mPasswordLayoutListener = clickListener;
        //父view拦截子view的点击事件
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPasswordLayoutListener != null) {
                    mPasswordLayoutListener.onclick();
                }
            }
        });
    }


    /**
     * 在第五个按钮上增加回调
     */
    public void addFinishListener() {
        tvList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() == 1) {

                    String strPassword = "";     //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱

                    for (int i = 0; i < 6; i++) {
                        strPassword += tvList[i].getText().toString().trim();
                    }
                    if (onPasswordInputFinish != null) {
                        onPasswordInputFinish.inputFinish(strPassword);    //接口中要实现的方法，完成密码输入完成后的响应逻辑
                    }
                } else {
                    if (onPasswordInputFinish != null) {
                        onPasswordInputFinish.inputCancel();
                    }
                }
            }
        });
    }
}
