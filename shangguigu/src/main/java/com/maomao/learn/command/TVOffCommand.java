package com.maomao.learn.command;

/********************************************
 * ������Ա: ѩ������
 * ��ְʱ��: 2016/05/16
 * ����ʱ��: 2021/7/31 10:11
 *********************************************/
public class TVOffCommand implements Command {
    private TVReceiver tv;
    public TVOffCommand(TVReceiver tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        tv.off();
    }

    @Override
    public void undo() {
        tv.on();
    }
}
