package org.usfirst.frc.team6210.robot.auto;

import java.util.ArrayList;

public class AutoManager {
		
	private AutoMode selectedMode;
	private Thread autoThread;
	
	public AutoManager() {
		this.autoThread = null;
		this.selectedMode = null;
	}
	
	/**
	 * 
	 * Run an auto mode from.
	 * 
	 * @param mode Selected mode to run
	 * @throws Exception If selected mode is not found.
	 */
	public void runSelectedMode(AutoMode mode) throws Exception {
		// Stop currently running auto mode.
		if(selectedMode != null) {
			selectedMode.stop();
			selectedMode = null;
		}
		if(autoThread != null) {
			autoThread.interrupt();
			autoThread = null;
		}
		
		// Start selected mode
		selectedMode = mode;

		autoThread = new Thread(new Runnable() {
			@Override
			public void run() {
				selectedMode.setUp();
				selectedMode.runAutoMode();
			}
		});
		autoThread.start();
	}

	public void stop() {
		if(selectedMode != null) {
			selectedMode.stop();
			selectedMode = null;
		}
		
		if(autoThread != null) {
			autoThread.interrupt();
			autoThread = null;
		}
	}
}
