package com.maomao.learn.adpater;

/********************************************
 * ������Ա: ѩ������
 * ��ְʱ��: 2016/05/16
 * ����ʱ��: 2021/7/26 14:48
 *********************************************/
interface Interface{
    void m1();
    void m2();
    void m3();
    void m4();
}
public  abstract class InterfaceAdapter implements Interface{

    @Override
    public void m1() {

    }

    @Override
    public void m2() {

    }

    @Override
    public void m3() {

    }

    @Override
    public void m4() {
        System.out.println("m4");
    }


    public static void main(String[] args) {
        new InterfaceAdapter() {
            @Override
            public void m1() {
                System.out.println("m1");
            }

            public void mm() {
                System.out.println("mm");
            }
        }.mm();
    }
}
