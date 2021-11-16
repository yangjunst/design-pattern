package com.maomao.learn.composite.improve;

import java.util.ArrayList;
import java.io.File;

/********************************************
 * 开发人员: 雪域青竹
 * 入职时间: 2016/05/16
 * 开发时间: 2021/7/28 13:09
 *********************************************/
abstract class Node{
    protected String name;
    public Node(String name){
        this.name=name;
    }

    public void addNode(Node node){
        throw new UnsupportedOperationException();
    }

    abstract void display();
}

class FileNode extends Node{
    public FileNode(String name){
        super(name);
    }

    public void display(){
        System.out.println(name);
    }
}

class DirectNode extends Node{
    ArrayList<Node> nodeList=new ArrayList<>();
    public DirectNode(String name){
        super(name);
    }

    public void addNode(Node node){
        nodeList.add(node);
    }
    public void display(){
        System.out.println(name);
        for(int i=0;i<nodeList.size();i++){
            nodeList.get(i).display();
        }
    }
}


class Test{
    public static void createTree(Node node) {
        File f=new File(node.name);
        File[] f2=f.listFiles();
        for(int i=0;i<f2.length;i++){
            if(f2[i].isFile()){
                Node node2=new FileNode(f2[i].getAbsolutePath());
                node.addNode(node2);
            }

            if(f2[i].isDirectory()){
                Node node2=new DirectNode(f2[i].getAbsolutePath());
                node.addNode(node2);
                createTree(node2);
            }
        }
    }

    public static void main(String[] args) {
        Node start=new DirectNode("F:\\考研高顿");
        createTree(start);
        start.display();
    }
}