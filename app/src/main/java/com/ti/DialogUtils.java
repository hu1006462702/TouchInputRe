package com.ti;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import me.panavtec.drawableview.DrawableView;
import me.panavtec.drawableview.DrawableViewConfig;

/**
 * Created by Administrator on 2017/4/17/017.
 */

public class DialogUtils {
    public static Dialog showTouchInputDialog(Context mContext, final TouchInputOnClick touchInputOnClick) {
        final Dialog dialog = new Dialog(mContext, R.style.tlh_touch_input_dialog);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = (View) inflater.inflate(R.layout.touch_input_dialog, null);
        // 内容
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        TextView text2 = (TextView) view.findViewById(R.id.text2);
        TextView text3 = (TextView) view .findViewById(R.id.text3);
        final DrawableView paintView=(DrawableView)view.findViewById(R.id.paintView);
        DrawableViewConfig config= new DrawableViewConfig();
        // 画笔颜色
        config.setStrokeColor(Color.BLACK);

        // 画布边界
        config.setShowCanvasBounds(false);

        // 设置画笔宽度
        config.setStrokeWidth(50.0f);

        // 缩放
        config.setMinZoom(1.0f);
        config.setMaxZoom(1.0f);

        // 画布宽和高
        config.setCanvasHeight(CommUtils.getSceenWidth(mContext)- CommUtils.dip2px(mContext,30));
        config.setCanvasWidth(CommUtils.getSceenWidth(mContext)- CommUtils.dip2px(mContext,30));
        paintView.setConfig(config);
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.clear();
            }
        });
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touchInputOnClick.sure(paintView);
                paintView.clear();
            }
        });
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touchInputOnClick.delete();
            }
        });
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.height = (int) (CommUtils.getSceenWidth(mContext));
        lp.width = (int) (CommUtils.getSceenWidth(mContext) );
        dialogWindow.setGravity( Gravity.BOTTOM);
        dialogWindow.setAttributes(lp);
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
        dialog.show();
        return dialog;
    }
    public interface TouchInputOnClick {
        void sure(DrawableView drawableView);
        void delete();
    }
}
