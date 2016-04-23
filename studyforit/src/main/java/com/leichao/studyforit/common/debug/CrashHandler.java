package com.leichao.studyforit.common.debug;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;

import com.leichao.studyforit.common.util.FileUtil;
import com.leichao.studyforit.config.DebugConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 程序异常崩溃捕捉处理类
 */
public class CrashHandler implements UncaughtExceptionHandler {

    // 日志打印开关
    private static final boolean ENABLE = DebugConfig.CRASH_CATCH;

    // 日志保存目录
    private static final String SAVE_DIR = DebugConfig.CRASH_SAVE_DIR;

    // 日志保存天数
    private static final int SAVE_DAYS = DebugConfig.CRASH_SAVE_DAYS;

    // 日志文件名称
    private static final String SAVE_NAME = DebugConfig.CRASH_SAVE_NAME;

    private static CrashHandler instance;

    private Context mContext;
    private UncaughtExceptionHandler mDefaultHandler;
    private Map<String, String> infos = new HashMap<>();

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    public void init(Context context) {
        if (ENABLE) {
            mContext = context;
            mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 处理异常
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        collectDeviceInfo(mContext);
        saveCrashInfo2File(ex);
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                restartApp(mContext);
                Looper.loop();
            }
        }.start();
        return true;
    }

    /**
     * 搜集手机设备信息
     */
    private void collectDeviceInfo(Context context) {
        try {
            PackageInfo pi = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                infos.put("versionName", pi.versionName + "");
                infos.put("versionCode", pi.versionCode + "");
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存异常信息以文件形式到Sdcard
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            String nowData = getDateFormat().format(new Date());
            String nowTime = getTimeFormat().format(new Date());
            long timestamp = System.currentTimeMillis();
            String fileName = nowData + "_" + nowTime + "_" + timestamp + "_" + SAVE_NAME;
            File dir = new File(SAVE_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(SAVE_DIR + File.separator + fileName);
            fos.write(sb.toString().getBytes());
            fos.close();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重启App
     */
    private static void restartApp(Context context) {
        /*Intent i = context.getPackageManager().getLaunchIntentForPackage(
                context.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);*/

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(
                context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    public static void delAllLog() {
        if (ENABLE) {
            File dir = new File(SAVE_DIR);
            if (dir.exists() && dir.isDirectory()) {
                FileUtil.delete(dir);
            }
        }
    }

    public static void delTimeoutLog() {
        if (ENABLE) {
            File dir = new File(SAVE_DIR);
            if (dir.exists() && dir.isDirectory()) {
                for(File file : dir.listFiles()) {
                    if (needDel(file.getName())) {
                        FileUtil.delete(file);
                    }
                }
            }
        }
    }

    private static boolean needDel(String fileName) {
        try {
            SimpleDateFormat dateFormat = getDateFormat();
            String nowTime = dateFormat.format(new Date());
            String fileTime = fileName.split("_")[0];
            //得到指定模范的时间
            Date d1  = dateFormat.parse(nowTime);
            Date d2 = dateFormat.parse(fileTime);
            //比较
            return Math.abs(((d1.getTime() - d2.getTime())/(24*3600*1000))) > SAVE_DAYS;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //命名格式不对的log文件也需删除
        return true;
    }

    private static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    private SimpleDateFormat getTimeFormat() {
        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    }
}