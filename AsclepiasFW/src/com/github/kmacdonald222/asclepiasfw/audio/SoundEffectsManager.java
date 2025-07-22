/*
 * File:		SoundEffectsManager.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.07.21
 * Purpose:		Defines the main class of the sound effects management system
 * 				for Asclepias Framework applications
 */

package com.github.kmacdonald222.asclepiasfw.audio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.data.AudioData;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

// The main class of the sound effects management system for Asclepias Framework
// applications
public class SoundEffectsManager {

	// Whether the sound effects management system has been initialized
	private boolean initialized = false;
	// The set of audio files loaded mapped to sets of clips playing them
	private Map<String, List<Clip>> effects = null;
	// The current volume of all sound effects
	private double volume = 0.0d;
	
	/*
	 * Initialize the sound effect management system's memory and set its
	 * initial volume
	 * @param double volume - The initial volume for all sound effects
	 * @return boolean - Whether the sound effect management system was
	 * initialized successfully
	 */
	public boolean initialize(double volume) {
		if (initialized) {
			return false;
		}
		effects = new HashMap<String, List<Clip>>();
		if (!setVolume(volume)) {
			App.Log.write(LogSource.SoundEffects, LogPriority.Error, "Failed ",
					"to set initial volume");
			return false;
		}
		App.Log.write(LogSource.SoundEffects, LogPriority.Info, "Initialized ",
				"sound effects management system");
		initialized = true;
		return initialized;
	}
	/*
	 * Update the sound effect management system's memory to clear finished
	 * clips
	 */
	public void update() {
		for (Map.Entry<String, List<Clip>> effect : effects.entrySet()) {
			List<Clip> finishedClips = new ArrayList<Clip>();
			for (Clip clip : effect.getValue()) {
				if (!clip.isActive()) {
					finishedClips.add(clip);
				}
			}
			for (Clip clip : finishedClips) {
				effect.getValue().remove(clip);
			}
		}
	}
	/*
	 * Play a sound effect by its file name
	 * @param String fileName - The file name of the sound effect to play
	 * @return boolean - Whether the sound effect was played
	 */
	public boolean play(String fileName) {
		AudioData data = AudioLoader.LoadAudioData(fileName);
		if (data == null) {
			App.Log.write(LogSource.SoundEffects, LogPriority.Warning,
					"Failed to load audio data for sound effect \"", fileName,
					"\"");
			return false;
		}
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			App.Log.write(LogSource.SoundEffects, LogPriority.Warning,
					"Failed to create audio system line for sound effect \"",
					fileName, "\"");
			return false;
		}
		try {
			clip.open(data.format, data.data, 0, data.data.length);
		} catch (LineUnavailableException e) {
			App.Log.write(LogSource.SoundEffects, LogPriority.Warning,
					"Failed to open audio system line for sound effect \"",
					fileName, "\"");
			return false;
		}
		if (!effects.containsKey(fileName)) {
			effects.put(fileName, new ArrayList<Clip>());
		}
		effects.get(fileName).add(clip);
		if (!setVolume(volume)) {
			App.Log.write(LogSource.SoundEffects, LogPriority.Warning,
					"Failed to set initial volume for clip of sound effect \"",
					fileName, "\"");
		}
		clip.start();
		return true;
	}
	/*
	 * Free the sound effect management system's memory
	 * @return boolean - Whether the sound effect management system was
	 * destroyed successfully
	 */
	public boolean destroy() {
		if (!initialized) {
			return false;
		}
		boolean success = true;
		App.Log.write(LogSource.SoundEffects, LogPriority.Info, "Destroying ",
				"sound effects management system");
		effects.clear();
		effects = null;
		volume = 0.0d;
		initialized = false;
		return success;
	}
	
	/*
	 * Get the current volume of all sound effects
	 * @return double - The current volume
	 */
	public double getVolume() {
		return volume;
	}
	/*
	 * Set the volume for all sound effects
	 * @param double volume - The new volume (0.0 - 1.0)
	 * @return boolean - Whether the volume was set successfully for all sound
	 * effects currently playing
	 */
	public boolean setVolume(double volume) {
		if (volume < 0.0d) {
			volume = 0.0d;
		} else if (volume > 1.0d) {
			volume = 1.0d;
		}
		this.volume = volume;
		boolean success = true;
		for (Map.Entry<String, List<Clip>> effect : effects.entrySet()) {
			for (Clip clip : effect.getValue()) {
				FloatControl gainControl = null;
				try {
					gainControl = (FloatControl)clip.getControl(
							FloatControl.Type.MASTER_GAIN);
				} catch (IllegalArgumentException e) {
					App.Log.write(LogSource.SoundEffects, LogPriority.Warning,
							"Failed to set volume for clip of sound effect \"",
							effect.getKey(), "\"");
					success = false;
				}
				if (gainControl != null) {
					float gain = (float)(20.0d * Math.log10(volume));
					if (gain < gainControl.getMinimum()) {
						gain = gainControl.getMinimum();
					} else if (gain > gainControl.getMaximum()) {
						gain = gainControl.getMaximum();
					}
					gainControl.setValue(gain);
				}
			}
		}
		return success;
	}
	
}
