package com.maomao.learn.command;

/********************************************
 * ������Ա: ѩ������
 * ��ְʱ��: 2016/05/16
 * ����ʱ��: 2021/7/31 10:14
 *********************************************/
public class Client {
    public static void main(String[] args) {
        RemoteController controller=new RemoteController(5);
        LightRecevier recevier=new LightRecevier();
        LightOnCommand onCommand=new LightOnCommand(recevier);
        LightOffCommand offCommand=new LightOffCommand(recevier);
        controller.setCommand(0,onCommand,offCommand);
        controller.onButtonPushed(0);
        controller.offButtonPushed(0);
        controller.undoButtonPushed();

        System.out.println("...���ӻ�...");
        TVReceiver tvReceiver=new TVReceiver();
        TVOffCommand tvOffCommand=new TVOffCommand(tvReceiver);
        TVOnCommand tvOnCommand=new TVOnCommand(tvReceiver);
        controller.setCommand(1,tvOnCommand,tvOffCommand);
        controller.onButtonPushed(1);
        controller.offButtonPushed(1);
        controller.undoButtonPushed();
        controller.undoButtonPushed();

        controller.onButtonPushed(2);
    }
}
