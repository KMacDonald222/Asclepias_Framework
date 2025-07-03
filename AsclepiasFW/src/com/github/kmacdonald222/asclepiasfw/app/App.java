/*
 * File:		App.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.06.30
 * Purpose:		Defines the main application class of the Asclepias Framework
 */

package com.github.kmacdonald222.asclepiasfw.app;

import com.github.kmacdonald222.asclepiasfw.graphics.WindowManager;
import com.github.kmacdonald222.asclepiasfw.logging.LogManager;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;

// The main application class of the Asclepias Framework
public class App {
	
	// Instance of the logging system manager
	public static LogManager Log = new LogManager();
	// Instance of the window management system
	public static WindowManager Window = new WindowManager();
	
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
		Log.write(LogSource.App, LogPriority.Info, "Initialized logging ",
				"system");
		if (!Window.initialize(config.window.title, config.window.dimensions,
				config.window.fullscreen, config.window.monitorIndex)) {
			Log.write(LogSource.App, LogPriority.Error, "Failed initialize ",
					"window management system");
			return false;
		}
		Log.write(LogSource.App, LogPriority.Info, "Initialized window ",
				"management system");
		Initialized = true;
		Log.write(LogSource.App, LogPriority.Info, "Initialized Asclepias ",
				"Framework application");
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
		Log.write(LogSource.App, LogPriority.Info, "Destroying Asclepias ",
				"Framework application");
		boolean success = true;
		Log.write(LogSource.App, LogPriority.Info, "Destroying window ",
				"management system");
		if (!Window.destroy()) {
			Log.write(LogSource.App, LogPriority.Warning, "Failed to destroy ",
					"window management system");
			success = false;
		}
		Log.write(LogSource.App, LogPriority.Info, "Destroying logging system");
		if (!Log.destroy()) {
			success = false;
		}
		Initialized = false;
		return success;
	}
	
}