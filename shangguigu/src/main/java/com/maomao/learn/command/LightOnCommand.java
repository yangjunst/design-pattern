package com.maomao.learn.command;

/********************************************
 * 开发人员: 雪域青竹
 * 入职时间: 2016/05/16
 * 开发时间: 2021/7/31 10:01
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
