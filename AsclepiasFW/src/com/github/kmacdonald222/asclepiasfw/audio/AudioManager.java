/*
 * File:		AudioManager.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.07.22
 * Purpose:		Defines a wrapper class for general audio management systems
 */

package com.github.kmacdonald222.asclepiasfw.audio;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

// Wrapper class for general audio management systems
public class AudioManager {

	// Whether the audio management system has been initialized
	private boolean initialized = false;
	// Instance of the sound effect management system
	public SoundEffectsManager soundEffects = null;
	// Instance of the music management system
	public MusicManager music = null;
	
	/*
	 * Initialize all audio management systems
	 * @return boolean - Whether the audio management systems were initialized
	 * successfully
	 */
	public boolean initialize(double soundEffectsVolume, double musicVolume) {
		if (initialized) {
			return false;
		}
		soundEffects = new SoundEffectsManager();
		if (!soundEffects.initialize(soundEffectsVolume)) {
			App.Log.write(LogSource.Audio, LogPriority.Error, "Failed to ",
					"initialize sound effect management system");
			return false;
		}
		App.Log.write(LogSource.Audio, LogPriority.Info, "Initialized sound ",
				"effect management system");
		music = new MusicManager();
		if (!music.initialize(musicVolume)) {
			App.Log.write(LogSource.Audio, LogPriority.Error, "Failed to ",
					"initialize music management system");
			return false;
		}
		App.Log.write(LogSource.Audio, LogPriority.Info, "Initialized music ",
				"management system");
		initialized = true;
		return initialized;
	}
	/*
	 * Update the logic of all audio management systems
	 */
	public void update() {
		soundEffects.update();
	}
	/*
	 * Free the memory of all audio management systems
	 * @return boolean - Whether all audio management systems were destroyed
	 * successfully
	 */
	public boolean destroy() {
		if (!initialized) {
			return false;
		}
		boolean success = true;
		App.Log.write(LogSource.Audio, LogPriority.Info, "Destroying sound ",
				"effect management system");
		if (!soundEffects.destroy()) {
			App.Log.write(LogSource.Audio, LogPriority.Warning, "Failed to ",
					"destroy sound effect management system");
			success = false;
		}
		soundEffects = null;
		App.Log.write(LogSource.Audio, LogPriority.Info, "Destroying music ",
				"management system");
		if (!music.destroy()) {
			App.Log.write(LogSource.Audio, LogPriority.Warning, "Failed to ",
					"destroy music management system");
			success = false;
		}
		music = null;
		initialized = false;
		return success;
	}
	
}
