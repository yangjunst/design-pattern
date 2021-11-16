package com.maomao.learn.flyweight;
import java.util.Random;
import java.util.Hashtable;

abstract class AbstractChessman {
    // 棋子坐标
    protected int x;
    protected int y;
    // 棋子类别（黑|白）
    protected String chess;

    public AbstractChessman(String chess) {
        this.chess = chess;
    }

    // 点坐标设置
    public abstract void point(int x, int y);

    // 显示棋子信息
    public void show() {
        System.out.println(this.chess + "(" + this.x + "," + this.y + ")");
    }
}

class WhiteChessman extends AbstractChessman {
    public WhiteChessman() {
        super("○");
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
     * 构造方法 初始化黑棋子
     */
    public BlackChessman() {
        super("●");
        System.out.println("--BlackChessman Construction Exec!!!");
    }

    // 点坐标设置
    @Override
    public void point(int x, int y) {
        this.x = x;
        this.y = y;
        // 显示棋子内容
        show();
    }
}

class FiveChessmanFactory {
    // 单例模式工厂
    private static FiveChessmanFactory fiveChessmanFactory = new FiveChessmanFactory();

    // 缓存存放共享对象
    private final Hashtable<Character, AbstractChessman> cache = new Hashtable<Character, AbstractChessman>();

    // 私有化构造方法
    private FiveChessmanFactory() {
    }

    // 获得单例工厂
    public static FiveChessmanFactory getInstance() {
        return fiveChessmanFactory;
    }

    /**
     * 根据字符获得棋子
     *
     * @param c （B：黑棋 W：白棋）
     * @return
     */
    public AbstractChessman getChessmanObject(char c) {
        // 从缓存中获得棋子对象实例
        AbstractChessman abstractChessman = this.cache.get(c);
        if (abstractChessman == null) {
            // 缓存中没有棋子对象实例信息 则创建棋子对象实例 并放入缓存
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

            // 为防止 非法字符的进入 返回null
            if (abstractChessman != null) {
                // 放入缓存
                this.cache.put(c, abstractChessman);
            }
        }
        // 如果缓存中存在 棋子对象则直接返回
        return abstractChessman;

    }
}

public class Client {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // 创建五子棋工厂
        FiveChessmanFactory fiveChessmanFactory = FiveChessmanFactory
                .getInstance();
        Random random = new Random();
        int radom = 0;
        AbstractChessman abstractChessman = null;
        // 随机获得棋子
        for (int i = 0; i < 10; i++) {
            radom = random.nextInt(2);
            switch (radom) {
                // 获得黑棋
                case 0:
                    abstractChessman = fiveChessmanFactory.getChessmanObject('B');
                    break;
                // 获得白棋
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
