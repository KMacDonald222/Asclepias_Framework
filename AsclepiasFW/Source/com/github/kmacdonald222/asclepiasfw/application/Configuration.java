/*
 * File:		Configuration.java
 * Author:		Keegan MacDonald
 * Created:		2025.05.18@19:50
 * Purpose:		Declare configuration data structures for the packages of the
 * 				Asclepias Framework application
 */

package com.github.kmacdonald222.asclepiasfw.application;

import java.util.ArrayList;
import java.util.Arrays;

// Wrapper class for configuration data structures for Asclepias Framework
// applications
public class Configuration {
	
	// Logging system configuration data structure
	public class LogConfiguration {
		// Whether console output should be enabled for the logging system
		public boolean consoleEnabled = true;
		// The set of file names to write logs to
		public ArrayList<String> outputFileNames = new ArrayList<String>(
				Arrays.asList("AsclepiasFW.log"));
	}
	
	// Instance of the logging system configuration structure
	public LogConfiguration log = new LogConfiguration();
	
}
