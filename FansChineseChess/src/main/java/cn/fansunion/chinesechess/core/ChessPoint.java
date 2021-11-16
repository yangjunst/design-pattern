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

import java.io.Serializable;

/**
 * ���ӵ�
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 *
 */
public class ChessPoint implements Serializable{
	
	private static final long serialVersionUID = 261L;

	int x, y;

	boolean hasPiece;

	private ChessPiece piece = null;

	public ChessPoint(int x, int y, boolean hasPiece) {
		this.x = x;
		this.y = y;
		this.hasPiece = hasPiece;
	}

	public boolean isPiece() {
		return hasPiece;
	}

	public void setHasPiece(boolean hasPiece) {
		this.hasPiece = hasPiece;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * �������ӵĿ�ȡ��߶Ⱥ�λ��(�����ʾ)
	 * @param piece
	 * @param board
	 */
	public void setPiece(ChessPiece piece, ChessBoard board) {
		this.piece = piece;
		board.add(piece);
		int w = (board.unitWidth);
		int h = (board.unitHeight);
		piece.setBounds(x - w / 2, y - h / 2, w, h);
		hasPiece = true;
		board.validate();
	}

	public ChessPiece getPiece() {
		return piece;
	}

	/**
	 * ɾ������
	 * @param piece
	 * @param board
	 */
	public void removePiece(ChessPiece piece, ChessBoard board) {
		board.remove(piece);
		board.validate();
		hasPiece = false;
	}
}
