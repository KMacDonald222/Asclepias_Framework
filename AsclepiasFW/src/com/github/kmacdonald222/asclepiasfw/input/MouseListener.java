/*
 * File:		MouseListener.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.07.10
 * Purpose:		Defines asynchronous callback functions for mouse input events
 */

package com.github.kmacdonald222.asclepiasfw.input;

import com.github.kmacdonald222.asclepiasfw.data.Vector2D;

// Abstract interface for mouse input event callbacks
public interface MouseListener {

	/*
	 * A button has been pressed on the mouse
	 * @param MouseButton button - The button pressed
	 */
	public default void mouseButtonPressed(MouseButton button) {
	}
	/*
	 * A button has been released on the mouse
	 * @param MouseButton button - The button released
	 */
	public default void mouseButtonReleased(MouseButton button) {
	}
	/*
	 * The scroll wheel has been moved on the mouse
	 * @param double scrollDistance - The distance the scroll wheel moved
	 * @param double previousScrollDistance - The distance the scroll wheel
	 * moved in the last logic update
	 */
	public default void mouseScrollWheelMoved(double scrollDistance,
			double previousScrollDistance) {
	}
	/*
	 * The mouse cursor has moved on the application's window
	 * @param Vec2D cursorPosition - The new position of the mouse cursor
	 * @param Vec2D previousCursorPosition - The previous position of the mouse
	 * cursor
	 */
	public default void mouseCursorMoved(Vector2D cursorPosition,
			Vector2D previousCursorPosition) {
	}
	
}
