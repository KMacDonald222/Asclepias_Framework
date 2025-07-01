/*
 * File:		App.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.06.30
 * Purpose:		Defines the main application class of the Asclepias Framework
 */

package com.github.kmacdonald222.asclepias_framework.app;

import com.github.kmacdonald222.asclepias_framework.logging.LogManager;

// The main application class of the Asclepias Framework
public class App {
	
	// Instance of the logging system manager
	public static LogManager Log = new LogManager();
	
	// Whether the application has been initialized
	private static boolean Initialized = false;
	
	/*
	 * Initialize the application with its configuration structure
	 * @param AppConfig config - The application's configuration structure
	 * @return boolean - Whether the application was successfully initialized
	 */
	public static boolean Initialize(AppConfig config) {
		if (Initialized) {
			return false;
		}
		if (!Log.initialize(config.log.consoleOutputEnabled,
				config.log.outputFileNames)) {
			return false;
		}
		Initialized = true;
		return Initialized;
	}
	/*
	 * Free the application's memory
	 * @return boolean - Whether the application was successfully destroyed
	 */
	public static boolean Destroy() {
		if (!Initialized) {
			return false;
		}
		boolean success = true;
		if (!Log.destroy()) {
			success = false;
		}
		Initialized = false;
		return success;
	}
	
}