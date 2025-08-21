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
	// Current scene source log flag
	public static LogSource Scene = new LogSource("Scene");
	// Logging system source log flag
	public static LogSource Log = new LogSource("Log");
	// Window management system source log flag
	public static LogSource Window = new LogSource("Window");
	// Keyboard input management system source log flag
	public static LogSource Keyboard = new LogSource("Keyboard");
	// Mouse input management system source log flag
	public static LogSource Mouse = new LogSource("Mouse");
	// General input management system source log flag
	public static LogSource Input = new LogSource("Input");
	// Sound effect management system source log flag
	public static LogSource SoundEffects = new LogSource("Sound Effects");
	// Music management system source log flag
	public static LogSource Music = new LogSource("Music");
	// General audio management system source log flag
	public static LogSource Audio = new LogSource("Audio");
	// Network client source log flag
	public static LogSource NetClient = new LogSource("Network Client");
	// Network connection source log flag
	public static LogSource NetConnection = new LogSource("Network Connection");
	// Network server source log flag
	public static LogSource NetServer = new LogSource("Network Server");
	// Network server listener source log flag
	public static LogSource NetListener = new LogSource("Network Listener");
	
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