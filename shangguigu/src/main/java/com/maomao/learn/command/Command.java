package com.maomao.learn.command;

/********************************************
 * ������Ա: ѩ������
 * ��ְʱ��: 2016/05/16
 * ����ʱ��: 2021/7/31 9:59
 *********************************************/
public interface Command {
    void execute();
    void undo();
}
