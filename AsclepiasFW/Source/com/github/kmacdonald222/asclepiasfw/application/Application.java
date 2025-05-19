/*
 * File:		Application.java
 * Author:		Keegan MacDonald
 * Created:		2025.05.18@20:04
 * Purpose:		Implement the main class of the Asclepias Framework
 */

package com.github.kmacdonald222.asclepiasfw.application;

import com.github.kmacdonald222.asclepiasfw.logging.LogManager;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

// The main class of the Asclepias Framework - manages scenes and wraps all
// other Framework packages
public class Application {
	
	// Instance of the logging system for the application
	public static LogManager Log = new LogManager();
	
	/*
	 * Initialize the application and all the packages of the Asclepias
	 * Framework
	 * @param Configuration configuration - The configuration data structure
	 * for the application
	 * @return boolean - Whether the application was initialized successfully
	 */
	public static boolean Initialize(Configuration configuration) {
		if (!Log.initialize(configuration.log.consoleEnabled,
				configuration.log.outputFileNames)) {
			return false;
		}
		Log.write(LogSource.Application, LogPriority.Info,
				"Initialized logging system");
		return true;
	}
	/*
	 * Free the application's memory and that of all the packages of the
	 * Asclepias Framework
	 * @return boolean - Whether the application was destroyed successfully
	 */
	public static boolean Destroy() {
		boolean success = true;
		success = Log.destroy();
		return success;
	}
	
}
