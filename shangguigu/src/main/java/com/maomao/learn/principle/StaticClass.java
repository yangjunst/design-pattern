package com.maomao.learn.principle;


/********************************************
 * ������Ա: ѩ������
 * ��ְʱ��: 2016/05/16
 * ����ʱ��: 2021/7/21 11:48
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
