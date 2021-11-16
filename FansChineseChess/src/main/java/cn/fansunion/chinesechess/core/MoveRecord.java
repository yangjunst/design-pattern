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
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 *
 */
public class MoveRecord implements Serializable {

	private static final long serialVersionUID = 259L;

    private int eatedPieceId;//�������ӵ�ID
	
	 private int movePieceId;
	
    private MoveStep moveStep;//�ƶ�����Ϣ
	
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
