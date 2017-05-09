package org.usfirst.frc.team6210.robot.auto.action;

import org.usfirst.frc.team6210.robot.auto.Action;
import org.usfirst.frc.team6210.robot.systems.DriveBase;

public class DriveAction implements Action{

	private static final int ENCODER_PPR = 1440;
	private static final double INCHES_PER_TICK = 3.1415 * 6 / ENCODER_PPR;
	
	private int error;
	private int delta;
	private int target;
	private double p = 0.4;
	private double tolerance = 2.0;
	
	private DriveBase base = DriveBase.getInstance();
	
	public DriveAction(double inches) {
		delta = (int) (inches * INCHES_PER_TICK); 
	}
	
	@Override
	public void setUp() {
		target = base.left.get() + delta;
	}

	@Override
	public void update() {
		int current = base.left.get();
		error = target - current;
		base.robotDrive.drive(error * p, 0);
	}

	@Override
	public boolean isFinished() {
		return Math.abs(error) < INCHES_PER_TICK * tolerance;
	}

	@Override
	public void cleanUp() {
		base.robotDrive.drive(0,0);
		
	}
	
	
	
}
