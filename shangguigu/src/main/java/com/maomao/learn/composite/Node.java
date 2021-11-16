package com.maomao.learn.composite;
import java.io.File;

import java.util.ArrayList;
import java.util.List;

/********************************************
 * 开发人员: 雪域青竹
 * 入职时间: 2016/05/16
 * 开发时间: 2021/7/28 12:52
 *********************************************/
class FileLeaf {
    String  filename;
    public FileLeaf(String fileName){
        this.filename=fileName;
    }

    public void display(){
        System.out.println(filename);
    }
}


class DirectNode{
    String nodeName;
    public DirectNode(String nodeName){
        this.nodeName=nodeName;
    }

    List<DirectNode> nodeList=new ArrayList<>();
    List<FileLeaf> leafList=new ArrayList<>();
    public void addNode(DirectNode node){
        nodeList.add(node);
    }

    public void addLeaf(FileLeaf leaf){
        leafList.add(leaf);
    }

    public void display(){
        System.out.println(nodeName);
        for(int i=0;i<leafList.size();i++){
            leafList.get(i).display();
        }

        for (int i=0;i<nodeList.size();i++){
            System.out.println(nodeList.get(i).nodeName);
            nodeList.get(i).display();
        }
    }
}
class Test{
    public static void createTree(DirectNode node) {
        File f=new File(node.nodeName);
        File[] f2=f.listFiles();
        for(int i=0;i<f2.length;i++){
            if(f2[i].isFile()){
                FileLeaf l=new FileLeaf(f2[i].getAbsolutePath());
                node.addLeaf(l);
            }

            if(f2[i].isDirectory()){
                DirectNode node2=new DirectNode(f2[i].getAbsolutePath());
                node.addNode(node2);
                createTree(node2);
            }
        }
    }

    public static void main(String[] args) {
        DirectNode start=new DirectNode("F:\\考研高顿");
        createTree(start);
        start.display();
    }
}