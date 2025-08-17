/*
 * File:		NetConnection.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.08.07
 * Purpose:		Define the interface class between client and server sides of
 * 				the Asclepias Framework's networking system
 */

package com.github.kmacdonald222.asclepiasfw.networking;

public class NetConnection {
	
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
