package org.usfirst.frc.team6210.robot.auto_2;

import org.usfirst.frc.team6210.robot.auto.action.DriveAction;
import org.usfirst.frc.team6210.robot.auto.action.CustomAction;
import org.usfirst.frc.team6210.robot.auto.action.TurnAction;
import org.usfirst.frc.team6210.robot.auto.action.WaitAction;

public class ExampleAuto extends AutoMode{

	public ExampleAuto() {
		super("Example");
	}

	@Override
	protected void addActions() {
		addAction(new WaitAction(10));
		addAction(new DriveAction(12 * 5));
		addAction(new TurnAction(90));
		addAction(new CustomAction(new Runnable() {
			@Override
			public void run() {
				System.out.println("Hi");
			}
		}));
	}

}
