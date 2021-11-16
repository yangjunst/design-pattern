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

import cn.fansunion.chinesechess.util.Constants;

/**
 * 游戏规则
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
	 * 移动棋子的规则
	 * 
	 * 正常下棋时，违反象棋规则的走法会很少
	 * 
	 * 大概率事件放在if的前边，小概率事件尽可能靠后
	 * 
	 * @param piece
	 *            将要移动的棋子
	 * @param startX起点横坐标
	 * @param startY起点纵坐标
	 * @param endX终点横坐标
	 * @param endY终点纵坐标
	 * @return
	 */
	public boolean movePieceRule(ChessPiece piece, int startX, int startY,
			int endX, int endY) {
		
		int minX = Math.min(startX, endX);
		int maxX = Math.max(startX, endX);
		int minY = Math.min(startY, endY);
		int maxY = Math.max(startY, endY);

		// 是否可以移动
		boolean canMove = false;

		// 棋子的类别
		String category = piece.getCategory();

		// 車的规则：車走直
		if (category.equals("車")) {
			if (startX == endX) {// 竖着走
				int j = 0;
				for (j = minY + 1; j <= maxY - 1; j++) {
					// 中间不能有棋子
					if (gamePoints[startX][j].isPiece()) {
						canMove = false;
						break;
					}
				}
				if (j == maxY) {
					canMove = true;
				}
			} else if (startY == endY) {// 横着走
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

		// 马的规则：马踏日,别马腿有4种情况
		else if (category.equals("馬")) {
			int xAxle = Math.abs(startX - endX);
			int yAxle = Math.abs(startY - endY);

			// X方向移动2步，Y方向移动1步
			if (xAxle == 2 && yAxle == 1) {
				if (endX > startX) {
					// 别马腿
					if (gamePoints[startX + 1][startY].isPiece()) {
						canMove = false;
					} else {
						canMove = true;
					}
				}
				if (endX < startX) {
					// 别马腿
					if (gamePoints[startX - 1][startY].isPiece()) {
						canMove = false;
					} else {
						canMove = true;
					}
				}

			}

			// X方向移动1步，Y方向移动2步
			else if (xAxle == 1 && yAxle == 2) {
				if (endY > startY) {
					// 别马腿
					if (gamePoints[startX][startY + 1].isPiece()) {
						canMove = false;
					} else {
						canMove = true;
					}
				}
				if (endY < startY) {
					// 别马腿
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

		// 象的规则： 象飞田，横坐标和纵坐标得变化绝对值必须是2
		else if (category.equals("象") || category.equals("相")) {
			int centerI = (startX + endX) / 2;
			int centerJ = (startY + endY) / 2;
			int xAxle = Math.abs(startX - endX);
			int yAxle = Math.abs(startY - endY);

			if (xAxle == 2 && yAxle == 2 && endY >= 6) {
				// 卡象眼
				if (gamePoints[centerI][centerJ].isPiece()) {
					canMove = false;
				} else {
					canMove = true;
				}
			} else {
				canMove = false;
			}
		}

		// 炮的规则：
		else if (category.equals("炮")) {
			// 如果吃子，中间只能隔着一个棋子
			int number = 0;
			// 垂直方向移动
			if (startX == endX) {
				int j = 0;
				for (j = minY + 1; j <= maxY - 1; j++) {
					if (gamePoints[startX][j].isPiece()) {
						number++;
					}
				}

				// 中间没有棋子，不能吃子
				if (number == 0 && !gamePoints[endX][endY].isPiece()) {
					canMove = true;
				}

				// 中间有一个棋子，必须吃一个子
				else if (number == 1) {
					if (gamePoints[endX][endY].isPiece()) {
						canMove = true;
					}
				}

				// 中间多于一个棋子，不能移动
				else if (number > 1) {
					canMove = false;
				}

			}
			// 水平方向移动
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

		// 兵的移动规则：
		else if (category.equals("兵") || category.equals("卒")) {
			int xAxle = Math.abs(startX - endX);
			// int yAxle = Math.abs(startJ - endJ);

			// 兵没有过河
			if (endY >= 6) {
				if (startY - endY == 1 && xAxle == 0) {
					canMove = true;
				} else {
					canMove = false;
				}
			}

			// 兵过河了，过河的兵不回头
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
		 * 士的规则：在一定范围内，坐标限制如下
		 * 
		 * 红士：4<=x<=6,8<=y<=10
		 * 
		 * 蓝士：4<=x<=6,1<=y<=3
		 * 
		 * 士划斜
		 */
		else if (category.equals("士") || category.equals("仕")) {
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
		 * 帅和将的规则：在一定范围内，坐标限制如下
		 * 
		 * 4<=x<=6,8<=y<=10 一步一步地直走
		 */
		else if ((category.equals("帥")) || (category.equals("將"))) {
			int xAxle = Math.abs(startX - endX);
			int yAxle = Math.abs(startY - endY);
			// 横坐标的限制条件
			if (endX <= 6 && endX >= 4) {
				// 一次只能移动一步
				if ((xAxle == 1 && yAxle == 0) || (xAxle == 0 && yAxle == 1)) {
					// 帅的纵坐标的限制条件
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
