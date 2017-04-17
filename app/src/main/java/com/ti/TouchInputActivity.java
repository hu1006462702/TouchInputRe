package com.ti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.panavtec.drawableview.DrawableView;

/**
 * Created by Administrator on 2017/4/13/013.
 */

public class TouchInputActivity extends Activity implements View.OnClickListener {
    private EditText editText;
    private Context context;
    private float fontHeight;
    private Paint.FontMetrics fontMetrics;
    private String photoUrl;
    private List<TouchInputBean> list;
    private ImageView back;
    private TextView complete;
    private LinearLayout linearLayout;
    private String preString;
    private SharedPreferences pref;

    private final  int CHANGE_IMAGE =1000;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHANGE_IMAGE:

                    Intent in = new Intent();
                    in.putExtra("image", photoUrl);
                    SpannableStringBuilder s = SpannableStringBuilder.valueOf(editText.getText());
                    ImageSpan[] im = s.getSpans(0, s.length(), ImageSpan.class);
                    list.clear();
                    for (int i = 0; i < im.length; i++) {
                        TouchInputBean touchInputBean = new TouchInputBean(i, CommUtils.drawableToByte(im[i].getDrawable()));
                        list.add(touchInputBean);
                    }

                    Preference.putString(pref,preString, listToJsonObject(list));
                    setResult(RESULT_OK, in);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_input);
        context = TouchInputActivity.this;
        pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        editText = (EditText) findViewById(R.id.et);
        back = (ImageView) findViewById(R.id.iv_top_left);
        complete = (TextView) findViewById(R.id.tv_top_right);
        linearLayout = (LinearLayout) findViewById(R.id.ll_input_dialog);
        disableShowSoftInput();
        list = new ArrayList<>();
        editText.setOnClickListener(this);
        back.setOnClickListener(this);
        complete.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
        fontMetrics = editText.getPaint().getFontMetrics();
        fontHeight = fontMetrics.descent - fontMetrics.ascent;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) fontHeight * 5);
        params.setMargins(CommUtils.dip2px(context, 15), 0, CommUtils.dip2px(context, 15), 0);
        linearLayout.setLayoutParams(params);
        toshowText();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et:
                showMyDialog();
                editText.requestFocus();
                break;
            case R.id.iv_top_left:
                onBackPressed();
                break;
            case R.id.tv_top_right:
                drawBitmap();
                break;
            case R.id.ll_input_dialog:
                showMyDialog();
                break;
        }
    }

    private void showMyDialog() {
        DialogUtils.showTouchInputDialog(context, new DialogUtils.TouchInputOnClick() {
            @Override
            public void sure(DrawableView drawableView) {
                toSetText(drawableView);
            }

            @Override
            public void delete() {
                toDeleteText();
            }
        });
    }
        //防止弹出弹出框
    public void disableShowSoftInput() {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                // TODO: handle exception
            }

            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }


    private void toSetText(DrawableView drawableView) {
        Bitmap bitmap = drawableView.obtainBitmap();
        Drawable drawable = new BitmapDrawable(bitmap);
        setEditText(drawable);

        if (!bitmap.isRecycled())
            bitmap.recycle();
    }


    private void setEditText(Drawable text) {
        final int lll = editText.getSelectionStart();
        editText.setText(editText.getText().insert(lll, Html.fromHtml("<img src='" + CommUtils.drawableToByte(text) + "'/>", new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Drawable d = CommUtils.byteToDrawable(source, context);
//            d.setBounds(0, 0, d.getIntrinsicWidth(),d.getIntrinsicHeight());
                d.setBounds(0, 0, (int) fontHeight, (int) fontHeight);
                return d;
            }
        }, null)));
        editText.setSelection(lll + 1);
    }

    private void toDeleteText() {
        final int lll = editText.getSelectionStart();
        if (lll != 0) {
            if (!TextUtils.isEmpty(editText.getText().toString())) {
                editText.getText().delete(lll - 1, lll);
            }
        }
    }

    private void drawBitmap() {
        Bitmap bmp = null;
        if (editText.getLineCount() > 1) {
            bmp = Bitmap.createBitmap(editText.getWidth(), (int) fontHeight * editText.getLineCount() + editText.getPaddingTop(), Bitmap.Config.ARGB_8888);
        } else {
            bmp = Bitmap.createBitmap((int) fontHeight * (editText.getText().length()) + editText.getPaddingLeft() + editText.getPaddingRight(), (int) fontHeight + editText.getPaddingTop(), Bitmap.Config.ARGB_8888);
        }

        editText.draw(new Canvas(bmp));
        photoUrl = ConStant.IMAGE_PATH + "/" + System.currentTimeMillis() + ".jpg";//换成自己的图片保存路径
        final File file = new File(photoUrl);
        final Bitmap finalBmp = bmp;
        new Thread() {
            @Override
            public void run() {
                boolean bitMapOk = false;
                try {
                    bitMapOk = finalBmp.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (bitMapOk) {
                    mHandler.sendEmptyMessage(CHANGE_IMAGE);
                }
                if (!finalBmp.isRecycled())
                    finalBmp.recycle();
            }
        }.start();
    }


    private String listToJsonObject(List<TouchInputBean> list) {
        JSONArray jsA = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            String js = JSON.toJSONString(list.get(i));
            jsA.add(JSON.parseObject(js));
        }
        return jsA.toJSONString();
    }

    private void toshowText() {
        preString = getIntent().getStringExtra("image");
        String s = Preference.getString(pref,preString, "");
        if (!TextUtils.isEmpty(s)) {
            list = JSON.parseArray(s, TouchInputBean.class);
        }
        for (int i = 0; i < list.size(); i++) {
            setEditText(CommUtils.byteToDrawable(list.get(i).getData(), context));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
