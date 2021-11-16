package com.maomao.learn.flyweight;
import java.util.Random;
import java.util.Hashtable;

abstract class AbstractChessman {
    // ��������
    protected int x;
    protected int y;
    // ������𣨺�|�ף�
    protected String chess;

    public AbstractChessman(String chess) {
        this.chess = chess;
    }

    // ����������
    public abstract void point(int x, int y);

    // ��ʾ������Ϣ
    public void show() {
        System.out.println(this.chess + "(" + this.x + "," + this.y + ")");
    }
}

class WhiteChessman extends AbstractChessman {
    public WhiteChessman() {
        super("��");
        System.out.println("--WhiteChessman Construction Exec!!!");
    }

    @Override
    public void point(int x, int y) {
        this.x = x;
        this.y = y;
        show();
    }
}

class BlackChessman extends AbstractChessman {

    /**
     * ���췽�� ��ʼ��������
     */
    public BlackChessman() {
        super("��");
        System.out.println("--BlackChessman Construction Exec!!!");
    }

    // ����������
    @Override
    public void point(int x, int y) {
        this.x = x;
        this.y = y;
        // ��ʾ��������
        show();
    }
}

class FiveChessmanFactory {
    // ����ģʽ����
    private static FiveChessmanFactory fiveChessmanFactory = new FiveChessmanFactory();

    // �����Ź������
    private final Hashtable<Character, AbstractChessman> cache = new Hashtable<Character, AbstractChessman>();

    // ˽�л����췽��
    private FiveChessmanFactory() {
    }

    // ��õ�������
    public static FiveChessmanFactory getInstance() {
        return fiveChessmanFactory;
    }

    /**
     * �����ַ��������
     *
     * @param c ��B������ W�����壩
     * @return
     */
    public AbstractChessman getChessmanObject(char c) {
        // �ӻ����л�����Ӷ���ʵ��
        AbstractChessman abstractChessman = this.cache.get(c);
        if (abstractChessman == null) {
            // ������û�����Ӷ���ʵ����Ϣ �򴴽����Ӷ���ʵ�� �����뻺��
            switch (c) {
                case 'B':
                    abstractChessman = new BlackChessman();
                    break;
                case 'W':
                    abstractChessman = new WhiteChessman();
                    break;
                default:
                    break;
            }

            // Ϊ��ֹ �Ƿ��ַ��Ľ��� ����null
            if (abstractChessman != null) {
                // ���뻺��
                this.cache.put(c, abstractChessman);
            }
        }
        // ��������д��� ���Ӷ�����ֱ�ӷ���
        return abstractChessman;

    }
}

public class Client {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // ���������幤��
        FiveChessmanFactory fiveChessmanFactory = FiveChessmanFactory
                .getInstance();
        Random random = new Random();
        int radom = 0;
        AbstractChessman abstractChessman = null;
        // ����������
        for (int i = 0; i < 10; i++) {
            radom = random.nextInt(2);
            switch (radom) {
                // ��ú���
                case 0:
                    abstractChessman = fiveChessmanFactory.getChessmanObject('B');
                    break;
                // ��ð���
                case 1:
                    abstractChessman = fiveChessmanFactory.getChessmanObject('W');
                    break;
            }
            if (abstractChessman != null) {
                abstractChessman.point(i, random.nextInt(15));
            }
        }
    }
}
