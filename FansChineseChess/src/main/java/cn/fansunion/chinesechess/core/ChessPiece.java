/**
 * 项目名称: FansChineseChess
 * 版本号：1.0
 * 名字：雷文
 * 博客: http://FansUnion.cn
 * 邮箱: leiwen@FansUnion.cn
 * QQ：240-370-818
 * 版权:通过Email和QQ等渠道通知我后，则拥有一切权力，包括修改-重新发布等。 
 * 
 */
package cn.fansunion.chinesechess.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

import javax.swing.JLabel;

/**
 * 象棋子
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class ChessPiece extends JLabel implements Serializable {

	private static final long serialVersionUID = 263L;

	private String category;// 棋子的类别

	private Color backColor = null;// 背景色

	private Color foreColor;// 前景色

	private String name = null;// 蓝方或红方

	private int width;// 宽度

	private int height;// 高度

	private int id;// ID

	private Point location;// 棋子的坐标

	/** 构造函数，初始化棋子的信息 */
	public ChessPiece(int id, String name, String category, Color fc, Color bc,
			int width, int height, ChessBoard board) {
		this.id = id;
		this.category = category;

		this.width = width;
		this.height = height;
		this.name = name;
		foreColor = fc;
		backColor = bc;

		setSize(width, height);
		setBackground(bc);

		// 鼠标事件由棋盘类监听
		addMouseMotionListener(board);
		addMouseListener(board);

	}

	/**
	 * 绘制棋子
	 */
	public void paint(Graphics g) {
		// 用指定的颜色填充棋子所在的区域
		g.setColor(backColor);
		g.fillOval(2, 2, width - 1, height - 1);

		// 用指定的字体和颜色绘制棋子的名字
		g.setColor(foreColor);
		g.setFont(new Font("隶书", Font.PLAIN, 28));
		g.drawString(category, 8, height - 10);

		// 用指定的颜色绘制圆
		g.setColor(Color.yellow);
		g.drawOval(2, 2, width - 2, height - 2);

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * 返回棋子的种类(車、兵、炮等)
	 * 
	 * @return
	 */
	public String getCategory() {
		return category;
	}

	public Color getForeColor() {
		return foreColor;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回棋子所在方的名字
	 */
	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
}
