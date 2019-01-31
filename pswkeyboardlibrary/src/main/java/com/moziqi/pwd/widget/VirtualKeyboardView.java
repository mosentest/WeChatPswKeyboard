package com.moziqi.pwd.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.moziqi.pwd.R;
import com.moziqi.pwd.adapter.KeyBoardAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 虚拟键盘
 */
public class VirtualKeyboardView extends RelativeLayout {


    Context context;

    //因为就6个输入框不会变了，用数组内存申请固定空间，比List省空间（自己认为）
    private GridView gridView;    //用GrideView布局键盘，其实并不是真正的键盘，只是模拟键盘的功能

    private ArrayList<Map<String, String>> valueList;    //有人可能有疑问，为何这里不用数组了？
    //因为要用Adapter中适配，用数组不能往adapter中填充

    private RelativeLayout layoutBack;

    private boolean random = false;

    private boolean showPoint;

    private KeyBoardAdapter keyBoardAdapter;

    public VirtualKeyboardView(Context context) {
        this(context, null);
    }

    public VirtualKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_virtual_keyboard, this, true);

        if (attrs != null) {
//            Resources resources = getContext().getResources();
            final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.VirtualKeyboardView, 0, 0);
            random = a.getBoolean(R.styleable.VirtualKeyboardView_virtualkeyboard_random, false);
            showPoint = a.getBoolean(R.styleable.VirtualKeyboardView_virtualkeyboard_point, false);
            a.recycle();
        }

        valueList = new ArrayList<>();

        //没必要嵌套一层
        layoutBack = (RelativeLayout) view.findViewById(R.id.layoutBack);

        gridView = (GridView) view.findViewById(R.id.gv_keybord);

        if (random) {
            initRandomValueList(showPoint);
        } else {
            initValueList(showPoint);
        }

        setupView();

        //没必要
        //addView(view);      //必须要，不然不显示控件
    }

    public RelativeLayout getLayoutBack() {
        return layoutBack;
    }

    public ArrayList<Map<String, String>> getValueList() {
        return valueList;
    }

    private void initValueList(boolean showPoint) {

        // 初始化按钮上应该显示的数字
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", showPoint ? "." : "");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }
    }

    public void initRandomValueList(boolean showPoint) {
        int[] ints = randomArray(0, 9, 10);
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<>();
            if (i < 10) {
                map.put("name", String.valueOf(ints[i]));
            } else if (i == 10) {
                map.put("name", showPoint ? "." : "");
            } else if (i == 11) {
                map.put("name", String.valueOf(ints[0]));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }
    }


    /**
     * 随机指定范围内N个不重复的数
     * 在初始化的无重复待选数组中随机产生一个数放入结果中，
     * 将待选数组被随机到的数，用待选数组(len-1)下标对应的数替换
     * 然后从len-2里随机产生下一个随机数，如此类推
     *
     * @param max 指定范围最大值
     * @param min 指定范围最小值
     * @param n   随机数个数
     * @return int[] 随机数结果集
     */
    public static int[] randomArray(int min, int max, int n) {
        int len = max - min + 1;

        if (max < min || n > len) {
            return null;
        }

        //初始化给定范围的待选数组
        int[] source = new int[len];
        for (int i = min; i < min + len; i++) {
            source[i - min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            //待选数组0到(len-2)随机一个下标
            index = Math.abs(rd.nextInt() % len--);
            //将随机到的数放入结果集
            result[i] = source[index];
            //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
            source[index] = source[len];
        }
        return result;
    }

    public GridView getGridView() {
        return gridView;
    }

    private void setupView() {

        keyBoardAdapter = new KeyBoardAdapter(context, valueList);
        gridView.setAdapter(keyBoardAdapter);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (random && visibility == GONE) {
            valueList.clear();
            initRandomValueList(showPoint);
            keyBoardAdapter.notifyDataSetChanged();
        }
    }
}
