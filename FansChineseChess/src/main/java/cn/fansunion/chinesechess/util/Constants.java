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
package cn.fansunion.chinesechess.util;

import java.awt.Color;
/**
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 *
 */
public interface Constants {

	public static final String RED_NAME ="�췽";
	
	public static final String BLUE_NAME ="����";
	
	public static int PLAYER1 = 1; // Indicate player 1

	public static int PLAYER2 = 2; // Indicate player 2

	public static int PLAYER1_WON = 1; // Indicate player 1 won

	public static int PLAYER2_WON = 2; // Indicate player 2 won

	public static int RUNNING= 3; // Indicate a draw

	public static int CONTINUE = 4; // Indicate to continue
	
	public static int GAME_EXIT = 0;
	
	public static int GAME_START = 6;
	
	public static int GAME_PAUSE = 7;
	
	public static int MOVING = 8;
	
	public static int GAME_BACK = 9;//����
	
	public static String SPACE = "    ";
	
	public static int PORT = 12345;
	
	public static int PLAYER2_ENTER = 158;
	
	public static int GIVEIN = 159;
	
	public static int MESSAGE = 201;
	
	public static int  PIECE_WIDTH = 45;
	
	public static int PIECE_HEIGHT = 45;
	
	public static Color bc = new Color(204, 153, 102);
}
