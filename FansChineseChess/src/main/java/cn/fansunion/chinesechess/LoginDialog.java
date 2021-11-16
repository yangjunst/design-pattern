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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import cn.fansunion.chinesechess.util.ChessUtils;

/**
 * �ͻ��˵�¼�Ի���
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class LoginDialog extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	JLabel petName = new JLabel("�û���");

	JTextField petNameField = new JTextField(10);

	JLabel serverIP = new JLabel("������IP");

	JTextField serverIPField = new JTextField(32);

	JButton ok = new JButton("ȷ��");

	JButton cancel = new JButton("ȡ��");

	public LoginDialog() {
		ok.addActionListener(this);
		ok.addKeyListener(this);
		cancel.addActionListener(this);
		serverIPField.addKeyListener(this);
		GridLayout layout = new GridLayout(3, 2);
		layout.setHgap(30);
		layout.setHgap(20);
		setLayout(layout);
		add(petName);
		add(petNameField);
		add(serverIP);
		add(serverIPField);
		add(ok);
		add(cancel);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("ȷ��")) {
			check();
		} else if (command.equals("ȡ��")) {

		}

	}

	public static void main(String[] args) {
		/* ��������GUI�����ʽ������ϵͳ��ʽ */
		String lookAndFeel = null;
		lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			System.out.println("���ñ�����۳���");
			e.printStackTrace();
		}

		LoginDialog loginFrame = new LoginDialog();
		loginFrame.setTitle("��¼����");
		loginFrame.setIconImage(ChessUtils.getImage());
		loginFrame.setSize(200, 180);
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setVisible(true);
		loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * ��Ӧ�����¼�
	 */
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ENTER) {
			System.out.println("hah");
			check();
		}

	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

	/**
	 * ��֤�Ƿ������ӵ�������������ɹ�������ʾ�����棬������ʾ�д���
	 * 
	 */
	private void check() {
		String name = petNameField.getText();
		String host = serverIPField.getText();
		ChessClient client = new ChessClient(name);
		boolean flag = client.connectToServer(host);

		if (host.equals("")) {
			flag = false;
		}

		if (flag) {
			this.dispose();
			client.setLocationRelativeTo(null);
			client.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "���ӷ�����ʧ��");
		}
	}
}
