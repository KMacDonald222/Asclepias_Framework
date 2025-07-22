/*
 * File:		AppConfig.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.06.30
 * Purpose:		Defines configuration structures for Asclepias Framework
 * 				applications
 */

package com.github.kmacdonald222.asclepiasfw.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.kmacdonald222.asclepiasfw.data.Vec2D;

// Wrapper class for all application configuration structures
public class AppConfig {
	
	// Logging system configuration structure
	public class Log {
		
		// Whether the logging system should write output to the console
		public boolean consoleOutputEnabled = true;
		// The file names the logging system should write output to
		public List<String> outputFileNames = new ArrayList<String>(
				Arrays.asList("Asclepias_Framework.log"));
		
	}
	// Window management system configuration structure
	public class Window {
		
		// The initial title for the window
		public String title = "Asclepias Framework";
		// The initial dimensions for the window
		public Vec2D dimensions = new Vec2D(800.0d, 600.0d);
		// Whether the window should initially appear in fullscreen mode
		public boolean fullscreen = false;
		// The index of the monitor the window should initially appear on
		public int monitorIndex = 0;
		
	}
	// Sound effects management system configuration structure
	public class SoundEffects {
		
		// The initial volume for all sound effects
		public double volume = 1.0d;
		
	}
	
	// Instance of the logging system configuration structure
	public Log log = new Log();
	// Instance of the window management system configuration structure
	public Window window = new Window();
	// Instance of the sound effects management system configuration structure
	public SoundEffects soundEffects = new SoundEffects();
	// The initial scene for the application
	public AppScene initialScene = null;
	
	/*
	 * Construct a default application configuration structure with an initial
	 * scene
	 * @param Scene initialScene - The initial scene for the application
	 */
	public AppConfig(AppScene initialScene) {
		this.initialScene = initialScene;
	}
	
}