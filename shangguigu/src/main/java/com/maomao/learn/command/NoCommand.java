package com.maomao.learn.command;

/********************************************
 * ������Ա: ѩ������
 * ��ְʱ��: 2016/05/16
 * ����ʱ��: 2021/7/31 10:02
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
