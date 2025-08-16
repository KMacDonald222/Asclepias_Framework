/*
 * File:		NetServer.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.08.06
 * Purpose:		Define the main class of the server side of the networking
 * 				system for the Asclepias Framework
 */

package com.github.kmacdonald222.asclepiasfw.networking;

public class NetServer implements NetListener {
	
	private boolean initialized = false;
	
	public boolean initialize() {
		if (initialized) {
			return false;
		}
		
		initialized = true;
		return initialized;
	}
	public boolean destroy() {
		if (!initialized) {
			return false;
		}
		boolean success = true;
		
		initialized = false;
		return success;
	}
	
}