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
 * 中国象棋对战平台服务器
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
		 * 首先设置GUI外观样式：本地系统样式
		 */
		String lookAndFeel = null;
		lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			System.out.println("设置本地外观出错！");
			e.printStackTrace();
		}

		ChessServer serverFrame = new ChessServer();
		serverFrame.setLocationRelativeTo(null);
		serverFrame.work();
		
		
	}

	/**
	 * 构造函数，初始化用户界面
	 */
	public ChessServer() {
		// 创建一个滚动条来容纳文本域
		JScrollPane scrollPane = new JScrollPane(jtaLog);
		// 将滚动条增加到框架中
		add(scrollPane, BorderLayout.CENTER);
		jtaLog.setFont(new Font("宋体", Font.PLAIN, 16));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setTitle("中国象棋对战平台服务器");
		setVisible(true);

	}

	/**
	 * 服务器端开始监听10000端口，等待玩家加入会话
	 * 
	 */
	private void work() {
		try {
			// 创建一个服务器端套接字
			ServerSocket serverSocket = new ServerSocket(3000);
			jtaLog.append(ChessUtils.getStringDate() + ": 服务器开始监听端口 3000\n");

			// 会话号
			int sessionNo = 1;

			// 准备为每2个玩家创建一个会话
			while (true) {
				jtaLog.append(ChessUtils.getStringDate() + ": 等待用户加入会话 "
						+ sessionNo + '\n');

				// 第1个玩家连接到服务器
				Socket player1 = serverSocket.accept();

				jtaLog.append(ChessUtils.getStringDate() + ": 玩家1 加入会话 "
						+ sessionNo + '\n');
				String player1IPAddress = player1.getInetAddress().getHostAddress();
				jtaLog.append("玩家1的IP地址："
						+ player1IPAddress + '\n');

				// 通知第一个玩家是"玩家1"
				ObjectOutputStream oos1 = new ObjectOutputStream(player1
						.getOutputStream());
				DataPacket packet1 = new DataPacket();
				packet1.setPlayer(PLAYER1);
				oos1.writeObject(packet1);
				// oos1.flush();

				// 第2个玩家连接到服务器
				Socket player2 = serverSocket.accept();

				
				jtaLog.append(ChessUtils.getStringDate() + ": 玩家2加入会话 "
						+ sessionNo + '\n');
				String player2IPAddress = player2.getInetAddress().getHostAddress();
				jtaLog.append("玩家2的 IP 地址："
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
				jtaLog.append(ChessUtils.getStringDate() + ": 为会话 "
						+ (sessionNo++) + "启动一个线程" + '\n');

				ObjectInputStream ois1 = new ObjectInputStream(player1
						.getInputStream());
				ObjectInputStream ois2 = new ObjectInputStream(player2
						.getInputStream());
				// 为2个玩家的会话增加一个任务
				HandleASession task = new HandleASession(player1, player2);
				task.setOis1(ois1);
				task.setOis2(ois2);
				task.setOos1(oos1);
				task.setOos2(oos2);

				// 开始新的线程
				//new Thread(task).start();
				threadPool.execute(task);
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}
