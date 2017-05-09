package org.usfirst.frc.team6210.robot.auto_2;

public class AutoWrapper {
	
	private static double UPDATE_INTERVAL = 1/30;
	
	private Thread autoThread;
	private AutoMode mode;
	private boolean isReady;
	
	public AutoWrapper(AutoMode mode) {
		this.mode = mode;
		autoThread = null;
		isReady = false;
	}
	
	public void setUp() {
		mode.setUp();
		isReady = true;
	}
	
	public void run() {
		if(autoThread != null) {
			if(!autoThread.isAlive())
				autoThread.start();
		}
		if(!isReady) {
			setUp();
		}
		
		autoThread = new Thread(new Runnable() {
			@Override
			public void run(){
				// Ensure a UPDATE_INTERVAL gap between loop intervals
				long startTime;
				long waitTime;
				while(!mode.isFinished() && !Thread.interrupted()) {
					startTime = System.currentTimeMillis();
					
					mode.update();
					
					waitTime = (long)(UPDATE_INTERVAL * 1000) - (startTime - System.currentTimeMillis()); 
					if(waitTime > 0)
						mode.waitMs(waitTime);
				}
				mode.cleanUp();
			}
		});
		autoThread.setName("auto");
		autoThread.start();
	}
	
	public boolean isFinished() {
		return mode.isFinished();
	}

	public void stop() {
		autoThread.interrupt();
	}
	
}
