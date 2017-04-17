package com.ti;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Administrator on 2017/4/17/017.
 */

public class CommUtils {
    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param filePath
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    //drawable转成String
    public static synchronized String drawableToByte(Drawable drawable) {

        if (drawable != null) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            int size = bitmap.getWidth() * bitmap.getHeight() * 4;
            // 创建一个字节数组输出流,流的大小为size
            ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
            // 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            // 将字节数组输出流转化为字节数组byte[]
            byte[] imagedata = baos.toByteArray();

            String icon = Base64.encodeToString(imagedata, Base64.DEFAULT);
            return icon;
        }
        return null;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * get the width of the device screen
     *
     * @param context
     * @return
     */
    public static int getSceenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * get the height of the device screen
     *
     * @param context
     * @return
     */
    public static int getSceenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    //String 转化成Drawable
    public static synchronized Drawable byteToDrawable(String icon,Context context) {

        byte[] img = Base64.decode(icon.getBytes(), Base64.DEFAULT);
        Bitmap bitmap;
        if (img != null) {
            bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            @SuppressWarnings("deprecation")
            // 设置bitmap转成drawable后尺寸不变
                    DisplayMetrics metrics = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            Resources resources = new Resources(context.getAssets(), metrics, null);
            Drawable drawable = new BitmapDrawable(resources, bitmap);
            return drawable;
        }
        return null;

    }



}
