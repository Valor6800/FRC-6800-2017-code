package org.usfirst.frc.team6210.robot;

import org.usfirst.frc.team6210.robot.auto_2.AutoMode;
import org.usfirst.frc.team6210.robot.auto_2.AutoWrapper;
import org.usfirst.frc.team6210.robot.auto_2.ExampleAuto;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//TODO Test auto manager and auto wrapper
	AutoWrapper autoWrapper;
 	SendableChooser<AutoMode> autoChooser;
 	
 	Joystick driver1_a, driver1_b, driver2_a;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// Add possible auto mode names to db
		autoChooser = new SendableChooser<AutoMode>();
		autoChooser.addDefault("Example", new ExampleAuto());
		SmartDashboard.putData("AutoModes", autoChooser);
		
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		if(autoWrapper != null) {
			autoWrapper.stop();
			autoWrapper = null;
		}
		
		AutoMode mode = (AutoMode)autoChooser.getSelected();
	
		autoWrapper = new AutoWrapper(mode);
		
		try {
			autoWrapper.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		// Update Driver station
		if(autoWrapper.isFinished())
			autoWrapper.stop();
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
	}
}
