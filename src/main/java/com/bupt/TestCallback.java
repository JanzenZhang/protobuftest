package com.bupt;

/**
 * Created by 张 成 on 2017/8/6.
 */
interface TestCallback {

    String getName();

    byte[] writeObject(Object source);

    Object readObject(byte[] bytes);
}
