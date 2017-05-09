package org.usfirst.frc.team6210.robot.auto_2;

import java.util.ArrayList;

import org.usfirst.frc.team6210.robot.auto.Action;

public abstract class AutoMode {

	// Unique string identifier for driver station
	public final String AUTO_NAME;
	// Holds sequence of all robot action for auto mode
	private ArrayList<Action> actions;
	// Holds index for current action we are executing
	private int currentAction;
	
	// If our auto mode has setup and init
	private boolean isReady;
	
	public AutoMode(String name) {
		AUTO_NAME = name;
		currentAction = -1;
		isReady = false;
	}
	
	// Set current action to start, and setup variables
	public void setUp() {
		actions = new ArrayList<Action>();
		addActions();
		
		currentAction = 0;
		actions.get(currentAction).setUp();
		isReady = true;	
	}
	
	protected abstract void addActions();
	
	public void addAction(Action a) {
		actions.add(a);
	}
	
	// Update the current action and check if it is finished.
	// If so, clean up current action and advance to the next
	public void update() {
		// If we haven't setup the mode yet!
		if(currentAction == -1 || !isReady) {
			setUp();
			return;
		}
		
		if(isFinished()) return;
		
		// If we were interrupted by the main thread, had to stop early
		if(Thread.interrupted()) {
			stop();
			return;
		}
		
		// Otherwise, continue as normal
		actions.get(currentAction).update();
		
		if(actions.get(currentAction).isFinished()) nextAction();
	}
	
	public void nextAction() {
		cleanUp();
		currentAction ++;
		
		if(currentAction < actions.size())
			actions.get(currentAction).setUp();
	}

	// Prepare the current action to clean up
	public void cleanUp() {
		if(currentAction < actions.size() && currentAction >= 0)
			actions.get(currentAction).cleanUp();
	}
	
	public void waitMs(long ms) {
		long endTime = System.currentTimeMillis() + ms;
		while(endTime > System.currentTimeMillis()) {
			if(Thread.interrupted()) return;
		}
	}
	
	// Clean current action up and don't continue to next
	public void stop() {
		cleanUp();
		currentAction = actions.size() + 1;

		if(!Thread.interrupted())
			Thread.currentThread().interrupt();
	}
	
	public boolean isFinished() {
		if(Thread.interrupted()) {
			stop();
		}
		return currentAction >= actions.size();
	}
	
}
