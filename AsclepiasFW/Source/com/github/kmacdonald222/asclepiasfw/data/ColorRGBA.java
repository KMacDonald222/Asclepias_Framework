/*
 * File:		ColorRGBA.java
 * Author:		Keegan MacDonald
 * Created:		2025.05.23@23:40
 * Purpose:		Implement an RGBA color class, wrapper for four bytes
 */

package com.github.kmacdonald222.asclepiasfw.data;

// Wrapper for four bytes, RGBA color
public class ColorRGBA {
	
	// The red channel of this color
	public int r = 0x00;
	// The green channel of this color
	public int g = 0x00;
	// The blue channel of this color
	public int b = 0x00;
	// The alpha channel of this color
	public int a = 0x00;
	
	/*
	 * Construct a blank RGBA color
	 */
	public ColorRGBA() {
		r = 0;
		g = 0;
		b = 0;
		a = 0;
	}
	/*
	 * Construct a color with constant RGBA values
	 * @param int r - The red channel value
	 * @param int g - The green channel value
	 * @param int b - The blue channel value
	 * @param int a - The alpha channel value
	 */
	public ColorRGBA(int r, int b, int g, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	/*
	 * Combine this color's RGBA channels into a 32-bit integer
	 * @return int - The integer representation of this color
	 */
	public int toInteger() {
		int integer = 0x00000000;
		integer |= r;
		integer <<= 8;
		integer |= g;
		integer <<= 8;
		integer |= b;
		integer <<= 8;
		integer |= a;
		return integer;
	}
	/*
	 * Convert this color to a string
	 * @return String - String representation of this color of form RGBA
	 */
	public String toString() {
		return "(" + Integer.toString(r) + "," + Integer.toString(g) + ","
				+ Integer.toString(b) + "," + Integer.toString(a) + ")";
	}
	
}
