package com.maomao.learn.command;

/********************************************
 * ������Ա: ѩ������
 * ��ְʱ��: 2016/05/16
 * ����ʱ��: 2021/7/31 10:01
 *********************************************/
public class LightOnCommand implements Command{
    private LightRecevier recevier;

    public LightOnCommand(LightRecevier recevier) {
        this.recevier = recevier;
    }

    @Override
    public void execute() {
        recevier.on();
    }

    @Override
    public void undo() {
        recevier.off();
    }
}
