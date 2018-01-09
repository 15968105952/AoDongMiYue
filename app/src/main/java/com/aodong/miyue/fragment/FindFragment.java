package com.aodong.miyue.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aodong.miyue.R;
import com.aodong.miyue.adapter.MyFragmentPagerAdapter;
import com.aodong.miyue.base.BaseFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by wangdh on 2016/8/25.
 */
public class FindFragment extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    @InjectView(R.id.timeline_viewpager)
    ViewPager timelineViewpager;
    @InjectView(R.id.btn_first)
    TextView btnFirst;
    @InjectView(R.id.vw_first)
    View vwFirst;
    @InjectView(R.id.btn_second)
    TextView btnSecond;
    @InjectView(R.id.vw_second)
    View vwSecond;
    @InjectView(R.id.btn_third)
    TextView btnThird;
    @InjectView(R.id.vw_third)
    View vwThird;
    @InjectView(R.id.btn_four)
    TextView btnFour;
    @InjectView(R.id.vw_four)
    View vwFour;
    @InjectView(R.id.bottomlinear)
    LinearLayout bottomlinear;
    @InjectView(R.id.cursor_btn)
    ImageView cursorBtn;
    @InjectView(R.id.cursorarea)
    LinearLayout cursorarea;
    @InjectView(R.id.ll_first)
    LinearLayout llFirst;
    @InjectView(R.id.ll_second)
    LinearLayout llSecond;
    @InjectView(R.id.ll_third)
    LinearLayout llThird;
    @InjectView(R.id.ll_four)
    LinearLayout llFour;
    private ViewPager myviewpager;
    //fragment的集合，对应每个子页面
    private ArrayList<Fragment> fragments;
    //选项卡中的按钮
    private TextView btn_first;
    private TextView btn_second;
    private TextView btn_third;
    private TextView btn_four;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_zixuan, null);
        ButterKnife.inject(this, view);
        myviewpager = (ViewPager) view.findViewById(R.id.timeline_viewpager);
        btn_first = (TextView) view.findViewById(R.id.btn_first);
        btn_second = (TextView) view.findViewById(R.id.btn_second);
        btn_third = (TextView) view.findViewById(R.id.btn_third);
        btn_four = (TextView) view.findViewById(R.id.btn_four);
        return view;
    }


    @Override
    protected void initData() {
        myviewpager.setOnPageChangeListener(this);
        llFirst.setOnClickListener(this);
        llSecond.setOnClickListener(this);
        llThird.setOnClickListener(this);
        llFour.setOnClickListener(this);


        fragments = new ArrayList<Fragment>();
        fragments.add(new UserfulChooseFragment());
        fragments.add(new ConcernFragment());
        fragments.add(new NewPersonFragment());
        fragments.add(new NearbyFragment());


        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        myviewpager.setAdapter(adapter);


        btn_first.setTextColor(getResources().getColor(R.color.color_black_333333));


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            showfirst();
        } else if (position == 1) {
            showsecond();
        } else if (position == 2) {
            showsthird();
        } else if (position == 3) {
            showsfour();
        }

    }

    private void showsfour() {
          /*设置下滑线的显示与消失*/
        myviewpager.setCurrentItem(3);
        vwFirst.setVisibility(View.INVISIBLE);
        vwSecond.setVisibility(View.INVISIBLE);
        vwThird.setVisibility(View.INVISIBLE);
        vwFour.setVisibility(View.VISIBLE);
                 /*设置字体颜色*/
        btn_first.setTextColor(getResources().getColor(R.color.color_upload_normal));
        btn_second.setTextColor(getResources().getColor(R.color.color_upload_normal));
        btn_third.setTextColor(getResources().getColor(R.color.color_upload_normal));
        btn_four.setTextColor(getResources().getColor(R.color.color_black_333333));
    }

    private void showsthird() {
          /*设置下滑线的显示与消失*/
        myviewpager.setCurrentItem(2);
        vwFirst.setVisibility(View.INVISIBLE);
        vwSecond.setVisibility(View.INVISIBLE);
        vwThird.setVisibility(View.VISIBLE);
        vwFour.setVisibility(View.INVISIBLE);
                  /*设置字体颜色*/
        btn_first.setTextColor(getResources().getColor(R.color.color_upload_normal));
        btn_second.setTextColor(getResources().getColor(R.color.color_upload_normal));
        btn_third.setTextColor(getResources().getColor(R.color.color_black_333333));
        btn_four.setTextColor(getResources().getColor(R.color.color_upload_normal));
    }

    private void showsecond() {
           /*设置下滑线的显示与消失*/
        myviewpager.setCurrentItem(1);
        vwFirst.setVisibility(View.INVISIBLE);
        vwSecond.setVisibility(View.VISIBLE);
        vwThird.setVisibility(View.INVISIBLE);
        vwFour.setVisibility(View.INVISIBLE);
                   /*设置字体颜色*/
        btn_first.setTextColor(getResources().getColor(R.color.color_upload_normal));
        btn_second.setTextColor(getResources().getColor(R.color.color_black_333333));
        btn_third.setTextColor(getResources().getColor(R.color.color_upload_normal));
        btn_four.setTextColor(getResources().getColor(R.color.color_upload_normal));
    }

    private void showfirst() {
        myviewpager.setCurrentItem(0);
         /*设置下滑线的显示与消失*/
        vwFirst.setVisibility(View.VISIBLE);
        vwSecond.setVisibility(View.INVISIBLE);
        vwThird.setVisibility(View.INVISIBLE);
        vwFour.setVisibility(View.INVISIBLE);
                /*设置字体颜色*/
        btn_first.setTextColor(getResources().getColor(R.color.color_black_333333));
        btn_second.setTextColor(getResources().getColor(R.color.color_upload_normal));
        btn_third.setTextColor(getResources().getColor(R.color.color_upload_normal));
        btn_four.setTextColor(getResources().getColor(R.color.color_upload_normal));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_first:
                showfirst();
//				cursorAnim(0);

                break;
            case R.id.ll_second:
                showsecond();
//				cursorAnim(1);

                break;
            case R.id.ll_third:
                showsthird();
//				cursorAnim(2);

                break;
            case R.id.ll_four:
                showsfour();
//				cursorAnim(3);

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
