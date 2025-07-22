/*
 * File:		MusicManager.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.07.22
 * Purpose:		Defines the main class of the music management system for
 * 				Asclepias Framework applications
 */

package com.github.kmacdonald222.asclepiasfw.audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.data.AudioData;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

// The main class of the music management system for Asclepias Framework
// applications
public class MusicManager {
	
	// Whether the music management system has been initialized
	private boolean initialized = false;
	// The file name of the current music track
	private String fileName = "";
	// The Java handle of the current music track
	private Clip music = null;
	// The current position in the current music track
	private long position = 0L;
	// Whether the current music track is paused
	private boolean paused = false;
	private double volume = 0.0d;
	
	/*
	 * Initialize the music management system's memory and set its initial
	 * volume
	 * @param double volume - The initial volume for music
	 * @return boolean - Whether the music management system was initialized
	 * successfully
	 */
	public boolean initialize(double volume) {
		if (initialized) {
			return false;
		}
		stop();
		this.volume = volume;
		App.Log.write(LogSource.Music, LogPriority.Info, "Initialized music ",
				"management system");
		initialized = true;
		return initialized;
	}
	/*
	 * Play a new music track by file name
	 * @param String fileName - The file name of the music track to play
	 * @return boolean - Whether the new music track was played successfully
	 */
	public boolean play(String fileName) {
		App.Log.write(LogSource.Music, LogPriority.Info, "Setting new music ",
				"track \"", fileName, "\"");
		this.fileName = fileName;
		position = 0L;
		return play();
	}
	/*
	 * Play/resume the current music track
	 * @return boolean - Whether a music track was present and played
	 * successfully
	 */
	public boolean play() {
		App.Log.write(LogSource.Music, LogPriority.Info, "Playing music track ",
				"\"", fileName, "\"");
		if (music != null) {
			music.stop();
			music.close();
			music = null;
		}
		AudioData data = AudioLoader.LoadAudioData(fileName);
		if (data == null) {
			App.Log.write(LogSource.Music, LogPriority.Warning, "Failed to ",
					"load audio data for music track \"", fileName, "\"");
			return false;
		}
		try {
			music = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			App.Log.write(LogSource.Music, LogPriority.Warning, "Failed to ",
					"create audio system line for music track \"",
					fileName, "\"");
			return false;
		}
		try {
			music.open(data.format, data.data, 0, data.data.length);
		} catch (LineUnavailableException e) {
			App.Log.write(LogSource.Music, LogPriority.Warning, "Failed to ",
					"open audio system line for music track \"", fileName,
					"\"");
			return false;
		}
		if (!setVolume(volume)) {
			App.Log.write(LogSource.Music, LogPriority.Warning, "Failed to ",
					"set initial volume for music track \"", fileName,
					"\"");
		}
		music.loop(Clip.LOOP_CONTINUOUSLY);
		music.setMicrosecondPosition(position);
		music.start();
		paused = false;
		return true;
	}
	/*
	 * Pause the currently playing music track
	 * @return boolean - Whether a music track was present and paused
	 * successfully
	 */
	public boolean pause() {
		App.Log.write(LogSource.Music, LogPriority.Info, "Pausing current ",
				"music track");
		if (fileName.isEmpty() || music == null) {
			App.Log.write(LogSource.Music, LogPriority.Warning, "No music ",
					"track is present");
			return false;
		}
		if (paused) {
			App.Log.write(LogSource.Music, LogPriority.Warning, "Current ",
					"music track is already paused");
			return false;
		}
		position = music.getMicrosecondPosition();
		music.stop();
		music.close();
		music = null;
		paused = true;
		return true;
	}
	/*
	 * Stop the currently playing music track if present and reset memory
	 */
	public void stop() {
		App.Log.write(LogSource.Music, LogPriority.Info, "Stopping current ",
				"music track");
		fileName = "";
		if (music != null) {
			music.stop();
			music.close();
		} else {
			App.Log.write(LogSource.Music, LogPriority.Warning, "No music ",
					"track is present");
		}
		music = null;
		position = 0L;
		paused = false;
	}
	/*
	 * Stop the current music track and free the music management system's
	 * memory
	 * @return boolean - Whether the music management system was destroyed
	 * successfully
	 */
	public boolean destroy() {
		if (!initialized) {
			return false;
		}
		boolean success = true;
		App.Log.write(LogSource.Music, LogPriority.Info, "Destroying music ",
				"management system");
		stop();
		volume = 0.0d;
		initialized = false;
		return success;
	}
	
	/*
	 * Test whether a music track is currently playing
	 * @return boolean - Whether a music track is currently playing
	 */
	public boolean isPlaying() {
		return !fileName.isEmpty() && !paused;
	}
	/*
	 * Test whether the current music track is currently paused
	 * @return boolean - Whether a music track is currently present and paused
	 */
	public boolean isPaused() {
		return paused;
	}
	/*
	 * Get the current volume for music tracks
	 * @return double - The current volume
	 */
	public double getVolume() {
		return volume;
	}
	/*
	 * Set the new volume for music tracks
	 * @param double volume - The new volume for music tracks (0.0 - 1.0)
	 * @return boolean - Whether the volume was set successfully
	 */
	public boolean setVolume(double volume) {
		if (volume < 0.0d) {
			volume = 0.0d;
		} else if (volume > 1.0d) {
			volume = 1.0d;
		}
		this.volume = volume;
		if (music == null) {
			return false;
		}
		FloatControl gainControl = null;
		try {
			gainControl = (FloatControl)music.getControl(
					FloatControl.Type.MASTER_GAIN);
		} catch (IllegalArgumentException e) {
			App.Log.write(LogSource.SoundEffects, LogPriority.Warning,
					"Failed to set volume for music track \"", fileName,
					"\"");
			return false;
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
		return true;
	}

}
