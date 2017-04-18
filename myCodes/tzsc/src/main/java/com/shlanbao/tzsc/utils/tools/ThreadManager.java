package com.shlanbao.tzsc.utils.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * 线程管理
 * @author Leejean
 * @create 2015年1月7日下午1:20:18
 */
public class ThreadManager {
	
	private static ThreadManager threadManager = null;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
	
	private ThreadManager(){
		
	}
	
	public static ThreadManager getInstance(){
		if(threadManager == null){
			threadManager = new ThreadManager();
		}
		return threadManager;
	}
	
	/***
	 * 新增执行线程
	 * @param command
	 */
	public void add(Runnable command){
		executorService.execute(command);
	}
	
	/***
	 * 增加定时执行线程
	 * @param command
	 * @param initialDelay
	 * @param delay
	 * @param unit
	 */
	public void addSchedule(Runnable command,int initialDelay,int delay,TimeUnit unit){
		scheduledExecutorService.scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}
	
	public void shutDownThread() {
		scheduledExecutorService.shutdown();
	}

}
