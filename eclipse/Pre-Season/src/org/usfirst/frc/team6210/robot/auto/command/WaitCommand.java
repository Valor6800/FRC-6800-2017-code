package org.usfirst.frc.team6210.robot.auto.command;

import edu.wpi.first.wpilibj.command.Command;

public class WaitCommand extends Command{
	
	long waitTime, endTime;
	
	public WaitCommand(double seconds) {
		super("Wait");
		waitTime = (long) (seconds * 1000);
	}
	
	@Override
	protected void initialize() {
		endTime = System.currentTimeMillis() + waitTime;
	}

	@Override
	protected boolean isFinished() {
		
		return System.currentTimeMillis() > endTime;
	}

}
