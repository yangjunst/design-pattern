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
package cn.fansunion.chinesechess;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import cn.fansunion.chinesechess.util.Constants;
import cn.fansunion.chinesechess.util.DataPacket;
import cn.fansunion.chinesechess.util.ChessUtils;
import cn.fansunion.chinesechess.util.HandleASession;
import cn.fansunion.chinesechess.util.ThreadPool;

/**
 * �й������սƽ̨������
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class ChessServer extends JFrame implements Constants {

	private static final long serialVersionUID = 1L;

	private static final int POOL_SIZE = 5;

	private JTextArea jtaLog = new JTextArea();

	
	ThreadPool threadPool = new ThreadPool(POOL_SIZE);
	
	public static void main(String[] args) {
		
		/*
		 * ��������GUI�����ʽ������ϵͳ��ʽ
		 */
		String lookAndFeel = null;
		lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			System.out.println("���ñ�����۳���");
			e.printStackTrace();
		}

		ChessServer serverFrame = new ChessServer();
		serverFrame.setLocationRelativeTo(null);
		serverFrame.work();
		
		
	}

	/**
	 * ���캯������ʼ���û�����
	 */
	public ChessServer() {
		// ����һ���������������ı���
		JScrollPane scrollPane = new JScrollPane(jtaLog);
		// �����������ӵ������
		add(scrollPane, BorderLayout.CENTER);
		jtaLog.setFont(new Font("����", Font.PLAIN, 16));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setTitle("�й������սƽ̨������");
		setVisible(true);

	}

	/**
	 * �������˿�ʼ����10000�˿ڣ��ȴ���Ҽ���Ự
	 * 
	 */
	private void work() {
		try {
			// ����һ�����������׽���
			ServerSocket serverSocket = new ServerSocket(3000);
			jtaLog.append(ChessUtils.getStringDate() + ": ��������ʼ�����˿� 3000\n");

			// �Ự��
			int sessionNo = 1;

			// ׼��Ϊÿ2����Ҵ���һ���Ự
			while (true) {
				jtaLog.append(ChessUtils.getStringDate() + ": �ȴ��û�����Ự "
						+ sessionNo + '\n');

				// ��1��������ӵ�������
				Socket player1 = serverSocket.accept();

				jtaLog.append(ChessUtils.getStringDate() + ": ���1 ����Ự "
						+ sessionNo + '\n');
				String player1IPAddress = player1.getInetAddress().getHostAddress();
				jtaLog.append("���1��IP��ַ��"
						+ player1IPAddress + '\n');

				// ֪ͨ��һ�������"���1"
				ObjectOutputStream oos1 = new ObjectOutputStream(player1
						.getOutputStream());
				DataPacket packet1 = new DataPacket();
				packet1.setPlayer(PLAYER1);
				oos1.writeObject(packet1);
				// oos1.flush();

				// ��2��������ӵ�������
				Socket player2 = serverSocket.accept();

				
				jtaLog.append(ChessUtils.getStringDate() + ": ���2����Ự "
						+ sessionNo + '\n');
				String player2IPAddress = player2.getInetAddress().getHostAddress();
				jtaLog.append("���2�� IP ��ַ��"
						+ player2IPAddress + '\n');

				// Notify that the player is Player 2
				ObjectOutputStream oos2 = new ObjectOutputStream(player2
						.getOutputStream());
				DataPacket packet2 = new DataPacket();
				packet2.setPlayer(PLAYER2);
				oos2.writeObject(packet2);
				oos2.flush();

				oos2.writeObject(player1IPAddress);
				oos1.writeObject(player2IPAddress);
				// Display this session and increment session number
				jtaLog.append(ChessUtils.getStringDate() + ": Ϊ�Ự "
						+ (sessionNo++) + "����һ���߳�" + '\n');

				ObjectInputStream ois1 = new ObjectInputStream(player1
						.getInputStream());
				ObjectInputStream ois2 = new ObjectInputStream(player2
						.getInputStream());
				// Ϊ2����ҵĻỰ����һ������
				HandleASession task = new HandleASession(player1, player2);
				task.setOis1(ois1);
				task.setOis2(ois2);
				task.setOos1(oos1);
				task.setOos2(oos2);

				// ��ʼ�µ��߳�
				//new Thread(task).start();
				threadPool.execute(task);
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}
