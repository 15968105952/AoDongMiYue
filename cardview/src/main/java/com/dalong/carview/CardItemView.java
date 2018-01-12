package com.dalong.carview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 卡片View项
 *
 * @author xmuSistone
 */
@SuppressLint("NewApi")
public class CardItemView extends LinearLayout {

ImageView cardimageview;//背景图片
ImageView ivsex;//性别图片
ImageView headImage;//用户头像
TextView tvnickname;//用户昵称
    TextView tvage;//用户名字
    TextView tv1;//标签1
    TextView tv2;//标签2
    TextView tv3;//标签3
    public CardItemView(Context context) {
        this(context, null);
    }

    public CardItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.activity_kuai_yue_item, this);
        cardimageview = (ImageView) findViewById(R.id.card_image_view);
        ivsex = (ImageView) findViewById(R.id.iv_sex);
        headImage = (ImageView) findViewById(R.id.headImage);
        tvnickname = (TextView) findViewById(R.id.tv_nick_name);
        tvage = (TextView) findViewById(R.id.tv_age);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
      /*  imageView = (ImageView) findViewById(R.id.card_image_view);
        userNameTv = (TextView) findViewById(R.id.card_user_name);
        imageNumTv = (TextView) findViewById(R.id.card_pic_num);
        likeNumTv = (TextView) findViewById(R.id.card_like);
        likeIcon = (ImageView) findViewById(R.id.card_like_icon);
        dislikeIcon = (ImageView) findViewById(R.id.card_dislike_icon);*/
    }

    public void fillData(CardDataItem itemData) {
        if(null!=itemData){
            tvnickname.setText(itemData.userName);
        }
      /*  ImageLoader.getInstance().displayImage(itemData.imagePath, imageView);
        userNameTv.setText(itemData.userName);
        imageNumTv.setText(itemData.imageNum + "");
        likeNumTv.setText(itemData.likeNum + "");*/
    }


}
