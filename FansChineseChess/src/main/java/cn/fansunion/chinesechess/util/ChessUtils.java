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
 * ������
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class ChessUtils {
	/**
	 * ��ȡ��ǰ��ʱ��
	 * 
	 * @return ���ַ�������ʽ����
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * ��ý������ڣ���ʽΪ2010��3��4�� ������
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
	 * @return Image�����null
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
	 * ��������
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
	 * �������֣���������Ŀ¼�µ������ļ�
	 * 
	 * @param name
	 *            �����ļ�������(����׺)
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
