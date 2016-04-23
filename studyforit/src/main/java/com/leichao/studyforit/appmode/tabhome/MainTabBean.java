package com.leichao.studyforit.appmode.tabhome;

import android.support.v4.app.Fragment;

/**
 *
 * Created by leichao on 2016/4/19.
 */
public class MainTabBean {

    Fragment fragment;
    int icoResource;
    int textResource;

    public MainTabBean(Fragment fragment, int icoResource, int textResource) {
        this.fragment = fragment;
        this.icoResource = icoResource;
        this.textResource = textResource;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getIcoResource() {
        return icoResource;
    }

    public void setIcoResource(int icoResource) {
        this.icoResource = icoResource;
    }

    public int getTextResource() {
        return textResource;
    }

    public void setTextResource(int textResource) {
        this.textResource = textResource;
    }
}
