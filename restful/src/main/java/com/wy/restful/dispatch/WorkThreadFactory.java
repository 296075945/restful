package com.wy.restful.dispatch;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkThreadFactory implements ThreadFactory {

	private final AtomicInteger threadNumber = new AtomicInteger(0);

	@Override
	public Thread newThread(Runnable r) {

		Thread t = new Thread(r, "work-thread-" + threadNumber.getAndIncrement());
		if (t.isDaemon()) {
			t.setDaemon(false);
		}
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}

}
