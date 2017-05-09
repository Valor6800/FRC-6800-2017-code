package org.usfirst.frc.team6210.robot.auto.action;

import org.usfirst.frc.team6210.robot.auto.Action;

public class WaitAction implements Action {

	private long waitTime, endTime;
	
	public WaitAction(double seconds) {
		waitTime = (long) (seconds * 1000);
	}
	
	@Override
	public void setUp() {
		endTime = waitTime + System.currentTimeMillis();
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isFinished() {
		return System.currentTimeMillis() >= endTime;
	}

	@Override
	public void cleanUp() {
			
	}

}
