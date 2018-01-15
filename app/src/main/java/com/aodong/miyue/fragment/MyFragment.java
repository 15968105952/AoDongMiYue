package com.aodong.miyue.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aodong.miyue.R;
import com.aodong.miyue.base.BaseFragment;
import com.netease.nim.uikit.common.ui.widget.SwitchButton;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by wangdh on 2016/8/25.
 */
public class MyFragment extends BaseFragment {
    @InjectView(R.id.back_btn)
    Button backBtn;
    @InjectView(R.id.page_name)
    TextView pageName;
    @InjectView(R.id.tv_editor)
    TextView tvEditor;
    @InjectView(R.id.tv_nick_name)
    TextView tvNickName;
    @InjectView(R.id.iv_sex)
    ImageView ivSex;
    @InjectView(R.id.tv_age)
    TextView tvAge;
    @InjectView(R.id.ll_sex)
    LinearLayout llSex;
    @InjectView(R.id.tv_concern)
    TextView tvConcern;
    @InjectView(R.id.tv_concern_count)
    TextView tvConcernCount;
    @InjectView(R.id.tv_fans)
    TextView tvFans;
    @InjectView(R.id.tv_fans_count)
    TextView tvFansCount;
    @InjectView(R.id.tv_black_list)
    TextView tvBlackList;
    @InjectView(R.id.tv_black_list_count)
    TextView tvBlackListCount;
    @InjectView(R.id.tv_Personality_signature)
    TextView tvPersonalitySignature;
    @InjectView(R.id.iv1)
    ImageView iv1;
    @InjectView(R.id.iv2)
    ImageView iv2;
    @InjectView(R.id.iv3)
    ImageView iv3;
    @InjectView(R.id.iv4)
    ImageView iv4;
    @InjectView(R.id.iv_disturb)
    ImageView ivDisturb;
    @InjectView(R.id.setting_item_toggle)
    SwitchButton settingItemToggle;
    @InjectView(R.id.rl_Charge_signature)
    RelativeLayout rlChargeSignature;
    @InjectView(R.id.rl_video_recording)
    RelativeLayout rlVideoRecording;
    @InjectView(R.id.rl_balance)
    RelativeLayout rlBalance;
    @InjectView(R.id.rl_video_auth)
    RelativeLayout rlVideoAuth;
    @InjectView(R.id.rl_View_evaluation)
    RelativeLayout rlViewEvaluation;
    @InjectView(R.id.rl_online_service)
    RelativeLayout rlOnlineService;
    @InjectView(R.id.rl_setting)
    RelativeLayout rlSetting;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_my, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {
        settingItemToggle.setCheck(true);
        settingItemToggle.setOnChangedListener(new SwitchButton.OnChangedListener() {
            @Override
            public void OnChanged(View v, boolean checkState) {
                if(checkState){
                    Toast.makeText(context,checkState+"",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"false",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
