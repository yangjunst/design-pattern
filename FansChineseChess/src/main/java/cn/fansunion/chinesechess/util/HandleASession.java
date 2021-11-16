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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cn.fansunion.chinesechess.core.MoveStep;

/**
 * 为2个玩家定义一个线程类来处理新的会话
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class HandleASession implements Runnable, Constants {

	private ObjectInputStream ois1;
	
	private ObjectOutputStream oos1;
	
	private ObjectOutputStream oos2;
	
	private ObjectInputStream ois2;
	
	DataPacket packet;
	// private boolean continueToPlay = true;

	/** Construct a thread */
	public HandleASession(Socket player1, Socket player2) {
		//this.player1 = player1;
		//this.player2 = player2;
	}

	/** Implement the run() method for the thread */
	public void run() {
		try {
			while (true) {			
				// 从玩家1接收消息
				packet = (DataPacket) ois1.readObject();

				int status = packet.getStatus();
				if (status == MOVING) {
					// 轮到玩家2走棋了
					System.out.println("服务器收到了玩家1的移动消息，正在向玩家2发送啊！");
					sendPacket(oos2);				
				}else if(status == GAME_BACK){
					System.out.println("服务器收到了玩家1的悔棋消息，正在向玩家2发送啊！");
					sendPacket(oos2);
				}

				// 从玩家2接收消息
				packet = (DataPacket)ois2.readObject();
				status = packet.getStatus();
	
				if (status == MOVING) {
					System.out.println("服务器收到了玩家2的消息，正在向玩家1发送啊！");
					sendPacket(oos1);
				}
			}
		} catch (IOException ex) {
			System.err.println(ex);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 服务器向玩家发送消息
	 * @param out
	 */
	private void sendPacket(ObjectOutputStream out) {
		try {
			MoveStep moveStep = packet.getMoveStep();
			int status  = packet.getStatus();
			if(status  == MOVING){
				System.out.println("起始坐标("+moveStep.getPStart().getX()+","+moveStep.getPStart().getY()+")");
				System.out.println("终点坐标("+moveStep.getPEnd().getX()+","+moveStep.getPEnd().getY()+")");
			}else if(status == GAME_BACK){
				System.out.println("服务器正在转发悔棋消息");
			}
		
			out.writeObject(packet);
		    out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ObjectInputStream getOis1() {
		return ois1;
	}

	public void setOis1(ObjectInputStream ois1) {
		this.ois1 = ois1;
	}

	public ObjectInputStream getOis2() {
		return ois2;
	}

	public void setOis2(ObjectInputStream ois2) {
		this.ois2 = ois2;
	}

	public ObjectOutputStream getOos1() {
		return oos1;
	}

	public void setOos1(ObjectOutputStream oos1) {
		this.oos1 = oos1;
	}

	public ObjectOutputStream getOos2() {
		return oos2;
	}

	public void setOos2(ObjectOutputStream oos2) {
		this.oos2 = oos2;
	}
}
