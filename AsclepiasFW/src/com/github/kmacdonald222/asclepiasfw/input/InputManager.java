/*
 * File:		InputManager.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.07.21
 * Purpose:		Defines a wrapper class for general user input management
 * 				systems
 */

package com.github.kmacdonald222.asclepiasfw.input;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

// Wrapper class for general user input management systems
public class InputManager {
	
	// Whether the user input management system has been initialized
	private boolean initialized = false;
	// Instance of the keyboard input management system
	public KeyboardManager keyboard = null;
	// Instance of the mouse input management system
	public MouseManager mouse = null;
	
	/*
	 * Initialize all user input management systems
	 * @return boolean - Whether the user input management systems were
	 * successfully initialized
	 */
	public boolean initialize() {
		if (initialized) {
			return false;
		}
		keyboard = new KeyboardManager();
		if (!keyboard.initialize()) {
			App.Log.write(LogSource.Input, LogPriority.Error, "Failed to ",
					"initialize keyboard input management system");
			return false;
		}
		App.Log.write(LogSource.Input, LogPriority.Info, "Initialized ",
				"keyboard input management system");
		mouse = new MouseManager();
		if (!mouse.initialize()) {
			App.Log.write(LogSource.Input, LogPriority.Error, "Failed to ",
					"initialize mouse input management system");
			return false;
		}
		App.Log.write(LogSource.Input, LogPriority.Info, "Initialized ",
				"mouse input management system");
		initialized = true;
		return initialized;
	}
	/*
	 * Update the logic of all user input management systems
	 */
	public void update() {
		keyboard.update();
		mouse.update();
	}
	/*
	 * Free the memory of all user input management systems
	 * @return boolean - Whether all user input management systems were
	 * destroyed successfully
	 */
	public boolean destroy() {
		if (!initialized) {
			return false;
		}
		boolean success = true;
		App.Log.write(LogSource.Input, LogPriority.Info, "Destroying keyboard ",
				"input management system");
		if (!keyboard.destroy()) {
			App.Log.write(LogSource.Input, LogPriority.Error, "Failed to ",
					"destroy keyboard input management system");
			success = false;
		}
		App.Log.write(LogSource.Input, LogPriority.Info, "Destroying mouse ",
				"input management system");
		if (!mouse.destroy()) {
			App.Log.write(LogSource.Input, LogPriority.Error, "Failed to ",
					"destroy mouse input management system");
			success = false;
		}
		initialized = false;
		return success;
	}
	
}
