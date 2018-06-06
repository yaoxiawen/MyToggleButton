package com.yxw.mytogglebutton;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.yxw.mytogglebutton.view.TButton;

public class MainActivity extends Activity {
    private TButton tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tb = findViewById(R.id.tb);
        //设置滑动块的背景图片
        tb.setSlideBackgroundResource(R.drawable.slide_button);
        //设置滑动开关的背景图片
        tb.setSwitchBackgroundResource(R.drawable.switch_background);
        //设置开关的状态
        tb.setTBState(TButton.TBState.Open);
        //设置状态监听
        tb.setOnStateChangeListener(new TButton.OnStateChangeListener() {
            @Override
            public void onStateChange(TButton.TBState state) {
                if (state == TButton.TBState.Open){
                    Toast.makeText(MainActivity.this,"开启",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"关闭",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
