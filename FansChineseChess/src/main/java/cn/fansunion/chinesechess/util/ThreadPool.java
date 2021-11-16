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

import java.util.LinkedList;

/**
 * �̳߳�
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class ThreadPool extends ThreadGroup {

	// �̳߳��Ƿ�ر�
	private boolean isClosed = false;

	// ��������
	private LinkedList<Runnable> workQueue;

	// �̳߳�ID
	private static int threadPoolID;

	// �����߳�ID
	private int threadID;

	// poolSizeָ���̳߳��еĹ����߳���Ŀ
	public ThreadPool(int poolSize) {
		super("ThreadPool-" + (threadPoolID++));
		setDaemon(true);

		// ������������
		workQueue = new LinkedList<Runnable>();

		for (int i = 0; i < poolSize; i++) {
			// ���������������߳�
			new WorkThread().start();
		}

	}

	/**
	 * ���������м���һ���������ɹ����߳�ȥִ������
	 * 
	 * @param task
	 */
	public synchronized void execute(Runnable task) {
		if (isClosed) {
			throw new IllegalStateException();
		}
		if (task != null) {
			workQueue.add(task);
			// ��������getTask()�����еȴ�����Ĺ����߳�
			notify();
		}
	}

	/**
	 * �ӹ���������ȡ��һ�����񣬹����̻߳���ô˷���
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	protected synchronized Runnable getTask() throws InterruptedException {
		while (workQueue.size() == 0) {
			if (isClosed) {
				return null;
			}
			// �������������û�����񣬾͵ȴ�����
			wait();
		}
		return workQueue.removeFirst();
	}

	/**
	 * �ر��̳߳�
	 * 
	 */
	public synchronized void close() {
		if (!isClosed) {
			isClosed = true;
			workQueue.clear();
			// �ж����еĹ����̣߳��÷����̳���ThreadGroup��
			interrupt();
		}
	}

	/**
	 * �ȴ������̰߳���������ִ����
	 * 
	 */
	public void join() {
		synchronized (this) {
			isClosed = true;
			notifyAll();
		}

		Thread[] threads = new Thread[activeCount()];
		// enumerate()�����̳���ThreadGroup�࣬����߳����е�ǰ���л��ŵĹ����߳�
		int count = enumerate(threads);

		for (int i = 0; i < count; i++) {
			try {
				// �ȴ������߳����н���
				threads[i].join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * �ڲ��࣬�����߳�
	 * 
	 */
	private class WorkThread extends Thread {

		// ���뵽��ǰThreadPool�߳�����
		public WorkThread() {
			super(ThreadPool.this, "WorkThread-" + (threadID++));
		}

		public void run() {
			while (!isInterrupted()) {// �ж��߳��Ƿ��ж�
				Runnable task = null;
				try {
					task = getTask();// ���ȡ������
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				// ���getTask()����null�������߳�ִ��getTask()ʱ���жϣ���������߳�
				if (task == null) {
					return;
				}

				try {
					task.run();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}// #while
		}// #run()
	}// #WorkThread��
}
