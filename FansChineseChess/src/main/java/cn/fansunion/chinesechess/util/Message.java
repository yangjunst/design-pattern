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

/**
 * 消息类，封装消息信息
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 *
 */
public class Message implements Serializable{
	
	private static final long serialVersionUID = 124L;

	private String content;

	private String date;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
