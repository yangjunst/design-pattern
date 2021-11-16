package com.maomao.learn.composite.test;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
/********************************************
 * 开发人员: 雪域青竹
 * 入职时间: 2016/05/16
 * 开发时间: 2021/7/28 13:38
 *********************************************/
abstract class Node {
    protected String name;

    public Node(String name) {
        this.name = name;
    }

    public void add(Node node) {
        throw new UnsupportedOperationException();
    }

    abstract void display();

}

class Leaf extends Node {

    public Leaf(String name) {
        super(name);
    }

    @Override
    public void display() {
        System.out.println(name);
    }
}

class Direct extends Node {
    private List<Node> nodes = new ArrayList<>();

    public Direct(String name) {
        super(name);
    }

    @Override
    public void add(Node node) {
        nodes.add(node);
    }

    @Override
    void display() {
        System.out.println(name);
        for(Node node:nodes){
            node.display();
        }
    }


}

public class Test {
    public static void createTree(Node node) {
        File file=new File(node.name);
        File[] files=file.listFiles();
        for(File f:files){
            if(f.isFile()){
                node.add(new Leaf(f.getAbsolutePath()));
            }
            if(f.isDirectory()){
                Direct direct = new Direct(f.getAbsolutePath());
                node.add(direct);
                createTree(direct);
            }
        }
    }

    public static void main(String[] args) {
        Direct direct = new Direct("F:\\考研高顿");
        createTree(direct);
        direct.display();

    }
}
