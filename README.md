# WeChatPswKeyboard
高仿微信数字键盘、支付键盘（密码键盘）

效果图：

![image](https://github.com/zuiwuyuan/WeChatPswKeyboard/blob/master/imgs/img.gif)

在原作者上增加密码界面禁止截屏和辅助功能的使用

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

gradle

Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency

```
	dependencies {
	        implementation 'com.github.moz1q1:WeChatPswKeyboard:1.0.7'
	}
```



```

隐藏密码输入框

<com.moziqi.pwd.widget.PasswordLayoutView
            android:id="@+id/psw_ly"
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:layout_marginBottom="16dp"
            android:layout_marginLeft="18dp"
            android:layout_marginRight="18dp"
            android:layout_marginTop="10dp" />

虚拟键盘

    <com.moziqi.pwd.widget.VirtualKeyboardView
        android:id="@+id/virtualKeyboardView"
        android:layout_width="match_parent"
        app:virtualkeyboard_random="true"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_gravity="bottom" />


        <resources>
            <declare-styleable name="VirtualKeyboardView">
                <attr name="virtualkeyboard_random" format="boolean"></attr>  键盘是否随机
                <attr name="virtualkeyboard_point" format="boolean"></attr>  是否显示 ‘.’ 数字键盘
            </declare-styleable>
        </resources>


    <com.moziqi.pwd.widget.VerificationCodeView
        android:id="@+id/verificationcodeview"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:orientation="horizontal"
        app:vcv_et_bg="@drawable/pwd_et_login_code"
        app:vcv_et_cursor="@drawable/pwd_et_cursor"
        app:vcv_et_inputType="number"
        app:vcv_et_number="6"
        app:vcv_et_text_color="@android:color/black"
        app:vcv_et_text_size="14sp" />
```
Share this release:


详细介绍，请查看：http://blog.csdn.net/zuiwuyuan/article/details/52198240

如果对你有帮助，请star下吧，谢谢。
