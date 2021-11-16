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
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import cn.fansunion.chinesechess.core.ChessBoard;
import cn.fansunion.chinesechess.core.MoveRecord;
import cn.fansunion.chinesechess.ui.Demo;
import cn.fansunion.chinesechess.ui.MakeChessManual;
import cn.fansunion.chinesechess.util.Constants;
import cn.fansunion.chinesechess.util.DataPacket;
import cn.fansunion.chinesechess.util.ChessUtils;
import cn.fansunion.chinesechess.util.Message;

/**
 * 中国象棋客户端
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class ChessClient extends JFrame implements Constants,
		ActionListener {

	private static final long serialVersionUID = 101L;

	private JPanel control;

	// 开始、暂停、悔棋、认输、发送消息按钮
	private JButton start, undo, pauseOrContinue, giveIn, send;

	ChessBoard board = null;

	Demo demo = null;

	MakeChessManual records = null;

	JComboBox msgComboBox;

	Container container = null;

	// 菜单及其选项
	JMenuBar bar;

	JMenu fileMenu;

	JMenuItem newGame, saveGame, loadGame, exitGame;

	JMenu settingMenu;

	JMenuItem setting;

	JMenu helpMenu;

	JMenuItem helpContent, aboutGame;

	JMenu chattingMenu;

	JMenuItem chatting;

	JFileChooser fileChooser = null;

	LinkedList<MoveRecord> moveRecords = null;

	// 游戏状态
	JLabel gameStatus;

	// 组合框中的备选消息
	String[] initialMsg = { "见到您真高兴啊", "快点吧，我等到花都谢了", "您的棋走得太好了", "下次再玩吧，我要走了" };

	public JTextArea msgArea;// 便于访问

	public ChessClient(String petName) {
		// 构造菜单
		bar = new JMenuBar();
		fileMenu = new JMenu("中国象棋(G)");

		newGame = new JMenuItem("新游戏");
		saveGame = new JMenuItem("保存游戏");
		loadGame = new JMenuItem("装载游戏");
		exitGame = new JMenuItem("退出游戏");

		//fileMenu.add(newGame);
		fileMenu.add(saveGame);
		fileMenu.add(loadGame);
		fileMenu.add(exitGame);
		bar.add(fileMenu);

		settingMenu = new JMenu("设置");
		setting = new JMenuItem("常用设置");
		settingMenu.add(setting);
	//	bar.add(settingMenu);

		chattingMenu = new JMenu("聊天");
		chatting = new JMenuItem("聊天");
		chattingMenu.add(chatting);
	//	bar.add(chattingMenu);

		helpMenu = new JMenu("帮助");
		helpContent = new JMenuItem("帮助内容");
		aboutGame = new JMenuItem("关于中国象棋");
	//	helpMenu.add(helpContent);
		helpMenu.add(aboutGame);
		bar.add(helpMenu);

		setJMenuBar(bar);

		// 设置快捷键
		saveGame.setAccelerator(KeyStroke.getKeyStroke('S',
				InputEvent.CTRL_DOWN_MASK));
		loadGame.setAccelerator(KeyStroke.getKeyStroke('L',
				InputEvent.CTRL_DOWN_MASK));
		exitGame.setAccelerator(KeyStroke.getKeyStroke('E',
				InputEvent.CTRL_DOWN_MASK));

		helpContent.setAccelerator(KeyStroke.getKeyStroke('H',
				InputEvent.CTRL_DOWN_MASK));
		aboutGame.setAccelerator(KeyStroke.getKeyStroke('A',
				InputEvent.CTRL_DOWN_MASK));
		chatting.setAccelerator(KeyStroke.getKeyStroke('C',
				InputEvent.CTRL_DOWN_MASK));

		/*
		 * saveGame.setAccelerator(
		 * KeyStroke.getKeyStroke('S',InputEvent.CTRL_DOWN_MASK ));
		 * saveGame.setAccelerator(
		 * KeyStroke.getKeyStroke('S',InputEvent.CTRL_DOWN_MASK ));
		 * saveGame.setAccelerator(
		 * KeyStroke.getKeyStroke('S',InputEvent.CTRL_DOWN_MASK ));
		 */

		// 注册监听器
		newGame.addActionListener(this);
		saveGame.addActionListener(this);
		loadGame.addActionListener(this);
		exitGame.addActionListener(this);

		setting.addActionListener(this);

		helpContent.addActionListener(this);
		aboutGame.addActionListener(this);

		chatting.addActionListener(this);

		board = new ChessBoard(9, 10);

		records = board.records;
		container = getContentPane();

		// 构造右边的面板
		JPanel rightPanel = new JPanel(new BorderLayout());

		JPanel recordsPanel = new JPanel(new BorderLayout());

		TitledBorder recordsBorder = new TitledBorder("棋局信息");
		recordsPanel.setBorder(recordsBorder);

		recordsPanel.add(BorderLayout.CENTER, records);

		// 玩家消息面板
		BorderLayout msgLayout = new BorderLayout();
		JPanel msgPanel = new JPanel();
		msgPanel.setLayout(msgLayout);
		TitledBorder msgBorder = new TitledBorder("玩家消息");
		msgPanel.setBorder(msgBorder);

		msgArea = new JTextArea();
		msgArea.setRows(7);
		msgArea.setFont(new Font("宋体", Font.PLAIN, 16));
		msgArea.setEditable(false);
		JScrollPane displayScroll = new JScrollPane(msgArea);

		board.setMsgArea(msgArea);
		// 发送消息
		msgComboBox = new JComboBox(initialMsg);
		msgComboBox.setSelectedIndex(-1);
		msgComboBox.setEditable(true);
		//选择了列表中的消息之后，立即发送
		msgComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String msg = (String) msgComboBox.getSelectedItem();
				if (msg != null && !msg.equals("")) {// 消息非空
					String name = board.getPlayerName();
					DataPacket packet = new DataPacket();
					System.out.println(msg);
					packet.setStatus(MESSAGE);
					Message message = new Message();
					message.setContent(msg);
					message.setDate(ChessUtils.getStringDate());
					packet.setMessage(message);
					// 共用发消息的代码
					try {
						if (name.equals(RED_NAME)) {
							board.sendPacket(board.getToPlayer2(), packet);
						} else if (name.equals(BLUE_NAME)) {
							board.sendPacket(board.getToPlayer1(), packet);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					msgComboBox.setSelectedIndex(-1);
					msgArea.append(name + ":" + msg + "\n");
				}

			}

		});

		// 消息控制面板
		JPanel sendMsgPanel = new JPanel(new BorderLayout());
		sendMsgPanel.add(BorderLayout.NORTH, msgComboBox);
		send = new JButton("发送");

		sendMsgPanel.add(BorderLayout.CENTER, send);

		msgPanel.add(BorderLayout.CENTER, displayScroll);
		msgPanel.add(BorderLayout.SOUTH, sendMsgPanel);

		// 在右边的面板中加入记录面板和消息面板
		rightPanel.add(BorderLayout.NORTH, recordsPanel);
		rightPanel.add(BorderLayout.CENTER, msgPanel);

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
				board, rightPanel);
		split.setDividerSize(5);
		split.setDividerLocation(460);
		container.add(split, BorderLayout.CENTER);

		control = new JPanel();
		BorderLayout layout = new BorderLayout();
		control.setLayout(layout);

		// 功能按钮
		start = new JButton("开始");
		pauseOrContinue = new JButton("暂停");
		undo = new JButton("悔棋");
		giveIn = new JButton("认输");

		/**
		 * 注册监听器
		 */
		start.addActionListener(this);
		pauseOrContinue.addActionListener(this);
		undo.addActionListener(this);
		giveIn.addActionListener(this);
		send.addActionListener(this);

		JPanel controlPanel = new JPanel();

		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
		controlPanel.setLayout(flowLayout);

		TitledBorder controlBorder = new TitledBorder("游戏控制");

		controlPanel.setBorder(controlBorder);

		controlPanel.add(start);
		controlPanel.add(pauseOrContinue);
		controlPanel.add(undo);
		controlPanel.add(giveIn);

		control.add(BorderLayout.CENTER, controlPanel);

		gameStatus = new JLabel();
		gameStatus.setFont(new Font("宋体", Font.PLAIN, 16));
		gameStatus.setForeground(Color.BLACK);

		TitledBorder gameStatusBorder = new TitledBorder("游戏状态");
		gameStatusBorder.setTitleColor(Color.RED);
		gameStatusBorder.setTitleFont(new Font("宋体", Font.PLAIN, 16));
		gameStatus.setToolTipText("游戏状态");
		gameStatus.setBorder(gameStatusBorder);

		control.add(BorderLayout.SOUTH, gameStatus);

		// container.add(gameStatus,BorderLayout.SOUTH);
		container.add(BorderLayout.SOUTH, control);
		board.setGameStaus(gameStatus);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		fileChooser = new JFileChooser();
		setTitle("中国象棋--双人联机对战版");
		setSize(670, 650);
		setIconImage(ChessUtils.getImage());
		this.setResizable(false);// 不允许修改界面的大小
		container.validate();
		validate();

	}

	/**
	 * 连接到服务器
	 */
	public boolean connectToServer(String host) {
		Socket socket = null;
		try {
			// 创建一个客户端套接字来连接到服务器端
			socket = new Socket(host, 10000);

		} catch (Exception ex) {
			System.err.println(ex);
		}

		if (socket != null) {
			board.initSocket(socket);
			board.work();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 响应菜单事件
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String command = e.getActionCommand();
		String name = board.getPlayerName();
		DataPacket packet = new DataPacket();

		// 是否应该发送消息,默认应该发送
		boolean isSend = true;
		// 蓝方没有加入则禁止发送任何消息
		if (board.blueEnter) {
			if (command.equals("开始")) {
				// 通知对方
				packet.setPlayer(board.getPlayer());
				packet.setStatus(GAME_START);

				if (name.equals(RED_NAME)) {
					if (board.blueOk) {
						board.myTurn = true;
						((JButton) source).setEnabled(false);
						gameStatus.setText(SPACE + "游戏已经开始，我先走");
					} else {
						gameStatus.setText(SPACE + "蓝方还没有准备，请耐心等待！");
					}
				} else if (name.equals(BLUE_NAME)) {
					gameStatus.setText(SPACE + "已经准备，等待对方开始游戏");
					((JButton) source).setEnabled(false);
					board.blueOk = true;
				}

			} else if (command.equals("暂停")) {
				board.isPause = true;
				((JButton) source).setText("继续");
				packet.setStatus(GAME_PAUSE);
				if (board.otherIsPause) {
					gameStatus.setText(SPACE + "您和对方都已经暂停了游戏！");
				} else {
					gameStatus.setText(SPACE + "您已经暂停了游戏！");
				}

			} else if (command.equals("继续")) {
				board.isPause = false;
				((JButton) source).setText("暂停");
				packet.setStatus(CONTINUE);

				if (board.otherIsPause) {

					gameStatus.setText(SPACE + "对方已经暂停了游戏！请耐心等待！");
				} else {
					if (board.myTurn) {
						gameStatus.setText(SPACE + "轮到我走喽！");
					} else {
						gameStatus.setText(SPACE + "等待对方走棋！");
					}
				}

			} else if (command.equals("认输")) {
				int result = JOptionPane.showConfirmDialog(this, "您确定投降么？");
				if (result == JOptionPane.YES_OPTION) {
					packet.setStatus(GIVEIN);
					gameStatus.setText(SPACE + "胜败乃兵家常事！继续努力啊！");
					// FansUtil.playSound("lose.wav");
				} else {
					isSend = false;
				}
			} else if (command.equals("悔棋")) {
				int size = records.getRecords().size();
				if (size > 0) {
					records.back();
					board.myTurn = !board.myTurn;

					if (board.myTurn) {
						gameStatus.setText(SPACE + "现在轮到我走喽！");
					} else {
						gameStatus.setText(SPACE + "等待对方走棋！");
					}
					// 通知对方悔棋
					packet.setPlayer(board.getPlayer());
					packet.setStatus(GAME_BACK);
				} else {
					gameStatus.setText(gameStatus.getText() + "不能再悔棋了啊！");
					isSend = false;
				}

			} else if (command.equals("发送")) {
				String msg = (String) msgComboBox.getSelectedItem();
				if (msg != null && !msg.equals("")) {// 消息非空
					isSend = true;
					System.out.println(msg);
					packet.setStatus(MESSAGE);
					Message message = new Message();
					message.setContent(msg);
					message.setDate(ChessUtils.getStringDate());
					packet.setMessage(message);

					msgComboBox.setSelectedIndex(-1);
					msgArea.append(name + ":" + msg + "\n");
				}
			}
		} else {
			isSend = false;
		}

		if ((command.equals("开始") || command.equals("暂停")
				|| command.equals("继续") || command.equals("认输")
				|| command.equals("悔棋") || command.equals("发送"))
				&& isSend) {

			// 共用发消息的代码
			try {
				if (name.equals(RED_NAME)) {
					board.sendPacket(board.getToPlayer2(), packet);
				} else if (name.equals(BLUE_NAME)) {
					board.sendPacket(board.getToPlayer1(), packet);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (source == newGame) {
			container.removeAll();
			saveGame.setEnabled(true);
			board = new ChessBoard(9, 10);

			records = board.records;
			JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					true, board, records);
			split.setDividerSize(5);
			split.setDividerLocation(460);
			container.add(split, BorderLayout.CENTER);
			validate();
			repaint();
		}

		if (source == saveGame) {
			int state = fileChooser.showSaveDialog(null);
			File saveFile = fileChooser.getSelectedFile();
			if (saveFile != null && state == JFileChooser.APPROVE_OPTION) {
				try {
					FileOutputStream outOne = new FileOutputStream(saveFile);
					ObjectOutputStream outTwo = new ObjectOutputStream(outOne);
					outTwo.writeObject(records.getRecords());
					outOne.close();
					outTwo.close();
				} catch (NotSerializableException nse) {
					nse.printStackTrace();
					System.out.println(nse.getMessage());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} else if (source == loadGame) {
			container.removeAll();
			container.repaint();
			container.validate();
			validate();
			saveGame.setEnabled(false);

			int state = fileChooser.showOpenDialog(null);
			File openFile = fileChooser.getSelectedFile();
			if (openFile != null && state == JFileChooser.APPROVE_OPTION) {
				try {
					FileInputStream inOne = new FileInputStream(openFile);
					ObjectInputStream inTwo = new ObjectInputStream(inOne);
					moveRecords = (LinkedList) inTwo.readObject();
					inOne.close();
					inTwo.close();
					ChessBoard board = new ChessBoard(9, 10);
					board.player = 1;
					board.initChess(board.unitWidth, board.unitHeight,
							new Color(204, 153, 102));

					demo = new Demo(board);
					demo.setRecords(moveRecords);
					container.add(demo, BorderLayout.CENTER);
					container.validate();
					validate();
				} catch (Exception ex) {
					ex.printStackTrace();

					JLabel label = new JLabel("不是棋谱文件");
					label.setFont(new Font("隶书", Font.BOLD, 60));
					label.setForeground(Color.red);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					container.add(label, BorderLayout.CENTER);
					container.validate();
					validate();
				}
			} else {
				JLabel label = new JLabel("没有打开棋谱文件呢");
				label.setFont(new Font("隶书", Font.BOLD, 50));
				label.setForeground(Color.pink);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				container.add(label, BorderLayout.CENTER);
				container.validate();
				validate();
			}
		}

		// 通知对方和服务器自己退出之后再退出
		else if (source == exitGame) {
			try {
				packet.setStatus(GAME_EXIT);
				if (name.equals(RED_NAME)) {
					board.sendPacket(board.getToPlayer2(), packet);
				} else if (name.equals(BLUE_NAME)) {
					board.sendPacket(board.getToPlayer1(), packet);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			board.close();

			System.exit(0);
		}else if(source == aboutGame){
			JOptionPane.showMessageDialog(this, "本游戏由FansUnion制作\n联系方式:http://FansUnion.cn", "关于中国象棋", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
