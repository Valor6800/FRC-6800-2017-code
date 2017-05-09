package org.usfirst.frc.team6210.robot.auto;

import java.util.ArrayList;

public abstract class AutoMode{
	
	// Update with a 1/30th second interval between updates.
	private long UPDATE_INTERVAL = 1/30;

	private ArrayList<Action> actions;
	private String PROGRAM_NAME;
	
	// Current action being updated
	private int currentAction;
	// Is the program finished running all actions or has been requested to start
	private boolean isFinished;
	// Has the setUp method been run.
	private boolean isReady;
	
	public AutoMode(String name) {
		this.PROGRAM_NAME = name;
		this.actions = new ArrayList<>();
		currentAction = 0;
		isFinished = true;
		isReady = false;
		
		addActions();
	}
	
	protected abstract void addActions();
	
	public String getName() {
		return PROGRAM_NAME;
	}
	
	/**
	 * Called within specific AutoMode class. Add actions
	 * within constructor to create mode.
	 * @param a Action to add to routine.
	 */
	protected void addAction(Action a) {
		actions.add(a);
	}
	
	/**
	 * Prepare first action to run. Init robot
	 */
	public void setUp() {
		actions.get(currentAction).setUp();
		isFinished = false;
		isReady = true;
	}
	
	/**
	 * Updates the current action if not already finished. When
	 * current action is finished, clean up and start the next action.
	 */
	public void runAutoMode() {
		if(!isReady) setUp();
		while(!isFinished()) {
			try {
				update();
				Thread.sleep(UPDATE_INTERVAL * 1000);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Run periodically from the AutoManager class.
	 * Checks if current action is finished, if so continues to next action
	 * If all actions are complete, stops running mode
	 * Checks for interrupted thread and if the mode was requested to stop.
	 */
	private void update() {
		if(currentAction >= actions.size() || Thread.interrupted())
			stop();
		Action activeAction = actions.get(currentAction);
		
		if(activeAction.isFinished()) {
			activeAction.cleanUp();
			currentAction ++;
			if(currentAction < actions.size()) {
				actions.get(currentAction).setUp();
			} else stop();
		} 
		else activeAction.update();
	}
	
	public void cleanUp() {
		if(currentAction < actions.size())
			actions.get(currentAction).cleanUp();
	}

	
	public boolean isFinished() {
		return isFinished;
	}
	
	public void stop() {
		isFinished = true;
		isReady = false;
		if(currentAction < actions.size())
			actions.get(currentAction).cleanUp();
		currentAction = actions.size() + 1;
	}
}
