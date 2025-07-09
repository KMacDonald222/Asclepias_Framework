/*
 * File:		KeyboardKey.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.07.08
 * Purpose:		Enumerates all key codes and string representations for the
 * 				keyboard input management system
 */
package com.github.kmacdonald222.asclepiasfw.input;

import java.awt.event.KeyEvent;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

// Enumeration of all keys on the US-English keyboard
public enum KeyboardKey {
	
	// Enumerate all keyboard keys with ID numbers and string names
	ESCAPE(KeyEvent.VK_ESCAPE, "Escape"),
	F1(KeyEvent.VK_F1, "F1"),
	F2(KeyEvent.VK_F2, "F2"),
	F3(KeyEvent.VK_F3, "F3"),
	F4(KeyEvent.VK_F4, "F4"),
	F5(KeyEvent.VK_F5, "F5"),
	F6(KeyEvent.VK_F6, "F6"),
	F7(KeyEvent.VK_F7, "F7"),
	F8(KeyEvent.VK_F8, "F8"),
	F9(KeyEvent.VK_F9, "F9"),
	F10(KeyEvent.VK_F10, "F10"),
	F11(KeyEvent.VK_F11, "F11"),
	F12(KeyEvent.VK_F12, "F12"),
	F13(KeyEvent.VK_F13, "F13"),
	F14(KeyEvent.VK_F14, "F14"),
	F15(KeyEvent.VK_F15, "F15"),
	F16(KeyEvent.VK_F16, "F16"),
	F17(KeyEvent.VK_F17, "F17"),
	F18(KeyEvent.VK_F18, "F18"),
	F19(KeyEvent.VK_F19, "F19"),
	F20(KeyEvent.VK_F20, "F20"),
	F21(KeyEvent.VK_F21, "F21"),
	F22(KeyEvent.VK_F22, "F22"),
	F23(KeyEvent.VK_F23, "F23"),
	F24(KeyEvent.VK_F24, "F24"),
	HOME(KeyEvent.VK_HOME, "Home"),
	END(KeyEvent.VK_END, "End"),
	INSERT(KeyEvent.VK_INSERT, "Insert"),
	DELETE(KeyEvent.VK_DELETE, "Delete"),
	GRAVE(KeyEvent.VK_BACK_QUOTE, "`"),
	ONE(KeyEvent.VK_1, "1"),
	TWO(KeyEvent.VK_2, "2"),
	THREE(KeyEvent.VK_3, "3"),
	FOUR(KeyEvent.VK_4, "4"),
	FIVE(KeyEvent.VK_5, "5"),
	SIX(KeyEvent.VK_6, "6"),
	SEVEN(KeyEvent.VK_7, "7"),
	EIGHT(KeyEvent.VK_8, "8"),
	NINE(KeyEvent.VK_9, "9"),
	ZERO(KeyEvent.VK_0, "0"),
	MINUS(KeyEvent.VK_MINUS, "-"),
	EQUALS(KeyEvent.VK_EQUALS, "="),
	BACKSPACE(KeyEvent.VK_BACK_SPACE, "Backspace"),
	Q(KeyEvent.VK_Q, "Q"),
	W(KeyEvent.VK_W, "W"),
	E(KeyEvent.VK_E, "E"),
	R(KeyEvent.VK_R, "R"),
	T(KeyEvent.VK_T, "T"),
	Y(KeyEvent.VK_Y, "Y"),
	U(KeyEvent.VK_U, "U"),
	I(KeyEvent.VK_I, "I"),
	O(KeyEvent.VK_O, "O"),
	P(KeyEvent.VK_P, "P"),
	LEFT_BRACKET(KeyEvent.VK_OPEN_BRACKET, "["),
	RIGHT_BRACKET(KeyEvent.VK_CLOSE_BRACKET, "]"),
	BACKSLASH(KeyEvent.VK_BACK_SLASH, "\\"),
	CAPS_LOCK(KeyEvent.VK_CAPS_LOCK, "Caps Lock"),
	A(KeyEvent.VK_A, "A"),
	S(KeyEvent.VK_S, "S"),
	D(KeyEvent.VK_D, "D"),
	F(KeyEvent.VK_F, "F"),
	G(KeyEvent.VK_G, "G"),
	H(KeyEvent.VK_H, "H"),
	J(KeyEvent.VK_J, "J"),
	K(KeyEvent.VK_K, "K"),
	L(KeyEvent.VK_L, "L"),
	SEMICOLON(KeyEvent.VK_SEMICOLON, ";"),
	APOSTROPHE(KeyEvent.VK_QUOTE, "'"),
	ENTER(KeyEvent.VK_ENTER, "Enter"),
	SHIFT(KeyEvent.VK_SHIFT, "Shift"),
	Z(KeyEvent.VK_Z, "Z"),
	X(KeyEvent.VK_X, "X"),
	C(KeyEvent.VK_C, "C"),
	V(KeyEvent.VK_V, "V"),
	B(KeyEvent.VK_B, "B"),
	N(KeyEvent.VK_N, "N"),
	M(KeyEvent.VK_M, "M"),
	COMMA(KeyEvent.VK_COMMA, ","),
	PERIOD(KeyEvent.VK_PERIOD, "."),
	SLASH(KeyEvent.VK_SLASH, "/"),
	CONTROL(KeyEvent.VK_CONTROL, "Control"),
	WINDOWS(KeyEvent.VK_WINDOWS, "Windows"),
	ALT(KeyEvent.VK_ALT, "Alt"),
	SPACE(KeyEvent.VK_SPACE, "Space"),
	LEFT(KeyEvent.VK_LEFT, "Left"),
	DOWN(KeyEvent.VK_DOWN, "Down"),
	UP(KeyEvent.VK_UP, "Up"),
	RIGHT(KeyEvent.VK_RIGHT, "Right"),
	NUM_LOCK(KeyEvent.VK_NUM_LOCK, "Num Lock"),
	KEYPAD_DIVIDE(KeyEvent.VK_DIVIDE, "/"),
	KEYPAD_MULTIPLY(KeyEvent.VK_MULTIPLY, "*"),
	KEYPAD_MINUS(KeyEvent.VK_SUBTRACT, "-"),
	KEYPAD_PLUS(KeyEvent.VK_ADD, "+"),
	KEYPAD_DECIMAL(KeyEvent.VK_DECIMAL, "."),
	KEYPAD_SEVEN(KeyEvent.VK_NUMPAD7, "7"),
	KEYPAD_EIGHT(KeyEvent.VK_NUMPAD8, "8"),
	KEYPAD_NINE(KeyEvent.VK_NUMPAD9, "9"),
	KEYPAD_FOUR(KeyEvent.VK_NUMPAD4, "4"),
	KEYPAD_FIVE(KeyEvent.VK_NUMPAD5, "5"),
	KEYPAD_SIX(KeyEvent.VK_NUMPAD6, "6"),
	KEYPAD_ONE(KeyEvent.VK_NUMPAD1, "1"),
	KEYPAD_TWO(KeyEvent.VK_NUMPAD2, "2"),
	KEYPAD_THREE(KeyEvent.VK_NUMPAD3, "3"),
	KEYPAD_ZERO(KeyEvent.VK_NUMPAD0, "0"),
	KEYPAD_LEFT(KeyEvent.VK_KP_LEFT, "Left"),
	KEYPAD_DOWN(KeyEvent.VK_KP_DOWN, "Down"),
	KEYPAD_UP(KeyEvent.VK_KP_UP, "Up"),
	KEYPAD_RIGHT(KeyEvent.VK_KP_RIGHT, "Right"),
	PAGE_UP(KeyEvent.VK_PAGE_UP, "Page Up"),
	PAGE_DOWN(KeyEvent.VK_PAGE_DOWN, "Page Down"),
	BEGIN(KeyEvent.VK_BEGIN, "Begin"),
	CONTEXT_MENU(KeyEvent.VK_CONTEXT_MENU, "Context Menu"),
	PAUSE(KeyEvent.VK_PAUSE, "Pause"),
	SCROLL_LOCK(KeyEvent.VK_SCROLL_LOCK, "Scroll Lock"),
	UNKNOWN(-1, "Unknown");
	
