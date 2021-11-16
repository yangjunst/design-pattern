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
 * ������
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

	public int player;// ��ʶ��ң�1����2

	private ServerSocket serverSocket;// ���1��������

	private Socket player1Socket;// ���1�䵱�ͻ���

	private Socket player2Socket;// ���2�䵱�ͻ���

	public int unitWidth, unitHeight;

	public boolean blueEnter = false;

	int x�᳤, y�᳤;

	// int x, y;

	boolean move = false;

	ChessPiece �쳵1, �쳵2, ����1, ����2, ����1, ����2, ��˧, ��ʿ1, ��ʿ2, ���1, ���2, ���3, ���4,
			���5, ����1, ����2;

	ChessPiece ����1, ����2, ����1, ����2, ����1, ����2, ����, ��ʿ1, ��ʿ2, ����1, ����2, ����3, ����4,
			����5, ����1, ����2;

	private int startX, startY;

	private int startI, startJ;

	private GameRule gameRule = null;

	public MakeChessManual records = null;

	// �������ֻ�IP��ַ
	private String ipAddress;

	// ��Ϸ�Ƿ����
	public int running = RUNNING;

	// �����Ƿ��Ѿ�׼��
	public boolean blueOk = false;

	// ���Ƿ���ͣ����Ϸ
	public boolean isPause = false;

	// �Է��Ƿ���ͣ����Ϸ
	public boolean otherIsPause = false;

	private JTextArea msgArea;

	private String playerName;// ս���������֣��췽or����

	private String petName;// �û������֣�fans or farmer

	public boolean myTurn = false;

	public ChessBoard(int r, int c) {

		setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this);

		unitWidth = PIECE_WIDTH;
		unitHeight = PIECE_HEIGHT;
		x�᳤ = r;
		y�᳤ = c;

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
		// ��һ���������߳��п�����Ϸ
		Thread thread = new Thread(new TestThread());
		thread.start();
	}

	/**
	 * ��ʼ���췽���������ӵĿ�ȣ��߶Ⱥ�ǰ��ɫ
	 * 
	 * @param width
	 * @param height
	 * @param bc
	 */
	public void initChess(int width, int height, Color bc) {
		Color redColor = Color.red;
		Color blueColor = Color.blue;
		if (player == 1) {
			�쳵1 = new ChessPiece(1, RED_NAME, "܇", redColor, bc, width - 4,
					height - 4, this);
			�쳵2 = new ChessPiece(2, RED_NAME, "܇", redColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(3, RED_NAME, "�R", redColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(4, RED_NAME, "�R", redColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(5, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(6, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(7, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(8, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			��ʿ1 = new ChessPiece(9, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			��ʿ2 = new ChessPiece(10, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			��˧ = new ChessPiece(11, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			���1 = new ChessPiece(12, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			���2 = new ChessPiece(13, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			���3 = new ChessPiece(14, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			���4 = new ChessPiece(15, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			���5 = new ChessPiece(16, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);

			���� = new ChessPiece(17, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			��ʿ1 = new ChessPiece(18, BLUE_NAME, "ʿ", blueColor, bc, width - 4,
					height - 4, this);
			��ʿ2 = new ChessPiece(19, BLUE_NAME, "ʿ", blueColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(20, BLUE_NAME, "܇", blueColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(21, BLUE_NAME, "܇", blueColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(22, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(23, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(24, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(25, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(26, BLUE_NAME, "�R", blueColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(27, BLUE_NAME, "�R", blueColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(28, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(29, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����3 = new ChessPiece(30, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����4 = new ChessPiece(31, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����5 = new ChessPiece(32, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);

			gamePoints[1][10].setPiece(�쳵1, this);
			�쳵1.setLocation(new Point(1, 10));
			gamePoints[2][10].setPiece(����1, this);
			����1.setLocation(new Point(2, 10));
			gamePoints[3][10].setPiece(����1, this);
			����1.setLocation(new Point(3, 10));
			gamePoints[4][10].setPiece(��ʿ1, this);
			��ʿ1.setLocation(new Point(4, 10));
			gamePoints[5][10].setPiece(��˧, this);
			��˧.setLocation(new Point(5, 10));
			gamePoints[6][10].setPiece(��ʿ2, this);
			��ʿ2.setLocation(new Point(6, 10));
			gamePoints[7][10].setPiece(����2, this);
			����2.setLocation(new Point(7, 10));
			gamePoints[8][10].setPiece(����2, this);
			����2.setLocation(new Point(8, 10));
			gamePoints[9][10].setPiece(�쳵2, this);
			�쳵2.setLocation(new Point(9, 10));
			gamePoints[2][8].setPiece(����1, this);
			����1.setLocation(new Point(2, 8));
			gamePoints[8][8].setPiece(����2, this);
			����2.setLocation(new Point(8, 8));
			gamePoints[1][7].setPiece(���1, this);
			���1.setLocation(new Point(1, 7));
			gamePoints[3][7].setPiece(���2, this);
			���2.setLocation(new Point(3, 7));
			gamePoints[5][7].setPiece(���3, this);
			���3.setLocation(new Point(5, 7));
			gamePoints[7][7].setPiece(���4, this);
			���4.setLocation(new Point(7, 7));
			gamePoints[9][7].setPiece(���5, this);
			���5.setLocation(new Point(9, 7));

			gamePoints[1][1].setPiece(����1, this);
			����1.setLocation(new Point(1, 1));
			gamePoints[2][1].setPiece(����1, this);
			����1.setLocation(new Point(2, 1));
			gamePoints[3][1].setPiece(����1, this);
			����1.setLocation(new Point(3, 1));
			gamePoints[4][1].setPiece(��ʿ1, this);
			��ʿ1.setLocation(new Point(4, 1));
			gamePoints[5][1].setPiece(����, this);
			����.setLocation(new Point(5, 1));
			gamePoints[6][1].setPiece(��ʿ2, this);
			��ʿ2.setLocation(new Point(6, 1));
			gamePoints[7][1].setPiece(����2, this);
			����2.setLocation(new Point(7, 1));
			gamePoints[8][1].setPiece(����2, this);
			����2.setLocation(new Point(8, 1));
			gamePoints[9][1].setPiece(����2, this);
			����2.setLocation(new Point(9, 1));
			gamePoints[2][3].setPiece(����1, this);
			����1.setLocation(new Point(2, 3));
			gamePoints[8][3].setPiece(����2, this);
			����2.setLocation(new Point(8, 3));
			gamePoints[1][4].setPiece(����1, this);
			����1.setLocation(new Point(1, 4));
			gamePoints[3][4].setPiece(����2, this);
			����2.setLocation(new Point(3, 4));
			gamePoints[5][4].setPiece(����3, this);
			����3.setLocation(new Point(5, 4));
			gamePoints[7][4].setPiece(����4, this);
			����4.setLocation(new Point(7, 4));
			gamePoints[9][4].setPiece(����5, this);
			����5.setLocation(new Point(9, 4));

		} else {//
			�쳵1 = new ChessPiece(1, RED_NAME, "܇", redColor, bc, width - 4,
					height - 4, this);
			�쳵2 = new ChessPiece(2, RED_NAME, "܇", redColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(3, RED_NAME, "�R", redColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(4, RED_NAME, "�R", redColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(5, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(6, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(7, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(8, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			��ʿ1 = new ChessPiece(9, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			��ʿ2 = new ChessPiece(10, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			��˧ = new ChessPiece(11, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			���1 = new ChessPiece(12, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			���2 = new ChessPiece(13, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			���3 = new ChessPiece(14, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			���4 = new ChessPiece(15, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);
			���5 = new ChessPiece(16, RED_NAME, "��", redColor, bc, width - 4,
					height - 4, this);

			���� = new ChessPiece(17, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			��ʿ1 = new ChessPiece(18, BLUE_NAME, "ʿ", blueColor, bc, width - 4,
					height - 4, this);
			��ʿ2 = new ChessPiece(19, BLUE_NAME, "ʿ", blueColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(20, BLUE_NAME, "܇", blueColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(21, BLUE_NAME, "܇", blueColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(22, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(23, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(24, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(25, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(26, BLUE_NAME, "�R", blueColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(27, BLUE_NAME, "�R", blueColor, bc, width - 4,
					height - 4, this);
			����1 = new ChessPiece(28, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����2 = new ChessPiece(29, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����3 = new ChessPiece(30, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����4 = new ChessPiece(31, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);
			����5 = new ChessPiece(32, BLUE_NAME, "��", blueColor, bc, width - 4,
					height - 4, this);

			gamePoints[1][10].setPiece(����1, this);
			����1.setLocation(new Point(1, 10));
			gamePoints[2][10].setPiece(����1, this);
			����1.setLocation(new Point(2, 10));
			gamePoints[3][10].setPiece(����1, this);
			����1.setLocation(new Point(3, 10));
			gamePoints[4][10].setPiece(��ʿ1, this);
			��ʿ1.setLocation(new Point(4, 10));

			gamePoints[5][10].setPiece(����, this);
			����.setLocation(new Point(5, 10));

			gamePoints[6][10].setPiece(��ʿ2, this);
			��ʿ2.setLocation(new Point(6, 10));
			gamePoints[7][10].setPiece(����2, this);
			����2.setLocation(new Point(7, 10));
			gamePoints[8][10].setPiece(����2, this);
			����2.setLocation(new Point(8, 10));
			gamePoints[9][10].setPiece(����2, this);
			����2.setLocation(new Point(9, 10));
			gamePoints[2][8].setPiece(����1, this);
			����1.setLocation(new Point(2, 8));
			gamePoints[8][8].setPiece(����2, this);
			����2.setLocation(new Point(8, 8));
			gamePoints[1][7].setPiece(����1, this);
			����1.setLocation(new Point(1, 7));
			gamePoints[3][7].setPiece(����2, this);
			����2.setLocation(new Point(3, 7));
			gamePoints[5][7].setPiece(����3, this);
			����3.setLocation(new Point(5, 7));
			gamePoints[7][7].setPiece(����4, this);
			����4.setLocation(new Point(7, 7));
			gamePoints[9][7].setPiece(����5, this);
			����5.setLocation(new Point(9, 7));

			gamePoints[1][1].setPiece(�쳵1, this);
			�쳵1.setLocation(new Point(1, 1));
			gamePoints[2][1].setPiece(����1, this);
			����1.setLocation(new Point(2, 1));
			gamePoints[3][1].setPiece(����1, this);
			����1.setLocation(new Point(3, 1));
			gamePoints[4][1].setPiece(��ʿ1, this);
			��ʿ1.setLocation(new Point(4, 1));
			gamePoints[5][1].setPiece(��˧, this);
			��˧.setLocation(new Point(5, 1));
			gamePoints[6][1].setPiece(��ʿ2, this);
			��ʿ2.setLocation(new Point(6, 1));
			gamePoints[7][1].setPiece(����2, this);
			����2.setLocation(new Point(7, 1));
			gamePoints[8][1].setPiece(����2, this);
			����2.setLocation(new Point(8, 1));
			gamePoints[9][1].setPiece(�쳵2, this);
			�쳵2.setLocation(new Point(9, 1));
			gamePoints[2][3].setPiece(����1, this);
			����1.setLocation(new Point(2, 3));
			gamePoints[8][3].setPiece(����2, this);
			����2.setLocation(new Point(8, 3));
			gamePoints[1][4].setPiece(���1, this);
			���1.setLocation(new Point(1, 4));
			gamePoints[3][4].setPiece(���2, this);
			���2.setLocation(new Point(3, 4));
			gamePoints[5][4].setPiece(���3, this);
			���3.setLocation(new Point(5, 4));
			gamePoints[7][4].setPiece(���4, this);
			���4.setLocation(new Point(7, 4));
			gamePoints[9][4].setPiece(���5, this);
			���5.setLocation(new Point(9, 4));
		}
	}

	/**
	 * ��������
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 10������
		for (int j = 1; j <= y�᳤; j++) {
			g.drawLine(gamePoints[1][j].x, gamePoints[1][j].y,
					gamePoints[x�᳤][j].x, gamePoints[x�᳤][j].y);
		}

		// 9������
		for (int i = 1; i <= x�᳤; i++) {
			if (i != 1 && i != x�᳤) {
				// �м������
				g.drawLine(gamePoints[i][1].x, gamePoints[i][1].y,
						gamePoints[i][y�᳤ - 5].x, gamePoints[i][y�᳤ - 5].y);
				g.drawLine(gamePoints[i][y�᳤ - 4].x, gamePoints[i][y�᳤ - 4].y,
						gamePoints[i][y�᳤].x, gamePoints[i][y�᳤].y);
			} else {
				// ���ߵ�����
				g.drawLine(gamePoints[i][1].x, gamePoints[i][1].y,
						gamePoints[i][y�᳤].x, gamePoints[i][y�᳤].y);
			}
		}

		// ʿ��б
		g.drawLine(gamePoints[4][1].x, gamePoints[4][1].y, gamePoints[6][3].x,
				gamePoints[6][3].y);
		g.drawLine(gamePoints[6][1].x, gamePoints[6][1].y, gamePoints[4][3].x,
				gamePoints[4][3].y);
		g.drawLine(gamePoints[4][8].x, gamePoints[4][8].y,
				gamePoints[6][y�᳤].x, gamePoints[6][y�᳤].y);
		g.drawLine(gamePoints[4][y�᳤].x, gamePoints[4][y�᳤].y,
				gamePoints[6][8].x, gamePoints[6][8].y);

		// ��ʾ������
		for (int i = 1; i <= x�᳤; i++) {
			g.drawString("" + i, i * unitWidth, unitHeight / 2);
		}

		// ��ʾ������
		for (int j = 1; j <= 10; j++) {
			g.drawString("" + j, unitWidth / 4, j * unitHeight);
		}

		// ���ӡ�����
		g.setFont(new Font("����", Font.PLAIN, 32));
		g.drawString("�h ��", gamePoints[2][5].x, gamePoints[2][5].y + 2
				* unitHeight / 3);
		g.drawString("�� ��", gamePoints[6][5].x, gamePoints[2][5].y + 2
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
				for (int i = 1; i <= x�᳤; i++) {
					for (int j = 1; j <= y�᳤; j++) {
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
	 * �϶���꣬�ֵ��Լ�����ʱ�����ߣ�����ֻ���ƶ�����������
	 */
	public void mouseDragged(MouseEvent e) {
		if (myTurn) {
			ChessPiece piece = null;
			if (e.getSource() instanceof ChessPiece) {
				piece = (ChessPiece) e.getSource();
				if (piece.getName().equals(playerName)) {
					this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				// ֻ���ƶ��Լ���������
				if (piece.getName().equals(playerName)) {
					move = true;
				}

				e = SwingUtilities.convertMouseEvent(piece, e, this);
			}

			int x = 0, y = 0;// ����
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
	 * ��Ӧ����ͷ��¼�
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
				for (int i = 1; i <= x�᳤; i++) {
					for (int j = 1; j <= y�᳤; j++) {
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
				if (gamePoints[m][n].isPiece()) {// ������
					Color c = (gamePoints[m][n].getPiece()).getForeColor();
					if (pieceColor.getRGB() == c.getRGB()) {// ���ӵ���ɫ��ͬ
						piece.setLocation(startX, startY);
						(gamePoints[startI][startJ]).setHasPiece(true);
					} else {
						// ��ɫ��ͬ�������Ƿ���Ϲ���
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

							/** �������ݰ������߶Է��Լ����ӵ��ƶ� */
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

								System.out.println(playerName + "��Ҫ������Ϣ��");
							} catch (Exception ex) {
								System.out.println("�����ƶ���Ϣʧ�ܣ�");
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
				// �յ�û������
				else {
					boolean canMove = gameRule.movePieceRule(piece, startI,
							startJ, m, n);
					if (canMove) {
						gamePoints[m][n].setPiece(piece, this);
						piece.setLocation(new Point(m, n));
						(gamePoints[startI][startJ]).setHasPiece(false);

						/** ��¼���ӵ��ƶ� */
						MoveRecord record = new MoveRecord();
						MoveStep moveStep = new MoveStep(new Point(startI,
								startJ), new Point(m, n));
						record.setMoveStep(moveStep);
						record.setEatedPieceId(0);
						record.setMovePieceId(piece.getId());
						records.addRecord(record);

						/** �������ݰ������߶Է��Լ����ӵ��ƶ� */
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
							System.out.println("�����ƶ���Ϣʧ�ܣ�");
							ex.printStackTrace();
						}
						// ����
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
		 * �����̣߳����������Ϣ
		 */
		public void run() {
			try {
				// ���շ������˵�����
				player = ((DataPacket) fromServer.readObject()).getPlayer();

				// ���1Or�������2
				if (player == PLAYER1) {
					initChess(unitWidth, unitHeight, new Color(204, 153, 102));

					playerName = RED_NAME;
					text.append("���Ǻ췽��\n");
					gameStatus.setText(SPACE + "�ȴ�����������Ϸ��");
				} else if (player == PLAYER2) {
					initChess(unitWidth, unitHeight, new Color(204, 153, 102));

					playerName = BLUE_NAME;
					text.append("����������\n");
					gameStatus.setText(SPACE + "��ʼ��Ϸ��Ȼ��ȴ��췽��ʼ��");
				}

				String ip = (String) fromServer.readObject();
				System.out.println(playerName + "�Է���ip��ַΪ��" + ip);
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
						// ֪ͨ�췽�������Ѿ�������Ϸ
						toPlayer1.writeObject(packet);
					} else {
						System.out.println("player2Socket == null");
					}
				}

				/* ������Ϸ�� ���Ϸ��ͺͽ�����Ϣ */
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
						// System.out.println("����Ҫ�˳���");
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

	/** ��ʼ���׽��� */
	public void initSocket(Socket socket) {
		this.socket = socket;

		// ���ӵ�������
		// ����һ�����������ӷ������˽�������
		try {
			fromServer = new ObjectInputStream(socket.getInputStream());

			// ����һ����������ӷ������˽�������
			toServer = new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������ҵ��ƶ���Ϣ
	 * 
	 * @param out
	 *            ���������
	 * @param packet
	 *            ���ݰ�
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
				gameStatus.setText(SPACE + "�ȴ��Է����壡");
				System.out.println(playerName + "���ڷ����ƶ���Ϣ��");
			} else if (status == GAME_BACK) {
				// ����һ�Σ���ָ���һ��
				System.out.println(playerName + "���ڷ��ͻ�����Ϣ��");
			}
		}

	}

	/** ����ҽ�����Ϣ��������Ϣ��id���ദ��ͬʱ�������� */
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
			System.out.println("���1Ӯ��");
			// FansUtil.playSound("win.wav");
			running = GAME_PAUSE;
		} else if (status == PLAYER2_WON) {
			System.out.println("���2Ӯ���˱���");
			// FansUtil.playSound("win.wav");
			running = GAME_PAUSE;
		} else if (status == GAME_BACK) {
			// ˳��䷴
			myTurn = !myTurn;
			if (myTurn) {
				gameStatus.setText(SPACE + "�Է�������壡�����ֵ�����ඣ�");
			} else {
				gameStatus.setText(SPACE + "�Է�������壡�ȴ��Է����壡");
			}
			// ��¼�ع�
			records.back();
			// ����
			validate();
			repaint();
		}

		// �յ������ƶ���Ϣ
		else if (status == MOVING) {
			ChessUtils.playSound("moving.wav");
			gameStatus.setText(SPACE + "�ֵ�����ඣ�");
			myTurn = true;
			try {
				receiveMove(packet);
				if (isEatLeader()) {
					gameStatus.setText(SPACE + gameStatus.getText()
							+ ("�Է���������"));
					ChessUtils.playSound("jiangjun.wav");
				}
			} catch (Exception e) {
				System.out.println("�����ƶ���Ϣʧ������");
				e.printStackTrace();
			}

		}

		// ����������Ϸ��Ϣ
		else if (status == PLAYER2_ENTER) {
			blueEnter = true;
			gameStatus.setText(SPACE + "�����Ѿ�������Ϸ���ȴ�����׼����");
			ChessUtils.playSound("global.wav");
		}

		else if (status == GAME_START) {
			if (player == PLAYER1) {
				blueOk = true;
				gameStatus.setText(SPACE + "�����Ѿ���ʼ��Ϸ�������ʼ������Ϸ��");
			} else {
				if (blueOk) {
					gameStatus.setText(SPACE + "�췽�Ѿ���ʼ��Ϸ���ȴ��췽���壡");
				}
			}
		}

		// �յ��˳���Ϣ
		else if (status == GAME_EXIT) {
			if (player == PLAYER1) {
				gameStatus.setText(SPACE + "�����Ѿ��˳���Ϸ��");
			} else {
				gameStatus.setText(SPACE + "�췽�Ѿ��˳���Ϸ��");
			}
			running = GAME_EXIT;
			close();// �ر��׽���
		}

		// �յ���Ͷ����Ϣ
		else if (status == GIVEIN) {
			if (player == PLAYER1) {
				gameStatus.setText(SPACE + "�����Ѿ�Ͷ���������ֹ�Ȼ�вŰ���");
			} else {
				if (blueOk) {
					gameStatus.setText(SPACE + "�췽�Ѿ�Ͷ���������ֹ�Ȼ�вŰ���");
				}
			}
		}

		// �յ��˶Է�����ͣ��Ϣ
		else if (status == GAME_PAUSE) {
			otherIsPause = true;
			if (isPause) {
				gameStatus.setText(SPACE + "���ͶԷ����Ѿ���ͣ����Ϸ��");
			} else {
				gameStatus.setText(SPACE + "�Է���ͣ����Ϸ��");
			}
		}

		else if (status == CONTINUE) {
			otherIsPause = false;
			if (isPause) {
				gameStatus.setText(SPACE + "���Ѿ���ͣ����Ϸ��");
			} else {
				if (myTurn) {
					gameStatus.setText(SPACE + "�ֵ�����ඣ�");
				} else {
					gameStatus.setText(SPACE + "�ȴ��Է����壡");
				}
			}
		}

		// �յ���������Ϣ,��������
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
	 * �����õ�
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
	 * ���֮���������Ҫת�������������߶Գƣ�x���䣬y֮��Ϊ11
	 * 
	 * @param packet
	 *            �յ������ݰ�
	 */
	// ��ȡ��һ����ҵ��ƶ�,���޸Ľ�����ʵ��ͬ��
	private void receiveMove(DataPacket packet) {
		// ��������id��ȡ�ƶ�������
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

		// �����ӱ�ɾ��
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

		// ����
		validate();
		repaint();
	}

	/**
	 * �յ��Է����ƶ���Ϣ���������ж� �Ƿ񽫾�
	 * 
	 * ֻ�г������ں��������н����Ŀ���
	 * 
	 * ֻҪ��һ�����ӿ��Խ����򽫾�Ϊ�棬������������
	 * 
	 * @return �Ƿ񽫾�
	 */
	private boolean isEatLeader() {
		boolean flag = false;

		Point myLeaderLocation;// �Է�����˧��λ��
		Point[] paoLocations;// �ڵ�λ��
		Point[] juLocations;// ܇��λ��
		Point[] maLocations;// ���λ��
		Point[] zuLocations;// ������λ��

		// �ж������Ƿ񽫾�
		if (playerName.equals(RED_NAME)) {
			myLeaderLocation = getMyLeaderLocation("��");
			juLocations = getLocationByCategory("܇");
			paoLocations = getLocationByCategory("��");
			maLocations = getLocationByCategory("�R");
			zuLocations = getLocationByCategory("��");

			int leaderX = (int) myLeaderLocation.getX();
			int leaderY = (int) myLeaderLocation.getY();

			// �ж϶Է���܇�Ƿ񽫾�
			for (int i = 0; i < juLocations.length; i++) {
				int x = (int) juLocations[i].getX();
				int y = (int) juLocations[i].getY();

				int minX = Math.min(x, leaderX);
				int maxX = Math.max(x, leaderX);
				int minY = Math.min(y, leaderY);
				int maxY = Math.max(y, leaderY);

				if ((x == leaderX)) {
					System.out.println("˧��܇�ĺ�������ͬ��");
					int j = 0;
					for (j = minY + 1; j <= maxY - 1; j++) {
						// �м䲻��������
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
					System.out.println("˧��܇����������ͬ��");
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

			// �ж϶Է������Ƿ񽫾�
			for (int i = 0; i < paoLocations.length; i++) {
				int x = (int) paoLocations[i].getX();
				int y = (int) paoLocations[i].getY();

				int minX = Math.min(x, leaderX);
				int maxX = Math.max(x, leaderX);
				int minY = Math.min(y, leaderY);
				int maxY = Math.max(y, leaderY);

				// ������ӣ��м�ֻ�ܸ���һ������
				int number = 0;
				// ��ֱ�����ƶ�
				if (x == leaderX) {
					System.out.println("˧���ڵĺ�������ͬ��");
					int j = 0;
					for (j = minY + 1; j <= maxY - 1; j++) {
						if (gamePoints[x][j].isPiece()) {
							number++;
						}
					}
					System.out.println("�ں�˧֮����" + number + "������");
					// �м�����ֻ��һ������ʱ���򽫾�
					if (number == 1) {
						flag = true;
						return flag;
					} else {
						flag = false;
					}
				}
				// ˮƽ�����ƶ�
				else if (y == leaderY) {
					System.out.println("˧���ڵ���������ͬ��");
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

			// �ж϶Է������Ƿ񽫾�
			for (int i = 0; i < maLocations.length; i++) {
				int x = (int) maLocations[i].getX();
				int y = (int) maLocations[i].getY();

				int xAxle = Math.abs(x - leaderX);
				int yAxle = Math.abs(y - leaderY);

				// X�����ƶ�2����Y�����ƶ�1��
				if (xAxle == 2 && yAxle == 1) {
					if (leaderX > x) {
						// ������
						if (gamePoints[x + 1][y].isPiece()) {
							flag = false;
						} else {
							flag = true;
							return flag;
						}
					} else if (leaderX < x) {
						// ������
						if (gamePoints[x - 1][y].isPiece()) {
							flag = false;
						} else {
							flag = true;
							return flag;
						}
					}

				}

				// X�����ƶ�1����Y�����ƶ�2��
				else if (xAxle == 1 && yAxle == 2) {
					if (leaderY > y) {
						// ������
						if (gamePoints[x][y + 1].isPiece()) {
							flag = false;
						} else {
							flag = true;
							return flag;
						}
					}
					if (leaderY < y) {
						// ������
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
			// �ж����������Ƿ񽫾�
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

		// �жϺ췽�Ƿ񽫾�
		else if (playerName.equals(BLUE_NAME)) {
			myLeaderLocation = getMyLeaderLocation("��");
			juLocations = getLocationByCategory("܇");
			paoLocations = getLocationByCategory("��");
			maLocations = getLocationByCategory("�R");
			zuLocations = getLocationByCategory("��");

			int leaderX = (int) myLeaderLocation.getX();
			int leaderY = (int) myLeaderLocation.getY();

			// �ж϶Է���܇�Ƿ񽫾�
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
						// �м䲻��������
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

			// �ж϶Է������Ƿ񽫾�
			for (int i = 0; i < paoLocations.length; i++) {
				int x = (int) paoLocations[i].getX();
				int y = (int) paoLocations[i].getY();

				int minX = Math.min(x, leaderX);
				int maxX = Math.max(x, leaderX);
				int minY = Math.min(y, leaderY);
				int maxY = Math.max(y, leaderY);

				// ������ӣ��м�ֻ�ܸ���һ������
				int number = 0;
				// ��ֱ�����ƶ�
				if (x == leaderX) {
					int j = 0;
					for (j = minY + 1; j <= maxY - 1; j++) {
						if (gamePoints[x][j].isPiece()) {
							number++;
						}
					}

					// �м�����ֻ��һ������ʱ���򽫾�
					if (number == 1) {
						flag = true;
						return flag;
					} else {
						flag = false;
					}
				}
				// ˮƽ�����ƶ�
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

			// �ж϶Է������Ƿ񽫾�
			for (int i = 0; i < maLocations.length; i++) {
				int x = (int) maLocations[i].getX();
				int y = (int) maLocations[i].getY();

				int xAxle = Math.abs(x - leaderX);
				int yAxle = Math.abs(y - leaderY);

				// X�����ƶ�2����Y�����ƶ�1��
				if (xAxle == 2 && yAxle == 1) {
					if (leaderX > x) {
						// ������
						if (gamePoints[x + 1][y].isPiece()) {
							flag = false;
						} else {
							flag = true;
							return flag;
						}
					} else if (leaderX < x) {
						// ������
						if (gamePoints[x - 1][y].isPiece()) {
							flag = false;
						} else {
							flag = true;
							return flag;
						}
					}

				}

				// X�����ƶ�1����Y�����ƶ�2��
				else if (xAxle == 1 && yAxle == 2) {
					if (leaderY > y) {
						// ������
						if (gamePoints[x][y + 1].isPiece()) {
							flag = false;
						} else {
							flag = true;
							return flag;
						}
					}
					if (leaderY < y) {
						// ������
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
			// �ж����������Ƿ񽫾�
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
	 * �������ӵ����ࣨ�����䡢���ں�܇)��ȡ�Է����ӵ�λ�ã�
	 * 
	 * @param category
	 *            ���ӵ�����
	 * @return
	 */
	private Point[] getLocationByCategory(String category) {
		boolean b = category.equals("��") || category.equals("��");
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
				// ������
				if (piece != null) {
					boolean flag = piece.getName().equals(playerName);
					// �����ǶԷ�������
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
	 * �������ֻ�ȡ˧�򽫵�λ��
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
	 * �������ӵ�λ�û�����ӵ�����
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
	 * ����˳�����
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
	 * �ر��׽���
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
