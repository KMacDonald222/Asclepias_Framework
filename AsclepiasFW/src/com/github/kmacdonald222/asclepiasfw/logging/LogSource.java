/*
 * File:		LogSource.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.06.30
 * Purpose:		Defines source flags for logging system output
 */

package com.github.kmacdonald222.asclepiasfw.logging;

// Class defining source flags for logging system output
public class LogSource {
	
	// Application source log flag
	public static LogSource App = new LogSource("App");
	// Logging system source log flag
	public static LogSource Log = new LogSource("Log");
	// Window management system source log flag
	public static LogSource Window = new LogSource("Window");
	
	// The name of this source flag
	private String name = "";
	
	/*
	 * Initialize this source flag with a name
	 * @param String name - The name for this source flag
	 */
	private LogSource(String name) {
		this.name = name;
	}
	
	/*
	 * Convert this source flag to a string
	 * @return String - The name of this source flag
	 */
	@Override
	public String toString() {
		return name;
	}
	
}