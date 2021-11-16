package com.maomao.learn.prototype;

/********************************************
 * 开发人员: 雪域青竹
 * 入职时间: 2016/05/16
 * 开发时间: 2021/7/24 22:20
 *********************************************/
public class PrototypeDemo implements Cloneable{

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Integer a=1;
    private String s="str";

    public PrototypeDemo(Integer a, String s) {
        this.a = a;
        this.s = s;

    }

    @Override
    public String toString() {
        return "PrototypeDemo{" +
                "a=" + a +
                ", s='" + s + '\'' +
                '}';
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        PrototypeDemo demo=new PrototypeDemo(11,"A");
        PrototypeDemo demo1= (PrototypeDemo) demo.clone();
        demo.setA(2);
        System.out.println(demo1.getA());
    }
}
