package com.maomao.learn.principle.segregation.improve;

/********************************************
 * 开发人员: 雪域青竹
 * 入职时间: 2016/05/16
 * 开发时间: 2021/7/15 18:19
 *********************************************/
public class SegregationDemo {
    public static void main(String[] args) {
        A a=new A();
        a.depend1(new B());
        a.depend2(new B());
        a.depend3(new B());
        C c=new C();
        c.depend1(new D());
        c.depend4(new D());
        c.depend5(new D());
    }
}

class C{
    void depend1(Interface1 interface1){
        interface1.operation1();
    }
    void depend4(Interface3 interface3){
        interface3.operation4();
    }
    void depend5(Interface3 interface3){
        interface3.operation5();
    }
}
class A{
    void depend1(Interface1 interface1){
        interface1.operation1();
    }
    void depend2(Interface2 interface2){
        interface2.operation2();
    }
    void depend3(Interface2 interface2){
        interface2.operation3();
    }
}
class D implements Interface1,Interface3{

    @Override
    public void operation1() {
        System.out.println("D 实现了 operation1");
    }

    @Override
    public void operation4() {
        System.out.println("D 实现了 operation4");
    }

    @Override
    public void operation5() {
        System.out.println("D 实现了 operation5");
    }
}
class B implements Interface1,Interface2{

    @Override
    public void operation1() {
        System.out.println("B 实现了 operation1");
    }

    @Override
    public void operation2() {
        System.out.println("B 实现了 operation2");
    }

    @Override
    public void operation3() {
        System.out.println("B 实现了 operation3");
    }
}

interface Interface1{
    void operation1();
}

interface Interface2{
    void operation2();
    void operation3();
}

interface Interface3{
    void operation4();
    void operation5();
}