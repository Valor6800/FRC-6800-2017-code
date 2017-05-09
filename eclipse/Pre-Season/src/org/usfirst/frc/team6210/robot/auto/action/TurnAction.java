package org.usfirst.frc.team6210.robot.auto.action;

import org.usfirst.frc.team6210.robot.auto.Action;
import org.usfirst.frc.team6210.robot.systems.DriveBase;

public class TurnAction implements Action{

	private double target;
	private double error;
	private double current;
	
	private double tolerance = 3;
	private double p = 0.1;
	
	private DriveBase base = DriveBase.getInstance();
	
	public TurnAction(double target) {
		this.target = target;
	}
	
	@Override
	public void setUp() {
		current = base.gyro.getAngle();
		error = target - current;
	}

	@Override
	public void update() {
		current = base.gyro.getAngle();
		
		base.robotDrive.tankDrive(error * p, error * p);
		
	}

	@Override
	public boolean isFinished() {
		return error < tolerance;
	}

	@Override
	public void cleanUp() {
		base.robotDrive.drive(0, 0);
	}

}
