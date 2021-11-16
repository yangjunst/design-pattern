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

import java.io.Serializable;

/**
 * 棋子点
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
	 * 设置棋子的宽度、高度和位置(坐标表示)
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
	 * 删除棋子
	 * @param piece
	 * @param board
	 */
	public void removePiece(ChessPiece piece, ChessBoard board) {
		board.remove(piece);
		board.validate();
		hasPiece = false;
	}
}
