/*
 * File:		LogPriority.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.06.30
 * Purpose:		Defines priority flags for logging system output
 */

package com.github.kmacdonald222.asclepiasfw.logging;

// Class defining priority flags for logging system output
public class LogPriority {
	
	// Information (low) priority log flag
	public static LogPriority Info = new LogPriority("Info");
	// Warning (medium) priority log flag
	public static LogPriority Warning = new LogPriority("Warning");
	// Error (high) priority log flag
	public static LogPriority Error = new LogPriority("Error");
	
	// The name of this priority flag
	private String name = "";
	
	/*
	 * Initialize this priority flag with a name
	 * @param String name - The name for this priority flag
	 */
	private LogPriority(String name) {
		this.name = name;
	}
	
	/*
	 * Convert this priority flag to a string
	 * @return String - The name of this priority flag
	 */
	public String toString() {
		return name;
	}
	
}