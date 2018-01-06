package com.aodong.miyue.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.aodong.miyue.wanyiyun.DemoCache;
import com.aodong.miyue.wanyiyun.config.NimSDKOptionConfig;
import com.aodong.miyue.wanyiyun.manager.DemoOnlineStateContentProvider;
import com.aodong.miyue.wanyiyun.manager.NIMInitManager;
import com.aodong.miyue.wanyiyun.manager.NimDemoLocationProvider;
import com.aodong.miyue.wanyiyun.manager.SessionHelper;
import com.aodong.miyue.wanyiyun.parser.ContactHelper;
import com.aodong.miyue.wanyiyun.preferences.Preferences;
import com.aodong.miyue.wanyiyun.preferences.UserPreferences;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.UIKitOptions;
import com.netease.nim.uikit.business.contact.core.query.PinYin;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.mixpush.NIMPushClient;
import com.netease.nimlib.sdk.util.NIMUtil;



/**
 * Created by admin on 2018/1/3.
 */

public class MyApplation extends Application {
    public static Context context;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        /*初始化fresco*/
        initFresco();
        /*初始化网易云信*/
        initWYYX();

    }

    private void initWYYX() {
        DemoCache.setContext(this);
        // 注册小米推送appID 、appKey 以及在云信管理后台添加的小米推送证书名称，该逻辑放在 NIMClient init 之前
        NIMPushClient.registerMiPush(this, "DEMO_MI_PUSH", "2882303761517502883", "5671750254883");
        NIMClient.init(this, getLoginInfo(), NimSDKOptionConfig.getSDKOptions(this));
        // 以下逻辑只在主进程初始化时执行
        if (NIMUtil.isMainProcess(this)) {
            // 初始化红包模块，在初始化UIKit模块之前执行
//            NIMRedPacketClient.init(this);
            // init pinyin
            PinYin.init(this);
            PinYin.validate();
            // 初始化UIKit模块
            initUIKit();
            // 初始化消息提醒
            NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
            // 云信sdk相关业务初始化
            NIMInitManager.getInstance().init(true);
        }
    }

    private LoginInfo getLoginInfo() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }


    private void initUIKit() {
        // 初始化
        NimUIKit.init(this, buildUIKitOptions());

        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
        NimUIKit.setLocationProvider(new NimDemoLocationProvider());

        // IM 会话窗口的定制初始化。
        SessionHelper.init();

        // 聊天室聊天窗口的定制初始化。
//        ChatRoomSessionHelper.init();

        // 通讯录列表定制初始化
        ContactHelper.init();

        // 添加自定义推送文案以及选项，请开发者在各端（Android、IOS、PC、Web）消息发送时保持一致，以免出现通知不一致的情况
        //NimUIKit.setCustomPushContentProvider(new DemoPushContentProvider());

        NimUIKit.setOnlineStateContentProvider(new DemoOnlineStateContentProvider());
    }

    private UIKitOptions buildUIKitOptions() {
        UIKitOptions options = new UIKitOptions();
        // 设置app图片/音频/日志等缓存目录
        options.appCacheDir = NimSDKOptionConfig.getAppCacheDir(this) + "/app";
        return options;
    }

    private void initFresco() {
        Fresco.initialize(this);
    }
}