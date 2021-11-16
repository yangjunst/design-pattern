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

import java.util.LinkedList;

/**
 * 线程池
 * 
 * @author leiwen@fansunion.cn,http://FansUnion.cn,
 *         http://blog.csdn.net/FansUnion
 * 
 */
public class ThreadPool extends ThreadGroup {

	// 线程池是否关闭
	private boolean isClosed = false;

	// 工作队列
	private LinkedList<Runnable> workQueue;

	// 线程池ID
	private static int threadPoolID;

	// 工作线程ID
	private int threadID;

	// poolSize指定线程池中的工作线程数目
	public ThreadPool(int poolSize) {
		super("ThreadPool-" + (threadPoolID++));
		setDaemon(true);

		// 创建工作队列
		workQueue = new LinkedList<Runnable>();

		for (int i = 0; i < poolSize; i++) {
			// 创建并启动工作线程
			new WorkThread().start();
		}

	}

	/**
	 * 向工作队列中加入一个新任务，由工作线程去执行任务
	 * 
	 * @param task
	 */
	public synchronized void execute(Runnable task) {
		if (isClosed) {
			throw new IllegalStateException();
		}
		if (task != null) {
			workQueue.add(task);
			// 唤醒正在getTask()方法中等待任务的工作线程
			notify();
		}
	}

	/**
	 * 从工作队列中取出一个任务，工作线程会调用此方法
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	protected synchronized Runnable getTask() throws InterruptedException {
		while (workQueue.size() == 0) {
			if (isClosed) {
				return null;
			}
			// 如果工作队列中没有任务，就等待任务
			wait();
		}
		return workQueue.removeFirst();
	}

	/**
	 * 关闭线程池
	 * 
	 */
	public synchronized void close() {
		if (!isClosed) {
			isClosed = true;
			workQueue.clear();
			// 中断所有的工作线程，该方法继承自ThreadGroup类
			interrupt();
		}
	}

	/**
	 * 等待工作线程把所有任务执行完
	 * 
	 */
	public void join() {
		synchronized (this) {
			isClosed = true;
			notifyAll();
		}

		Thread[] threads = new Thread[activeCount()];
		// enumerate()方法继承自ThreadGroup类，获得线程组中当前所有活着的工作线程
		int count = enumerate(threads);

		for (int i = 0; i < count; i++) {
			try {
				// 等待工作线程运行结束
				threads[i].join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 内部类，工作线程
	 * 
	 */
	private class WorkThread extends Thread {

		// 加入到当前ThreadPool线程组中
		public WorkThread() {
			super(ThreadPool.this, "WorkThread-" + (threadID++));
		}

		public void run() {
			while (!isInterrupted()) {// 判断线程是否被中断
				Runnable task = null;
				try {
					task = getTask();// 如果取出任务
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				// 如果getTask()返回null，或者线程执行getTask()时被中断，则结束此线程
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
	}// #WorkThread类
}
