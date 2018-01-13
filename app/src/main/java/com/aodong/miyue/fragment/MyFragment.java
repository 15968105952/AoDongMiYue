package com.aodong.miyue.fragment;

import android.annotation.SuppressLint;
import android.view.View;

import com.aodong.miyue.R;
import com.aodong.miyue.base.BaseFragment;
import com.gyf.barlibrary.ImmersionBar;


/**
 * Created by wangdh on 2016/8/25.
 */
public class MyFragment extends BaseFragment {
    private ImmersionBar mImmersionBar;
    @SuppressLint("ResourceAsColor")
    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_my,null);
//        view.findViewById(R.id.rl_tool_bar).setBackgroundColor(R.color.transparent);
     /*   mImmersionBar = ImmersionBar.with(this).transparentStatusBar().navigationBarColor(R.color.transparent);
        mImmersionBar.init();           //所有子类都将继承这些相同的属性*/
        return view;
    }

    @Override
    protected void initData() {

    }
}
