/*
 * File:		LogPriority.java
 * Author:		Keegan MacDonald
 * Created:		2025.05.18@19:48
 * Purpose:		Declare various log message priority levels
 */

package com.github.kmacdonald222.asclepiasfw.logging;

// Structure containing the priority level of log sources
public class LogPriority {
	
	// Low priority informational message
	public static LogPriority Info = new LogPriority("Info");
	// Medium priority warning message
	public static LogPriority Warning = new LogPriority("Warning");
	// High priority error message
	public static LogPriority Error = new LogPriority("Error");
	
	// The name of this log priority level
	private String name;
	
	/*
	 * Initialize this log priority level with a name
	 * @param String name - The name for this log priority level
	 */
	protected LogPriority(String name) {
		this.name = name;
	}
	/*
	 * Convert this log priority level to a string
	 * @return String - The name of this log priority level
	 */
	@Override
	public String toString() {
		return name;
	}
	
}
