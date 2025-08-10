/*
 * File:		NetClient.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.08.07
 * Purpose:		Defines the main class of the client (app) side of the
 * 				networking system for the Asclepias Framework
 */

package com.github.kmacdonald222.asclepiasfw.networking;

import java.util.List;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

public class NetClient {

	private boolean initialized = false;
	private List<NetListener> listeners = null;
	private NetConnection connection = null;
	
	public boolean initialize() {
		if (initialized) {
			return false;
		}
		
		initialized = true;
		return initialized;
	}
	public boolean connect(String hostName, int port, int timeout) {
		return false;
	}
	public void update() {
		
	}
	public boolean send(NetMessage message) {
		return false;
	}
	public boolean disconnect() {
		return false;
	}
	public boolean destroy() {
		if (!initialized) {
			return false;
		}
		boolean success = true;
		
		initialized = false;
		return success;
	}
	
	public List<NetListener> getListeners() {
		return listeners;
	}
	public boolean addListener(NetListener listener) {
		if (listeners.contains(listener)) {
			return false;
		}
		App.Log.write(LogSource.NetClient, LogPriority.Info, "Adding network ",
				"listener");
		listeners.add(listener);
		return true;
	}
	public boolean removeListener(NetListener listener) {
		if (!listeners.contains(listener)) {
			return false;
		}
		App.Log.write(LogSource.NetClient, LogPriority.Info, "Removing ",
				"network listener");
		listeners.remove(listener);
		return true;
	}
	
}
