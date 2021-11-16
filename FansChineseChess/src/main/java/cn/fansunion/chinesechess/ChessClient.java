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
 * �й�����ͻ���
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class ChessClient extends JFrame implements Constants,
		ActionListener {

	private static final long serialVersionUID = 101L;

	private JPanel control;

	// ��ʼ����ͣ�����塢���䡢������Ϣ��ť
	private JButton start, undo, pauseOrContinue, giveIn, send;

	ChessBoard board = null;

	Demo demo = null;

	MakeChessManual records = null;

	JComboBox msgComboBox;

	Container container = null;

	// �˵�����ѡ��
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

	// ��Ϸ״̬
	JLabel gameStatus;

	// ��Ͽ��еı�ѡ��Ϣ
	String[] initialMsg = { "����������˰�", "���ɣ��ҵȵ�����л��", "�������ߵ�̫����", "�´�����ɣ���Ҫ����" };

	public JTextArea msgArea;// ���ڷ���

	public ChessClient(String petName) {
		// ����˵�
		bar = new JMenuBar();
		fileMenu = new JMenu("�й�����(G)");

		newGame = new JMenuItem("����Ϸ");
		saveGame = new JMenuItem("������Ϸ");
		loadGame = new JMenuItem("װ����Ϸ");
		exitGame = new JMenuItem("�˳���Ϸ");

		//fileMenu.add(newGame);
		fileMenu.add(saveGame);
		fileMenu.add(loadGame);
		fileMenu.add(exitGame);
		bar.add(fileMenu);

		settingMenu = new JMenu("����");
		setting = new JMenuItem("��������");
		settingMenu.add(setting);
	//	bar.add(settingMenu);

		chattingMenu = new JMenu("����");
		chatting = new JMenuItem("����");
		chattingMenu.add(chatting);
	//	bar.add(chattingMenu);

		helpMenu = new JMenu("����");
		helpContent = new JMenuItem("��������");
		aboutGame = new JMenuItem("�����й�����");
	//	helpMenu.add(helpContent);
		helpMenu.add(aboutGame);
		bar.add(helpMenu);

		setJMenuBar(bar);

		// ���ÿ�ݼ�
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

		// ע�������
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

		// �����ұߵ����
		JPanel rightPanel = new JPanel(new BorderLayout());

		JPanel recordsPanel = new JPanel(new BorderLayout());

		TitledBorder recordsBorder = new TitledBorder("�����Ϣ");
		recordsPanel.setBorder(recordsBorder);

		recordsPanel.add(BorderLayout.CENTER, records);

		// �����Ϣ���
		BorderLayout msgLayout = new BorderLayout();
		JPanel msgPanel = new JPanel();
		msgPanel.setLayout(msgLayout);
		TitledBorder msgBorder = new TitledBorder("�����Ϣ");
		msgPanel.setBorder(msgBorder);

		msgArea = new JTextArea();
		msgArea.setRows(7);
		msgArea.setFont(new Font("����", Font.PLAIN, 16));
		msgArea.setEditable(false);
		JScrollPane displayScroll = new JScrollPane(msgArea);

		board.setMsgArea(msgArea);
		// ������Ϣ
		msgComboBox = new JComboBox(initialMsg);
		msgComboBox.setSelectedIndex(-1);
		msgComboBox.setEditable(true);
		//ѡ�����б��е���Ϣ֮����������
		msgComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String msg = (String) msgComboBox.getSelectedItem();
				if (msg != null && !msg.equals("")) {// ��Ϣ�ǿ�
					String name = board.getPlayerName();
					DataPacket packet = new DataPacket();
					System.out.println(msg);
					packet.setStatus(MESSAGE);
					Message message = new Message();
					message.setContent(msg);
					message.setDate(ChessUtils.getStringDate());
					packet.setMessage(message);
					// ���÷���Ϣ�Ĵ���
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

		// ��Ϣ�������
		JPanel sendMsgPanel = new JPanel(new BorderLayout());
		sendMsgPanel.add(BorderLayout.NORTH, msgComboBox);
		send = new JButton("����");

		sendMsgPanel.add(BorderLayout.CENTER, send);

		msgPanel.add(BorderLayout.CENTER, displayScroll);
		msgPanel.add(BorderLayout.SOUTH, sendMsgPanel);

		// ���ұߵ�����м����¼������Ϣ���
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

		// ���ܰ�ť
		start = new JButton("��ʼ");
		pauseOrContinue = new JButton("��ͣ");
		undo = new JButton("����");
		giveIn = new JButton("����");

		/**
		 * ע�������
		 */
		start.addActionListener(this);
		pauseOrContinue.addActionListener(this);
		undo.addActionListener(this);
		giveIn.addActionListener(this);
		send.addActionListener(this);

		JPanel controlPanel = new JPanel();

		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
		controlPanel.setLayout(flowLayout);

		TitledBorder controlBorder = new TitledBorder("��Ϸ����");

		controlPanel.setBorder(controlBorder);

		controlPanel.add(start);
		controlPanel.add(pauseOrContinue);
		controlPanel.add(undo);
		controlPanel.add(giveIn);

		control.add(BorderLayout.CENTER, controlPanel);

		gameStatus = new JLabel();
		gameStatus.setFont(new Font("����", Font.PLAIN, 16));
		gameStatus.setForeground(Color.BLACK);

		TitledBorder gameStatusBorder = new TitledBorder("��Ϸ״̬");
		gameStatusBorder.setTitleColor(Color.RED);
		gameStatusBorder.setTitleFont(new Font("����", Font.PLAIN, 16));
		gameStatus.setToolTipText("��Ϸ״̬");
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
		setTitle("�й�����--˫��������ս��");
		setSize(670, 650);
		setIconImage(ChessUtils.getImage());
		this.setResizable(false);// �������޸Ľ���Ĵ�С
		container.validate();
		validate();

	}

	/**
	 * ���ӵ�������
	 */
	public boolean connectToServer(String host) {
		Socket socket = null;
		try {
			// ����һ���ͻ����׽��������ӵ���������
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
	 * ��Ӧ�˵��¼�
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String command = e.getActionCommand();
		String name = board.getPlayerName();
		DataPacket packet = new DataPacket();

		// �Ƿ�Ӧ�÷�����Ϣ,Ĭ��Ӧ�÷���
		boolean isSend = true;
		// ����û�м������ֹ�����κ���Ϣ
		if (board.blueEnter) {
			if (command.equals("��ʼ")) {
				// ֪ͨ�Է�
				packet.setPlayer(board.getPlayer());
				packet.setStatus(GAME_START);

				if (name.equals(RED_NAME)) {
					if (board.blueOk) {
						board.myTurn = true;
						((JButton) source).setEnabled(false);
						gameStatus.setText(SPACE + "��Ϸ�Ѿ���ʼ��������");
					} else {
						gameStatus.setText(SPACE + "������û��׼���������ĵȴ���");
					}
				} else if (name.equals(BLUE_NAME)) {
					gameStatus.setText(SPACE + "�Ѿ�׼�����ȴ��Է���ʼ��Ϸ");
					((JButton) source).setEnabled(false);
					board.blueOk = true;
				}

			} else if (command.equals("��ͣ")) {
				board.isPause = true;
				((JButton) source).setText("����");
				packet.setStatus(GAME_PAUSE);
				if (board.otherIsPause) {
					gameStatus.setText(SPACE + "���ͶԷ����Ѿ���ͣ����Ϸ��");
				} else {
					gameStatus.setText(SPACE + "���Ѿ���ͣ����Ϸ��");
				}

			} else if (command.equals("����")) {
				board.isPause = false;
				((JButton) source).setText("��ͣ");
				packet.setStatus(CONTINUE);

				if (board.otherIsPause) {

					gameStatus.setText(SPACE + "�Է��Ѿ���ͣ����Ϸ�������ĵȴ���");
				} else {
					if (board.myTurn) {
						gameStatus.setText(SPACE + "�ֵ�����ඣ�");
					} else {
						gameStatus.setText(SPACE + "�ȴ��Է����壡");
					}
				}

			} else if (command.equals("����")) {
				int result = JOptionPane.showConfirmDialog(this, "��ȷ��Ͷ��ô��");
				if (result == JOptionPane.YES_OPTION) {
					packet.setStatus(GIVEIN);
					gameStatus.setText(SPACE + "ʤ���˱��ҳ��£�����Ŭ������");
					// FansUtil.playSound("lose.wav");
				} else {
					isSend = false;
				}
			} else if (command.equals("����")) {
				int size = records.getRecords().size();
				if (size > 0) {
					records.back();
					board.myTurn = !board.myTurn;

					if (board.myTurn) {
						gameStatus.setText(SPACE + "�����ֵ�����ඣ�");
					} else {
						gameStatus.setText(SPACE + "�ȴ��Է����壡");
					}
					// ֪ͨ�Է�����
					packet.setPlayer(board.getPlayer());
					packet.setStatus(GAME_BACK);
				} else {
					gameStatus.setText(gameStatus.getText() + "�����ٻ����˰���");
					isSend = false;
				}

			} else if (command.equals("����")) {
				String msg = (String) msgComboBox.getSelectedItem();
				if (msg != null && !msg.equals("")) {// ��Ϣ�ǿ�
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

		if ((command.equals("��ʼ") || command.equals("��ͣ")
				|| command.equals("����") || command.equals("����")
				|| command.equals("����") || command.equals("����"))
				&& isSend) {

			// ���÷���Ϣ�Ĵ���
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

					JLabel label = new JLabel("���������ļ�");
					label.setFont(new Font("����", Font.BOLD, 60));
					label.setForeground(Color.red);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					container.add(label, BorderLayout.CENTER);
					container.validate();
					validate();
				}
			} else {
				JLabel label = new JLabel("û�д������ļ���");
				label.setFont(new Font("����", Font.BOLD, 50));
				label.setForeground(Color.pink);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				container.add(label, BorderLayout.CENTER);
				container.validate();
				validate();
			}
		}

		// ֪ͨ�Է��ͷ������Լ��˳�֮�����˳�
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
			JOptionPane.showMessageDialog(this, "����Ϸ��FansUnion����\n��ϵ��ʽ:http://FansUnion.cn", "�����й�����", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
