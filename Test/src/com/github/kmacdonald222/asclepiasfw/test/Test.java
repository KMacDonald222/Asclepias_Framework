package com.github.kmacdonald222.asclepiasfw.test;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.app.AppConfig;
import com.github.kmacdonald222.asclepiasfw.data.Vec2D;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

public class Test {
	
	public static void main(String[] args) {
		AppConfig config = new AppConfig();
		if (!App.Initialize(config)) {
			return;
		}
		pause();
		App.Log.write(LogSource.App, LogPriority.Info, "Window title: \"",
				App.Window.getTitle(), "\"");
		pause();
		App.Window.setTitle("Test");
		pause();
		App.Log.write(LogSource.App, LogPriority.Info, "Window dimensions: ",
				App.Window.getDimensions());
		pause();
		App.Window.setDimensions(new Vec2D(500.0d, 400.0d));
		pause();
		App.Log.write(LogSource.App, LogPriority.Info, "Window fullscreen ",
				"mode: ", App.Window.isFullscreen());
		pause();
		App.Window.setFullscreen(true);
		pause();
		App.Log.write(LogSource.App, LogPriority.Info, "Window fullscreen ",
				"mode: ", App.Window.isFullscreen());
		pause();
		App.Window.setFullscreen(false);
		pause();
		App.Log.write(LogSource.App, LogPriority.Info, "Window monitor count: ",
				App.Window.getMonitorCount());
		pause();
		App.Log.write(LogSource.App, LogPriority.Info, "Window monitor ",
				"name: \"", App.Window.getMonitorName(), "\"");
		pause();
		App.Log.write(LogSource.App, LogPriority.Info, "Monitor 1 name: \"",
				App.Window.getMonitorName(1), "\"");
		pause();
		App.Log.write(LogSource.App, LogPriority.Info, "Window monitor ",
				"position: ", App.Window.getMonitorPosition());
		pause();
		App.Log.write(LogSource.App, LogPriority.Info, "Monitor 1 position: ",
				App.Window.getMonitorPosition(1));
		pause();
		App.Log.write(LogSource.App, LogPriority.Info, "Window monitor ",
				"dimensions: ", App.Window.getMonitorDimensions());
		pause();
		App.Log.write(LogSource.App, LogPriority.Info, "Monitor 1 dimensions: ",
				App.Window.getMonitorDimensions(1));
		pause();
		App.Log.write(LogSource.App, LogPriority.Info, "Window monitor index: ",
				App.Window.getMonitorIndex());
		pause();
		if (!App.Window.setMonitorIndex(1)) {
			App.Log.write(LogSource.App, LogPriority.Warning, "Invalid ",
					"monitor");
		}
		pause();
		App.Window.setFullscreen(true);
		pause();
		App.Window.setDimensions(new Vec2D(300.0d, 200.0d));
		pause();
		App.Window.setFullscreen(false);
		pause();
		if (!App.Destroy()) {
			return;
		}
	}
	
	private static void pause() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}