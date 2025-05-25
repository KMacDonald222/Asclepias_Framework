/*
 * File:		Vector2D.java
 * Author:		Keegan MacDonald
 * Created:		2025.05.23@20:00
 * Purpose:		Implement a 2D vector class, wrapper for a pair of doubles
 */

package com.github.kmacdonald222.asclepiasfw.data;

// A 2D double-precision vector class
public class Vector2D {
	
	// Horizontal component
	public double x = 0.0f;
	// Vertical component
	public double y = 0.0f;
	
	/*
	 * Construct a 2D 0-vector
	 */
	public Vector2D() {
		x = 0.0f;
		y = 0.0f;
	}
	/*
	 * Construct a 2D vector from constant values
	 * @param double x - Horizontal component
	 * @param double y - Vertical component
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/*
	 * Compute the magnitude of this 2D vector
	 * @return double - The magnitude of this vector
	 */
	public double magnitude() {
		return Math.sqrt((x * x) + (y * y));
	}
	/*
	 * Compute the unit vector with the same direction of this 2D vector
	 * @return Vector2D - This vector's normalized version
	 */
	public Vector2D normalize() {
		return scale(1.0f / magnitude());
	}
	/*
	 * Compute the element-wise product of this vector with another given vector
	 * @param Vector2D vector - The vector to multiply with this one
	 * @return Vector2D - The product of this vector with the given one
	 */
	public Vector2D multiply(Vector2D vector) {
		return new Vector2D(x * vector.x, y * vector.y);
	}
	/*
	 * Compute the sum of the elements of this vector
	 * @return double - This vector's element-wise sum
	 */
	public double sum() {
		return x + y;
	}
	/*
	 * Compute the dot product of this vector with another given vector
	 * @param Vector2D vector - The vector to dot-multiply with this one
	 * @return double - The dot product of this vector with the given one
	 */
	public double dot(Vector2D vector) {
		return multiply(vector).sum();
	}
	/*
	 * Compute the product of this vector with a constant scalar
	 * @param double scalar - The scalar to multiply with this vector
	 * @return Vector2D - The product of this vector with the given scalar
	 */
	public Vector2D scale(double scalar) {
		return new Vector2D(x * scalar, y * scalar);
	}
	/*
	 * Compute the sum of this vector and another given vector
	 * @param Vector2D vector - The vector to add to this one
	 * @return Vector2D - The sum of this vector and the given one
	 */
	public Vector2D add(Vector2D vector) {
		return new Vector2D(x + vector.x, y + vector.y);
	}
	/*
	 * Compute the difference between this vector and another given vector
	 * @param Vector2D vector - The vector to subtract from this one
	 * @return Vector2D - The difference between this vector and the given one
	 */
	public Vector2D subtract(Vector2D vector) {
		return add(vector.scale(-1.0f));
	}
	/*
	 * Convert this vector to a string
	 * @return String - String representation of form [x,y]
	 */
	@Override
	public String toString() {
		return "[" + Double.toString(x) + "," + Double.toString(y) + "]";
	}
	
}
