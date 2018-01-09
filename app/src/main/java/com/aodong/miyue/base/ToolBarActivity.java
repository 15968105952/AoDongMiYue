package com.aodong.miyue.base;

import android.os.Bundle;
import android.widget.Button;

import com.gyf.barlibrary.ImmersionBar;


/**
 * Created by wangyc on 2017/9/12.
 */

public abstract class ToolBarActivity extends BaseActivity {

    public Button btnBack;

    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true);
        mImmersionBar.init();           //所有子类都将继承这些相同的属性
    }

    @Override
    public void onStart() {
        super.onStart();
       /* btnBack = (Button) findViewById(R.id.back_btn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();     //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
}
