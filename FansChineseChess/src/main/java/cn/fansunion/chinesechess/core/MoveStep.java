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

import java.awt.Point;
import java.io.Serializable;

/**
 * 移动一步
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class MoveStep implements Serializable {

	private static final long serialVersionUID = 260L;

	// 移动的起点
	public Point pStart;

	// 移动的终点
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
