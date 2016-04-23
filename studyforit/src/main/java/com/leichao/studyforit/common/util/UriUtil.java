package com.leichao.studyforit.common.util;

import android.content.Context;
import android.net.Uri;

import java.io.File;

/**
 * Uri相关的工具类
 * Created by leichao on 2016/4/20.
 */
public class UriUtil {

    public static Uri resourceIdtoUri(Context context, int resourceId) {
        return Uri.parse("android.resource://" + context.getPackageName() + File.separator + resourceId);
    }

}
