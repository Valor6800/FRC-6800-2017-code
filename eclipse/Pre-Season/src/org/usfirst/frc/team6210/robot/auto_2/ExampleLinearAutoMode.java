package org.usfirst.frc.team6210.robot.auto_2;

import org.usfirst.frc.team6210.robot.auto.action.DriveAction;
import org.usfirst.frc.team6210.robot.systems.DriveBase;

public class ExampleLinearAutoMode extends LinearAutoMode {

	DriveAction driver;
	
	public ExampleLinearAutoMode() {
		super("Example Linear");
		
		driver = new DriveAction(10);
	}

	@Override
	public void setupAutoMode() {
		
	}

	@Override
	public void runAutoMode() {
		driver.setUp();
		while(!driver.isFinished() && isFinished()) {
			driver.update();
		}
		driver.cleanUp();
	}

	@Override
	public void cleanUpAutoMode() {
		// TODO Auto-generated method stub
		
	}

}
