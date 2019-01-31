package com.lnyp.pswkeyboard.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lnyp.pswkeyboard.R;
import com.moziqi.pwd.adapter.KeyBoardAdapter;
import com.moziqi.pwd.widget.OnPasswordInputFinish;
import com.moziqi.pwd.widget.VirtualKeyboardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 弹框里面的View
 */
public class PasswordView extends RelativeLayout {

    Context mContext;

    private VirtualKeyboardView virtualKeyboardView;

    private com.moziqi.pwd.widget.PasswordLayoutView pwdLayoutView;

    private GridView gridView;

    private ImageView imgCancel;

    private ArrayList<Map<String, String>> valueList;

    private int currentIndex = -1;    //用于记录当前输入密码格位置

    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        View view = View.inflate(context, R.layout.layout_popup_bottom, null);

        virtualKeyboardView = (VirtualKeyboardView) view.findViewById(R.id.virtualKeyboardView);
        imgCancel = (ImageView) view.findViewById(R.id.img_cancel);
        gridView = virtualKeyboardView.getGridView();

        pwdLayoutView = (com.moziqi.pwd.widget.PasswordLayoutView) view.findViewById(R.id.psw_ly);


        valueList = virtualKeyboardView.getValueList();

//        initValueList();

        setupView();

        addView(view);
    }


    // 这里，我们没有使用默认的数字键盘，因为第10个数字不显示.而是空白
    private void initValueList() {

        valueList = new ArrayList<>();

        // 初始化按钮上应该显示的数字
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<String, String>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }
    }

    private void setupView() {

        // 这里、重新为数字键盘gridView设置了Adapter
//        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(mContext, valueList);
//        gridView.setAdapter(keyBoardAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 11 && position != 9) {    //点击0~9按钮

                    if (currentIndex >= -1 && currentIndex < 5) {      //判断输入位置————要小心数组越界
                        ;
                        ++currentIndex;

                        pwdLayoutView.show(currentIndex, valueList.get(position).get("name"));
                    }
                } else {
                    if (position == 11) {      //点击退格键
                        if (currentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界

                            pwdLayoutView.del(currentIndex);

                            currentIndex--;
                        }
                    }
                }
            }
        });
    }

    //设置监听方法，在第6位输入完成后触发
    public void setOnFinishInput(final OnPasswordInputFinish pass) {
        pwdLayoutView.setOnPasswordInputFinish(pass);
        pwdLayoutView.addFinishListener();
    }

    public VirtualKeyboardView getVirtualKeyboardView() {

        return virtualKeyboardView;
    }

    public ImageView getImgCancel() {
        return imgCancel;
    }
}
