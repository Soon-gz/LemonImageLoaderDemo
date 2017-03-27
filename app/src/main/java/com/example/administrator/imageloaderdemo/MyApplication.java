package com.example.administrator.imageloaderdemo;

import android.app.Application;

import com.example.lemonimagelibrary.config.GlobalConfig;

/**
 * Created by ShuWen on 2017/3/27.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //使用默认的初始化全局队列
        GlobalConfig.initDefault();
        //设置当前手机并发的线程数
//        GlobalConfig.init(3);

    }
}
