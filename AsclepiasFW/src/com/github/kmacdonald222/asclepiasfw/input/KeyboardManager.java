/*
 * File:		KeyboardManager.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.07.08
 * Purpose:		Defines the main class of the keyboard input management system
 * 				for Asclepias Framework applications
 */

package com.github.kmacdonald222.asclepiasfw.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

// The keyboard input management system for Asclepias Framework applications
public class KeyboardManager implements KeyListener {
	
	// Wrapper for the current and previous logic update's state of a key on the
	// keyboard
	public class KeyState {
		
		// Whether the key is currently down
		public boolean isDown = false;
		// Whether the key was down before the last logic update
		public boolean wasDown = false;
		
		/*
		 * Construct a blank key state
		 */
		public KeyState() {
			isDown = false;
			wasDown = false;
		}
		/*
		 * Construct a key state with pre-defined states
		 * @param boolean isDown - Whether the key is currently down
		 * @param boolean wasDown - Whether the key was down before the last
		 * logic update
		 */
		public KeyState(boolean isDown, boolean wasDown) {
			this.isDown = isDown;
			this.wasDown = wasDown;
		}
		
	}

	// Whether the keyboard input management system has been initialized
	private boolean initialized = false;
	// The set of key states mapped to their codes
	private Map<Integer, KeyState> keyStates = null;
	// The set of classes subscribed to keyboard input event callbacks
	private List<KeyboardListener> listeners = null;
	
	/*
	 * Initialize the keyboard input management system's memory and attach it to
	 * the application's window
	 * @return boolean - Whether the keyboard input management system was
	 * successfully initialized
	 */
	public boolean initialize() {
		if (initialized) {
			return false;
		}
		App.Log.write(LogSource.Keyboard, LogPriority.Info, "Initializing ",
				"keyboard input manager");
		App.Window.getWindowHandle().addKeyListener(this);
		keyStates = new HashMap<Integer, KeyState>();
		listeners = new ArrayList<KeyboardListener>();
		initialized = true;
		return initialized;
	}
	/*
	 * Update the states of the keys on the keyboard
	 */
	public void update() {
		for (Map.Entry<Integer, KeyState> keyState : keyStates.entrySet()) {
			keyState.getValue().wasDown = keyState.getValue().isDown;
		}
	}
	/*
	 * Asynchronous callback for key press events on the keyboard
	 * @param KeyEvent event - The event data passed by the application's window
	 */
	@Override
	public void keyPressed(KeyEvent event) {
		if (keyStates.containsKey(event.getKeyCode())) {
			keyStates.get(event.getKeyCode()).isDown = true;
		} else {
			keyStates.put(event.getKeyCode(), new KeyState(true, false));
		}
		if (!wasKeyDown(KeyboardKey.fromInt(event.getKeyCode()))) {
			for (KeyboardListener listener : listeners) {
				listener.keyboardKeyPressed(
						KeyboardKey.fromInt(event.getKeyCode()));
			}
		}
	}
	/*
	 * Asynchronous callback for key release events on the keyboard
	 * @param KeyEvent event - The event data passed by the application's window
	 */
	@Override
	public void keyReleased(KeyEvent event) {
		if (keyStates.containsKey(event.getKeyCode())) {
			keyStates.get(event.getKeyCode()).isDown = false;
		} else {
			keyStates.put(event.getKeyCode(), new KeyState(false, true));
		}
		if (!isKeyDown(KeyboardKey.fromInt(event.getKeyCode()))) {
			for (KeyboardListener listener : listeners) {
				listener.keyboardKeyReleased(
						KeyboardKey.fromInt(event.getKeyCode()));
			}
		}
	}
	/*
	 * Asynchronous callback for text character typing events on the keyboard
	 * @param KeyEvent event - The event data passed by the application's window
	 */
	@Override
	public void keyTyped(KeyEvent event) {
		for (KeyboardListener listener : listeners) {
			listener.characterTyped(event.getKeyChar());
		}
	}
	/*
	 * Disconnect the keyboard input management system from the application's
	 * window and free its memory
	 * @return boolean - Whether the keyboard input management system was
	 * successfully destroyed
	 */
	public boolean destroy() {
		if (!initialized) {
			return false;
		}
		App.Log.write(LogSource.Keyboard, LogPriority.Info, "Destroying ",
				"keyboard input manager");
		boolean success = true;
		keyStates.clear();
		keyStates = null;
		listeners.clear();
		listeners = null;
		App.Window.getWindowHandle().removeKeyListener(this);
		initialized = false;
		return success;
	}
	
	/*
	 * Test if a key is currently down on the keyboard
	 * @param KeyboardKey key - The key to test
	 * @return boolean - Whether the given key is currently down
	 */
	public boolean isKeyDown(KeyboardKey key) {
		if (!keyStates.containsKey(key.getKeyCode())) {
			return false;
		}
		return keyStates.get(key.getKeyCode()).isDown;
	}
	/*
	 * Test if a key was down on the keyboard before the last logic update
	 * @param KeyboardKey key - The key to test
	 * @return boolean - Whether the given key was down
	 */
	public boolean wasKeyDown(KeyboardKey key) {
		if (!keyStates.containsKey(key.getKeyCode())) {
			return false;
		}
		return keyStates.get(key.getKeyCode()).wasDown;
	}
	/*
	 * Test if a key was just pressed on the keyboard
	 * @param KeyboardKey key - The key to test
	 * @return boolean - Whether the given key was just pressed
	 */
	public boolean isKeyPressed(KeyboardKey key) {
		return isKeyDown(key) && !wasKeyDown(key);
	}
	/*
	 * Test if a key was just released on the keyboard
	 * @param KeyboardKey key - The key to test
	 * @return boolean - Whether the given key was just released
	 */
	public boolean isKeyReleased(KeyboardKey key) {
		return !isKeyDown(key) && wasKeyDown(key);
	}
	/*
	 * Get the current set of listeners subscribed to the keyboard input
	 * management system
	 * @return List<KeyboardListener> - The current set of listeners
	 */
	public List<KeyboardListener> getListeners() {
		return listeners;
	}
	/*
	 * Add a listener to the set subscribed to the keyboard input management
	 * system
	 * @param KeyboardListener listener - The listener to add
	 * @return boolean - Whether the listener was added successfully
	 */
	public boolean addListener(KeyboardListener listener) {
		if (listeners.contains(listener)) {
			return false;
		}
		App.Log.write(LogSource.Keyboard, LogPriority.Info, "Adding keyboard ",
				"input listener");
		listeners.add(listener);
		return true;
	}
	/*
	 * Remove a listener from the set subscribed to the keyboard input
	 * management system
	 * @param KeyboardListener listener - The listener to remove
	 * @return boolean - Whether the listener was removed successfully
	 */
	public boolean removeListener(KeyboardListener listener) {
		if (!listeners.contains(listener)) {
			return false;
		}
		App.Log.write(LogSource.Keyboard, LogPriority.Info, "Removing ",
				"keyboard input listener");
		listeners.remove(listener);
		return true;
	}

}