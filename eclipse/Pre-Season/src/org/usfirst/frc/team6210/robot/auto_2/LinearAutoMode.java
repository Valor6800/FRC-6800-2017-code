package org.usfirst.frc.team6210.robot.auto_2;

import java.util.ArrayList;

import org.usfirst.frc.team6210.robot.auto.Action;

public abstract class LinearAutoMode extends AutoMode{

	private boolean isSetup, hasRun;
	
	public LinearAutoMode(String name) {
		super(name);
		
		isSetup = false;
		hasRun = false;
	}
	
	// Set current action to start, and setup variables
	public void setUp() {
		setupAutoMode();
		isSetup = true;
	}
	
	public abstract void setupAutoMode();
	
	public void addActions() {
		
	}
	
	@Override
	public void addAction(Action a) {
		return;
	}
	
	public void update() {
		if(!hasRun)
			runAutoMode();
		hasRun = true;
			
	}
	
	
	public abstract void runAutoMode();
	
	@Override
	public void nextAction() {
		return;
	}
	// Prepare the current action to clean up
	public void cleanUp() {
		cleanUpAutoMode();
	}
	
	public abstract void cleanUpAutoMode();
	
	public void stop() {
		if(!Thread.interrupted())
			Thread.currentThread().interrupt();
		hasRun = true;
	}
	
	public boolean isFinished() {
		if(Thread.interrupted()) {
			stop();
		}
		return Thread.interrupted() || hasRun;
	}
	
}
