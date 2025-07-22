/*
 * File:		AudioLoader.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.07.21
 * Purpose:		Defines a static loader/cache utility for audio data
 */

package com.github.kmacdonald222.asclepiasfw.audio;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.data.AudioData;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

// Static loader/cache utility class for audio data
public class AudioLoader {

	// The audio data in memory mapped to files loaded from
	private static Map<String, AudioData> Audio = null;
	
	/*
	 * Retrieve an audio file's data and format if already in memory or load it
	 * into memory from the disk
	 * @param String fileName - The file to load data from
	 * @return AudioData - The data and contained in the file and its format or
	 * null if an error occurred
	 */
	public static AudioData LoadAudioData(String fileName) {
		if (Audio == null) {
			Audio = new HashMap<String, AudioData>();
		}
		if (!Audio.containsKey(fileName)) {
			App.Log.write(LogSource.Audio, LogPriority.Info, "Loading audio ",
					"data from \"", fileName, "\"");
			File audioFile = new File(fileName);
			if (!audioFile.exists()) {
				App.Log.write(LogSource.Audio, LogPriority.Warning, "Audio ",
						"file \"", fileName, "\" does not exist");
				return null;
			}
			if (!audioFile.canRead()) {
				App.Log.write(LogSource.Audio, LogPriority.Warning, "Audio ",
						"file \"", fileName, "\" is not readable");
				return null;
			}
			AudioInputStream audioIn = null;
			try {
				audioIn = AudioSystem.getAudioInputStream(audioFile);
			} catch (UnsupportedAudioFileException e) {
				App.Log.write(LogSource.Audio, LogPriority.Warning, "Audio ",
						"file \"", fileName, "\" contains invalid format");
				return null;
			} catch (IOException e) {
				App.Log.write(LogSource.Audio, LogPriority.Warning, "Failed ",
						"to open audio file \"", fileName, "\"");
				return null;
			}
			AudioFormat format = audioIn.getFormat();
			byte[] data = null;
			try {
				data = audioIn.readAllBytes();
			} catch (IOException e) {
				App.Log.write(LogSource.Audio, LogPriority.Warning, "Failed ",
						"to read audio file \"", fileName, "\"");
				return null;
			}
			try {
				audioIn.close();
			} catch (IOException e) {
				App.Log.write(LogSource.Audio, LogPriority.Warning, "Failed ",
						"to close audio file \"", fileName, "\"");
				return null;
			}
			Audio.put(fileName, new AudioData(format, data));
		}
		return Audio.get(fileName);
	}
	/*
	 * Free a chunk of audio data from memory by its file name
	 * @param String fileName - The file name to free
	 * @return boolean - Whether the audio data was successfully found and freed
	 */
	public static boolean FreeAudioData(String fileName) {
		if (!Audio.containsKey(fileName)) {
			return false;
		}
		Audio.remove(fileName);
		return true;
	}
	
}
