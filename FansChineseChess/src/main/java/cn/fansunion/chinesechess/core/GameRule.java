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

import cn.fansunion.chinesechess.util.Constants;

/**
 * ��Ϸ����
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class GameRule implements Constants {
	ChessBoard board = null;

	ChessPoint gamePoints[][];

	public GameRule(ChessBoard board, ChessPoint gamePoints[][]) {
		this.board = board;
		this.gamePoints = gamePoints;
	}

	/**
	 * �ƶ����ӵĹ���
	 * 
	 * ��������ʱ��Υ�����������߷������
	 * 
	 * ������¼�����if��ǰ�ߣ�С�����¼������ܿ���
	 * 
	 * @param piece
	 *            ��Ҫ�ƶ�������
	 * @param startX��������
	 * @param startY���������
	 * @param endX�յ������
	 * @param endY�յ�������
	 * @return
	 */
	public boolean movePieceRule(ChessPiece piece, int startX, int startY,
			int endX, int endY) {
		
		int minX = Math.min(startX, endX);
		int maxX = Math.max(startX, endX);
		int minY = Math.min(startY, endY);
		int maxY = Math.max(startY, endY);

		// �Ƿ�����ƶ�
		boolean canMove = false;

		// ���ӵ����
		String category = piece.getCategory();

		// ܇�Ĺ���܇��ֱ
		if (category.equals("܇")) {
			if (startX == endX) {// ������
				int j = 0;
				for (j = minY + 1; j <= maxY - 1; j++) {
					// �м䲻��������
					if (gamePoints[startX][j].isPiece()) {
						canMove = false;
						break;
					}
				}
				if (j == maxY) {
					canMove = true;
				}
			} else if (startY == endY) {// ������
				int i = 0;
				for (i = minX + 1; i <= maxX - 1; i++) {
					if (gamePoints[i][startY].isPiece()) {
						canMove = false;
						break;
					}
				}
				if (i == maxX) {
					canMove = true;
				}
			} else {
				canMove = false;
			}
		}

		// ��Ĺ�����̤��,��������4�����
		else if (category.equals("�R")) {
			int xAxle = Math.abs(startX - endX);
			int yAxle = Math.abs(startY - endY);

			// X�����ƶ�2����Y�����ƶ�1��
			if (xAxle == 2 && yAxle == 1) {
				if (endX > startX) {
					// ������
					if (gamePoints[startX + 1][startY].isPiece()) {
						canMove = false;
					} else {
						canMove = true;
					}
				}
				if (endX < startX) {
					// ������
					if (gamePoints[startX - 1][startY].isPiece()) {
						canMove = false;
					} else {
						canMove = true;
					}
				}

			}

			// X�����ƶ�1����Y�����ƶ�2��
			else if (xAxle == 1 && yAxle == 2) {
				if (endY > startY) {
					// ������
					if (gamePoints[startX][startY + 1].isPiece()) {
						canMove = false;
					} else {
						canMove = true;
					}
				}
				if (endY < startY) {
					// ������
					if (gamePoints[startX][startY - 1].isPiece()) {
						canMove = false;
					} else {
						canMove = true;
					}
				}

			} else {
				canMove = false;
			}
		}

		// ��Ĺ��� �����������������ñ仯����ֵ������2
		else if (category.equals("��") || category.equals("��")) {
			int centerI = (startX + endX) / 2;
			int centerJ = (startY + endY) / 2;
			int xAxle = Math.abs(startX - endX);
			int yAxle = Math.abs(startY - endY);

			if (xAxle == 2 && yAxle == 2 && endY >= 6) {
				// ������
				if (gamePoints[centerI][centerJ].isPiece()) {
					canMove = false;
				} else {
					canMove = true;
				}
			} else {
				canMove = false;
			}
		}

		// �ڵĹ���
		else if (category.equals("��")) {
			// ������ӣ��м�ֻ�ܸ���һ������
			int number = 0;
			// ��ֱ�����ƶ�
			if (startX == endX) {
				int j = 0;
				for (j = minY + 1; j <= maxY - 1; j++) {
					if (gamePoints[startX][j].isPiece()) {
						number++;
					}
				}

				// �м�û�����ӣ����ܳ���
				if (number == 0 && !gamePoints[endX][endY].isPiece()) {
					canMove = true;
				}

				// �м���һ�����ӣ������һ����
				else if (number == 1) {
					if (gamePoints[endX][endY].isPiece()) {
						canMove = true;
					}
				}

				// �м����һ�����ӣ������ƶ�
				else if (number > 1) {
					canMove = false;
				}

			}
			// ˮƽ�����ƶ�
			else if (startY == endY) {
				int i = 0;
				for (i = minX + 1; i <= maxX - 1; i++) {
					System.out.println("i=" + i + " starty=" + startY);
					System.out.println((gamePoints == null) + "1");
					System.out.println((gamePoints[i] == null) + "2");
					System.out.println((gamePoints[i][startY] == null) + "3");
					if (gamePoints[i][startY].isPiece()) {
						number++;
					}
				}
				if (number > 1) {
					canMove = false;
				} else if (number == 1) {
					if (gamePoints[endX][endY].isPiece()) {
						canMove = true;
					}
				} else if (number == 0 && !gamePoints[endX][endY].isPiece()) {
					canMove = true;
				}
			} else {
				canMove = false;
			}
		}

		// �����ƶ�����
		else if (category.equals("��") || category.equals("��")) {
			int xAxle = Math.abs(startX - endX);
			// int yAxle = Math.abs(startJ - endJ);

			// ��û�й���
			if (endY >= 6) {
				if (startY - endY == 1 && xAxle == 0) {
					canMove = true;
				} else {
					canMove = false;
				}
			}

			// �������ˣ����ӵı�����ͷ
			else if (endY <= 5) {
				if ((startY - endY == 1) && (xAxle == 0)) {
					canMove = true;
				} else if ((endY - startY == 0) && (xAxle == 1)) {
					canMove = true;
				} else {
					canMove = false;
				}
			}
		}

		/*
		 * ʿ�Ĺ�����һ����Χ�ڣ�������������
		 * 
		 * ��ʿ��4<=x<=6,8<=y<=10
		 * 
		 * ��ʿ��4<=x<=6,1<=y<=3
		 * 
		 * ʿ��б
		 */
		else if (category.equals("ʿ") || category.equals("��")) {
			int xAxle = Math.abs(startX - endX);
			int yAxle = Math.abs(startY - endY);
			if (xAxle == 1 && yAxle == 1 && endX <= 6 && endX >= 4) {
				if (endY >= 8 && endY <= 10) {
					canMove = true;
				}
			} else {
				canMove = false;
			}
		}

		/*
		 * ˧�ͽ��Ĺ�����һ����Χ�ڣ�������������
		 * 
		 * 4<=x<=6,8<=y<=10 һ��һ����ֱ��
		 */
		else if ((category.equals("��")) || (category.equals("��"))) {
			int xAxle = Math.abs(startX - endX);
			int yAxle = Math.abs(startY - endY);
			// ���������������
			if (endX <= 6 && endX >= 4) {
				// һ��ֻ���ƶ�һ��
				if ((xAxle == 1 && yAxle == 0) || (xAxle == 0 && yAxle == 1)) {
					// ˧�����������������
					if (endY >= 8 && endY <= 10) {
						canMove = true;
					}
				} else {
					canMove = false;
				}
			} else {
				canMove = false;
			}
		}

		return canMove;

	}
}
