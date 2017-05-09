package org.usfirst.frc.team6210.robot.auto.action;

import org.usfirst.frc.team6210.robot.auto.Action;

public class CustomAction implements Action{

	private Runnable toRun, toSetUp, toCleanUp;
	private boolean done;
	
	public CustomAction (Runnable toRun) {
		this.toRun = toRun;
		done = false;
	}
	
	public CustomAction (Runnable toSetUp, Runnable toRun, Runnable toCleanUp) {
		this.toRun = toRun;
		this.toCleanUp = toCleanUp;
		this.toSetUp = toSetUp;
		done = false;
	}
	
	@Override
	public void setUp() {
		if(toSetUp != null)
			toSetUp.run();
	}

	@Override
	public void update() {
		if(!done)
			toRun.run();
		done = true;
	}

	@Override
	public boolean isFinished() {
		return done;
	}

	@Override
	public void cleanUp() {
		if(toCleanUp != null)
			toCleanUp.run();
	}

}
