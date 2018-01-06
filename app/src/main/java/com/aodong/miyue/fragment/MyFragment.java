package com.aodong.miyue.fragment;

import android.view.View;

import com.aodong.miyue.R;
import com.aodong.miyue.base.BaseFragment;





/**
 * Created by wangdh on 2016/8/25.
 */
public class MyFragment extends BaseFragment {
    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_my,null);
        return view;
    }

    @Override
    protected void initData() {

    }
}
