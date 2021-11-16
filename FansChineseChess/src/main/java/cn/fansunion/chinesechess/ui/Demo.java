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
package cn.fansunion.chinesechess.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cn.fansunion.chinesechess.core.ChessBoard;
import cn.fansunion.chinesechess.core.ChessPiece;
import cn.fansunion.chinesechess.core.MoveRecord;
import cn.fansunion.chinesechess.core.MoveStep;
import cn.fansunion.chinesechess.util.Constants;

/**
 * ����������ʾ���
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class Demo extends JPanel implements ActionListener, Runnable, Constants {

	private static final long serialVersionUID = 266L;

	public JButton replay, next, auto, stop;

	LinkedList<MoveRecord> records = null;

	Thread autoThread = null;

	int index = -1;

	ChessBoard board = null;

	JTextArea text;

	JTextField ʱ���� = null;

	int time = 1000;

	String ��ʾ���� = "";

	JSplitPane splitH = null, splitV = null;

	public Demo(ChessBoard board) {
		this.board = board;
		replay = new JButton("������ʾ");
		next = new JButton("��һ��");
		auto = new JButton("�Զ���ʾ");
		stop = new JButton("��ͣ��ʾ");
		autoThread = new Thread(this);
		replay.addActionListener(this);
		next.addActionListener(this);
		auto.addActionListener(this);
		stop.addActionListener(this);
		text = new JTextArea();
		ʱ���� = new JTextField("1");
		setLayout(new BorderLayout());
		JScrollPane pane = new JScrollPane(text);
		JPanel p = new JPanel(new GridLayout(3, 2));
		p.add(next);
		p.add(replay);
		p.add(auto);
		p.add(stop);
		p.add(new JLabel("ʱ����(��)", SwingConstants.CENTER));
		p.add(ʱ����);
		splitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pane, p);
		splitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, board, splitV);
		splitV.setDividerSize(5);
		splitV.setDividerLocation(400);
		splitH.setDividerSize(5);
		splitH.setDividerLocation(460);
		add(splitH, BorderLayout.CENTER);
		validate();
	}

	public void setRecords(LinkedList<MoveRecord> records) {
		this.records = records;
	}

	/**
	 * ��Ӧ�¼�
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		// ��һ��
		if (source == next) {
			index++;
			if (index < records.size()) {
				step(index);
			} else {
				end("������ʾ���");
			}
		}
		// ������ʾ
		else if (source == replay) {
			board = new ChessBoard(9, 10);
			board.player = 1;
			board.initChess(board.unitWidth, board.unitHeight, bc);

			splitH.remove(board);
			splitH.setDividerSize(5);
			splitH.setDividerLocation(460);
			splitH.setLeftComponent(board);
			splitH.validate();
			index = -1;
			text.setText(null);
		}

		// �Զ���ʾ
		else if (source == auto) {
			next.setEnabled(false);
			replay.setEnabled(false);
			try {
				time = 1000 * Integer.parseInt(ʱ����.getText().trim());
			} catch (NumberFormatException ee) {
				time = 1000;
			}

			if (!(autoThread.isAlive())) {
				autoThread = new Thread(this);
				board = new ChessBoard(9, 10);
				board.player = 1;
				board.initChess(board.unitWidth, board.unitHeight, bc);

				splitH.remove(board);
				splitH.setDividerSize(5);
				splitH.setDividerLocation(460);
				splitH.setLeftComponent(board);
				splitH.validate();
				text.setText(null);
				autoThread.start();
			}

		}
		// ֹͣ��ʾ
		else if (source == stop) {
			if (e.getActionCommand().equals("��ͣ��ʾ")) {
				��ʾ���� = "��ͣ��ʾ";
				stop.setText("������ʾ");
				stop.repaint();
			}
			if (e.getActionCommand().equals("������ʾ")) {
				��ʾ���� = "������ʾ";
				autoThread.interrupt();
				stop.setText("��ͣ��ʾ");
				stop.repaint();
			}
		}
	}

	public synchronized void run() {
		int size = records.size();
		for (index = 0; index < size; index++) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			while (��ʾ����.equals("��ͣ��ʾ")) {
				try {
					wait();
				} catch (InterruptedException e) {
					notifyAll();
				}
			}
			step(index);
		}
		if (index >= records.size()) {
			end("������ʾ���");
			next.setEnabled(true);
			replay.setEnabled(true);
		}
	}

	/**
	 * ��ʾindexָ����һ��
	 * 
	 * @param index
	 */
	public void step(int index) {
		MoveRecord moveRecord = records.get(index);
		MoveStep step = moveRecord.getMoveStep();
		Point pStart = step.pStart;
		Point pEnd = step.pEnd;
		int startI = pStart.x;
		int startJ = pStart.y;
		int endI = pEnd.x;
		int endJ = pEnd.y;
		ChessPiece piece = (board.gamePoints)[startI][startJ].getPiece();
		if ((board.gamePoints)[endI][endJ].isPiece()) {
			ChessPiece pieceRemoved = (board.gamePoints)[endI][endJ].getPiece();
			(board.gamePoints)[endI][endJ].removePiece(pieceRemoved, board);
			board.repaint();
			(board.gamePoints)[endI][endJ].setPiece(piece, board);
			(board.gamePoints)[startI][startJ].setHasPiece(false);
			board.repaint();
		} else {
			(board.gamePoints)[endI][endJ].setPiece(piece, board);
			(board.gamePoints)[startI][startJ].setHasPiece(false);

		}
		String category = piece.getCategory();
		String name = piece.getName();

		String desc = name + ": " + category + "(" + (int) pStart.getX() + ","
				+ ((int) pStart.getY()) + ")��(" + (int) pEnd.getX() + ","
				+ ((int) pEnd.getY()) + ")";
		text.append(desc + "\n");

		if (piece.getName().equals(BLUE_NAME))
			text.append("\n");
	}

	/**
	 * ��ʾ����
	 * 
	 * @param message
	 */
	public void end(String message) {
		splitH.remove(board);
		splitH.setDividerSize(5);
		splitH.setDividerLocation(460);
		JLabel label = new JLabel(message);
		label.setFont(new Font("����", Font.BOLD, 60));
		label.setForeground(Color.blue);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		splitH.setLeftComponent(label);
		splitH.validate();
	}
}
