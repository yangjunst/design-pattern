package com.maomao.learn.command;

/********************************************
 * 开发人员: 雪域青竹
 * 入职时间: 2016/05/16
 * 开发时间: 2021/7/31 10:02
 *********************************************/
public class NoCommand implements Command{
    @Override
    public void execute() {
        System.out.println("unCommand");
    }

    @Override
    public void undo() {

    }
}
