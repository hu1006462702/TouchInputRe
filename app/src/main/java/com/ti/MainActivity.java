package com.ti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener {
    private TextView iv1,iv2,iv3,iv4;
    private Button btn1,btn2,btn3,btn4;
    private Context context;
    private final int TOUCH_INPUT = 2001;
    private int  number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;
        iv1=(TextView)findViewById(R.id.iv1);
        iv2=(TextView)findViewById(R.id.iv2);
        iv3=(TextView)findViewById(R.id.iv3);
        iv4=(TextView)findViewById(R.id.iv4);
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);
        btn4=(Button)findViewById(R.id.btn4);

        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                number=1;
                startTouchInputActivity("1");

                break;
            case R.id.btn2:
                number=2;
                startTouchInputActivity("2");

                break;
            case R.id.btn3:
                number=3;
                startTouchInputActivity("3");
                break;
            case R.id.btn4:
                number=4;
                startTouchInputActivity("4");
                break;
        }
    }

    private void startTouchInputActivity(String s) {
        Intent intent =new Intent(context,TouchInputActivity.class);
        intent.putExtra("image",s);
        startActivityForResult(intent, TOUCH_INPUT);
    }

    /**
     * 返回应用时回调方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//对应第二种方法
            switch (requestCode) {
                case TOUCH_INPUT:
                   final String imagePath = data.getStringExtra("image");
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            switch (number){
                                case 1:
                                    iv1.setText(imagePath);
                                    break;
                                case 2:
                                    iv2.setText(imagePath);
                                    break;
                                case 3:
                                    iv3.setText(imagePath);
                                    break;
                                case 4:
                                    iv4.setText(imagePath);
                                    break;
                            }
                        }
                    });

                    break;
            }
        }
    }

}
