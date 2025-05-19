/*
 * File:		LogSource.java
 * Author:		Keegan MacDonald
 * Created:		2025.05.18@19:44
 * Purpose:		Declare various log message sources from packages of the
 * 				Asclepias Framework
 */

package com.github.kmacdonald222.asclepiasfw.logging;

// Structure containing the name of log sources
public class LogSource {
	
	// Log source for application messages
	public static LogSource Application = new LogSource("Application");
	// Log source for logging system messages
	public static LogSource Log = new LogSource("Log");
	
	// The name of this log source
	private String name;
	
	/*
	 * Initialize this log source with a name
	 * @param String name - The name for this log source
	 */
	protected LogSource(String name) {
		this.name = name;
	}
	/*
	 * Convert this log source to a string
	 * @return String - The name of this log source
	 */
	@Override
	public String toString() {
		return name;
	}
	
}
