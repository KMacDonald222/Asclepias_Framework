/*
 * File:		NetServer.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.08.07
 * Purpose:		Defines the main class of the server side of the networking
 * 				system for the Asclepias Framework
 */

package com.github.kmacdonald222.asclepiasfw.networking;

import java.net.ServerSocket;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.github.kmacdonald222.asclepiasfw.app.AppConfig;
import com.github.kmacdonald222.asclepiasfw.logging.LogManager;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

public class NetServer {

	private boolean initialized = false;
	private LogManager log = null;
	private List<NetListener> listeners = null;
	private Thread acceptorThread = null;
	private ServerSocket acceptor = null;
	private List<NetConnection> clients = null;
	private LinkedBlockingQueue<NetMessage> messagesIn = null;
	
	public boolean initialize(AppConfig.Log logConfig) {
		if (initialized) {
			return false;
		}
		
		initialized = true;
		return initialized;
	}
	public void open(int port) {
		
	}
	public void update() {
		
	}
	public boolean send(NetMessage message) {
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
	
	public LogManager getLog() {
		return log;
	}
	public List<NetListener> getListeners() {
		return listeners;
	}
	public boolean addListener(NetListener listener) {
		if (listeners.contains(listener)) {
			return false;
		}
		log.write(LogSource.NetServer, LogPriority.Info, "Adding network ",
				"listener");
		listeners.add(listener);
		return true;
	}
	public boolean removeListener(NetListener listener) {
		if (!listeners.contains(listener)) {
			return false;
		}
		log.write(LogSource.NetServer, LogPriority.Info, "Removing network ",
				"listener");
		listeners.remove(listener);
		return true;
	}
	public int getPort() {
		if (acceptor == null) {
			return 0;
		}
		return acceptor.getLocalPort();
	}
	public List<NetConnection> getClients() {
		return clients;
	}
	public NetConnection getClient(int ID) {
		for (NetConnection client : clients) {
			if (client.getID() == ID) {
				return client;
			}
		}
		return null;
	}
	public boolean removeClient(NetConnection client) {
		if (!clients.contains(client)) {
			return false;
		}
		boolean success = client.disconnect();
		clients.remove(client);
		return success;
	}
	public boolean removeClient(int ID) {
		return removeClient(getClient(ID));
	}
	
}
