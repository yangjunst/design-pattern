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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cn.fansunion.chinesechess.core.MoveStep;

/**
 * Ϊ2����Ҷ���һ���߳����������µĻỰ
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
				// �����1������Ϣ
				packet = (DataPacket) ois1.readObject();

				int status = packet.getStatus();
				if (status == MOVING) {
					// �ֵ����2������
					System.out.println("�������յ������1���ƶ���Ϣ�����������2���Ͱ���");
					sendPacket(oos2);				
				}else if(status == GAME_BACK){
					System.out.println("�������յ������1�Ļ�����Ϣ�����������2���Ͱ���");
					sendPacket(oos2);
				}

				// �����2������Ϣ
				packet = (DataPacket)ois2.readObject();
				status = packet.getStatus();
	
				if (status == MOVING) {
					System.out.println("�������յ������2����Ϣ�����������1���Ͱ���");
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
	 * ����������ҷ�����Ϣ
	 * @param out
	 */
	private void sendPacket(ObjectOutputStream out) {
		try {
			MoveStep moveStep = packet.getMoveStep();
			int status  = packet.getStatus();
			if(status  == MOVING){
				System.out.println("��ʼ����("+moveStep.getPStart().getX()+","+moveStep.getPStart().getY()+")");
				System.out.println("�յ�����("+moveStep.getPEnd().getX()+","+moveStep.getPEnd().getY()+")");
			}else if(status == GAME_BACK){
				System.out.println("����������ת��������Ϣ");
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
