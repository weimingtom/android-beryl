package org.beryl.schedule;

public interface IJob extends Runnable {
	boolean isAbandoned();
	void cancel();
}
