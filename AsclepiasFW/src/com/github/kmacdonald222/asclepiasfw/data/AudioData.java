/*
 * File:		AudioData.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.07.21
 * Purpose:		Defines a wrapper class for a chunk of audio data and its format
 */

package com.github.kmacdonald222.asclepiasfw.data;

import javax.sound.sampled.AudioFormat;

// Wrapper class for a chunk of audio data and its format
public class AudioData {
	
	// The format of this audio data
	public AudioFormat format = null;
	// The audio data
	public byte[] data = null;
	
	/*
	 * Construct an audio data wrapper with a format and data
	 * @param AudioFormat format - The format of this audio data
	 * @param byte[] data - The audio data
	 */
	public AudioData(AudioFormat format, byte[] data) {
		this.format = format;
		this.data = data;
	}

}
