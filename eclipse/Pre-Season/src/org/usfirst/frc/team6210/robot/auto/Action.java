package org.usfirst.frc.team6210.robot.auto;



/**
 * @author adamj
 * Action used within autonomous programs as subroutines.
 * AutonomousModes have many Actions.
 */
public interface Action {
	
	/**
	 * Called once before the action is run periodically. 
	 */
	public void setUp();
	
	
	/**
	 * Run any actions required to complete until isFinished(); returns true.
	 * All action logic lives here. 
	 */
	public void update();
	
	/**
	 * @return If the action is finished completing whatever task it is
	 * designed for, ex. Finished driving 10 inches. Called every update
	 * cycle for the auto runner.
	 */
	public boolean isFinished();
	
	/**
	 * Called once the Action has completed, used to turn off any motors/actuators
	 * or things that would use battery/processing power. 
	 * Leave the robot how you found it before the action was started
	 */
	public void cleanUp();
	
}
