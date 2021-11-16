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

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * 工具类
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class ChessUtils {
	/**
	 * 获取当前的时间
	 * 
	 * @return 以字符串的形式返回
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获得今日日期，格式为2010年3月4日 星期四
	 */
	public static String getStringDate2() {
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		String today = dateFormat.format(calendar.getTime());

		return today;
	}

	/**
	 * 
	 * @return Image对象或null
	 */
	public static Image getImage() {
		URL imgURL = ChessUtils.class.getResource("img/chess.jpg");
		if (imgURL != null) {
			return new ImageIcon(imgURL).getImage();
		} else {
			return null;
		}
	}

	/**
	 * 播放声音
	 * 
	 * @param name
	 */
	public static void playSound2(String name) {
		String path = "";
		try {
			URL url = ChessUtils.class.getResource("");
			// System.out.println(url+"sound/global.wav");
			if (url != null) {
				// System.out.println(url.getPath());
				path = url.getPath() + "sounds/" + name;
			}

			InputStream is = new FileInputStream(path);
			AudioStream as = new AudioStream(is);
			AudioPlayer.player.start(as);
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	/**
	 * 根据名字，播放声音目录下的声音文件
	 * 
	 * @param name
	 *            声音文件的名字(带后缀)
	 */
	public static void playSound(String name) {
		String path = "";
		path = "sounds/" + name;
		File file = new File(path);
		InputStream is;
		try {
			is = new FileInputStream(file.getAbsolutePath());
			AudioStream as = new AudioStream(is);
			AudioPlayer.player.start(as);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public char numberToLetter(int n) {
		char c = '\0';
		switch (n) {
		case 1:
			c = 'A';
			break;
		case 2:
			c = 'B';
			break;
		case 3:
			c = 'C';
			break;
		case 4:
			c = 'D';
			break;
		case 5:
			c = 'E';
			break;
		case 6:
			c = 'F';
			break;
		case 7:
			c = 'G';
			break;
		case 8:
			c = 'H';
			break;
		case 9:
			c = 'I';
			break;
		case 10:
			c = 'J';
			break;
		}
		return c;
	}

	public static void main(String[] args) {
		ChessUtils.playSound("jiangjun.wav");
	}
}
