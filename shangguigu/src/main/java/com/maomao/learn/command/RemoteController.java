package com.maomao.learn.command;

/********************************************
 * 开发人员: 雪域青竹
 * 入职时间: 2016/05/16
 * 开发时间: 2021/7/31 10:03
 *********************************************/
public class RemoteController {
    private Command undoCommand;
    private Command[] onCommands;
    private Command[] offCommands;

    public RemoteController(int num) {
        this.undoCommand = new NoCommand();
        this.onCommands = new Command[num];
        this.offCommands = new Command[num];
        for (int i = 0; i < num; i++) {
            this.offCommands[i] = new NoCommand();
            this.onCommands[i] = new NoCommand();
        }
    }

    public void setCommand(int no, Command onCommand, Command offCommand) {
        this.offCommands[no] = offCommand;
        this.onCommands[no] = onCommand;
    }

    public void onButtonPushed(int no){
        this.onCommands[no].execute();
        this.undoCommand=this.onCommands[no];
    }

    public void offButtonPushed(int no){
        this.offCommands[no].execute();
        this.undoCommand=this.offCommands[no];
    }

    public void undoButtonPushed(){
        this.undoCommand.undo();
    }

}
