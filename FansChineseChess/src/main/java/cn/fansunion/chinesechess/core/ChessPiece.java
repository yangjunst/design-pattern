/**
 * ��Ŀ����: FansChineseChess
 * �汾�ţ�1.0
 * ���֣�����
 * ����: http://FansUnion.cn
 * ����: leiwen@FansUnion.cn
 * QQ��240-370-818
 * ��Ȩ:ͨ��Email��QQ������֪ͨ�Һ���ӵ��һ��Ȩ���������޸�-���·����ȡ� 
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
 * ������
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class ChessPiece extends JLabel implements Serializable {

	private static final long serialVersionUID = 263L;

	private String category;// ���ӵ����

	private Color backColor = null;// ����ɫ

	private Color foreColor;// ǰ��ɫ

	private String name = null;// ������췽

	private int width;// ���

	private int height;// �߶�

	private int id;// ID

	private Point location;// ���ӵ�����

	/** ���캯������ʼ�����ӵ���Ϣ */
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

		// ����¼������������
		addMouseMotionListener(board);
		addMouseListener(board);

	}

	/**
	 * ��������
	 */
	public void paint(Graphics g) {
		// ��ָ������ɫ����������ڵ�����
		g.setColor(backColor);
		g.fillOval(2, 2, width - 1, height - 1);

		// ��ָ�����������ɫ�������ӵ�����
		g.setColor(foreColor);
		g.setFont(new Font("����", Font.PLAIN, 28));
		g.drawString(category, 8, height - 10);

		// ��ָ������ɫ����Բ
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
	 * �������ӵ�����(܇�������ڵ�)
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
	 * �����������ڷ�������
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
