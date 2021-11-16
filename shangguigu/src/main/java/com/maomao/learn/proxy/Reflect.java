package com.maomao.learn.proxy;

/********************************************
 * 开发人员: 雪域青竹
 * 入职时间: 2016/05/16
 * 开发时间: 2021/7/30 16:04
 *********************************************/
abstract class A{
    void show(){
        System.out.println(this.getClass()+"-->A");
        this.love();
    }

    abstract void love();
}

abstract class B extends A{
    @Override
    void love() {
        System.out.println("B--->love");
    }
}
public class Reflect extends B{

    @Override
    void love() {
        System.out.println("Reflect--->love");
    }

    public static void main(String[] args) {
        A a=new Reflect();
        a.show();
    }
}
