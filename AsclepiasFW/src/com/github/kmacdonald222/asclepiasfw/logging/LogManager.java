/*
 * File:		LogManager.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.06.30
 * Purpose:		Defines the main class of the logging system for Asclepias
 * 				Framework applications
 */

package com.github.kmacdonald222.asclepiasfw.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// The main class of the logging system for Asclepias Framework applications
public class LogManager {
	
	// Whether the logging system has been initialized
	private boolean initialized = false;
	// Whether the logging system will print output to the console
	private boolean consoleOutputEnabled = false;
	// The set of files the logging system will print output to
	private Map<String, FileWriter> outputFileWriters = null;
	
	/*
	 * Initialize the logging system
	 * @param boolean consoleOutputEnabled - Whether the logging system should
	 * print output to the console
	 * @param List<String> outputFileNames - The set of files the logging system
	 * should print output to
	 * @return boolean - Whether the logging system was initialized successfully
	 */
	public boolean initialize(boolean consoleOutputEnabled,
			List<String> outputFileNames) {
		if (initialized) {
			return false;
		}
		setConsoleOutputEnabled(consoleOutputEnabled);
		outputFileWriters = new HashMap<String, FileWriter>();
		for (int i = 0; i < outputFileNames.size(); i++) {
			if (!addOutputFileName(outputFileNames.get(i))) {
				return false;
			}
		}
		initialized = true;
		return initialized;
	}
	/*
	 * Write a set of objects to the log with source and priority flags
	 * @param LogSource source - The source flag for this log
	 * @param LogPriority priority - The priority flag for this log
	 * @param Object... data - The set of objects to write
	 */
	public void write(LogSource source, LogPriority priority, Object... data) {
		String output = getTimestamp() + " - [" + source.toString() + "] ["
				+ priority.toString() + "] ";
		for (int i = 0; i < data.length; i++) {
			output += data[i].toString();
		}
		output += "\n";
		if (consoleOutputEnabled) {
			System.out.print(output);
		}
		List<String> outputFileNames = getOutputFileNames();
		List<String> badOutputFileNames = new ArrayList<String>();
		for (int i = 0; i < outputFileWriters.size(); i++) {
			try {
				outputFileWriters.get(outputFileNames.get(i)).write(output);
			} catch (IOException e) {
				badOutputFileNames.add(outputFileNames.get(i));
			}
		}
		for (int i = 0; i < badOutputFileNames.size(); i++) {
			removeOutputFileName(badOutputFileNames.get(i));
		}
	}
	/*
	 * Free the logging system's memory and close all output files
	 * @return boolean - Whether the logging system was destroyed successfully
	 */
	public boolean destroy() {
		if (!initialized) {
			return false;
		}
		boolean success = true;
		consoleOutputEnabled = false;
		for (Map.Entry<String, FileWriter> entry
				: outputFileWriters.entrySet()) {
			try {
				entry.getValue().close();
			} catch (IOException e) {
				success = false;
			}
			entry.setValue(null);
		}
		outputFileWriters.clear();
		outputFileWriters = null;
		initialized = false;
		return success;
	}
	/*
	 * Get the current date and time as a string
	 * @return String - The current date and time in format
	 * yyyy.MM.dd@HH:mm:ss:SSS
	 */
	private String getTimestamp() {
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter
				= DateTimeFormatter.ofPattern("yyyy.MM.dd@HH:mm:ss:SSS");
		return dateTime.format(formatter);
	}
	
	/*
	 * Test whether the logging system is set to write output to the console
	 * @return boolean - Whether the logging system writes output to the console
	 */
	public boolean isConsoleOutputEnabled() {
		return consoleOutputEnabled;
	}
	/*
	 * Set whether the logging system writes output to the console
	 * @param boolean consoleOutputEnabled - Whether the logging system should
	 * write output to the console
	 */
	public void setConsoleOutputEnabled(boolean consoleOutputEnabled) {
		this.consoleOutputEnabled = consoleOutputEnabled;
	}
	/*
	 * Get the set of file names the logging system is set to write output to
	 * @return List<String> - The logging system's set of output file names
	 */
	public List<String> getOutputFileNames() {
		return new ArrayList<String>(outputFileWriters.keySet());
	}
	/*
	 * Add a file name for the logging system to write output to
	 * @param String outputFileName - The new file name the logging system
	 * should write output to
	 * @return boolean - Whether the new file name was added successfully
	 */
	public boolean addOutputFileName(String outputFileName) {
		if (getOutputFileNames().contains(outputFileName)) {
			return false;
		}
		File outputFile = new File(outputFileName);
		if (!outputFile.exists()) {
			try {
				outputFile.createNewFile();
			} catch (IOException e) {
				return false;
			}
		}
		if (!outputFile.canWrite()) {
			return false;
		}
		try {
			outputFileWriters.put(outputFileName, new FileWriter(outputFile));
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	/*
	 * Remove a file name from the set the logging system writes output to
	 * @param String outputFileName - The file name to remove from the logging
	 * system
	 * @return boolean - Whether the file name was successfully removed
	 */
	public boolean removeOutputFileName(String outputFileName) {
		if (!getOutputFileNames().contains(outputFileName)) {
			return false;
		}
		try {
			outputFileWriters.get(outputFileName).close();
		} catch (IOException e) {
			return false;
		}
		outputFileWriters.remove(outputFileName);
		return true;
	}
	
}