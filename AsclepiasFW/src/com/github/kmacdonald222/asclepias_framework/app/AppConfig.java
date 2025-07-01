/*
 * File:		AppConfig.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.06.30
 * Purpose:		Defines configuration structures for Asclepias Framework
 * 				applications
 */

package com.github.kmacdonald222.asclepias_framework.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	// Instance of the logging system configuration structure
	public Log log = new Log();
	
}