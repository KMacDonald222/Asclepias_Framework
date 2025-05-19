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
		configuration.log.outputFileNames.clear();
		configuration.log.outputFileNames.add("AsclepiasFW 1.log");
		// Initialize the application
		if (!Application.Initialize(configuration)) {
			return;
		}
		// Test the functions of the application
		Application.Log.write(LogSource.Application, LogPriority.Info,
				"Test 1 ", 5, 'x');
		Application.Log.setConsoleEnabled(false);
		Application.Log.write(LogSource.Application, LogPriority.Info,
				"Test 2");
		Application.Log.setConsoleEnabled(true);
		Application.Log.write(LogSource.Application, LogPriority.Info,
				"Test 3");
		Application.Log.addOutputFileName("AsclepiasFW 2.log");
		Application.Log.write(LogSource.Application, LogPriority.Info,
				"Test 4");
		System.out.println("Output file names: "
				+ Application.Log.getOutputFileNames().toString());
		Application.Log.removeOutputFileName("AsclepiasFW 1.log");
		Application.Log.write(LogSource.Application, LogPriority.Info,
				"Test 5");
		// Free the application's memory
		Application.Destroy();
	}
	
}
