package com.leichao.studyforit.config;

import com.leichao.studyforit.appmode.tabhome.StudyApplication;

/**
 * 常用配置
 * Created by leichao on 2016/4/21.
 */
public class CommonConfig {

    /**
     * 应用存储所用的文件夹名称
     * 应用存储所用的文件夹的公有路径，在SDCard下(若无SDCard,则在私有路径下)
     * 应用存储所用的文件夹的私有路径，在私有路径下(data/data/包名/文件夹名)
     */
    public static final String STORAGE_NAME = "study";
    public static final String PUBLIC_DIR = StudyApplication.getInstance().getPublicDir().getAbsolutePath();
    public static final String PRIVATE_DIR = StudyApplication.getInstance().getPrivateDir().getAbsolutePath();

}
