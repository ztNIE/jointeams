package com.jointeams.backend;

public class SingletonKey {

    private String value;
    // 静态内部类
    private static class InnerClass{
        // 初始化实例
        private final static SingletonKey INSTANCE = new SingletonKey();
    }

    // 私有构造
    private SingletonKey() {
        value = "TestKey";
    }

    // 公关获取实例方法（线程安全，延迟加载）
//    public static SingletonKey getInstance() {
//        return InnerClass.INSTANCE;
//    }

    public static String getKeyValue() {
        return InnerClass.INSTANCE.value;
    }
}