	// The Java key ID code of this key
	private int keyCode = 0;
	// The common name or symbol of this key
	private String name = "";
	
	/*
	 * Construct a new keyboard key with a code and common name
	 * @param int keyCode - The Java key ID code of this key
	 * @param String name - The common name or symbol of this key
	 */
	private KeyboardKey(int keyCode, String name) {
		this.keyCode = keyCode;
		this.name = name;
	}
	
	/*
	 * Get a key by its Java key ID code
	 * @param int keyCode - The Java key ID code of the key to find
	 * @return KeyboardKey - The keyboard key with the given ID code or UNKNOWN
	 */
	public static KeyboardKey fromInt(int keyCode) {
		for (int i = 0; i < values().length; i++) {
			if (values()[i].keyCode == keyCode) {
				return values()[i];
			}
		}
		App.Log.write(LogSource.Keyboard, LogPriority.Warning, "Unknown ",
				"keyboard event with key code ", keyCode);
		return UNKNOWN;
	}
	
	/*
	 * Convert this key to its common name or symbol to represent as a string
	 * @return String - The string representation of this key
	 */
	@Override
	public String toString() {
		return name;
	}
	
	/*
	 * Get the Java key ID code of this key
	 * @return int - The Java key ID code of this key
	 */
	public int getKeyCode() {
		return keyCode;
	}
	/*
	 * Get the common name or symbol of this key as a string
	 * @return String - The common name or symbol of this key
	 */
	public String getName() {
		return name;
	}
	
}
