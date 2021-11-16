package com.maomao.learn.flyweight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/********************************************
 * 开发人员: 雪域青竹
 * 入职时间: 2016/05/16
 * 开发时间: 2021/7/29 15:39
 *********************************************/

class MyJpanel extends JFrame {
    PicJpanel pj;
    int map[][] = new int[16][16]; // 棋盘
    int flag = 2; // 1为白 2为黑 黑白交替默认为黑
    int winer = 0; //规定赢者

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public MyJpanel() {
        init();
    }

    private void init() {
        // 窗体的常用设置
        this.setSize(750, 780);// 设置窗体的大小，宽度和高度，单位是像素
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);// 设置窗体关闭时主线程自动关闭
        this.setLocationRelativeTo(null);// 设置窗体出现的位置在显示器正中间
        this.setResizable(false);// 设置窗体固定大小
        this.setLayout(null);// 不启用布局管理器，改为手动布局
        this.setTitle("画图");

        // 添加面板到窗体

        pj = new PicJpanel(this);
        pj.addMouseListener(new MyMouse(this));// 给面板添加一个鼠标监听事件
        this.add(pj);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new MyJpanel();
    }
}

class PicJpanel extends JPanel {
    MyJpanel mj;

    public PicJpanel(MyJpanel mj) {
        // 设定面板在窗体中的位置以及高度和宽度
        this.setBounds(0, 0, mj.getWidth(), mj.getHeight());
        this.mj = mj;
    }

    /**
     * 画组件
     */
    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage bi = new BufferedImage(100, 100, 2);
        g.drawImage(bi, 0, 0, this);

        int map[][] = mj.map;

        g.setColor(Color.BLACK);

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                g.drawRect(50 * i, 50 * j, 50, 50);
                // 1为白
                if (map[i][j] == 1) {
                    g.setColor(Color.WHITE);
                    g.fillOval(50 * j, 50 * i, 50, 50);
                }
                g.setColor(Color.black);
                // 2为黑
                if (map[i][j] == 2) {
                    g.setColor(Color.black);
                    g.fillOval(50 * j, 50 * i, 50, 50);
                }
            }
        }


        // g.setColor(Color.RED);//设置画笔为红色
        // g.drawRect(50, 50, 100, 100);//画一个矩形.前面两个参数决定位置,后两个参数决定大小
        // g.setColor(Color.BLUE);
        // g.fillRect(200,50, 100, 100);//画一个实心的矩形
        // g.drawOval(50, 200, 100, 100);
        // g.fillOval(200, 200, 100, 100);
    }

}

class MyMouse extends MouseAdapter {
    MyJpanel mj;

    public MyMouse(MyJpanel mj) {
        super();
        this.mj = mj;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        //重置1
        if (mj.winer != 0) {
            mj.setMap(new int[16][16]);
            mj.winer = 0;
            mj.pj.repaint();
            return;
        }
        int map[][] = mj.map;
        Point p = e.getPoint();// 获取鼠标点下的位置的坐标
        // 点击之后map值发生改变
        int x = (int) p.getX() / (750 / 15);
        int y = (int) p.getY() / (750 / 15);
        System.out.println("x=" + p.getX() + "   y=" + p.getY());
        System.out.println("x=" + x + "   y=" + y);

        // 改变棋子的颜色
        if (map[y][x] == 0) {
            map[y][x] = mj.flag;
        }

        // 改变顺序
        if (mj.flag == 1) {
            mj.flag = 2;
        } else if (mj.flag == 2) {
            mj.flag = 1;
        }


        //判断输赢
        MyMouse mouse = new MyMouse(mj);
        if (mouse.isWin(y, x, map)) {
            if (map[y][x] == 1) {
                mj.winer = 1;
                mj.pj.repaint();
                //弹出获胜信息
                JOptionPane.showMessageDialog(mj, "白子胜");
                //重置棋子颜色为黑棋
                mj.flag = 2;
            }
            if (map[y][x] == 2) {
                mj.winer = 2;
                mj.pj.repaint();
                JOptionPane.showMessageDialog(mj, "黑子胜");
                //重置棋子颜色为黑棋
                mj.flag = 2;
            }
        }

        //先调整数组 在绘图
        mj.setMap(map);
        mj.pj.repaint();


    }

    public boolean isWin(int x, int y, int map[][]) {
        // 对胜负判断
        // 4个方向 左右 上下 左斜 右斜
        // 对一个棋子的一个方向的10颗棋子进行判断 是否满足5子连续
        // 左右

        int num = 1;
        for (int i = 0; i < 14; i++) {
            if (map[x][i] != 0) {
                if (map[x][i] == map[x][i + 1]) {
                    num++;
                    if (num >= 5) {
                        System.out.println("win");
                        return true;
                    }
                } else {
                    num = 1;
                }
            }
        }

        // 上下
        num = 1;
        for (int i = 0; i < 14; i++) {
            if (map[i][y] != 0) {
                if (map[i][y] == map[i + 1][y]) {
                    num++;
                    if (num >= 5) {
                        System.out.println("win");
                        return true;
                    }
                } else {
                    num = 1;
                }
            }
        }

        num = 1;
        // 右斜 x-1 j+1
        for (int i = 0; i < map.length * 2 - 1; i++) {
            for (int j = 1; j < map.length; j++) {
                if (((i - j) >= 0) && ((i - j) < map.length)) {
                    if (map[j][i - j] != 0) {
                        if (map[j][i - j] == map[j - 1][i - j + 1]) {
                            num++;
                            if (num >= 5) {
                                System.out.println("win");
                                return true;
                            }
                        } else {
                            num = 1;
                        }
                    }
                }
            }
        }
        num = 1;
        //左斜 x+1 y+1
        for (int i = -map.length; i < map.length; i++) {
            for (int j = 1; j < map.length; j++) {
                if (((i + j) >= 0) && ((i + j) < map.length)) {
                    if (map[j][j + i] != 0) {
                        if (map[j][i + j] == map[j + 1][i + j + 1]) {
                            num++;
                            if (num >= 5) {
                                System.out.println("win");
                                return true;
                            }
                        } else {
                            num = 1;
                        }
                    }
                }
            }
        }
        return false;
    }
}
