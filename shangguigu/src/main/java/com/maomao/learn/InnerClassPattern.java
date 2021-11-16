package com.maomao.learn;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/********************************************
 * 开发人员: 雪域青竹
 * 入职时间: 2016/05/16
 * 开发时间: 2021/7/21 15:09
 *********************************************/
public class InnerClassPattern {
    public static void main(String[] args) throws Exception{
        Constructor<InnerClassPattern> declaredConstructor = null;
        declaredConstructor = InnerClassPattern.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        InnerClassPattern innerClassPattern = declaredConstructor.newInstance();

        System.out.println(innerClassPattern);
    }

    private static synchronized void printException(Exception e, int a) {
        System.out.println("-------------------------------------");
        System.out.println(a + " e---->" + e.getClass() + "---->" + e.getMessage());
        if (e.getCause() != null)
            System.out.println(a + " e.getCause()-->" + e.getCause().getClass() + "--" + e.getCause().getMessage());
        if (e.getCause().getCause() != null)
            System.out.println(a + " e.getCause().getCause() --->" + e.getCause().getCause().getClass() + "--" + e.getCause().getCause().getMessage());
    }

    private InnerClassPattern() {
        if (Inner.FLAG) {
            throw new IllegalStateException("无法通过构造函数破坏单例...");
        } else {
            Inner.FLAG = true;
            System.out.println("pk");
        }
    }

    private static final class Inner {
        private static volatile boolean FLAG = false;
        private static final InnerClassPattern PATTERN = new InnerClassPattern();

        static {
            System.out.println("init...");
            System.out.println(PATTERN);
        }

    }
}
