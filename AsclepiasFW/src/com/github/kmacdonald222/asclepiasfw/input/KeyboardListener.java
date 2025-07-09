/*
 * File:		KeyboardListener.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.07.08
 * Purpose:		Defines asynchronous callback functions for keyboard input
 */

package com.github.kmacdonald222.asclepiasfw.input;

// Abstract interface for keyboard input event callbacks
public interface KeyboardListener {

	/*
	 * A key has been been pressed on the keyboard
	 * @param KeyboardKey key - The ID of the key
	 */
	public default void keyboardKeyPressed(KeyboardKey key) {
	}
	/*
	 * A key has been released on the keyboard
	 * @param KeyboardKey key - The ID of the key
	 */
	public default void keyboardKeyReleased(KeyboardKey key) {
	}
	/*
	 * A character has been typed on the keyboard
	 * @param char character - The character typed
	 */
	public default void characterTyped(char character) {
	}
	
}
