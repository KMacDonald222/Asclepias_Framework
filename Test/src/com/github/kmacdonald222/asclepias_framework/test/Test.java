package com.github.kmacdonald222.asclepias_framework.test;

import com.github.kmacdonald222.asclepias_framework.app.App;
import com.github.kmacdonald222.asclepias_framework.app.AppConfig;
import com.github.kmacdonald222.asclepias_framework.logging.LogPriority;
import com.github.kmacdonald222.asclepias_framework.logging.LogSource;

public class Test {
	
	public static void main(String[] args) {
		AppConfig config = new AppConfig();
		if (!App.Initialize(config)) {
			return;
		}
		App.Log.write(LogSource.App, LogPriority.Info, "Test 1: Output");
		App.Log.write(LogSource.App, LogPriority.Info, "Test 2: Console ",
				"enabledness = ", App.Log.isConsoleOutputEnabled());
		App.Log.setConsoleOutputEnabled(false);
		App.Log.write(LogSource.App, LogPriority.Info, "Test 3: Console ",
				"enabledness = ", App.Log.isConsoleOutputEnabled());
		App.Log.setConsoleOutputEnabled(true);
		App.Log.write(LogSource.App, LogPriority.Info, "Test 4: Console ",
				"enabledness = ", App.Log.isConsoleOutputEnabled());
		App.Log.write(LogSource.App, LogPriority.Info, "Test 5: Output file ",
				"names = ", App.Log.getOutputFileNames());
		App.Log.addOutputFileName("Asclepias_Framework_1.log");
		App.Log.write(LogSource.App, LogPriority.Info, "Test 6: Output file ",
				"names = ", App.Log.getOutputFileNames());
		App.Log.removeOutputFileName("Asclepias_Framework.log");
		App.Log.write(LogSource.App, LogPriority.Info, "Test 7: Output file ",
				"names = ", App.Log.getOutputFileNames());
		if (!App.Destroy()) {
			return;
		}
	}
	
}