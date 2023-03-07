package io.github.openguava.jvtool.lang.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程工具类
 * 
 * @author openguava
 *
 */
public class ThreadUtils {

	protected ThreadUtils() {

	}

	/**
	 * 获取当前线程
	 * 
	 * @return 被中断返回false，否则true
	 */
	public static Thread currentThread() {
		return Thread.currentThread();
	}
	
	/**
	 * 挂起当前线程
	 *
	 * @param millis 挂起的毫秒数
	 * @return 被中断返回false，否则true
	 */
	public static boolean sleep(Number millis) {
		if (millis == null) {
			return true;
		}
		return sleep(millis.longValue());
	}

	/**
	 * 挂起当前线程
	 * 
	 * @param milliseconds 挂起的毫秒数
	 * @return
	 */
	public static boolean sleep(long milliseconds) {
		if (milliseconds > 0) {
			try {
				Thread.sleep(milliseconds);
			} catch (InterruptedException e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 考虑{@link Thread#sleep(long)}方法有可能时间不足给定毫秒数，此方法保证sleep时间不小于给定的毫秒数
	 *
	 * @param millis 给定的sleep时间
	 * @return 被中断返回false，否则true
	 * @see ThreadUtils#sleep(Number)
	 */
	public static boolean safeSleep(Number millis) {
		if (millis == null) {
			return true;
		}
		return safeSleep(millis.longValue());
	}

	/**
	 * 考虑{@link Thread#sleep(long)}方法有可能时间不足给定毫秒数，此方法保证sleep时间不小于给定的毫秒数
	 *
	 * @param millis 给定的sleep时间
	 * @return 被中断返回false，否则true
	 * @see ThreadUtils#sleep(Number)
	 */
	public static boolean safeSleep(long millis) {
		long done = 0;
		long before;
		long spendTime;
		while (done >= 0 && done < millis) {
			before = System.currentTimeMillis();
			if (false == sleep(millis - done)) {
				return false;
			}
			spendTime = System.currentTimeMillis() - before;
			if (spendTime <= 0) {
				// Sleep花费时间为0或者负数，说明系统时间被拨动
				break;
			}
			done += spendTime;
		}
		return true;
	}

	/**
	 * 结束线程，调用此方法后，线程将抛出 {@link InterruptedException}异常
	 * 
	 * @param thread
	 * @param join   是否等待结束
	 */
	public static void shutdown(Thread thread, boolean join) {
		if (thread == null || thread.isInterrupted()) {
			return;
		}
		thread.interrupt();
		if (join) {
			join(thread);
		}
	}

	/**
	 * 等待线程结束. 调用 {@link Thread#join()} 并忽略 {@link InterruptedException}
	 * 
	 * @param thread
	 */
	public static void join(Thread thread) {
		if (thread == null) {
			return;
		}
		boolean dead = false;
		do {
			try {
				thread.join();
				dead = true;
			} catch (InterruptedException e) {
				// ignore
			}
		} while (!dead);
	}

	/**
	 * 停止线程池 先使用shutdown, 停止接收新任务并尝试完成所有已存在任务. 如果超时, 则调用shutdownNow,
	 * 取消在workQueue中Pending的任务,并中断所有阻塞函数. 如果仍然超時，則強制退出. 另对在shutdown时线程本身被调用中断做了处理.
	 */
	public static void shutdownAndAwaitTermination(ExecutorService pool) {
		if (pool == null || pool.isShutdown()) {
			return;
		}
		pool.shutdown();
		try {
			if (pool.awaitTermination(120, TimeUnit.SECONDS)) {
				return;
			}
			pool.shutdownNow();
			if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
				LogUtils.info(ThreadUtils.class, "Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			pool.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * 创建新线程，非守护线程，正常优先级，线程组与当前线程的线程组一致
	 *
	 * @param runnable {@link Runnable}
	 * @param name     线程名
	 * @return {@link Thread}
	 */
	public static Thread createThread(Runnable runnable, String name) {
		final Thread t = createThread(runnable, name, false, null);
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}
	
	/**
	 * 创建新线程
	 *
	 * @param runnable {@link Runnable}
	 * @param name     线程名
	 * @param daemon 是否守护线程
	 * @param priority 优先级(Thread.NORM_PRIORITY/Thread.MIN_PRIORITY/Thread.MAX_PRIORITY)
	 * @return {@link Thread}
	 */
	public static Thread createThread(Runnable runnable, String name, boolean daemon, Integer priority) {
		final Thread t = new Thread(null, runnable, name);
		t.setDaemon(daemon);
		if(priority != null) {
			t.setPriority(priority.intValue());
		}
		return t;
	}
	
	/**
	 * 启动线程
	 * 
	 * @param runnable {@link Runnable}
	 * @param name 线程名
	 * @return
	 */
	public static Thread startThread(Runnable runnable, String name) {
		Thread thread = createThread(runnable, name);
		thread.start();
		return thread;
	}
	
	/**
	 * 创建本地线程对象
	 *
	 * @param <T>           持有对象类型
	 * @param isInheritable 是否为子线程提供从父线程那里继承的值
	 * @return 本地线程
	 */
	public static <T> ThreadLocal<T> createThreadLocal(boolean isInheritable) {
		if (isInheritable) {
			return new InheritableThreadLocal<>();
		} else {
			return new ThreadLocal<>();
		}
	}
	
	/**
	 * 新建一个CountDownLatch，一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
	 *
	 * @param threadCount 线程数量
	 * @return CountDownLatch
	 */
	public static CountDownLatch createCountDownLatch(int threadCount) {
		return new CountDownLatch(threadCount);
	}
}
