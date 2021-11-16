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
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import cn.fansunion.chinesechess.ui.MakeChessManual;
import cn.fansunion.chinesechess.util.Constants;
import cn.fansunion.chinesechess.util.DataPacket;
import cn.fansunion.chinesechess.util.ChessUtils;
import cn.fansunion.chinesechess.util.Message;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * 棋盘类
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class ChessBoard extends JPanel implements MouseListener,
		MouseMotionListener, Constants {

	private static final long serialVersionUID = 1L;

	private JTextArea text;

	private Socket socket;

	private ObjectInputStream fromServer;

	private ObjectOutputStream toServer;

	private ObjectInputStream fromPlayer1;

	private ObjectInputStream fromPlayer2;

	private ObjectOutputStream toPlayer1;

	private ObjectOutputStream toPlayer2;

	public ChessPoint gamePoints[][];

	private DataPacket packet;

	private JLabel gameStatus;

	public int player;// 标识玩家，1还是2

	private ServerSocket serverSocket;// 玩家1服务器端

	private Socket player1Socket;// 玩家1充当客户端

	private Socket player2Socket;// 玩家2充当客户端

	public int unitWidth, unitHeight;

	public boolean blueEnter = false;

	int x轴长, y轴长;

	// int x, y;

	boolean move = false;

	ChessPiece 红车1, 红车2, 红马1, 红马2, 红相1, 红相2, 红帅, 红士1, 红士2, 红兵1, 红兵2, 红兵3, 红兵4,
			红兵5, 红炮1, 红炮2;

	ChessPiece 蓝车1, 蓝车2, 蓝马1, 蓝马2, 蓝象1, 蓝象2, 蓝将, 蓝士1, 蓝士2, 蓝卒1, 蓝卒2, 蓝卒3, 蓝卒4,
			蓝卒5, 蓝炮1, 蓝炮2;

	private int startX, startY;

	private int startI, startJ;

	private GameRule gameRule = null;

	public MakeChessManual records = null;

	// 主机名字或IP地址
	private String ipAddress;

	// 游戏是否就绪
	public int running = RUNNING;

	// 蓝方是否已经准备
	public boolean blueOk = false;

	// 我是否暂停了游戏
	public boolean isPause = false;

	// 对方是否暂停了游戏
	public boolean otherIsPause = false;

	private JTextArea msgArea;

	private String playerName;// 战斗方的名字，红方or蓝方

	private String petName;// 用户的名字，fans or farmer

	public boolean myTurn = false;

	public ChessBoard(int r, int c) {

		setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this);

		unitWidth = PIECE_WIDTH;
		unitHeight = PIECE_HEIGHT;
		x轴长 = r;
		y轴长 = c;

		gamePoints = new ChessPoint[r + 1][c + 1];

		for (int i = 1; i <= r; i++) {
			for (int j = 1; j <= c; j++) {
				gamePoints[i][j] = new ChessPoint(i * unitWidth,
						j * unitHeight, false);
			}
		}

		gameRule = new GameRule(this, gamePoints);
		records = new MakeChessManual(this, gamePoints);
		text = records.getText();
	}

	public void work() {
		// 在一个单独的线程中控制游戏
		Thread thread = new Thread(new TestThread());
		thread.start();
	}

	/**
	 * 初始化红方、蓝方棋子的宽度，高度和前景色
	 * 
	 * @param width
	 * @param height
	 * @param bc
	 */
	public void initChess(int width, int height, Color bc) {
		Color redColor = Color.red;
		Color blueColor = Color.blue;
		if (player == 1) {
			红车1 = new ChessPiece(1, RED_NAME, "車", redColor, bc, width - 4,
					height - 4, this);
			红车2 = new ChessPiece(2, RED_NAME, "車", redColor, bc, width - 4,
					height - 4, this);
			红马1 = new ChessPiece(3, RED_NAME, "馬", redColor, bc, width - 4,
					height - 4, this);
			红马2 = new ChessPiece(4, RED_NAME, "馬", redColor, bc, width - 4,
					height - 4, this);
			红炮1 = new ChessPiece(5, RED_NAME, "炮", redColor, bc, width - 4,
					height - 4, this);
			红炮2 = new ChessPiece(6, RED_NAME, "炮", redColor, bc, width - 4,
					height - 4, this);
			红相1 = new ChessPiece(7, RED_NAME, "相", redColor, bc, width - 4,
					height - 4, this);
			红相2 = new ChessPiece(8, RED_NAME, "相", redColor, bc, width - 4,
					height - 4, this);
			红士1 = new ChessPiece(9, RED_NAME, "仕", redColor, bc, width - 4,
					height - 4, this);
			红士2 = new ChessPiece(10, RED_NAME, "仕", redColor, bc, width - 4,
					height - 4, this);
			红帅 = new ChessPiece(11, RED_NAME, "帥", redColor, bc, width - 4,
					height - 4, this);
			红兵1 = new ChessPiece(12, RED_NAME, "兵", redColor, bc, width - 4,
					height - 4, this);
			红兵2 = new ChessPiece(13, RED_NAME, "兵", redColor, bc, width - 4,
					height - 4, this);
			红兵3 = new ChessPiece(14, RED_NAME, "兵", redColor, bc, width - 4,
					height - 4, this);
			红兵4 = new ChessPiece(15, RED_NAME, "兵", redColor, bc, width - 4,
					height - 4, this);
			红兵5 = new ChessPiece(16, RED_NAME, "兵", redColor, bc, width - 4,
					height - 4, this);

			蓝将 = new ChessPiece(17, BLUE_NAME, "將", blueColor, bc, width - 4,
					height - 4, this);
			蓝士1 = new ChessPiece(18, BLUE_NAME, "士", blueColor, bc, width - 4,
					height - 4, this);
			蓝士2 = new ChessPiece(19, BLUE_NAME, "士", blueColor, bc, width - 4,
					height - 4, this);
			蓝车1 = new ChessPiece(20, BLUE_NAME, "車", blueColor, bc, width - 4,
					height - 4, this);
			蓝车2 = new ChessPiece(21, BLUE_NAME, "車", blueColor, bc, width - 4,
					height - 4, this);
			蓝炮1 = new ChessPiece(22, BLUE_NAME, "炮", blueColor, bc, width - 4,
					height - 4, this);
			蓝炮2 = new ChessPiece(23, BLUE_NAME, "炮", blueColor, bc, width - 4,
					height - 4, this);
			蓝象1 = new ChessPiece(24, BLUE_NAME, "象", blueColor, bc, width - 4,
					height - 4, this);
			蓝象2 = new ChessPiece(25, BLUE_NAME, "象", blueColor, bc, width - 4,
					height - 4, this);
			蓝马1 = new ChessPiece(26, BLUE_NAME, "馬", blueColor, bc, width - 4,
					height - 4, this);
			蓝马2 = new ChessPiece(27, BLUE_NAME, "馬", blueColor, bc, width - 4,
					height - 4, this);
			蓝卒1 = new ChessPiece(28, BLUE_NAME, "卒", blueColor, bc, width - 4,
					height - 4, this);
			蓝卒2 = new ChessPiece(29, BLUE_NAME, "卒", blueColor, bc, width - 4,
					height - 4, this);
			蓝卒3 = new ChessPiece(30, BLUE_NAME, "卒", blueColor, bc, width - 4,
					height - 4, this);
			蓝卒4 = new ChessPiece(31, BLUE_NAME, "卒", blueColor, bc, width - 4,
					height - 4, this);
			蓝卒5 = new ChessPiece(32, BLUE_NAME, "卒", blueColor, bc, width - 4,
					height - 4, this);

			gamePoints[1][10].setPiece(红车1, this);
			红车1.setLocation(new Point(1, 10));
			gamePoints[2][10].setPiece(红马1, this);
			红马1.setLocation(new Point(2, 10));
			gamePoints[3][10].setPiece(红相1, this);
			红相1.setLocation(new Point(3, 10));
			gamePoints[4][10].setPiece(红士1, this);
			红士1.setLocation(new Point(4, 10));
			gamePoints[5][10].setPiece(红帅, this);
			红帅.setLocation(new Point(5, 10));
			gamePoints[6][10].setPiece(红士2, this);
			红士2.setLocation(new Point(6, 10));
			gamePoints[7][10].setPiece(红相2, this);
			红相2.setLocation(new Point(7, 10));
			gamePoints[8][10].setPiece(红马2, this);
			红马2.setLocation(new Point(8, 10));
			gamePoints[9][10].setPiece(红车2, this);
			红车2.setLocation(new Point(9, 10));
			gamePoints[2][8].setPiece(红炮1, this);
			红炮1.setLocation(new Point(2, 8));
			gamePoints[8][8].setPiece(红炮2, this);
			红炮2.setLocation(new Point(8, 8));
			gamePoints[1][7].setPiece(红兵1, this);
			红兵1.setLocation(new Point(1, 7));
			gamePoints[3][7].setPiece(红兵2, this);
			红兵2.setLocation(new Point(3, 7));
			gamePoints[5][7].setPiece(红兵3, this);
			红兵3.setLocation(new Point(5, 7));
			gamePoints[7][7].setPiece(红兵4, this);
			红兵4.setLocation(new Point(7, 7));
			gamePoints[9][7].setPiece(红兵5, this);
			红兵5.setLocation(new Point(9, 7));

			gamePoints[1][1].setPiece(蓝车1, this);
			蓝车1.setLocation(new Point(1, 1));
			gamePoints[2][1].setPiece(蓝马1, this);
			蓝马1.setLocation(new Point(2, 1));
			gamePoints[3][1].setPiece(蓝象1, this);
			蓝象1.setLocation(new Point(3, 1));
			gamePoints[4][1].setPiece(蓝士1, this);
			蓝士1.setLocation(new Point(4, 1));
			gamePoints[5][1].setPiece(蓝将, this);
			蓝将.setLocation(new Point(5, 1));
			gamePoints[6][1].setPiece(蓝士2, this);
			蓝士2.setLocation(new Point(6, 1));
			gamePoints[7][1].setPiece(蓝象2, this);
			蓝象2.setLocation(new Point(7, 1));
			gamePoints[8][1].setPiece(蓝马2, this);
			蓝马2.setLocation(new Point(8, 1));
			gamePoints[9][1].setPiece(蓝车2, this);
			蓝车2.setLocation(new Point(9, 1));
			gamePoints[2][3].setPiece(蓝炮1, this);
			蓝炮1.setLocation(new Point(2, 3));
			gamePoints[8][3].setPiece(蓝炮2, this);
			蓝炮2.setLocation(new Point(8, 3));
			gamePoints[1][4].setPiece(蓝卒1, this);
			蓝卒1.setLocation(new Point(1, 4));
			gamePoints[3][4].setPiece(蓝卒2, this);
			蓝卒2.setLocation(new Point(3, 4));
			gamePoints[5][4].setPiece(蓝卒3, this);
			蓝卒3.setLocation(new Point(5, 4));
			gamePoints[7][4].setPiece(蓝卒4, this);
			蓝卒4.setLocation(new Point(7, 4));
			gamePoints[9][4].setPiece(蓝卒5, this);
			蓝卒5.setLocation(new Point(9, 4));

		} else {//
			红车1 = new ChessPiece(1, RED_NAME, "車", redColor, bc, width - 4,
					height - 4, this);
			红车2 = new ChessPiece(2, RED_NAME, "車", redColor, bc, width - 4,
					height - 4, this);
			红马1 = new ChessPiece(3, RED_NAME, "馬", redColor, bc, width - 4,
					height - 4, this);
			红马2 = new ChessPiece(4, RED_NAME, "馬", redColor, bc, width - 4,
					height - 4, this);
			红炮1 = new ChessPiece(5, RED_NAME, "炮", redColor, bc, width - 4,
					height - 4, this);
			红炮2 = new ChessPiece(6, RED_NAME, "炮", redColor, bc, width - 4,
					height - 4, this);
			红相1 = new ChessPiece(7, RED_NAME, "相", redColor, bc, width - 4,
					height - 4, this);
			红相2 = new ChessPiece(8, RED_NAME, "相", redColor, bc, width - 4,
					height - 4, this);
			红士1 = new ChessPiece(9, RED_NAME, "仕", redColor, bc, width - 4,
					height - 4, this);
			红士2 = new ChessPiece(10, RED_NAME, "仕", redColor, bc, width - 4,
					height - 4, this);
			红帅 = new ChessPiece(11, RED_NAME, "帥", redColor, bc, width - 4,
					height - 4, this);
			红兵1 = new ChessPiece(12, RED_NAME, "兵", redColor, bc, width - 4,
					height - 4, this);
			红兵2 = new ChessPiece(13, RED_NAME, "兵", redColor, bc, width - 4,
					height - 4, this);
			红兵3 = new ChessPiece(14, RED_NAME, "兵", redColor, bc, width - 4,
					height - 4, this);
			红兵4 = new ChessPiece(15, RED_NAME, "兵", redColor, bc, width - 4,
					height - 4, this);
			红兵5 = new ChessPiece(16, RED_NAME, "兵", redColor, bc, width - 4,
					height - 4, this);

			蓝将 = new ChessPiece(17, BLUE_NAME, "將", blueColor, bc, width - 4,
					height - 4, this);
			蓝士1 = new ChessPiece(18, BLUE_NAME, "士", blueColor, bc, width - 4,
					height - 4, this);
			蓝士2 = new ChessPiece(19, BLUE_NAME, "士", blueColor, bc, width - 4,
					height - 4, this);
			蓝车1 = new ChessPiece(20, BLUE_NAME, "車", blueColor, bc, width - 4,
					height - 4, this);
			蓝车2 = new ChessPiece(21, BLUE_NAME, "車", blueColor, bc, width - 4,
					height - 4, this);
			蓝炮1 = new ChessPiece(22, BLUE_NAME, "炮", blueColor, bc, width - 4,
					height - 4, this);
			蓝炮2 = new ChessPiece(23, BLUE_NAME, "炮", blueColor, bc, width - 4,
					height - 4, this);
			蓝象1 = new ChessPiece(24, BLUE_NAME, "象", blueColor, bc, width - 4,
					height - 4, this);
			蓝象2 = new ChessPiece(25, BLUE_NAME, "象", blueColor, bc, width - 4,
					height - 4, this);
			蓝马1 = new ChessPiece(26, BLUE_NAME, "馬", blueColor, bc, width - 4,
					height - 4, this);
			蓝马2 = new ChessPiece(27, BLUE_NAME, "馬", blueColor, bc, width - 4,
					height - 4, this);
			蓝卒1 = new ChessPiece(28, BLUE_NAME, "卒", blueColor, bc, width - 4,
					height - 4, this);
			蓝卒2 = new ChessPiece(29, BLUE_NAME, "卒", blueColor, bc, width - 4,
					height - 4, this);
			蓝卒3 = new ChessPiece(30, BLUE_NAME, "卒", blueColor, bc, width - 4,
					height - 4, this);
			蓝卒4 = new ChessPiece(31, BLUE_NAME, "卒", blueColor, bc, width - 4,
					height - 4, this);
			蓝卒5 = new ChessPiece(32, BLUE_NAME, "卒", blueColor, bc, width - 4,
					height - 4, this);

			gamePoints[1][10].setPiece(蓝车1, this);
			蓝车1.setLocation(new Point(1, 10));
			gamePoints[2][10].setPiece(蓝马1, this);
			蓝马1.setLocation(new Point(2, 10));
			gamePoints[3][10].setPiece(蓝象1, this);
			蓝象1.setLocation(new Point(3, 10));
			gamePoints[4][10].setPiece(蓝士1, this);
			蓝士1.setLocation(new Point(4, 10));

			gamePoints[5][10].setPiece(蓝将, this);
			蓝将.setLocation(new Point(5, 10));

			gamePoints[6][10].setPiece(蓝士2, this);
			蓝士2.setLocation(new Point(6, 10));
			gamePoints[7][10].setPiece(蓝象2, this);
			蓝象2.setLocation(new Point(7, 10));
			gamePoints[8][10].setPiece(蓝马2, this);
			蓝马2.setLocation(new Point(8, 10));
			gamePoints[9][10].setPiece(蓝车2, this);
			蓝车2.setLocation(new Point(9, 10));
			gamePoints[2][8].setPiece(蓝炮1, this);
			蓝炮1.setLocation(new Point(2, 8));
			gamePoints[8][8].setPiece(蓝炮2, this);
			蓝炮2.setLocation(new Point(8, 8));
			gamePoints[1][7].setPiece(蓝卒1, this);
			蓝卒1.setLocation(new Point(1, 7));
			gamePoints[3][7].setPiece(蓝卒2, this);
			蓝卒2.setLocation(new Point(3, 7));
			gamePoints[5][7].setPiece(蓝卒3, this);
			蓝卒3.setLocation(new Point(5, 7));
			gamePoints[7][7].setPiece(蓝卒4, this);
			蓝卒4.setLocation(new Point(7, 7));
			gamePoints[9][7].setPiece(蓝卒5, this);
			蓝卒5.setLocation(new Point(9, 7));

			gamePoints[1][1].setPiece(红车1, this);
			红车1.setLocation(new Point(1, 1));
			gamePoints[2][1].setPiece(红马1, this);
			红马1.setLocation(new Point(2, 1));
			gamePoints[3][1].setPiece(红相1, this);
			红相1.setLocation(new Point(3, 1));
			gamePoints[4][1].setPiece(红士1, this);
			红士1.setLocation(new Point(4, 1));
			gamePoints[5][1].setPiece(红帅, this);
			红帅.setLocation(new Point(5, 1));
			gamePoints[6][1].setPiece(红士2, this);
			红士2.setLocation(new Point(6, 1));
			gamePoints[7][1].setPiece(红相2, this);
			红相2.setLocation(new Point(7, 1));
			gamePoints[8][1].setPiece(红马2, this);
			红马2.setLocation(new Point(8, 1));
			gamePoints[9][1].setPiece(红车2, this);
			红车2.setLocation(new Point(9, 1));
			gamePoints[2][3].setPiece(红炮1, this);
			红炮1.setLocation(new Point(2, 3));
			gamePoints[8][3].setPiece(红炮2, this);
			红炮2.setLocation(new Point(8, 3));
			gamePoints[1][4].setPiece(红兵1, this);
			红兵1.setLocation(new Point(1, 4));
			gamePoints[3][4].setPiece(红兵2, this);
			红兵2.setLocation(new Point(3, 4));
			gamePoints[5][4].setPiece(红兵3, this);
			红兵3.setLocation(new Point(5, 4));
			gamePoints[7][4].setPiece(红兵4, this);
			红兵4.setLocation(new Point(7, 4));
			gamePoints[9][4].setPiece(红兵5, this);
			红兵5.setLocation(new Point(9, 4));
		}
	}

	/**
	 * 绘制棋盘
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 10条横线
		for (int j = 1; j <= y轴长; j++) {
			g.drawLine(gamePoints[1][j].x, gamePoints[1][j].y,
					gamePoints[x轴长][j].x, gamePoints[x轴长][j].y);
		}

		// 9条纵线
		for (int i = 1; i <= x轴长; i++) {
			if (i != 1 && i != x轴长) {
				// 中间的纵线
				g.drawLine(gamePoints[i][1].x, gamePoints[i][1].y,
						gamePoints[i][y轴长 - 5].x, gamePoints[i][y轴长 - 5].y);
				g.drawLine(gamePoints[i][y轴长 - 4].x, gamePoints[i][y轴长 - 4].y,
						gamePoints[i][y轴长].x, gamePoints[i][y轴长].y);
			} else {
				// 两边的纵线
				g.drawLine(gamePoints[i][1].x, gamePoints[i][1].y,
						gamePoints[i][y轴长].x, gamePoints[i][y轴长].y);
			}
		}

		// 士划斜
		g.drawLine(gamePoints[4][1].x, gamePoints[4][1].y, gamePoints[6][3].x,
				gamePoints[6][3].y);
		g.drawLine(gamePoints[6][1].x, gamePoints[6][1].y, gamePoints[4][3].x,
				gamePoints[4][3].y);
		g.drawLine(gamePoints[4][8].x, gamePoints[4][8].y,
				gamePoints[6][y轴长].x, gamePoints[6][y轴长].y);
		g.drawLine(gamePoints[4][y轴长].x, gamePoints[4][y轴长].y,
				gamePoints[6][8].x, gamePoints[6][8].y);

		// 显示横坐标
		for (int i = 1; i <= x轴长; i++) {
			g.drawString("" + i, i * unitWidth, unitHeight / 2);
		}

		// 显示纵坐标
		for (int j = 1; j <= 10; j++) {
			g.drawString("" + j, unitWidth / 4, j * unitHeight);
		}

		// 楚河、汉界
		g.setFont(new Font("宋体", Font.PLAIN, 32));
		g.drawString("漢 界", gamePoints[2][5].x, gamePoints[2][5].y + 2
				* unitHeight / 3);
		g.drawString("楚 河", gamePoints[6][5].x, gamePoints[2][5].y + 2
				* unitHeight / 3);

	}

	public void mousePressed(MouseEvent e) {
		ChessPiece piece = null;
		Rectangle rect = null;
		if (e.getSource() == this)
			move = false;
		if (move == false)
			if (e.getSource() instanceof ChessPiece) {
				piece = (ChessPiece) e.getSource();
				if (myTurn) {
					if (piece.getName().equals(playerName)) {
						this.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}
				}
				startX = piece.getBounds().x;
				startY = piece.getBounds().y;

				rect = piece.getBounds();
				for (int i = 1; i <= x轴长; i++) {
					for (int j = 1; j <= y轴长; j++) {
						int x = gamePoints[i][j].getX();
						int y = gamePoints[i][j].getY();
						if (rect.contains(x, y)) {
							startI = i;
							startJ = j;
							break;
						}

					}
				}
			}
	}

	public void mouseEntered(MouseEvent e) {
		ChessPiece piece = null;
		boolean flag = false;

		if (e.getSource() instanceof ChessPiece) {
			piece = (ChessPiece) e.getSource();
			if (myTurn && piece.getName().equals(playerName)) {
				flag = true;
			}
		}
		if (flag) {
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	/**
	 * 拖动鼠标，轮到自己走棋时才能走，并且只能移动己方的棋子
	 */
	public void mouseDragged(MouseEvent e) {
		if (myTurn) {
			ChessPiece piece = null;
			if (e.getSource() instanceof ChessPiece) {
				piece = (ChessPiece) e.getSource();
				if (piece.getName().equals(playerName)) {
					this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				// 只能移动自己方的棋子
				if (piece.getName().equals(playerName)) {
					move = true;
				}

				e = SwingUtilities.convertMouseEvent(piece, e, this);
			}

			int x = 0, y = 0;// 坐标
			if (e.getSource() == this) {
				if (move && piece != null) {
					x = e.getX();
					y = e.getY();
					if (myTurn && ((piece.getName()).equals(playerName))) {
						piece.setLocation(x - piece.getWidth() / 2,
								y - piece.getHeight() / 2);
					}

				}
			}
		}
	}

	/**
	 * 响应鼠标释放事件
	 */
	public void mouseReleased(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		ChessPiece piece = null;
		move = false;
		Rectangle rect = null;
		if (e.getSource() instanceof ChessPiece) {
			piece = (ChessPiece) e.getSource();
			rect = piece.getBounds();
			e = SwingUtilities.convertMouseEvent(piece, e, this);
		}
		if (e.getSource() == this) {
			boolean containChessPoint = false;
			int x = 0, y = 0;
			int m = 0, n = 0;
			if (piece != null) {
				for (int i = 1; i <= x轴长; i++) {
					for (int j = 1; j <= y轴长; j++) {
						x = gamePoints[i][j].getX();
						y = gamePoints[i][j].getY();
						if (rect.contains(x, y)) {
							containChessPoint = true;
							m = i;
							n = j;
							break;
						}
					}
				}
			}
			if (piece != null && containChessPoint) {
				Color pieceColor = piece.getForeColor();
				if (gamePoints[m][n].isPiece()) {// 有棋子
					Color c = (gamePoints[m][n].getPiece()).getForeColor();
					if (pieceColor.getRGB() == c.getRGB()) {// 棋子的颜色相同
						piece.setLocation(startX, startY);
						(gamePoints[startI][startJ]).setHasPiece(true);
					} else {
						// 颜色不同，走棋是否符合规则
						boolean ok = gameRule.movePieceRule(piece, startI,
								startJ, m, n);
						if (ok) {
							ChessPiece pieceRemoved = gamePoints[m][n]
									.getPiece();
							gamePoints[m][n].removePiece(pieceRemoved, this);
							gamePoints[m][n].setPiece(piece, this);
							piece.setLocation(new Point(m, n));
							(gamePoints[startI][startJ]).setHasPiece(false);
							gamePoints[m][n].setHasPiece(true);

							MoveRecord record = new MoveRecord();
							MoveStep moveStep = new MoveStep(new Point(startI,
									startJ), new Point(m, n));
							record.setMoveStep(moveStep);
							record.setEatedPieceId(pieceRemoved.getId());
							record.setMovePieceId(piece.getId());
							records.addRecord(record);

							/** 发送数据包，告诉对方自己棋子的移动 */
							try {
								packet = new DataPacket();

								packet.setMoveStep(moveStep);
								packet.setStatus(MOVING);
								packet.setPieceId(piece.getId());
								packet.setDelPieceId(pieceRemoved.getId());

								if (playerName.equals(RED_NAME)) {
									packet.setPlayer(PLAYER1);
									sendPacket(toPlayer2, packet);
								} else if (playerName.equals(BLUE_NAME)) {
									packet.setPlayer(PLAYER2);
									sendPacket(toPlayer1, packet);
								}

								System.out.println(playerName + "将要发送消息！");
							} catch (Exception ex) {
								System.out.println("发送移动消息失败！");
								ex.printStackTrace();
							}
							validate();
							repaint();
						} else {
							piece.setLocation(startX, startY);
							(gamePoints[startI][startJ]).setHasPiece(true);
						}
					}

				}
				// 终点没有棋子
				else {
					boolean canMove = gameRule.movePieceRule(piece, startI,
							startJ, m, n);
					if (canMove) {
						gamePoints[m][n].setPiece(piece, this);
						piece.setLocation(new Point(m, n));
						(gamePoints[startI][startJ]).setHasPiece(false);

						/** 记录棋子的移动 */
						MoveRecord record = new MoveRecord();
						MoveStep moveStep = new MoveStep(new Point(startI,
								startJ), new Point(m, n));
						record.setMoveStep(moveStep);
						record.setEatedPieceId(0);
						record.setMovePieceId(piece.getId());
						records.addRecord(record);

						/** 发送数据包，告诉对方自己棋子的移动 */
						try {
							packet = new DataPacket();
							packet.setMoveStep(moveStep);
							packet.setStatus(MOVING);
							packet.setPieceId(piece.getId());
							packet.setDelPieceId(0);
							if (playerName.equals(RED_NAME)) {
								packet.setPlayer(PLAYER1);
								sendPacket(toPlayer2, packet);
							} else if (playerName.equals(BLUE_NAME)) {
								packet.setPlayer(PLAYER2);
								sendPacket(toPlayer1, packet);
							}
						} catch (Exception ex) {
							System.out.println("发送移动消息失败！");
							ex.printStackTrace();
						}
						// 更新
						validate();
						repaint();
					} else {
						piece.setLocation(startX, startY);
						(gamePoints[startI][startJ]).setHasPiece(true);
					}
				}
			}

			if (piece != null && !containChessPoint) {
				piece.setLocation(startX, startY);
				(gamePoints[startI][startJ]).setHasPiece(true);
			}
		}
	}

	private class TestThread implements Runnable {
		/**
		 * 核心线程，负责接收消息
		 */
		public void run() {
			try {
				// 接收服务器端的数据
				player = ((DataPacket) fromServer.readObject()).getPlayer();

				// 玩家1Or还是玩家2
				if (player == PLAYER1) {
					initChess(unitWidth, unitHeight, new Color(204, 153, 102));

					playerName = RED_NAME;
					text.append("我是红方！\n");
					gameStatus.setText(SPACE + "等待蓝方加入游戏！");
				} else if (player == PLAYER2) {
					initChess(unitWidth, unitHeight, new Color(204, 153, 102));

					playerName = BLUE_NAME;
					text.append("我是蓝方！\n");
					gameStatus.setText(SPACE + "开始游戏，然后等待红方开始！");
				}

				String ip = (String) fromServer.readObject();
				System.out.println(playerName + "对方的ip地址为：" + ip);
				ipAddress = ip;

				if (player == PLAYER1) {
					serverSocket = new ServerSocket(PORT);
					player1Socket = serverSocket.accept();

					if (player1Socket != null) {
						toPlayer2 = new ObjectOutputStream(
								player1Socket.getOutputStream());
						fromPlayer2 = new ObjectInputStream(
								player1Socket.getInputStream());
					} else {
						System.out.println("player1Socket == null");
					}
				} else if (player == PLAYER2) {
					player2Socket = new Socket(ipAddress, PORT);

					if (player2Socket != null) {
						toPlayer1 = new ObjectOutputStream(
								player2Socket.getOutputStream());
						fromPlayer1 = new ObjectInputStream(
								player2Socket.getInputStream());

						DataPacket packet = new DataPacket();
						packet.setStatus(PLAYER2_ENTER);
						blueEnter = true;
						// 通知红方，蓝方已经加入游戏
						toPlayer1.writeObject(packet);
					} else {
						System.out.println("player2Socket == null");
					}
				}

				/* 继续游戏！ 不断发送和接收消息 */
				while (true) {
					if (running == RUNNING) {
						if (player == PLAYER1) {
							if (fromPlayer2.available() >= 0) {
								receiveInfoFromPlayer();
							} else {
								Thread.sleep(1000);
							}
						} else if (player == PLAYER2) {
							if (fromPlayer1.available() >= 0) {
								receiveInfoFromPlayer();
							} else {
								Thread.sleep(1000);
							}
						}
					} else if (running == GAME_EXIT) {
						// System.out.println("程序将要退出！");
						Thread.sleep(1000);
					} else if (running == GAME_PAUSE) {
						Thread.sleep(1000);
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/** 初始化套接字 */
	public void initSocket(Socket socket) {
		this.socket = socket;

		// 连接到服务器
		// 创建一个输入流来从服务器端接收数据
		try {
			fromServer = new ObjectInputStream(socket.getInputStream());

			// 创建一个输出流来从服务器端接收数据
			toServer = new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送玩家的移动信息
	 * 
	 * @param out
	 *            对象输出流
	 * @param packet
	 *            数据包
	 */
	public void sendPacket(ObjectOutputStream out, DataPacket packet)
			throws IOException {

		if (out == null) {
			toServer.writeObject(packet);
		} else {
			out.writeObject(packet);
			int status = packet.getStatus();
			if (status == MOVING) {
				myTurn = false;
				gameStatus.setText(SPACE + "等待对方走棋！");
				System.out.println(playerName + "正在发送移动消息！");
			} else if (status == GAME_BACK) {
				// 悔棋一次，棋局各退一步
				System.out.println(playerName + "正在发送悔棋消息！");
			}
		}

	}

	/** 从玩家接收信息，根据消息的id分类处理，同时播放声音 */
	private void receiveInfoFromPlayer() {
		DataPacket packet = new DataPacket();
		int status = 0;
		try {
			if (player == PLAYER1) {
				packet = (DataPacket) fromPlayer2.readObject();
			} else if (player == PLAYER2) {
				packet = (DataPacket) fromPlayer1.readObject();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		status = packet.getStatus();

		if (status == PLAYER1_WON) {
			System.out.println("玩家1赢了");
			// FansUtil.playSound("win.wav");
			running = GAME_PAUSE;
		} else if (status == PLAYER2_WON) {
			System.out.println("玩家2赢得了比赛");
			// FansUtil.playSound("win.wav");
			running = GAME_PAUSE;
		} else if (status == GAME_BACK) {
			// 顺序变反
			myTurn = !myTurn;
			if (myTurn) {
				gameStatus.setText(SPACE + "对方请求悔棋！现在轮到我走喽！");
			} else {
				gameStatus.setText(SPACE + "对方请求悔棋！等待对方走棋！");
			}
			// 记录回滚
			records.back();
			// 更新
			validate();
			repaint();
		}

		// 收到棋子移动消息
		else if (status == MOVING) {
			ChessUtils.playSound("moving.wav");
			gameStatus.setText(SPACE + "轮到我走喽！");
			myTurn = true;
			try {
				receiveMove(packet);
				if (isEatLeader()) {
					gameStatus.setText(SPACE + gameStatus.getText()
							+ ("对方将军啦！"));
					ChessUtils.playSound("jiangjun.wav");
				}
			} catch (Exception e) {
				System.out.println("接收移动消息失败啦！");
				e.printStackTrace();
			}

		}

		// 蓝方进入游戏消息
		else if (status == PLAYER2_ENTER) {
			blueEnter = true;
			gameStatus.setText(SPACE + "蓝方已经加入游戏！等待蓝方准备！");
			ChessUtils.playSound("global.wav");
		}

		else if (status == GAME_START) {
			if (player == PLAYER1) {
				blueOk = true;
				gameStatus.setText(SPACE + "蓝方已经开始游戏！点击开始进行游戏！");
			} else {
				if (blueOk) {
					gameStatus.setText(SPACE + "红方已经开始游戏！等待红方走棋！");
				}
			}
		}

		// 收到退出消息
		else if (status == GAME_EXIT) {
			if (player == PLAYER1) {
				gameStatus.setText(SPACE + "蓝方已经退出游戏！");
			} else {
				gameStatus.setText(SPACE + "红方已经退出游戏！");
			}
			running = GAME_EXIT;
			close();// 关闭套接字
		}

		// 收到了投降消息
		else if (status == GIVEIN) {
			if (player == PLAYER1) {
				gameStatus.setText(SPACE + "蓝方已经投降啦！仁兄果然有才啊！");
			} else {
				if (blueOk) {
					gameStatus.setText(SPACE + "红方已经投降啦！仁兄果然有才啊！");
				}
			}
		}

		// 收到了对方的暂停消息
		else if (status == GAME_PAUSE) {
			otherIsPause = true;
			if (isPause) {
				gameStatus.setText(SPACE + "您和对方都已经暂停了游戏！");
			} else {
				gameStatus.setText(SPACE + "对方暂停了游戏！");
			}
		}

		else if (status == CONTINUE) {
			otherIsPause = false;
			if (isPause) {
				gameStatus.setText(SPACE + "我已经暂停了游戏！");
			} else {
				if (myTurn) {
					gameStatus.setText(SPACE + "轮到我走喽！");
				} else {
					gameStatus.setText(SPACE + "等待对方走棋！");
				}
			}
		}

		// 收到了聊天消息,播放声音
		else if (status == MESSAGE) {
			ChessUtils.playSound("msg.wav");
			Message message = packet.getMessage();
			// String date = message.getDate();
			String content = message.getContent();
			if (playerName.equals(RED_NAME)) {
				msgArea.append(BLUE_NAME + ":" + content + "\n");
			} else {
				msgArea.append(RED_NAME + ":" + content + "\n");
			}
		}
	}

	/**
	 * 测试用的
	 */
	public static void main(String[] args) {
		String path = "";
		String name = "global.wav";
		try {
			// FileInputStream fileau = new FileInputStream("msg.wav");
			URL url = ChessBoard.class.getResource("");
			System.out.println(url + "sound/global.wav");
			if (url != null) {
				System.out.println(url.getPath());
				path = url.getPath() + "sound/" + name;
			}

			InputStream is = new FileInputStream(path);
			AudioStream as = new AudioStream(is);
			AudioPlayer.player.start(as);
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	/**
	 * 玩家之间的坐标需要转换，关于中心线对称，x不变，y之和为11
	 * 
	 * @param packet
	 *            收到的数据包
	 */
	// 获取另一个玩家的移动,并修改界面来实现同步
	private void receiveMove(DataPacket packet) {
		// 根据棋子id获取移动的棋子
		ChessPiece piece = this.getChessPieceById(packet.getPieceId());
		MoveStep moveStep = packet.getMoveStep();
		int startX = (int) moveStep.getPStart().getX();
		int startY = 11 - (int) moveStep.getPStart().getY();
		int endX = (int) moveStep.getPEnd().getX();
		int endY = 11 - (int) moveStep.getPEnd().getY();

		int delPieceId = packet.getDelPieceId();
		MoveRecord record = new MoveRecord();
		moveStep.setPStart(new Point(startX, startY));
		moveStep.setPEnd(new Point(endX, endY));
		record.setMoveStep(moveStep);
		record.setMovePieceId(piece.getId());

		// 有棋子被删除
		if (delPieceId != 0) {
			gamePoints[endX][endY].removePiece(getChessPieceById(delPieceId),
					this);
		}

		record.setEatedPieceId(delPieceId);
		records.addRecord(record);

		gamePoints[endX][endY].setPiece(piece, this);
		piece.setLocation(new Point(endX, endY));
		gamePoints[startX][startY].setHasPiece(false);
		gamePoints[endX][endY].setHasPiece(true);

		// 更新
		validate();
		repaint();
	}

	/**
	 * 收到对方的移动消息后，则立即判断 是否将军
	 * 
	 * 只有车、马、炮和卒或兵才有将军的可能
	 * 
	 * 只要有一个棋子可以将军则将军为真，并且立即返回
	 * 
	 * @return 是否将军
	 */
	private boolean isEatLeader() {
		boolean flag = false;

		Point myLeaderLocation;// 对方将或帅的位置
		Point[] paoLocations;// 炮的位置
		Point[] juLocations;// 車的位置
		Point[] maLocations;// 马的位置
		Point[] zuLocations;// 卒或兵的位置

		// 判断蓝方是否将军
		if (playerName.equals(RED_NAME)) {
			myLeaderLocation = getMyLeaderLocation("帥");
			juLocations = getLocationByCategory("車");
			paoLocations = getLocationByCategory("炮");
			maLocations = getLocationByCategory("馬");
			zuLocations = getLocationByCategory("卒");

			int leaderX = (int) myLeaderLocation.getX();
			int leaderY = (int) myLeaderLocation.getY();

			// 判断对方的車是否将军
			for (int i = 0; i < juLocations.length; i++) {
				int x = (int) juLocations[i].getX();
				int y = (int) juLocations[i].getY();

				int minX = Math.min(x, leaderX);
				int maxX = Math.max(x, leaderX);
				int minY = Math.min(y, leaderY);
				int maxY = Math.max(y, leaderY);

				if ((x == leaderX)) {
					System.out.println("帅和車的横坐标相同！");
					int j = 0;
					for (j = minY + 1; j <= maxY - 1; j++) {
						// 中间不能有棋子
						if (gamePoints[x][j].isPiece()) {
							flag = false;
							break;
						}
					}
					if (j == maxY) {
						flag = true;
						return flag;
					}
				} else if (y == leaderY) {
					System.out.println("帅和車的纵坐标相同！");
					int k = 0;
					for (k = minX + 1; k <= maxX - 1; k++) {
						if (gamePoints[k][y].isPiece()) {
							flag = false;
							break;
						}
					}
					if (k == maxX) {
						flag = true;
						return flag;

					}
				} else {
					flag = false;
				}
			}

			// 判断对方的炮是否将军
			for (int i = 0; i < paoLocations.length; i++) {
				int x = (int) paoLocations[i].getX();
				int y = (int) paoLocations[i].getY();

				int minX = Math.min(x, leaderX);
				int maxX = Math.max(x, leaderX);
				int minY = Math.min(y, leaderY);
				int maxY = Math.max(y, leaderY);

				// 如果吃子，中间只能隔着一个棋子
				int number = 0;
				// 垂直方向移动
				if (x == leaderX) {
					System.out.println("帅和炮的横坐标相同！");
					int j = 0;
					for (j = minY + 1; j <= maxY - 1; j++) {
						if (gamePoints[x][j].isPiece()) {
							number++;
						}
					}
					System.out.println("炮和帅之间有" + number + "个棋子");
					// 中间有且只有一个棋子时，则将军
					if (number == 1) {
						flag = true;
						return flag;
					} else {
						flag = false;
					}
				}
				// 水平方向移动
				else if (y == leaderY) {
					System.out.println("帅和炮的纵坐标相同！");
					int k = 0;
					for (k = minX + 1; k <= maxX - 1; k++) {
						if (gamePoints[k][y].isPiece()) {
							number++;
						}
					}
					if (number == 1) {
						flag = true;
						return flag;
					} else {
						flag = false;
					}
				}
			}

			// 判断对方的马是否将军
			for (int i = 0; i < maLocations.length; i++) {
				int x = (int) maLocations[i].getX();
				int y = (int) maLocations[i].getY();

				int xAxle = Math.abs(x - leaderX);
				int yAxle = Math.abs(y - leaderY);

				// X方向移动2步，Y方向移动1步
				if (xAxle == 2 && yAxle == 1) {
					if (leaderX > x) {
						// 别马腿
						if (gamePoints[x + 1][y].isPiece()) {
							flag = false;
						} else {
							flag = true;
							return flag;
						}
					} else if (leaderX < x) {
						// 别马腿
						if (gamePoints[x - 1][y].isPiece()) {
							flag = false;
						} else {
							flag = true;
							return flag;
						}
					}

				}

				// X方向移动1步，Y方向移动2步
				else if (xAxle == 1 && yAxle == 2) {
					if (leaderY > y) {
						// 别马腿
						if (gamePoints[x][y + 1].isPiece()) {
							flag = false;
						} else {
							flag = true;
							return flag;
						}
					}
					if (leaderY < y) {
						// 别马腿
						if (gamePoints[x][y - 1].isPiece()) {
							flag = false;
						} else {
							flag = true;
							return flag;
						}
					}
				} else {
					flag = false;
				}
			}
			// 判断蓝方的卒是否将军
			for (int i = 0; i < zuLocations.length; i++) {
				int x = (int) zuLocations[i].getX();
				int y = (int) zuLocations[i].getY();

				int xAxle = Math.abs(x - leaderX);

				if ((leaderY - y == 1) && (xAxle == 0)) {
					flag = true;
					return flag;
				} else if ((leaderY - y == 0) && (xAxle == 1)) {
					flag = true;
					return flag;
				} else {
					flag = false;
				}
			}
		}

		// 判断红方是否将军
		else if (playerName.equals(BLUE_NAME)) {
			myLeaderLocation = getMyLeaderLocation("將");
			juLocations = getLocationByCategory("車");
			paoLocations = getLocationByCategory("炮");
			maLocations = getLocationByCategory("馬");
			zuLocations = getLocationByCategory("兵");

			int leaderX = (int) myLeaderLocation.getX();
			int leaderY = (int) myLeaderLocation.getY();

			// 判断对方的車是否将军
			for (int i = 0; i < juLocations.length; i++) {
				int x = (int) juLocations[i].getX();
				int y = (int) juLocations[i].getY();

				int minX = Math.min(x, leaderX);
				int maxX = Math.max(x, leaderX);
				int minY = Math.min(y, leaderY);
				int maxY = Math.max(y, leaderY);

				if ((x == leaderX)) {
					int j = 0;
					for (j = minY + 1; j <= maxY - 1; j++) {
						// 中间不能有棋子
						if (gamePoints[x][j].isPiece()) {
							flag = false;
							break;
						}
					}
					if (j == maxY) {
						flag = true;
						return flag;
					}
				} else if (y == leaderY) {
					int k = 0;
					for (k = minX + 1; k <= maxX - 1; k++) {
						if (gamePoints[k][y].isPiece()) {
							flag = false;
							break;
						}
					}
					if (k == maxX) {
						flag = true;
						return flag;

					}
				} else {
					flag = false;
				}
			}

			// 判断对方的炮是否将军
			for (int i = 0; i < paoLocations.length; i++) {
				int x = (int) paoLocations[i].getX();
				int y = (int) paoLocations[i].getY();

				int minX = Math.min(x, leaderX);
				int maxX = Math.max(x, leaderX);
				int minY = Math.min(y, leaderY);
				int maxY = Math.max(y, leaderY);

				// 如果吃子，中间只能隔着一个棋子
				int number = 0;
				// 垂直方向移动
				if (x == leaderX) {
					int j = 0;
					for (j = minY + 1; j <= maxY - 1; j++) {
						if (gamePoints[x][j].isPiece()) {
							number++;
						}
					}

					// 中间有且只有一个棋子时，则将军
					if (number == 1) {
						flag = true;
						return flag;
					} else {
						flag = false;
					}
				}
				// 水平方向移动
				else if (y == leaderY) {

					int k = 0;
					for (k = minX + 1; k <= maxX - 1; k++) {
						if (gamePoints[k][y].isPiece()) {
							number++;
						}
					}
					if (number == 1) {
						flag = true;
						return flag;
					} else {
						flag = false;
					}
				}
			}

			// 判断对方的马是否将军
			for (int i = 0; i < maLocations.length; i++) {
				int x = (int) maLocations[i].getX();
				int y = (int) maLocations[i].getY();

				int xAxle = Math.abs(x - leaderX);
				int yAxle = Math.abs(y - leaderY);

				// X方向移动2步，Y方向移动1步
				if (xAxle == 2 && yAxle == 1) {
					if (leaderX > x) {
						// 别马腿
						if (gamePoints[x + 1][y].isPiece()) {
							flag = false;
						} else {
							flag = true;
							return flag;
						}
					} else if (leaderX < x) {
						// 别马腿
						if (gamePoints[x - 1][y].isPiece()) {
							flag = false;
						} else {
							flag = true;
							return flag;
						}
					}

				}

				// X方向移动1步，Y方向移动2步
				else if (xAxle == 1 && yAxle == 2) {
					if (leaderY > y) {
						// 别马腿
						if (gamePoints[x][y + 1].isPiece()) {
							flag = false;
						} else {
							flag = true;
							return flag;
						}
					}
					if (leaderY < y) {
						// 别马腿
						if (gamePoints[x][y - 1].isPiece()) {
							flag = false;
						} else {
							flag = true;
							return flag;
						}
					}
				} else {
					flag = false;
				}
			}
			// 判断蓝方的卒是否将军
			for (int i = 0; i < zuLocations.length; i++) {
				int x = (int) zuLocations[i].getX();
				int y = (int) zuLocations[i].getY();

				int xAxle = Math.abs(x - leaderX);

				if ((leaderY - y == 1) && (xAxle == 0)) {
					flag = true;
					return flag;
				} else if ((leaderY - y == 0) && (xAxle == 1)) {
					flag = true;
					return flag;
				} else {
					flag = false;
				}
			}
		}

		return flag;
	}

	/**
	 * 根据棋子的种类（兵、卒、马、炮和車)获取对方棋子的位置）
	 * 
	 * @param category
	 *            棋子的种类
	 * @return
	 */
	private Point[] getLocationByCategory(String category) {
		boolean b = category.equals("兵") || category.equals("卒");
		Point[] points;
		if (b) {
			points = new Point[] { new Point(-1, -1), new Point(-1, -1),
					new Point(-1, -1), new Point(-1, -1), new Point(-1, -1) };
		} else {
			points = new Point[] { new Point(-1, -1), new Point(-1, -1) };
		}

		int num = 0;
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 10; j++) {
				ChessPiece piece = gamePoints[i][j].getPiece();
				// 有棋子
				if (piece != null) {
					boolean flag = piece.getName().equals(playerName);
					// 并且是对方的棋子
					if (!flag) {
						String category2 = piece.getCategory();
						if (category2.equals(category)) {
							points[num] = new Point((int) piece.getLocation()
									.getX(), (int) piece.getLocation().getY());
							num++;

							if (b) {
								if (num == 5) {
									return points;
								}
							} else {
								if (num == 2) {
									return points;
								}
							}
						}
					}
				}
			}
		}

		return points;
	}

	/**
	 * 根据名字获取帅或将的位置
	 * 
	 * @param category
	 * @return
	 */
	private Point getMyLeaderLocation(String category) {
		Point point = null;
		if (playerName.equals(RED_NAME)) {
			for (int i = 4; i <= 6; i++) {
				for (int j = 8; j <= 10; j++) {
					ChessPiece piece = gamePoints[i][j].getPiece();
					if (piece != null) {
						if (piece.getCategory().equals(category)) {
							point = new Point((int) piece.getLocation().getX(),
									(int) piece.getLocation().getY());
							return point;
						}
					}
				}
			}
		} else if (playerName.equals(BLUE_NAME)) {
			for (int i = 4; i <= 6; i++) {
				for (int j = 8; j <= 10; j++) {
					ChessPiece piece = gamePoints[i][j].getPiece();
					if (piece != null) {
						if (piece.getCategory().equals(category)) {
							point = new Point((int) piece.getLocation().getX(),
									(int) piece.getLocation().getY());
							return point;
						}
					}
				}
			}
		}
		return point;
	}

	/**
	 * 根据棋子的位置获得棋子的引用
	 * 
	 * @param start
	 * @return
	 */
	public ChessPiece getChessPieceById(int id) {
		ChessPiece piece;
		for (int i = 1; i < gamePoints.length; i++) {
			for (int j = 1; j < gamePoints[i].length; j++) {
				piece = gamePoints[i][j].getPiece();
				if (piece != null) {
					if (piece.getId() == id) {
						return piece;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 鼠标退出棋盘
	 */
	public void mouseExited(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {

	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public ObjectOutputStream getToServer() {
		return toServer;
	}

	public void setToServer(ObjectOutputStream toServer) {
		this.toServer = toServer;
	}

	public int getPlayer() {
		return player;
	}

	public void setGameStaus(JLabel gameStaus) {
		this.gameStatus = gameStaus;
	}

	public Socket getClientSocket() {
		return player1Socket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.player1Socket = clientSocket;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public ObjectInputStream getFromPlayer1() {
		return fromPlayer1;
	}

	public void setFromPlayer1(ObjectInputStream fromPlayer1) {
		this.fromPlayer1 = fromPlayer1;
	}

	public ObjectInputStream getFromPlayer2() {
		return fromPlayer2;
	}

	public void setFromPlayer2(ObjectInputStream fromPlayer2) {
		this.fromPlayer2 = fromPlayer2;
	}

	public ObjectOutputStream getToPlayer1() {
		return toPlayer1;
	}

	public void setToPlayer1(ObjectOutputStream toPlayer1) {
		this.toPlayer1 = toPlayer1;
	}

	public ObjectOutputStream getToPlayer2() {
		return toPlayer2;
	}

	public void setToPlayer2(ObjectOutputStream toPlayer2) {
		this.toPlayer2 = toPlayer2;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public JTextArea getMsgArea() {
		return msgArea;
	}

	public void setMsgArea(JTextArea msgArea) {
		this.msgArea = msgArea;
	}

	/**
	 * 关闭套接字
	 * 
	 */
	public void close() {
		try {
			if (socket != null) {
				socket.close();
			}

			if (serverSocket != null) {
				serverSocket.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
