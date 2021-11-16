package com.maomao.learn.principle.inversion.improve;

/********************************************
 * ������Ա: ѩ������
 * ��ְʱ��: 2016/05/16
 * ����ʱ��: 2021/7/15 20:05
 *********************************************/
abstract class SuperInversion{
    private InversionDemo demo;

    public void setDemo(InversionDemo demo) {
        this.demo = demo;
    }

    void show(){
        System.out.println("super change before...");
        this.demo.display();
        System.out.println("super change after...");
    }
}
public class InversionDemo extends SuperInversion{

    void display() {
        System.out.println("InversionDemo display...");
    }

    public static void main(String[] args) {
        SuperInversion inversion=new InversionDemo();
        inversion.setDemo(new InversionDemo());
        inversion.show();
    }
}
