package com.jdkProxy.dao.impl;

import com.jdkProxy.dao.IStar;

public class ZhouJieLun implements IStar {
    @Override
    public void sing(double money) {
        System.out.println("唱歌去");
    }

    @Override
    public void dance(double money) {
        System.out.println("跳舞去");
    }
}
