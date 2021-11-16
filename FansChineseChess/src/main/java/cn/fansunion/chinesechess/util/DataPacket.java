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
package cn.fansunion.chinesechess.util;

import java.io.Serializable;

import cn.fansunion.chinesechess.core.MoveStep;

/**
 * 数据包
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
	
//	private int  turn;//0红方走，1蓝方走
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
