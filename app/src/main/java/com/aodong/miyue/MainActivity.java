package com.aodong.miyue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aodong.miyue.activity.KuaiYueActivity;
import com.aodong.miyue.adapter.mViewPagerAdapter;
import com.aodong.miyue.base.BaseActivity;
import com.aodong.miyue.chat.ReminderItem;
import com.aodong.miyue.chat.ReminderManager;
import com.aodong.miyue.chat.SystemMessageUnreadManager;
import com.aodong.miyue.fragment.FindFragment;
import com.aodong.miyue.fragment.MessageFragment;
import com.aodong.miyue.fragment.MyFragment;
import com.aodong.miyue.fragment.VideoShowFragment;
import com.aodong.miyue.wanyiyun.DemoCache;
import com.aodong.miyue.wanyiyun.activity.VideoActivity;
import com.aodong.miyue.wanyiyun.manager.AVChatProfile;
import com.aodong.miyue.wanyiyun.manager.SessionHelper;
import com.aodong.miyue.wanyiyun.modle.Extras;
import com.aodong.miyue.wanyiyun.preferences.Preferences;
import com.aodong.miyue.wanyiyun.preferences.UserPreferences;
import com.gyf.barlibrary.ImmersionBar;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity implements ReminderManager.UnreadNumChangedCallback {

    private static final String EXTRA_APP_QUIT = "APP_QUIT";

    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager viewPager;
    private TextView[] name = new TextView[4];
    private ImageView[] icon = new ImageView[4];
    private RelativeLayout[] tabLayout = new RelativeLayout[4];
    private ImmersionBar mImmersionBar;
    private static final String TAG = "MainActivity";
    private AbortableFuture<LoginInfo> loginRequest;

    private RequestCallback<LoginInfo> callback =
            new RequestCallback<LoginInfo>() {
                @Override
                public void onSuccess(LoginInfo param) {
//                        TODO 登录成功 存储信息 保证下次自动登录 1020025  1020723

                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                    isBoolean = true;
                }

                @Override
                public void onFailed(int code) {
                    Toast.makeText(MainActivity.this, "登录成功错误吗" + code, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onException(Throwable exception) {
                    Toast.makeText(MainActivity.this, "登录成功exception" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
                // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true);
        mImmersionBar.init();           //所有子类都将继承这些相同的属性
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initViews();
        initData();
        inject();
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        /*tab名字*/
        name[0] = (TextView) findViewById(R.id.find_name);
        name[1] = (TextView) findViewById(R.id.videoshow_name);
        name[2] = (TextView) findViewById(R.id.message_name);
        name[3] = (TextView) findViewById(R.id.my_name);
        /*tab图标*/
        icon[0] = (ImageView) findViewById(R.id.find_icon);
        icon[1] = (ImageView) findViewById(R.id.videoshow_icon);
        icon[2] = (ImageView) findViewById(R.id.message_icon);
        icon[3] = (ImageView) findViewById(R.id.my_icon);
        icon[1].setEnabled(false);
        icon[2].setEnabled(false);
        icon[3].setEnabled(false);
        tabLayout[0] = (RelativeLayout) findViewById(R.id.rb_find);
        tabLayout[1] = (RelativeLayout) findViewById(R.id.rb_videoshow);
        tabLayout[2] = (RelativeLayout) findViewById(R.id.rb_message);
        tabLayout[3] = (RelativeLayout) findViewById(R.id.rb_my);
        FindFragment findFragment = new FindFragment();
        VideoShowFragment videoshowFragment = new VideoShowFragment();
        MessageFragment messageFragment = new MessageFragment();
        MyFragment myFragment = new MyFragment();
        fragmentList.add(findFragment);
        fragmentList.add(videoshowFragment);
        fragmentList.add(messageFragment);
        fragmentList.add(myFragment);
        viewPager.setAdapter(new mViewPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setOffscreenPageLimit(4);
    }


    private void inject() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTabColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int i = 0; i < 4; i++) {
            final int j = i;
            final int p = i;
            tabLayout[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  if (p == 1) {
                        EventBus.getDefault().post(new OrderRefreshEvent("刷新~"));
                    }*/
                    icon[j].setEnabled(true);
                    viewPager.setCurrentItem(j, false);
                }
            });
        }
    }


    private void setTabColor(int position) {
        if (position > 3) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            icon[i].setEnabled(false);
            name[i].setTextColor(getResources().getColor(R.color.color_grey_666666));
        }
        icon[position].setEnabled(true);
        name[position].setTextColor(getResources().getColor(R.color.color_black_333333));
    }


    protected void initData() {
 /*显示消息数量*/
        registerMsgUnreadInfoObserver(true);
        registerSystemMessageObservers(true);
        requestSystemMessageUnreadCount();
        initUnreadCover();
        //退出登陆
        onParseIntent();
        //监听是否强制下线
        registerObservers(true);


    }

    /**
     * 注册/注销系统消息未读数变化
     *
     * @param register
     */
    private void registerSystemMessageObservers(boolean register) {
        NIMClient.getService(SystemMessageObserver.class).observeUnreadCountChange(sysMsgUnreadCountChangedObserver,
                register);
    }

    private Observer<Integer> sysMsgUnreadCountChangedObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer unreadCount) {
            SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unreadCount);
            ReminderManager.getInstance().updateContactUnreadNum(unreadCount);
        }
    };

    /**
     * 注册未读消息数量观察者
     */
    private void registerMsgUnreadInfoObserver(boolean register) {
        if (register) {
            ReminderManager.getInstance().registerUnreadNumChangedCallback(this);
        } else {
            ReminderManager.getInstance().unregisterUnreadNumChangedCallback(this);
        }
    }

    /**
     * 查询系统消息未读数
     */
    private void requestSystemMessageUnreadCount() {
        int unread = NIMClient.getService(SystemMessageService.class).querySystemMessageUnreadCountBlock();
        SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unread);
        ReminderManager.getInstance().updateContactUnreadNum(unread);
    }

    /**
     * 初始化未读红点动画
     */
    private void initUnreadCover() {
        /*DropManager.getInstance().init(MainActivity.this, (DropCover) findView(R.id.unread_cover),
                new DropCover.IDropCompletedListener() {
                    @Override
                    public void onCompleted(Object id, boolean explosive) {
                        if (id == null || !explosive) {
                            return;
                        }

                        if (id instanceof RecentContact) {
                            RecentContact r = (RecentContact) id;
                            NIMClient.getService(MsgService.class).clearUnreadCount(r.getContactId(), r.getSessionType());
                            LogUtil.i("HomeFragment", "clearUnreadCount, sessionId=" + r.getContactId());
                        } else if (id instanceof String) {
                            if (((String) id).contentEquals("0")) {
                                NIMClient.getService(MsgService.class).clearAllUnreadCount();
                                LogUtil.i("HomeFragment", "clearAllUnreadCount");
                            } else if (((String) id).contentEquals("1")) {
                                NIMClient.getService(SystemMessageService.class).resetSystemMessageUnreadCount();
                                LogUtil.i("HomeFragment", "clearAllSystemUnreadCount");
                            }
                        }
                    }
                });*/
    }


    /********聊天部分监听*********/
    private void registerObservers(boolean register) {
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, register);
//        MyUserInfoCache.getInstance().registerFriendDataChangedObserver(friendDataChangedObserver,register);
    }


    //被其他设备剔下线
    Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {

        @Override
        public void onEvent(StatusCode code) {
            if (code.wontAutoLogin()) {
                Preferences.saveUserToken("");

                if (code == StatusCode.PWD_ERROR) {
                    LogUtil.e("Auth", "user password error");
                    Toast.makeText(MainActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else if (code == StatusCode.KICKOUT) {
                    LogUtil.i("Auth", "Kicked!");
//                    showTip("您的账号已在其他设备登录！");
                    Toast.makeText(MainActivity.this, "您的账号已在其他设备登录！", Toast.LENGTH_SHORT).show();
                } else {
                    LogUtil.i("Auth", "Kicked!");
//                    showTip("您的账号已被踢下线！");
                    Toast.makeText(MainActivity.this, "您的账号已被踢下线！", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent();
                intent.setAction("exitApp");
                sendBroadcast(intent);

//                onLogout();
            }
        }
    };

    private void onParseIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
            IMMessage message = (IMMessage) getIntent().getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
            switch (message.getSessionType()) {
                case P2P:
                    SessionHelper.startP2PSession(this, message.getSessionId());
                    break;
                case Team:
                    SessionHelper.startTeamSession(this, message.getSessionId());
                    break;
                default:
                    break;
            }
        } else if (intent.hasExtra(EXTRA_APP_QUIT)) {
//            onLogout();
            return;
        } else if (intent.hasExtra(VideoActivity.INTENT_ACTION_AVCHAT)) {
            if (AVChatProfile.getInstance().isAVChatting()) {
                Intent localIntent = new Intent();
                localIntent.setClass(this, VideoActivity.class);
                startActivity(localIntent);
            }
        } else if (intent.hasExtra(Extras.EXTRA_JUMP_P2P)) {
            Intent data = intent.getParcelableExtra(Extras.EXTRA_DATA);
            String account = data.getStringExtra(Extras.EXTRA_ACCOUNT);
            if (!TextUtils.isEmpty(account)) {
                SessionHelper.startP2PSession(this, account);
            }
        }
    }


    private void login() {
        final String account = "1824902907";
        final String token = "mazhuang123";
        /*// 登录
        LoginInfo info = new LoginInfo("13938217043", "mazhuang123"); // config...
        NIMClient.getService(AuthService.class).login(info).setCallback(callback);*/
        // 登录
        loginRequest = NimUIKit.login(new LoginInfo(account, token), new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                LogUtil.i(TAG, "login success");
                Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
//                onLoginDone();

                DemoCache.setAccount(account);
                saveLoginInfo(account, token);

                // 初始化消息提醒配置
                initNotificationConfig();

                // 进入主界面
//                MainActivity.start(LoginActivity.this, null);
//                finish();
            }

            @Override
            public void onFailed(int code) {
//                onLoginDone();
                if (code == 302 || code == 404) {
                    Toast.makeText(MainActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "登录失败: " + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
                Toast.makeText(MainActivity.this, R.string.login_exception, Toast.LENGTH_LONG).show();
//                onLoginDone();
            }
        });
    }

    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
    }

    private void initNotificationConfig() {
        // 初始化消息提醒
        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = DemoCache.getNotificationConfig();
            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
    }

    @Override
    public void onUnreadNumChanged(ReminderItem item) {

    }

    @OnClick(R.id.iv_kuaiyue)
    public void onClick() {
        startActivity(KuaiYueActivity.class,false);
    }
}
