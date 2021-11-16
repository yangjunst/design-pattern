package com.maomao.learn.flyweight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/********************************************
 * ������Ա: ѩ������
 * ��ְʱ��: 2016/05/16
 * ����ʱ��: 2021/7/29 15:39
 *********************************************/

class MyJpanel extends JFrame {
    PicJpanel pj;
    int map[][] = new int[16][16]; // ����
    int flag = 2; // 1Ϊ�� 2Ϊ�� �ڰ׽���Ĭ��Ϊ��
    int winer = 0; //�涨Ӯ��

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
        // ����ĳ�������
        this.setSize(750, 780);// ���ô���Ĵ�С����Ⱥ͸߶ȣ���λ������
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);// ���ô���ر�ʱ���߳��Զ��ر�
        this.setLocationRelativeTo(null);// ���ô�����ֵ�λ������ʾ�����м�
        this.setResizable(false);// ���ô���̶���С
        this.setLayout(null);// �����ò��ֹ���������Ϊ�ֶ�����
        this.setTitle("��ͼ");

        // �����嵽����

        pj = new PicJpanel(this);
        pj.addMouseListener(new MyMouse(this));// ��������һ���������¼�
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
        // �趨����ڴ����е�λ���Լ��߶ȺͿ��
        this.setBounds(0, 0, mj.getWidth(), mj.getHeight());
        this.mj = mj;
    }

    /**
     * �����
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
                // 1Ϊ��
                if (map[i][j] == 1) {
                    g.setColor(Color.WHITE);
                    g.fillOval(50 * j, 50 * i, 50, 50);
                }
                g.setColor(Color.black);
                // 2Ϊ��
                if (map[i][j] == 2) {
                    g.setColor(Color.black);
                    g.fillOval(50 * j, 50 * i, 50, 50);
                }
            }
        }


        // g.setColor(Color.RED);//���û���Ϊ��ɫ
        // g.drawRect(50, 50, 100, 100);//��һ������.ǰ��������������λ��,����������������С
        // g.setColor(Color.BLUE);
        // g.fillRect(200,50, 100, 100);//��һ��ʵ�ĵľ���
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

        //����1
        if (mj.winer != 0) {
            mj.setMap(new int[16][16]);
            mj.winer = 0;
            mj.pj.repaint();
            return;
        }
        int map[][] = mj.map;
        Point p = e.getPoint();// ��ȡ�����µ�λ�õ�����
        // ���֮��mapֵ�����ı�
        int x = (int) p.getX() / (750 / 15);
        int y = (int) p.getY() / (750 / 15);
        System.out.println("x=" + p.getX() + "   y=" + p.getY());
        System.out.println("x=" + x + "   y=" + y);

        // �ı����ӵ���ɫ
        if (map[y][x] == 0) {
            map[y][x] = mj.flag;
        }

        // �ı�˳��
        if (mj.flag == 1) {
            mj.flag = 2;
        } else if (mj.flag == 2) {
            mj.flag = 1;
        }


        //�ж���Ӯ
        MyMouse mouse = new MyMouse(mj);
        if (mouse.isWin(y, x, map)) {
            if (map[y][x] == 1) {
                mj.winer = 1;
                mj.pj.repaint();
                //������ʤ��Ϣ
                JOptionPane.showMessageDialog(mj, "����ʤ");
                //����������ɫΪ����
                mj.flag = 2;
            }
            if (map[y][x] == 2) {
                mj.winer = 2;
                mj.pj.repaint();
                JOptionPane.showMessageDialog(mj, "����ʤ");
                //����������ɫΪ����
                mj.flag = 2;
            }
        }

        //�ȵ������� �ڻ�ͼ
        mj.setMap(map);
        mj.pj.repaint();


    }

    public boolean isWin(int x, int y, int map[][]) {
        // ��ʤ���ж�
        // 4������ ���� ���� ��б ��б
        // ��һ�����ӵ�һ�������10�����ӽ����ж� �Ƿ�����5������
        // ����

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

        // ����
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
        // ��б x-1 j+1
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
        //��б x+1 y+1
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
