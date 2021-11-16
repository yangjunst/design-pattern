package com.maomao.learn.command;

/********************************************
 * ������Ա: ѩ������
 * ��ְʱ��: 2016/05/16
 * ����ʱ��: 2021/7/31 10:02
 *********************************************/
public class LightOffCommand implements Command {
    private LightRecevier recevier;

    public LightOffCommand(LightRecevier recevier) {
        this.recevier = recevier;
    }

    @Override
    public void execute() {
        recevier.off();
    }

    @Override
    public void undo() {
        recevier.on();
    }
}
