package com.aodong.miyue.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aodong.miyue.R;
import com.aodong.miyue.adapter.ModeAdapter;
import com.aodong.miyue.adapter.ViewHolder;
import com.aodong.miyue.base.BaseFragment;
import com.aodong.miyue.listener.PullToRefreshListener;
import com.aodong.miyue.view.PullToRefreshRecyclerView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by wangdh on 2016/8/25.
 */
public class VideoShowFragment extends BaseFragment implements PullToRefreshListener {

    @InjectView(R.id.page_name)
    TextView pageName;
    @InjectView(R.id.rl_beautiful_girl)
    PullToRefreshRecyclerView rlBeautifulGirl;
    @InjectView(R.id.back_btn)
    Button backBtn;
    private List<String> data;
    private ModeAdapter adapter;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_video_show, null);
        ButterKnife.inject(this, view);
        pageName.setText(getResources().getString(R.string.videoshow));
        backBtn.setVisibility(View.INVISIBLE);
        /*设置recycleview和空布局*/
        setPullToRefreshRecyclerViewAndEmptyView();
        return view;
    }

    private void setPullToRefreshRecyclerViewAndEmptyView() {
        //设置EmptyView（没有数据时的默认布局）
        View emptyView = View.inflate(context, R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        rlBeautifulGirl.setEmptyView(emptyView);
        //设置列表的样式
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlBeautifulGirl.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
       /*设置适配器*/
        setdapter();
    }

    private void setdapter() {
        data = new ArrayList<>();
        data.clear();
        data.add("苹果");
        data.add("香蕉");
        data.add("葡萄");
        data.add("荔枝");
        data.add("栗子");
        data.add("火龙果");
        data.add("西瓜");
        //创建适配器，参数为1.上下文    2.布局id   3.当前item的position
        adapter = new ModeAdapter<String>(getContext(), R.layout.fragment_video_show_item, data) {

            @Override
            public void convert(ViewHolder holder, String s, final int position) {
                SimpleDraweeView sd = holder.getView(R.id.headImage);
                sd.setImageURI(Uri.parse("https://pic4.zhimg.com/03b2d57be62b30f158f48f388c8f3f33_b.png"));
                //设置文本信息
//                holder.setText(R.id.textView, s);

                //设置图片
                // holder.setImageResource(R.id.iv,)


                //设置字体颜色
                // holder.setTextColor(R.id.textView, );

                //设置当前界面是否可见
                // holder.setViewVisiable();

                //设置当前控件的背景色
                //holder.setViewBackgroundResource();

                // 设置点击事件
                holder.setOnclickListener(R.id.ll_video_show_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "我是第" + position + "个item", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        };
        rlBeautifulGirl.setAdapter(adapter);
        //设置是否开启上拉加载
        rlBeautifulGirl.setLoadingMoreEnabled(true);
        //设置是否开启下拉刷新
        rlBeautifulGirl.setPullRefreshEnabled(true);
        //设置是否显示上次刷新的时间
        rlBeautifulGirl.displayLastRefreshTime(true);
        //设置刷新回调
        rlBeautifulGirl.setPullToRefreshListener(this);
        //主动触发下拉刷新操作
        //recyclerView.onRefresh();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onRefresh() {
        rlBeautifulGirl.postDelayed(new Runnable() {
            @Override
            public void run() {
                rlBeautifulGirl.setRefreshComplete();
                //模拟没有数据的情况
                data.clear();
                data.add("苹果");
                data.add("香蕉");
                data.add("葡萄");
                data.add("荔枝");
                adapter.notifyDataSetChanged();
//                int size = data.size();
//                for (int i = size; i < size + 4; i++) {
//                    data.add("" + i + i + i + i);
//                }
//                adapter.notifyDataSetChanged();
            }
        }, 3000);
    }

    @Override
    public void onLoadMore() {
        rlBeautifulGirl.postDelayed(new Runnable() {
            @Override
            public void run() {
               /* i++;
                if (i >= 4) {
                    //这个地方可以设置数据全部加载完毕以后，提示用户没有更多数据的提示信息
//                    recyclerView.addFooterView(footerView);
//                    recyclerView.setLoadingMoreEnabled(false);
                    rlBeautifulGirl.setLoadMoreFail();
                    return;
                }
                rlBeautifulGirl.setLoadMoreComplete();
                //模拟加载数据的情况
                int size = data.size();
                for (int i = size; i < size + 6; i++) {
                    data.add("" + i + i + i + i);
                }*/
                data.clear();
                data.add("苹果");
                data.add("香蕉");
                data.add("葡萄");
                data.add("荔枝");
                rlBeautifulGirl.setLoadMoreComplete();

                adapter.notifyDataSetChanged();

            }
        }, 3000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }
}

