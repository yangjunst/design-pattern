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

import java.awt.Point;
import java.io.Serializable;

/**
 * �ƶ�һ��
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class MoveStep implements Serializable {

	private static final long serialVersionUID = 260L;

	// �ƶ������
	public Point pStart;

	// �ƶ����յ�
	public Point pEnd;

	public MoveStep(Point p1, Point p2) {
		pStart = p1;
		pEnd = p2;
	}

	public Point getPEnd() {
		return pEnd;
	}

	public void setPEnd(Point end) {
		pEnd = end;
	}

	public Point getPStart() {
		return pStart;
	}

	public void setPStart(Point start) {
		pStart = start;
	}
}
