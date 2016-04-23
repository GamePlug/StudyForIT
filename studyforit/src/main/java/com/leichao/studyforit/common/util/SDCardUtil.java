package com.leichao.studyforit.common.util;

import android.os.Environment;

/**
 * SDCard相关操作
 * Created by leichao on 2016/4/21.
 */
public class SDCardUtil {

    /**
     * 判断SDCard是否可用
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

}
