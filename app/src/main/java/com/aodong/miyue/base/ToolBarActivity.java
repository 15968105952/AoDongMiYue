package com.aodong.miyue.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aodong.miyue.R;


/**
 * Created by wangyc on 2017/9/12.
 */

public abstract class ToolBarActivity extends BaseActivity {

    public Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        btnBack = (Button) findViewById(R.id.back_btn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
