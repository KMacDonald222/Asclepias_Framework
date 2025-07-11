/*
 * File:		MouseButton.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.07.10
 * Purpose:		Enumerates all mouse button codes and string representations for
 * 				the mouse input management system
 */

package com.github.kmacdonald222.asclepiasfw.input;

import java.awt.event.MouseEvent;

// Enumeration of all buttons on a standard mouse
public enum MouseButton {
	
	// Enumerate all mouse buttons with ID numbers and string names
	LEFT(MouseEvent.BUTTON1, "Left"),
	MIDDLE(MouseEvent.BUTTON2, "Middle"),
	RIGHT(MouseEvent.BUTTON3, "Right"),
	UNKNOWN(-1, "Unknown");
	
	// The Java button ID code of this button
	private int buttonCode = 0;
	// The name of this button
	private String name = "";
	
	/*
	 * Construct a new mouse button with a code and common name
	 * @param int buttonCode - The Java ID code of this mouse button
	 * @param String name - The name of this mouse button
	 */
	private MouseButton(int buttonCode, String name) {
		this.buttonCode = buttonCode;
		this.name = name;
	}
	
	/*
	 * Get a button by its Java ID code
	 * @param int buttonCode - The Java ID code of the mouse button to find
	 * @return MouseButton - The mouse button with the give ID code or UNKNOWN
	 */
	public static MouseButton fromButtonCode(int buttonCode) {
		for (int i = 0; i < values().length; i++) {
			if (values()[i].buttonCode == buttonCode) {
				return values()[i];
			}
		}
		return UNKNOWN;
	}
	
	/*
	 * Convert this button to its name to represent as a string
	 * @return String - The string representation of this button
	 */
	@Override
	public String toString() {
		return name;
	}
	
	/*
	 * Get the Java ID code of this button
	 * @return int - The Java ID code of this button
	 */
	public int getButtonCode() {
		return buttonCode;
	}
	/*
	 * Get the name of this button as a string
	 * @return String - The name of this button
	 */
	public String getName() {
		return name;
	}

}
