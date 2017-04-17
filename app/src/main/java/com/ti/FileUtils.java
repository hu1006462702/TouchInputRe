package com.ti;

import android.os.Environment;


import java.io.File;

/**
 * Created by Administrator on 2017/4/12/012.
 */

public class FileUtils {
    public static String getExternalStorageState(String string) {
        String status = Environment.getExternalStorageState();
        File dir;
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            dir = new File(Environment.getExternalStorageDirectory() + "/" + string);
            if (!dir.exists()){
                dir.mkdirs();
            }
             return  dir.getPath();
        } else {
        }
        return "";
    }
}
