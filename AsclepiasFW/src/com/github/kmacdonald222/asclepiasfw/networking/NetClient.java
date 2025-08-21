/*
 * File:		NetClient.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.08.07
 * Purpose:		Defines the main class of the client (app) side of the
 * 				networking system for the Asclepias Framework
 */

package com.github.kmacdonald222.asclepiasfw.networking;

import java.util.ArrayList;
import java.util.List;

import com.github.kmacdonald222.asclepiasfw.app.App;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

// The main class of the client side of the Asclepias Framework's networking
// system
public class NetClient implements NetListener {

	// Whether this network client has been initialized
	private boolean initialized = false;
	// The set of listeners for the network client to publish network events to
	private List<NetListener> listeners = null;
	// This network client's connection
	private NetConnection connection = null;
	// The maximum number of incoming messages this client will publish
	// callbacks for each logic update
	private int maximumMessagesPerUpdate = 0;
	
	/*
	 * Initialize this network client's memory
	 * @param int maximumMessagesPerUpdate - The maximum number of incoming
	 * messages to process per logic update
	 * @return boolean - Whether the network client was initialized successfully
	 */
	public boolean initialize(int maximumMessagesPerUpdate) {
		if (initialized) {
			return false;
		}
		listeners = new ArrayList<NetListener>();
		App.Log.write(LogSource.NetClient, LogPriority.Info, "Initialized ",
				"network listeners set");
		connection = new NetConnection();
		if (!connection.initialize(App.Log, this, 0)) {
			App.Log.write(LogSource.NetClient, LogPriority.Error, "Failed to ",
					"initialize network connection");
			return false;
		}
		this.maximumMessagesPerUpdate = maximumMessagesPerUpdate;
		App.Log.write(LogSource.NetClient, LogPriority.Info, "Initialized ",
				"network connection");
		initialized = true;
		return initialized;
	}
	/*
	 * Start the connection attempt process to a remote server and push an
	 * event to the application's current scene with the result
	 * @param String address - The address of the remote server
	 * @param int port - The port to attempt to connect on
	 * @param int timeout - The timeout of the connection attempt in
	 * milliseconds
	 */
	public void connect(String address, int port, int timeout) {
		App.Log.write(LogSource.NetClient, LogPriority.Info, "Attempting ",
				"connection to ", address, ":", port, " with timeout ",
				timeout);
		connection.connectToServer(address, port, timeout);
	}
	/*
	 * Update this network client's connection and process messages and events
	 */
	public void update() {
		connection.update(maximumMessagesPerUpdate);
	}
	/*
	 * Attempt to send a message to the currently connected remote server
	 * @param NetMessage message - The message to send
	 * @return boolean - Whether the message sending process was started
	 * successfully
	 */
	public boolean send(NetMessage message) {
		if (!connection.send(message)) {
			App.Log.write(LogSource.NetClient, LogPriority.Warning, "Failed ",
					"to send message to host");
			return false;
		}
		return true;
	}
	/*
	 * Notify all network listeners that a remote server has been connected to
	 * @param int ID - The unique identifier of the network connection connected
	 * to by the server (unused by clients)
	 * @return boolean - Whether all listeners accepted the connection
	 */
	@Override
	public boolean netConnected(int ID) {
		App.Log.write(LogSource.NetClient, LogPriority.Info, "Received ",
				"response on network connection ", ID);
		for (NetListener listener : listeners) {
			if (!listener.netConnected(ID)) {
				App.Log.write(LogSource.NetClient, LogPriority.Warning,
						"Connection ", ID, " rejected by network listener");
				return false;
			}
		}
		App.Log.write(LogSource.NetClient, LogPriority.Info, "Accepted ",
				"connection ", ID);
		return true;
	}
	/*
	 * Notify all network listeners that a remote server has sent a message
	 * @param NetMessage message - The message received
	 */
	@Override
	public void netMessageReceived(NetMessage message) {
		for (NetListener listener : listeners) {
			listener.netMessageReceived(message);
		}
	}
	/*
	 * Notify all network listeners that a remote server has disconnected
	 * @param int ID - The unique identifier of the network connection
	 * disconnected from by the server
	 */
	@Override
	public void netDisconnected(int ID) {
		App.Log.write(LogSource.NetClient, LogPriority.Info, "Network ",
				"connection ", ID, " disconnected");
		for (NetListener listener : listeners) {
			listener.netDisconnected(ID);
		}
	}
	/*
	 * Disconnect this network client from any currently connected remote server
	 */
	public void disconnect() {
		App.Log.write(LogSource.NetClient, LogPriority.Info, "Disconnecting ",
				"from server");
		connection.disconnect();
	}
	/*
	 * Disconnect this network client from any currently connected remote server
	 * and free its memory
	 * @return boolean - Whether this network client was destroyed successfully
	 */
	public boolean destroy() {
		if (!initialized) {
			return false;
		}
		boolean success = true;
		App.Log.write(LogSource.NetClient, LogPriority.Info, "Destroying ",
				"network connection");
		if (!connection.destroy()) {
			success = false;
		}
		connection = null;
		App.Log.write(LogSource.NetClient, LogPriority.Info, "Clearing ",
				"network listeners set");
		listeners.clear();
		listeners = null;
		initialized = false;
		return success;
	}
	
	/*
	 * Get the set of network listeners attached to this network client
	 * @return List<NetListener> - This network client's current set of network
	 * listeners
	 */
	public List<NetListener> getListeners() {
		return listeners;
	}
	/*
	 * Add a network listener to this network client
	 * @param NetListener listener - The network listener to add
	 * @return boolean - Whether the network listener was added successfully
	 */
	public boolean addListener(NetListener listener) {
		if (listeners.contains(listener)) {
			return false;
		}
		App.Log.write(LogSource.NetClient, LogPriority.Info, "Adding network ",
				"listener");
		listeners.add(listener);
		return true;
	}
	/*
	 * Remove a network listener from this network client
	 * @param NetListener listener - The network listener to remove
	 * @return boolean - Whether the network listener was removed successfully
	 */
	public boolean removeListener(NetListener listener) {
		if (!listeners.contains(listener)) {
			return false;
		}
		App.Log.write(LogSource.NetClient, LogPriority.Info, "Removing ",
				"network listener");
		listeners.remove(listener);
		return true;
	}
	/*
	 * Get the network connection this network client uses to connect to servers
	 * @return NetConnection - This network client's network connection
	 */
	public NetConnection getConnection() {
		return connection;
	}
	/*
	 * Test whether this network client is connected to a remote server
	 * @return boolean - Whether this network client is connected
	 */
	public boolean isConnected() {
		return connection.isConnected();
	}
	/*
	 * Get the maximum number of network messages for this network client to
	 * process in each logic update
	 * @return int - The maximum number of messages to process per logic update
	 */
	public int getMaximumMessagesPerUpdate() {
		return maximumMessagesPerUpdate;
	}
	/*
	 * Set the maximum number of network messages for this network client to
	 * process in each logic update
	 * @param int maximumMessagesPerUpdate - The new maximum number of messages
	 * to process per logic update
	 */
	public void setMaximumMessagesPerUpdate(int maximumMessagesPerUpdate) {
		this.maximumMessagesPerUpdate = maximumMessagesPerUpdate;
	}

}
