/*
 * File:		AppScene.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.07.08
 * Purpose:		Defines an abstract scene class to be called on by the Asclepias
 * 				Framework application
 */

package com.github.kmacdonald222.asclepiasfw.app;

import com.github.kmacdonald222.asclepiasfw.input.KeyboardListener;
import com.github.kmacdonald222.asclepiasfw.input.MouseListener;
import com.github.kmacdonald222.asclepiasfw.networking.NetListener;

//An abstract scene for the Asclepias Framework application
public abstract class AppScene implements KeyboardListener, MouseListener,
		NetListener {
	
	// Whether this scene has been initialized
	private boolean initialized = false;
	
	/*
	 * Call initialize and flag this scene as initialized
	 * @return boolean - Whether the scene was initialized successfully
	 */
	public boolean initializeScene() {
		if (initialized) {
			return false;
		}
		initialized = initialize();
		return initialized;
	}
	/*
	 * Call destroy and flag this scene as not initialized
	 * @return boolean - Whether the scene was destroyed successfully
	 */
	public boolean destroyScene() {
		if (!initialized) {
			return false;
		}
		initialized = false;
		return destroy();
	}
	/*
	 * Initialize this scene's memory
	 * @return boolean - Whether the scene was initialized successfully
	 */
	public abstract boolean initialize();
	/*
	 * Enter this scene from another in the application
	 * @param AppScene previousScene - The last scene in the application
	 */
	public abstract void enter(AppScene previousScene);
	/*
	 * Process user input to this scene
	 * @return boolean - Whether the application should remain open
	 */
	public abstract boolean processInput();
	/*
	 * Update this application's timed logic
	 * @param double delta - The target number of logic updates elapsed since
	 * the last timed update
	 */
	public abstract void timedUpdate(double delta);
	/*
	 * Leave this scene for another in the application
	 * @param AppScene nextScene - The next scene for the application
	 */
	public abstract void leave(AppScene scene);
	/*
	 * Free this scene's memory
	 * @return boolean - Whether the scene was destroyed successfully
	 */
	public abstract boolean destroy();
	
	/*
	 * Test whether this scene has been initialized
	 * @return boolean - Whether this scene has been initialized
	 */
	public boolean isInitialized() {
		return initialized;
	}
	
}