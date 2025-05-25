/*
 * File:		AsclepiasFWTest.java
 * Author:		Keegan MacDonald
 * Created:		2025.05.18@19:41
 * Purpose:		Implement the main entry point of the AsclepiasFWTest program
 */

package com.github.kmacdonald222.asclepiasfwtest;

import com.github.kmacdonald222.asclepiasfw.application.Configuration;
import com.github.kmacdonald222.asclepiasfw.application.Application;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;
import com.github.kmacdonald222.asclepiasfw.data.Dimensions2D;
import com.github.kmacdonald222.asclepiasfw.data.Vector2D;
import com.github.kmacdonald222.asclepiasfw.data.ColorRGBA;

// Main class of the AsclepiasFWTest program
public class AsclepiasFWTest {
	
	/*
	 * Main entry point of the AsclepiasFWTest program - runs a test of the
	 * newest module of the Asclepias Framework
	 * @param String[] arguments - The command line arguments passed to the
	 * AsclepiasFWTest program
	 */
	public static void main(String[] arguments) {
		// Create the Asclepias Framework application's configuration
		Configuration configuration = new Configuration();
		// Initialize the application
		if (!Application.Initialize(configuration)) {
			return;
		}
		// Test the Dimensions2D class
		Dimensions2D d = new Dimensions2D(200, 300);
		Application.Log.write(LogSource.Application, LogPriority.Info,
				"d = ", d, ", area(d) = ", d.area());
		// Test the Vector2D class
		Vector2D v1 = new Vector2D();
		v1.x = 4.0f;
		v1.y = 3.0f;
		Vector2D v2 = new Vector2D();
		v2.x = 1.5f;
		v2.y = 3.5f;
		Application.Log.write(LogSource.Application, LogPriority.Info,
				"v1 = ", v1, ", v2 = ", v2, ", mag(v1) = ", v1.magnitude(),
				", norm(v1) = ", v1.normalize(),  ", v1 * v2 = ",
				v1.multiply(v2), ", sum(v1) = ", v1.sum(), ", v1 dot v2 = ",
				v1.dot(v2), ", 3 * v1 = ", v1.scale(3.0f), ", v1 + v2 = ",
				v1.add(v2), ", v1 - v2 = ", v1.subtract(v2));
		// Test the ColorRGBA class
		ColorRGBA c = new ColorRGBA();
		c.r = 255;
		c.g = 128;
		c.b = 23;
		c.a = 15;
		Application.Log.write(LogSource.Application, LogPriority.Info,
				"c = ", c, ", int(c) = ", c.toInteger(), " = ",
				Integer.toBinaryString(c.toInteger()));
		// Free the application's memory
		Application.Destroy();
	}
	
}
