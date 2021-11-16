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

import java.io.Serializable;

import cn.fansunion.chinesechess.core.MoveStep;

/**
 * ���ݰ�
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class DataPacket implements Serializable{

	private static final long serialVersionUID = 123L;

	private int player;

	private int status;

	private int pieceId;

	private int delPieceId;
	
//	private int  turn;//0�췽�ߣ�1������
	private Message message;
	
	private MoveStep moveStep;

	public DataPacket(){
	//	turn = -1;
		delPieceId = 0;
	}
	public MoveStep getMoveStep() {
		return moveStep;
	}

	public void setMoveStep(MoveStep moveStep) {
		this.moveStep = moveStep;
	}

	public int getPieceId() {
		return pieceId;
	}

	public void setPieceId(int pieceId) {
		this.pieceId = pieceId;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public int getDelPieceId() {
		return delPieceId;
	}
	public void setDelPieceId(int delPieceId) {
		this.delPieceId = delPieceId;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
}
