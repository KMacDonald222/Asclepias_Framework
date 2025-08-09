/*
 * File:		Vector2D.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.07.01
 * Purpose:		Define a 2D floating-point vector class and related math
 * 				operations
 */

package com.github.kmacdonald222.asclepiasfw.data;

// 2D floating-point vector class
public class Vector2D {

	// The first component of this vector
	public double x = 0.0d;
	// The second component of this vector
	public double y = 0.0d;
	
	/*
	 * Initialize a zero-vector
	 */
	public Vector2D() {
		x = 0.0d;
		y = 0.0d;
	}
	/*
	 * Initialize a vector with components
	 * @param double x - The first component
	 * @param double y - The second component
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Compute the sum of this vector with another one
	 * @param Vec2D vec - The vector to add to this one
	 * @return Vec2D - The sum of this vector with vec
	 */
	public Vector2D add(Vector2D vec) {
		return new Vector2D(x + vec.x, y + vec.y);
	}
	/*
	 * Compute the difference between this vector and another one
	 * @param Vec2D vec - The vector to subtract from this one
	 * @return Vec2D - The difference between this vector and vec
	 */
	public Vector2D subtract(Vector2D vec) {
		return add(vec.scale(-1.0d));
	}
	/*
	 * Compute the product of this vector and a scalar
	 * @param double scalar - The scalar to multiply this vector by
	 * @return Vec2D - The product of this vector and the scalar
	 */
	public Vector2D scale(double scalar) {
		return new Vector2D(x * scalar, y * scalar);
	}
	/*
	 * Compute the sum of the elements of this vector
	 * @return double - The sum of this vector's components
	 */
	public double sum() {
		return x + y;
	}
	/*
	 * Compute the element-wise product of this vector and another one
	 * @param Vec2D vec - The vector to multiply with this one
	 * @return Vec2D - The element-wise product of this vector and vec
	 */
	public Vector2D multiply(Vector2D vec) {
		return new Vector2D(x * vec.x, y * vec.y);
	}
	/*
	 * Compute the dot product of this vector and another one
	 * @param Vec2D vec - The vector to dot-multiply with this one
	 * @return double - The dot product of this vector and vec
	 */
	public double dot(Vector2D vec) {
		return multiply(vec).sum();
	}
	/*
	 * Compute the magnitude of this vector
	 * @return double - This vector's magnitude
	 */
	public double magnitude() {
		return Math.sqrt((x * x) + (y * y));
	}
	/*
	 * Compute the distance between this vector and another one
	 * @param Vec2D vec - The vector to compute this vector's distance from
	 * @return double - The distance between this vector and vec
	 */
	public double distance(Vector2D vec) {
		return subtract(vec).magnitude();
	}
	/*
	 * Compute the normalized version of this vector (setting this vector's
	 * magnitude to 1 while preserving its direction)
	 * @return Vec2D - The normalized version of this vector
	 */
	public Vector2D normalize() {
		return scale(1.0d / magnitude());
	}
	/*
	 * Compute the angle between this vector and another one
	 * @param Vec2D vec - The vector to compute this vector's angle with
	 * @return double - The angle between this vector and vec in radians
	 */
	public double angle(Vector2D vec) {
		return Math.acos(dot(vec) / (magnitude() * vec.magnitude()));
	}
	/*
	 * Compute the vector resulting from a rotation of this vector about the
	 * origin
	 * @param double angle - The angle to rotate by in radians
	 * @return Vec2D - The rotated version of this vector
	 */
	public Vector2D rotate(double angle) {
		return new Vector2D((x * Math.cos(angle)) - (y * Math.sin(angle)),
				(x * Math.sin(angle)) + (y * Math.cos(angle)));
	}
	/*
	 * Compute the vector resulting from a rotation of this vector about another
	 * one
	 * @param double angle - The angle to rotate by in radians
	 * @param Vec2D center - The vector to rotate this one about
	 * @return Vec2D - The rotated version of this vector
	 */
	public Vector2D rotate(double angle, Vector2D center) {
		return subtract(center).rotate(angle).add(center);
	}
	/*
	 * Convert this vector to a string in format <x,y>
	 * @return String - A string representing this vector's components
	 */
	@Override
	public String toString() {
		return "<" + Double.toString(x) + "," + Double.toString(y) + ">";
	}
	
}
