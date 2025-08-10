/*
 * File:		NetConnection.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.08.07
 * Purpose:		Define the interface class between client and server sides of
 * 				the Asclepias Framework's networking system
 */

package com.github.kmacdonald222.asclepiasfw.networking;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class NetConnection {
	
	private boolean initialized = false;
	private NetListener listener = null;
	private int ID = 0;
	private Thread connectionThread = null;
	private Socket socket = null;
	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;
	private Thread inputThread = null;
	private LinkedBlockingQueue<NetMessage> messagesIn = null;
	private Thread outputThread = null;
	private LinkedBlockingQueue<NetMessage> messagesOut = null;
	
	public boolean initialize(NetListener listener) {
		if (initialized) {
			return false;
		}
		
		initialized = true;
		return initialized;
	}
	public boolean connectToServer(String hostName, int port, int timeout) {
		return false;
	}
	public boolean connectToClient(Socket socket, int ID) {
		return false;
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
	
	public NetListener getListener() {
		return listener;
	}
	public int getID() {
		return ID;
	}
	public String getHostName() {
		if (socket == null) {
			return "";
		}
		return socket.getInetAddress().getHostName();
	}
	public String getHostAddress() {
		if (socket == null) {
			return "";
		}
		return socket.getInetAddress().getHostAddress();
	}
	public LinkedBlockingQueue<NetMessage> getMessagesIn() {
		return messagesIn;
	}

}
