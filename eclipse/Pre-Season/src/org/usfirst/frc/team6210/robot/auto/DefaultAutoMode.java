package org.usfirst.frc.team6210.robot.auto;

import org.usfirst.frc.team6210.robot.auto.action.WaitAction;

public class DefaultAutoMode extends AutoMode {

	public DefaultAutoMode() {
		super("Default");
	}

	@Override
	protected void addActions() {
		addAction(new WaitAction(10));
	}

}
