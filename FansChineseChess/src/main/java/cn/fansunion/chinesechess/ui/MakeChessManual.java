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
import java.awt.Point;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cn.fansunion.chinesechess.core.ChessBoard;
import cn.fansunion.chinesechess.core.ChessPiece;
import cn.fansunion.chinesechess.core.ChessPoint;
import cn.fansunion.chinesechess.core.MoveRecord;
import cn.fansunion.chinesechess.core.MoveStep;
import cn.fansunion.chinesechess.util.Constants;

/**
 * ��¼��ֵı仯
 * 
  * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class MakeChessManual extends JPanel implements Constants {

	private static final long serialVersionUID = 1L;

	JTextArea text = null;

	JScrollPane scroll = null;

	ChessBoard board = null;

	ChessPoint[][] gamePoints;

	private LinkedList<MoveRecord> records;

	/**
	 * ���캯������ʼ������
	 */
	public MakeChessManual(ChessBoard board, ChessPoint[][] gamePoints) {
		this.board = board;
		this.gamePoints = gamePoints;
		records = new LinkedList<MoveRecord>();

		text = new JTextArea();
		text.setRows(10);
		text.setEditable(false);
		scroll = new JScrollPane(text);
		text.setFont(new Font("����", Font.PLAIN, 16));

		setLayout(new BorderLayout());
		add(scroll, BorderLayout.CENTER);
	}

	/**
	 * ��¼�ƶ����Ӻͱ������ӵ���Ϣ���Լ��ƶ���Ϣ
	 */
	public void addRecord(MoveRecord record) {
		records.add(record);
		
		ChessPiece piece = board.getChessPieceById(record.getMovePieceId());
		MoveStep moveStep = record.getMoveStep();
		Point pStart = moveStep.getPStart();
		Point pEnd = moveStep.getPEnd();

		String name = piece.getName();
		String category = piece.getCategory();
		String desc = name + ": " + category + "(" + (int) pStart.getX() + ","
				+ ((int) pStart.getY()) + ")��(" + (int) pEnd.getX() + ","
				+ ((int) pEnd.getY()) + ")";
		text.append(desc + "\n");
		if (piece.getName().equals(BLUE_NAME)) {
			text.append("\n");
		}
	}

	/**
	 * ������е������ƶ���¼
	 * 
	 * @return
	 */
	public LinkedList<MoveRecord> getRecords() {
		return records;
	}

	public void setRecords(LinkedList<MoveRecord> records) {
		this.records = records;
	}

	/**
	 * ����
	 * 
	 */
	public void back() {
		int size = records.size();

		MoveRecord record = new MoveRecord();
		MoveStep moveStep;

		ChessPiece eatedPiece;
		int startI = 0;
		int startJ = 0;
		int endI = 0;
		int endJ = 0;

		if (size > 0) {
			// ɾ�����������һ��Ԫ��
			record = (MoveRecord) records.removeLast();
			eatedPiece = getEatedPiece(record.getEatedPieceId());

			moveStep = record.getMoveStep();
			startI = moveStep.pStart.x;
			startJ = moveStep.pStart.y;
			endI = moveStep.pEnd.x;
			endJ = moveStep.pEnd.y;

			// ��һ��û�г�����
			if (eatedPiece == null) {
				System.out.println("û������");

				ChessPiece piece = gamePoints[endI][endJ].getPiece();

				gamePoints[startI][startJ].setPiece(piece, board);
				(gamePoints[endI][endJ]).setHasPiece(false);

			}
			// ��һ����������
			else {
				ChessPiece piece = gamePoints[endI][endJ].getPiece();
				gamePoints[startI][startJ].setPiece(piece, board);

				gamePoints[endI][endJ].setPiece(eatedPiece, board);
				(gamePoints[endI][endJ]).setHasPiece(true);

			}
		}
	}

	private ChessPiece getEatedPiece(int eatedPieceId) {

		Color redColor = Color.RED;
		Color blueColor = Color.blue;
		switch (eatedPieceId) {
		case 1:
			return new ChessPiece(1, RED_NAME, "܇", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 2:
			return new ChessPiece(2, RED_NAME, "܇", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 3:
			return new ChessPiece(3, RED_NAME, "�R", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 4:
			return new ChessPiece(4, RED_NAME, "�R", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 5:
			return new ChessPiece(5, RED_NAME, "��", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 6:
			return new ChessPiece(6, RED_NAME, "��", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 7:
			return new ChessPiece(7, RED_NAME, "��", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 8:

			return new ChessPiece(8, RED_NAME, "��", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 9:
			return new ChessPiece(9, RED_NAME, "��", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 10:
			return new ChessPiece(10, RED_NAME, "��", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 11:
			return new ChessPiece(11, RED_NAME, "��", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 12:
			return new ChessPiece(12, RED_NAME, "��", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 13:
			return new ChessPiece(13, RED_NAME, "��", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 14:
			return new ChessPiece(14, RED_NAME, "��", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 15:
			return new ChessPiece(15, RED_NAME, "��", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 16:
			return new ChessPiece(16, RED_NAME, "��", redColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 17:

			return new ChessPiece(17, BLUE_NAME, "��", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 18:
			return new ChessPiece(18, BLUE_NAME, "ʿ", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 19:
			return new ChessPiece(19, BLUE_NAME, "ʿ", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 20:
			return new ChessPiece(20, BLUE_NAME, "܇", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 21:
			return new ChessPiece(21, BLUE_NAME, "܇", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 22:
			return new ChessPiece(22, BLUE_NAME, "��", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 23:
			return new ChessPiece(23, BLUE_NAME, "��", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 24:
			return new ChessPiece(24, BLUE_NAME, "��", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 25:
			return new ChessPiece(25, BLUE_NAME, "��", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 26:
			return new ChessPiece(26, BLUE_NAME, "�R", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 27:
			return new ChessPiece(27, BLUE_NAME, "�R", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 28:
			return new ChessPiece(28, BLUE_NAME, "��", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 29:
			return new ChessPiece(29, BLUE_NAME, "��", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 30:
			return new ChessPiece(30, BLUE_NAME, "��", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 31:
			return new ChessPiece(31, BLUE_NAME, "��", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		case 32:
			return new ChessPiece(32, BLUE_NAME, "��", blueColor, bc,
					PIECE_WIDTH - 4, PIECE_HEIGHT - 4, board);

		default:
			break;
		}
		return null;
	}

	public JTextArea getText() {
		return text;
	}
}
