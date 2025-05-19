/*
 * File:		LogManager.java
 * Author:		Keegan MacDonald
 * Created:		2025.05.18@20:09
 * Purpose:		Implement the main class of the Asclepias Framework's logging
 * 				system
 */

package com.github.kmacdonald222.asclepiasfw.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

// The main class of the Asclepias Framework's logging system
public class LogManager {
	
	// Whether logging output to the console is enabled
	private boolean consoleEnabled = false;
	// The set of files to write logs to associated with their file names
	private HashMap<String, FileWriter> outputFileWriters
			= new HashMap<String, FileWriter>();
	
	/*
	 * Initialize the logging system - set console output enabledness and add
	 * all output files
	 * @param boolean consoleEnabled - Whether console output should be enabled
	 * @param ArrayList<String> outputFileNames - The set of file names of
	 * output files
	 * @return boolean - Whether all output files could be opened
	 */
	public boolean initialize(boolean consoleEnabled,
		ArrayList<String> outputFileNames) {
		// Set console output enabledness
		setConsoleEnabled(consoleEnabled);
		// Add output files
		for (int i = 0; i < outputFileNames.size(); i++) {
			if (!addOutputFileName(outputFileNames.get(i))) {
				return false;
			}
		}
		return true;
	}
	/*
	 * Write a log to the console and/or the output files
	 * @param LogSource source - The source package of this log
	 * @param LogPriority priority - The priority level of this log
	 * @param Object... data - The set of objects to write to the log
	 */
	public void write(LogSource source, LogPriority priority, Object... data) {
		// Concatenate the log message
		String output = getTimestamp() + ": [" + source.toString() + "] ["
				+ priority.toString() + "] ";
		for (int i = 0; i < data.length; i++) {
			output += data[i].toString();
		}
		output += "\n";
		// Write the log to the console if enabled
		if (consoleEnabled) {
			System.out.print(output);
		}
		// Write the log to the output files
		for (HashMap.Entry<String, FileWriter> entry
				: outputFileWriters.entrySet()) {
			try {
				entry.getValue().write(output);
			} catch (IOException e) {}
		}
	}
	/*
	 * Close this log manager's output files and free its memory
	 * @return boolean - Whether this log manager was destroyed successfully
	 */
	public boolean destroy() {
		boolean success = true;
		// Reset console enabledness
		consoleEnabled = false;
		// Close and clear output files
		for (HashMap.Entry<String, FileWriter> entry
				: outputFileWriters.entrySet()) {
			try {
				entry.getValue().close();
			} catch (IOException e) {
				success = false;
			}
		}
		outputFileWriters.clear();
		return success;
	}
	
	/*
	 * Get the current date and time as a string
	 * @return String - The current date and time
	 */
	private String getTimestamp() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter
				= DateTimeFormatter.ofPattern("yyyy.MM.dd@HH:mm:ss");
		return formatter.format(now);
	}
	
	/*
	 * Test whether console log output is currently enabled
	 * @return boolean - Whether console output is enabled
	 */
	public boolean isConsoleEnabled() {
		return consoleEnabled;
	}
	/*
	 * Set whether console log output should be enabled
	 * @param boolean consoleEnabled - Whether console output should be enabled
	 */
	public void setConsoleEnabled(boolean consoleEnabled) {
		this.consoleEnabled = consoleEnabled;
	}
	/*
	 * Get the current set of output files' names
	 * @return ArrayList<String> - The set of output file names
	 */
	public ArrayList<String> getOutputFileNames() {
		ArrayList<String> outputFileNames = new ArrayList<String>();
		outputFileNames.addAll(outputFileWriters.keySet());
		return outputFileNames;
	}
	/*
	 * Add an output file to write logs to
	 * @param String outputFileName - The name of the new output file
	 * @return boolean - Whether the output file was added successfully
	 */
	public boolean addOutputFileName(String outputFileName) {
		// Ensure the output file is not already present
		if (outputFileWriters.keySet().contains(outputFileName)) {
			return false;
		}
		// Open and test the new output file
		File outputFile = new File(outputFileName);
		try {
			outputFile.createNewFile();
		} catch (IOException e) {
			return false;
		}
		if (!outputFile.canWrite()) {
			return false;
		}
		// Add the new output file with its name
		try {
			outputFileWriters.put(outputFileName, new FileWriter(outputFile));
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	/*
	 * Close and remove an output file
	 * @param String outputFileName - The name of the output file
	 * @return boolean - Whether the output file was removed successfully
	 */
	public boolean removeOutputFileName(String outputFileName) {
		// Ensure the output file is present
		if (!outputFileWriters.keySet().contains(outputFileName)) {
			return false;
		}
		// Attempt to close the output file
		try {
			outputFileWriters.get(outputFileName).close();
		} catch (IOException e) {
			return false;
		}
		// Remove the output file by its name
		outputFileWriters.remove(outputFileName);
		return true;
	}
	
}
