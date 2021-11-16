package com.maomao.learn.principle;


/********************************************
 * 开发人员: 雪域青竹
 * 入职时间: 2016/05/16
 * 开发时间: 2021/7/21 11:48
 *********************************************/
class Static {
     static final class ClassDemo {
        static {
            System.out.println("static block class demo...");
        }
        ClassDemo(){
            System.out.println("ClassDemo Constructor...");
        }
        static final ClassDemo demo=null;
    }

    public static ClassDemo getDemo(){
        return ClassDemo.demo;
    }
}

public class StaticClass {
    public static void main(String[] args) {
        Static.ClassDemo demo = Static.ClassDemo.demo;

        System.out.println(demo);
    }
}
