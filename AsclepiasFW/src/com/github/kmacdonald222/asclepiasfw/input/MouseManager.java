/*
 * File:		MouseManager.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.07.10
 * Purpose:		Defines the main class of the mouse input management system for
 * 				Asclepias Framework applications
 */

package com.github.kmacdonald222.asclepiasfw.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.data.Vec2D;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

// The mouse input management system for Asclepias Framework applications
public class MouseManager implements java.awt.event.MouseListener,
		MouseWheelListener, MouseMotionListener {
	
	// Wrapper for the current and previous logic update's state of a button on
	// the mouse
	private class ButtonState {
		
		// Whether the button is currently down
		public boolean state = false;
		// Whether a button was down before the last logic update
		public boolean previousState = false;
		
		/*
		 * Construct a button state with pre-defined states
		 * @param boolean state - Whether the button is currently down
		 * @param boolean previousState - Whether the button was down before the
		 * last logic update
		 */
		public ButtonState(boolean state, boolean previousState) {
			this.state = state;
			this.previousState = previousState;
		}
		
	}
	
	// Whether the mouse input management system has been initialized
	private boolean initialized = false;
	// The set of button states mapped to their codes
	private Map<Integer, ButtonState> buttonStates = null;
	// The distance the mouse scroll wheel has moved this logic update
	private double scrollDistance = 0.0d;
	// The distance the mouse scroll wheel moved before the last logic update
	private double previousScrollDistance = 0.0d;
	// The current position of the mouse cursor on the application's window
	private Vec2D cursorPosition = null;
	// The position of the mouse cursor on the application's window before the
	// last logic update
	private Vec2D previousCursorPosition = null;
	// The set of classes subscribed to mouse input event callbacks
	private List<MouseListener> listeners = null;
	
	/*
	 * Initialize the mouse input management system's memory and attach it to
	 * the application's window
	 * @return boolean - Whether the mouse input management system was
	 * successfully initialized
	 */
	public boolean initialize() {
		if (initialized) {
			return false;
		}
		App.Log.write(LogSource.Mouse, LogPriority.Info, "Initializing mouse ",
				"input manager");
		App.Window.getWindowHandle().getContentPane().addMouseListener(this);
		App.Window.getWindowHandle().getContentPane()
				.addMouseWheelListener(this);
		App.Window.getWindowHandle().getContentPane()
				.addMouseMotionListener(this);
		buttonStates = new HashMap<Integer, ButtonState>();
		scrollDistance = 0.0d;
		previousScrollDistance = 0.0d;
		cursorPosition = new Vec2D();
		previousCursorPosition = new Vec2D();
		listeners = new ArrayList<MouseListener>();
		initialized = true;
		return initialized;
	}
	/*
	 * Update the states of the buttons on the mouse, its scroll distance, and
	 * its cursor position
	 */
	public void update() {
		for (Map.Entry<Integer, ButtonState> buttonState
				: buttonStates.entrySet()) {
			buttonState.getValue().previousState = buttonState.getValue().state;
		}
		previousScrollDistance = scrollDistance;
		scrollDistance = 0.0d;
		previousCursorPosition = cursorPosition;
	}
	/*
	 * A button has been clicked on the mouse (unused method)
	 * @param MouseEvent event - The event data
	 */
	@Override
	public void mouseClicked(MouseEvent event) {
	}
	/*
	 * A button has been pressed on the mouse
	 * @param MouseEvent event - The event data
	 */
	@Override
	public void mousePressed(MouseEvent event) {
		MouseButton button = MouseButton.fromButtonCode(event.getButton());
		if (button == MouseButton.UNKNOWN) {
			return;
		}
		if (buttonStates.containsKey(event.getButton())) {
			buttonStates.get(event.getButton()).state = true;
		} else {
			buttonStates.put(event.getButton(), new ButtonState(true, false));
		}
		if (!wasButtonDown(button)) {
			for (MouseListener listener : listeners) {
				listener.mouseButtonPressed(button);
			}
		}
	}
	/*
	 * A button has been released on the mouse
	 * @param MouseEvent event - The event data
	 */
	@Override
	public void mouseReleased(MouseEvent event) {
		MouseButton button = MouseButton.fromButtonCode(event.getButton());
		if (button == MouseButton.UNKNOWN) { 
			return;
		}
		if (buttonStates.containsKey(event.getButton())) {
			buttonStates.get(event.getButton()).state = false;
		} else {
			buttonStates.put(event.getButton(), new ButtonState(false, true));
		}
		if (!isButtonDown(button)) {
			for (MouseListener listener : listeners) {
				listener.mouseButtonReleased(button);
			}
		}
	}
	/*
	 * The mouse cursor has been dragged on the application's window
	 * @param MouseEvent event - The event data
	 */
	@Override
	public void mouseDragged(MouseEvent event) {
	}
	/*
	 * The mouse cursor has entered the application's window
	 * @param MouseEvent event - The event data
	 */
	@Override
	public void mouseEntered(MouseEvent event) {
	}
	/*
	 * The mouse cursor has exited the application's window
	 * @param MouseEvent event - The event data
	 */
	@Override
	public void mouseExited(MouseEvent event) {
	}
	/*
	 * The mouse's scroll wheel has been moved
	 * @param MouseWheelEvent event - The event data
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		double scrollDistance = -event.getPreciseWheelRotation();
		this.scrollDistance = scrollDistance;
		for (MouseListener listener : listeners) {
			listener.mouseScrollWheelMoved(scrollDistance);
		}
	}
	/*
	 * The mouse cursor has moved on the application's window
	 * @param MouseEvent event - The event data
	 */
	@Override
	public void mouseMoved(MouseEvent event) {
		Vec2D cursorPosition = new Vec2D(event.getX(),
				App.Window.getDimensions().y - event.getY());
		this.cursorPosition = cursorPosition;
		for (MouseListener listener : listeners) {
			listener.mouseCursorMoved(cursorPosition, previousCursorPosition);
		}
	}
	/*
	 * Disconnect the mouse input management system from the application's
	 * window and free its memory
	 * @return boolean - Whether the mouse input management system was
	 * successfully destroyed
	 */
	public boolean destroy() {
		if (!initialized) {
			return false;
		}
		App.Log.write(LogSource.Mouse, LogPriority.Info, "Destroying mouse ",
				"input manager");
		boolean success = true;
		App.Window.getWindowHandle().getContentPane().removeMouseListener(this);
		App.Window.getWindowHandle().getContentPane()
				.removeMouseWheelListener(this);
		App.Window.getWindowHandle().getContentPane()
				.removeMouseMotionListener(this);
		buttonStates.clear();
		buttonStates = null;
		scrollDistance = 0.0d;
		previousScrollDistance = 0.0d;
		cursorPosition = null;
		previousCursorPosition = null;
		listeners.clear();
		listeners = null;
		initialized = false;
		return success;
	}
	
	/*
	 * Test if a button is currently down on the mouse
	 * @param MouseButton button - The button to test
	 * @return boolean - Whether the given button is currently down
	 */
	public boolean isButtonDown(MouseButton button) {
		if (!buttonStates.containsKey(button.getButtonCode())) {
			return false;
		}
		return buttonStates.get(button.getButtonCode()).state;
	}
	/*
	 * Test if a button was down on the mouse before the last logic update
	 * @pram MouseButton button - The button to test
	 * @return boolean - Whether the given button was down
	 */
	public boolean wasButtonDown(MouseButton button) {
		if (!buttonStates.containsKey(button.getButtonCode())) {
			return false;
		}
		return buttonStates.get(button.getButtonCode()).previousState;
	}
	/*
	 * Test if a button was just pressed on the mouse
	 * @param MouseButton button - The button to test
	 * @return boolean - Whether the given button was just pressed
	 */
	public boolean isButtonPressed(MouseButton button) {
		return isButtonDown(button) && !wasButtonDown(button);
	}
	/*
	 * Test if a button was just released on the mouse
	 * @param MouseButton button - The button to test
	 * @return boolean - Whether the given button was just released
	 */
	public boolean isButtonReleased(MouseButton button) {
		return !isButtonDown(button) && wasButtonDown(button);
	}
	/*
	 * Test if the mouse's scroll wheel was just moved
	 * @return boolean - Whether the mouse's scroll wheel was just moved
	 */
	public boolean isScrollWheelMoved() {
		return scrollDistance != previousScrollDistance
				&& scrollDistance != 0.0d;
	}
	/*
	 * Get the distance the mouse's scroll wheel moved this logic update
	 * @return double - The distance the mouse's scroll wheel moved this logic
	 * update
	 */
	public double getScrollDistance() {
		return scrollDistance;
	}
	/*
	 * Get the distance the mouse's scroll wheel moved in the last logic update
	 * @return double - The distance the mouse's scroll wheel moved in the last
	 * logic update
	 */
	public double getPreviousScrollDistance() {
		return previousScrollDistance;
	}
	/*
	 * Test whether the mouse cursor moved since the last logic update
	 * @boolean - Whether the mouse cursor was just moved
	 */
	public boolean isCursorMoved() {
		return cursorPosition != previousCursorPosition;
	}
	/*
	 * Get the current position of the mouse cursor on the application's window
	 * @return Vec2D - The current position of the mouse cursor
	 */
	public Vec2D getCursorPosition() {
		return cursorPosition;
	}
	/*
	 * Get the position of the mouse cursor on the application's window in the
	 * last logic update
	 * @return Vec2D - The previous position of the mouse cursor
	 */
	public Vec2D getPreviousCursorPosition() {
		return previousCursorPosition;
	}
	/*
	 * Get the current set of listeners subscribed to the mouse input management
	 * system
	 * @return List<MouseListener> - The current set of listeners
	 */
	public List<MouseListener> getListeners() {
		return listeners;
	}
	/*
	 * Add a listener to the set subscribed to the mouse input management system
	 * @param MouseListener listener - The listener to add
	 * @return boolean - Whether the listener was added successfully
	 */
	public boolean addListener(MouseListener listener) {
		if (listeners.contains(listener)) {
			return false;
		}
		App.Log.write(LogSource.Mouse, LogPriority.Info, "Adding mouse input ",
				"listener");
		listeners.add(listener);
		return true;
	}
	/*
	 * Remove a listener from the set subscribed to the mouse input management
	 * system
	 * @param MouseListener listener - The listener to remove
	 * @return boolean - Whether the listener was removed successfully
	 */
	public boolean removeListener(MouseListener listener) {
		if (!listeners.contains(listener)) {
			return false;
		}
		App.Log.write(LogSource.Mouse, LogPriority.Info, "Removing mouse ",
				"input listener");
		listeners.remove(listener);
		return true;
	}

}
