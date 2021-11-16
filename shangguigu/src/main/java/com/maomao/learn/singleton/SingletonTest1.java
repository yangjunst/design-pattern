package com.maomao.learn.singleton;

/********************************************
 * 开发人员: 雪域青竹
 * 入职时间: 2016/05/16
 * 开发时间: 2021/7/21 12:45
 *********************************************/
public class SingletonTest1 {
    static class Singleton {
        private Singleton() {
        }

        private static final Singleton singleton;
        private static final Singleton s = new Singleton();

        static {
            singleton = new Singleton();
        }

        public static Singleton getS() {
            return s;
        }

        public static Singleton getSingleton() {
            return singleton;
        }
    }
}
