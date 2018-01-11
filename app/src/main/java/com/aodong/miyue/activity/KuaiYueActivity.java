package com.aodong.miyue.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aodong.miyue.R;
import com.aodong.miyue.base.ToolBarActivity;
import com.aodong.miyue.fragment.MainFragment;
import com.dalong.carview.CardDataItem;
import com.dalong.carview.CardSlidePanel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class KuaiYueActivity extends ToolBarActivity {

    @InjectView(R.id.back_btn)
    Button backBtn;
    @InjectView(R.id.page_name)
    TextView pageName;
    private MainFragment mMainFragment;
    private List<CardDataItem> dataList = new ArrayList<>();
    private CardSlidePanel slidePanel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuai_yue);
        ButterKnife.inject(this);
        backBtn.setVisibility(View.VISIBLE);
        pageName.setText(getResources().getString(R.string.kuaiyue));
        mMainFragment=new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mMainFragment).commit();
    }
}
