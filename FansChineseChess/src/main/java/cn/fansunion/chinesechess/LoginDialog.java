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
 * 客户端登录对话框
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class LoginDialog extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	JLabel petName = new JLabel("用户名");

	JTextField petNameField = new JTextField(10);

	JLabel serverIP = new JLabel("服务器IP");

	JTextField serverIPField = new JTextField(32);

	JButton ok = new JButton("确定");

	JButton cancel = new JButton("取消");

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

		if (command.equals("确定")) {
			check();
		} else if (command.equals("取消")) {

		}

	}

	public static void main(String[] args) {
		/* 首先设置GUI外观样式：本地系统样式 */
		String lookAndFeel = null;
		lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			System.out.println("设置本地外观出错！");
			e.printStackTrace();
		}

		LoginDialog loginFrame = new LoginDialog();
		loginFrame.setTitle("登录界面");
		loginFrame.setIconImage(ChessUtils.getImage());
		loginFrame.setSize(200, 180);
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setVisible(true);
		loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * 响应键盘事件
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
	 * 验证是否能连接到服务器，如果成功，则显示主界面，否则提示有错误
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
			JOptionPane.showMessageDialog(null, "连接服务器失败");
		}
	}
}
