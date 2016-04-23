package com.leichao.studyforit.config;

import java.io.File;

/**
 * Debug相关配置
 * Created by Huaner on 2016/4/22.
 */
public class DebugConfig {

    /**
     * Debug日志打印开关
     * Debug日志保存目录
     * Debug日志保存天数
     * Debug日志文件名称
     */
    public static final boolean DEBUG_ENABLE = true;
    public static final String DEBUG_SAVE_DIR = CommonConfig.PUBLIC_DIR + File.separator + "debug";
    public static final int DEBUG_SAVE_DAYS = 2;
    public static final String DEBUG_SAVE_NAME = "log";

    /**
     * Crash日志捕获开关
     * Crash日志保存目录
     * Crash日志保存天数
     * Crash日志文件名称
     */
    public static final boolean CRASH_CATCH = false;
    public static final String CRASH_SAVE_DIR = CommonConfig.PUBLIC_DIR + File.separator + "crash";
    public static final int CRASH_SAVE_DAYS = 2;
    public static final String CRASH_SAVE_NAME = "crash";

}
