package com.moziqi.pwd.widget.deledittext;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

/**
 * https://blog.csdn.net/sollian/article/details/60959542
 */
public class DetectDelEventEditText extends EditText implements View.OnKeyListener,
        EditableInputConnection.OnDelEventListener {
    private DelEventListener delEventListener;

    /**
     * 防止delEvent触发两次。
     * 0：未初始化；1：使用onKey方法触发；2：使用onDelEvdent方法触发
     */
    private int flag;

    public DetectDelEventEditText(Context context) {
        super(context);
        init();
    }

    public DetectDelEventEditText(Context context,
                                  @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DetectDelEventEditText(Context context,
                                  @Nullable
                                          AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnKeyListener(this);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        super.onCreateInputConnection(outAttrs);
        EditableInputConnection editableInputConnection = new EditableInputConnection(this);
        outAttrs.initialSelStart = getSelectionStart();
        outAttrs.initialSelEnd = getSelectionEnd();
        outAttrs.initialCapsMode = editableInputConnection.getCursorCapsMode(getInputType());

        editableInputConnection.setDelEventListener(this);
        flag = 0;

        return editableInputConnection;
    }

    public void setDelListener(DelEventListener l) {
        delEventListener = l;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (flag == 2) {
            return false;
        }
        flag = 1;
        return delEventListener != null && keyCode == KeyEvent.KEYCODE_DEL && event
                .getAction() == KeyEvent.ACTION_DOWN && delEventListener.delEvent();
    }

    @Override
    public boolean onDelEvent() {
        if (flag == 1) {
            return false;
        }
        flag = 2;
        return delEventListener != null && delEventListener.delEvent();
    }

//    @Override
//    public boolean onKey(View v, int keyCode, KeyEvent event) {
//        return false;
//    }

    public interface DelEventListener {
        boolean delEvent();
    }
}