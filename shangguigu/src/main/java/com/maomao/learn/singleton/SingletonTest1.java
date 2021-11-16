package com.maomao.learn.singleton;

/********************************************
 * ������Ա: ѩ������
 * ��ְʱ��: 2016/05/16
 * ����ʱ��: 2021/7/21 12:45
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
