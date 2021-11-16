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
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 *
 */
public class MoveRecord implements Serializable {

	private static final long serialVersionUID = 259L;

    private int eatedPieceId;//被吃棋子的ID
	
	 private int movePieceId;
	
    private MoveStep moveStep;//移动的信息
	
	public MoveRecord (){
		moveStep = null;
	}

	public MoveStep getMoveStep() {
		return moveStep;
	}

	public int getEatedPieceId() {
		return eatedPieceId;
	}

	public void setEatedPieceId(int eatedPieceId) {
		this.eatedPieceId = eatedPieceId;
	}

	public int getMovePieceId() {
		return movePieceId;
	}

	public void setMovePieceId(int movePieceId) {
		this.movePieceId = movePieceId;
	}

	public void setMoveStep(MoveStep moveStep) {
		this.moveStep = moveStep;
	}
}
