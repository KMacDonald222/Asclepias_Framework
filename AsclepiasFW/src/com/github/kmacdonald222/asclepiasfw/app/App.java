/*
 * File:		App.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.06.30
 * Purpose:		Defines the main application class of the Asclepias Framework
 */

package com.github.kmacdonald222.asclepiasfw.app;

import java.util.ArrayList;
import java.util.List;

import com.github.kmacdonald222.asclepiasfw.audio.AudioManager;
import com.github.kmacdonald222.asclepiasfw.graphics.WindowManager;
import com.github.kmacdonald222.asclepiasfw.input.InputManager;
import com.github.kmacdonald222.asclepiasfw.logging.LogManager;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;

// The main application class of the Asclepias Framework
public class App {
	
	// Instance of the logging system manager
	public static LogManager Log = null;
	// Instance of the window management system
	public static WindowManager Window = null;
	// Instance of the user input management system
	public static InputManager Input = null;
	// Instance of the audio management systems
	public static AudioManager Audio = null;
	// The current scene of the application
	private static AppScene CurrentScene = null;
	// List of all previous scenes in the application
	private static List<AppScene> Scenes = null;
	
	// Whether the application has been initialized
	private static boolean Initialized = false;
	
	/*
	 * Initialize the application with its configuration structure
	 * @param AppConfig config - The application's configuration structure
	 * @return boolean - Whether the application was successfully initialized
	 */
	public static boolean Initialize(AppConfig config) {
		if (Initialized) {
			return false;
		}
		Log = new LogManager();
		if (!Log.initialize(config.log.consoleOutputEnabled,
				config.log.outputFileNames)) {
			return false;
		}
		Log.write(LogSource.App, LogPriority.Info, "Initialized logging ",
				"system");
		Window = new WindowManager();
		if (!Window.initialize(config.window.title, config.window.dimensions,
				config.window.fullscreen, config.window.monitorIndex)) {
			Log.write(LogSource.App, LogPriority.Error, "Failed to initialize ",
					"window management system");
			return false;
		}
		Log.write(LogSource.App, LogPriority.Info, "Initialized window ",
				"management system");
		Input = new InputManager();
		if (!Input.initialize()) {
			Log.write(LogSource.App, LogPriority.Error, "Failed to initialize ",
					"user input management systems");
			return false;
		}
		Log.write(LogSource.App, LogPriority.Info, "Initialized user input ",
				"management systems");
		Audio = new AudioManager();
		if (!Audio.initialize(config.audio.soundEffects.volume,
				config.audio.music.volume)) {
			Log.write(LogSource.App, LogPriority.Error, "Failed to initialize ",
					"audio management systems");
			return false;
		}
		Log.write(LogSource.App, LogPriority.Info, "Initialized audio ",
				"management systems");
		Log.write(LogSource.App, LogPriority.Info, "Setting initial scene");
		Scenes = new ArrayList<AppScene>();
		if (!SetCurrentScene(config.initialScene)) {
			Log.write(LogSource.App, LogPriority.Error, "Failed to set ",
					"initial scene");
			return false;
		}
		Log.write(LogSource.App, LogPriority.Info, "Initialized Asclepias ",
				"Framework application");
		Initialized = true;
		return Initialized;
	}
	/*
	 * Run the application's main loop
	 */
	public static void Run() {
		Log.write(LogSource.App, LogPriority.Info, "Running main loop");
		while (!Window.isWindowClosing()) {
			if (CurrentScene == null) {
				Log.write(LogSource.App, LogPriority.Error,	"No current scene ",
						"available");
				break;
			}
			if (!CurrentScene.processInput()) {
				Log.write(LogSource.App, LogPriority.Info, "Current scene ",
						"requested to stop main loop");
				break;
			}
			Input.update();
			Audio.update();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Log.write(LogSource.App, LogPriority.Info, "Finished main loop");
	}
	/*
	 * Free the application's memory
	 * @return boolean - Whether the application was successfully destroyed
	 */
	public static boolean Destroy() {
		if (!Initialized) {
			return false;
		}
		Log.write(LogSource.App, LogPriority.Info, "Destroying Asclepias ",
				"Framework application");
		boolean success = true;
		Log.write(LogSource.App, LogPriority.Info, "Clearing current scene");
		SetCurrentScene(null);
		Log.write(LogSource.App, LogPriority.Info, "Destroying all previous ",
				"scenes");
		for (int i = 0; i < Scenes.size(); i++) {
			Scenes.get(i).destroyScene();
		}
		Scenes.clear();
		Scenes = null;
		Log.write(LogSource.App, LogPriority.Info, "Destroying audio ",
				"management systems");
		if (!Audio.destroy()) {
			Log.write(LogSource.App, LogPriority.Warning, "Failed to destroy ",
					"audio management systems");
			success = false;
		}
		Audio = null;
		Log.write(LogSource.App, LogPriority.Info, "Destroying user input ",
				"management system");
		if (!Input.destroy()) {
			Log.write(LogSource.App, LogPriority.Warning, "Failed to destroy ",
					"user input management system");
			success = false;
		}
		Input = null;
		Log.write(LogSource.App, LogPriority.Info, "Destroying window ",
				"management system");
		if (!Window.destroy()) {
			Log.write(LogSource.App, LogPriority.Warning, "Failed to destroy ",
					"window management system");
			success = false;
		}
		Window = null;
		Log.write(LogSource.App, LogPriority.Info, "Destroying logging system");
		if (!Log.destroy()) {
			success = false;
		}
		Log = null;
		Initialized = false;
		return success;
	}
	
	/*
	 * Get the current scene in the application
	 * @return AppScene - The application's current scene
	 */
	public static AppScene GetCurrentScene() {
		return CurrentScene;
	}
	/*
	 * Set a new current scene for the application
	 * @param AppScene nextScene - The new scene for the application
	 * @return boolean - Whether the new scene was set successfully
	 */
	public static boolean SetCurrentScene(AppScene nextScene) {
		Log.write(LogSource.App, LogPriority.Info, "Setting new scene");
		if (CurrentScene != null) {
			Log.write(LogSource.App, LogPriority.Info, "Leaving current scene");
			CurrentScene.leave(nextScene);
			if (!Input.keyboard.removeListener(CurrentScene)) {
				Log.write(LogSource.App, LogPriority.Warning, "Failed to ",
						"remove current scene from keyboard input listeners");
			}
			if (!Input.mouse.removeListener(CurrentScene)) {
				Log.write(LogSource.App, LogPriority.Warning, "Failed to ",
						"remove current scene from mouse input listeners");
			}
		}
		if (nextScene == null) {
			Log.write(LogSource.App, LogPriority.Warning, "No new scene ",
					"passed to app");
			CurrentScene = null;
			return true;
		}
		if (!nextScene.isInitialized()) {
			Log.write(LogSource.App, LogPriority.Info, "Initializing new ",
					"scene");
			if (!nextScene.initializeScene()) {
				Log.write(LogSource.App, LogPriority.Error, "Failed to ",
						"initialize new scene");
				CurrentScene = null;
				return false;
			}
		}
		Log.write(LogSource.App, LogPriority.Info, "Entering new scene");
		if (!Input.keyboard.addListener(nextScene)) {
			Log.write(LogSource.App, LogPriority.Warning, "Failed to add new ",
					"scene to keyboard input listeners");
		}
		if (!Input.mouse.addListener(nextScene)) {
			Log.write(LogSource.App, LogPriority.Warning, "Failed to add new ",
					"scene to mouse input listeners");
		}
		nextScene.enter(CurrentScene);
		CurrentScene = nextScene;
		Scenes.add(nextScene);
		return true;
	}
	
}