/*
 * File:		Dimensions2D.java
 * Author:		Keegan MacDonald
 * Created:		2025.05.23@19:57
 * Purpose:		Implement a 2D dimensions class, wrapper class for two integers
 */

package com.github.kmacdonald222.asclepiasfw.data;

// Wrapper for two integers
public class Dimensions2D {
	
	// Horizontal measure
	public int width = 0;
	// Vertical measure
	public int height = 0;
	
	/*
	 * Construct a blank pair of dimensions
	 */
	public Dimensions2D() {
		width = 0;
		height = 0;
	}
	/*
	 * Construct a pair of dimensions with predefined values
	 * @param int width - Horizontal measure
	 * @param int height - Vertical measure
	 */
	public Dimensions2D(int width, int height) {
		this.width = width;
		this.height = height;
	}
	/*
	 * Compute the area of this pair of dimensions
	 * @return int - The area - width x height
	 */
	public int area() {
		return width * height;
	}
	/*
	 * Convert this pair of dimensions to a string
	 * @return String - String representation of form widthxheight
	 */
	@Override
	public String toString() {
		return Integer.toString(width) + "x" + Integer.toString(height);
	}
	
}
