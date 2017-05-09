package org.usfirst.frc.team6210.robot.systems;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class DriveBase {
	
	private static DriveBase instance;
	
	public Encoder left, right;
	public Jaguar left1, left2, right1, right2;
	public RobotDrive robotDrive;
	public AnalogGyro gyro;
	
	private DriveBase() {
		left1 = new Jaguar(0);
		left2 = new Jaguar(1);

		right1 = new Jaguar(2);
		right2 = new Jaguar(3);
		
		left = new Encoder(7,8,false);
		right = new Encoder(9,10,false);
		
		gyro = new AnalogGyro(5);
		
		robotDrive = new RobotDrive(left1, left2, right1, right2);
	}
	
	public static DriveBase getInstance() {
		if(instance == null)
			instance = new DriveBase();
		return instance;
	}
	
	public void drive(Joystick leftStick, Joystick rightStick) {
		robotDrive.tankDrive(leftStick, rightStick);
	}
	
}
