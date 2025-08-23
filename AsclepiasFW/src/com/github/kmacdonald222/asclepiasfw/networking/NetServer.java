/*
 * File:		NetServer.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.08.06
 * Purpose:		Define the main class of the server side of the networking
 * 				system for the Asclepias Framework
 */

package com.github.kmacdonald222.asclepiasfw.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.github.kmacdonald222.asclepiasfw.logging.LogManager;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

// The main class of the server side of the Asclepias Framework's networking
// system
public class NetServer implements NetListener {
	
	// Whether this network server has been initialized
	private boolean initialized = false;
	// The logging system used by this network server
	private LogManager log = null;
	// The parent network listener for this network server's callback functions
	private NetListener listener = null;
	// Whether this network server is currently listening for new connections
	private boolean accepting = false;
	// Thread for accepting new client connections
	private Thread acceptorThread = null;
	// Java server socket handle for accepting client connections
	private ServerSocket acceptor = null;
	// The next unique identifier to assign to a new client connection
	private volatile int nextClientID = 0;
	// The set of clients currently connected to this network server
	private LinkedBlockingQueue<NetConnection> clients = null;
	// The maximum number of clients for this network server to be connected to
	// simultaneously
	private volatile int maximumClientCount = 0;
	
	/*
	 * Initialize this network server's memory and start accepting new clients
	 * @param LogManager log - The logging system for this network server to
	 * write messages to
	 * @param NetListener listener - The parent network listener for this
	 * network server's callback functions
	 * @param int port - The port for this network server to listen for new
	 * client connections on
	 * @param int maximumClientCount - The maximum number of clients for this
	 * network server to connect to simultaneously
	 */
	public boolean initialize(LogManager log, NetListener listener, int port,
			int maximumClientCount) {
		if (initialized) {
			return false;
		}
		this.log = log;
		log.write(LogSource.NetServer, LogPriority.Info, "Set logging system");
		this.listener = listener;
		log.write(LogSource.NetServer, LogPriority.Info, "Set network ",
				"listener");
		accepting = false;
		acceptorThread = new Thread(this::listen);
		try {
			acceptor = new ServerSocket(port);
		} catch (IOException e) {
			log.write(LogSource.NetServer, LogPriority.Error, "Failed to bind ",
					"acceptor socket to port ", port);
			return false;
		}
		log.write(LogSource.NetServer, LogPriority.Info, "Bound acceptor ",
				"socket to port ", port);
		clients = new LinkedBlockingQueue<NetConnection>();
		setMaximumClientCount(maximumClientCount);
		log.write(LogSource.NetServer, LogPriority.Info, "Initialized clients ",
				"queue");
		accepting = true;
		acceptorThread.start();
		log.write(LogSource.NetServer, LogPriority.Info, "Started acceptor");
		initialized = true;
		return initialized;
	}
	/*
	 * Update this network server's client connections
	 * @param int maximumMessagesPerClient - The maximum number of incoming
	 * messages for this network server to process from each client
	 */
	public void update(int maximumMessagesPerClient) {
		for (NetConnection client : clients) {
			client.update(maximumMessagesPerClient);
		}
	}
	/*
	 * Broadcast a message to all currently connected clients
	 * @param NetMessage message - The message to broadcast
	 * @return boolean - Whether the message was broadcast to all clients
	 * successfully
	 */
	public boolean broadcast(NetMessage message) {
		return broadcast(message, -1);
	}
	/*
	 * Broadcast a message to all currently connected clients except one
	 * @param NetMessage message - The message to broadcast
	 * @param int ignoredClientID - The identifier of the client to ignore (-1
	 * to ignore no clients)
	 * @return boolean - Whether the message was broadcast to all intended
	 * clients successfully
	 */
	public boolean broadcast(NetMessage message, int ignoredClientID) {
		boolean success = true;
		for (NetConnection client : clients) {
			if (client.getID() != ignoredClientID || ignoredClientID == -1) {
				if (!client.send(message)) {
					log.write(LogSource.NetServer, LogPriority.Warning,
							"Failed to send message ", message, " to client ",
							client.getID(), " in broadcast");
					success = false;
				}
			}
		}
		return success;
	}
	/*
	 * Send a message to client currently connected to this network server
	 * @param NetMessage - The message to send
	 * @param int clientID - The unique identifier of the connection to send the
	 * message over
	 * @return boolean - Whether the message was sent successfully
	 */
	public boolean send(NetMessage message, int clientID) {
		NetConnection client = getClient(clientID);
		if (client == null) {
			log.write(LogSource.NetServer, LogPriority.Warning, "Failed to ",
					"send message ", message, " to client ", clientID);
			return false;
		}
		return client.send(message);
	}
	/*
	 * Notify the parent listener that a client has connected to this network
	 * server
	 * @param int ID - The unique identifier of the new client
	 * @return boolean - Whether the parent listener accepted the connection
	 */
	@Override
	public boolean netConnected(int ID) {
		return listener.netConnected(ID);
	}
	/*
	 * Notify the parent listener that a client has sent a message to this
	 * network server
	 * @param NetMessage message - The message received
	 */
	@Override
	public void netMessageReceived(NetMessage message) {
		listener.netMessageReceived(message);
	}
	/*
	 * Notify the parent listener that a client has disconnected from this
	 * network server and remove the client from memory
	 * @param int ID - The unique identifier of the disconnected client
	 */
	@Override
	public void netDisconnected(int ID) {
		listener.netDisconnected(ID);
		NetConnection client = getClient(ID);
		if (client != null) {
			if (clients.contains(client)) {
				clients.remove(client);
			}
		}
	}
	/*
	 * Disconnect all current clients and free this network server's memory
	 * @return boolean - Whether this network server was destroyed successfully
	 */
	public boolean destroy() {
		if (!initialized) {
			return false;
		}
		boolean success = true;
		log.write(LogSource.NetServer, LogPriority.Info, "Stopping client ",
				"accepting thread");
		accepting = false;
		try {
			acceptor.close();
		} catch (IOException e) {
			log.write(LogSource.NetServer, LogPriority.Warning, "Failed to ",
					"close acceptor socket");
			success = false;
		}
		try {
			acceptorThread.join();
		} catch (InterruptedException e) {
			log.write(LogSource.NetServer, LogPriority.Warning, "Failed to ",
					"join client accepting thread");
			success = false;
		}
		acceptorThread = null;
		log.write(LogSource.NetServer, LogPriority.Info, "Disconnecting all ",
				"clients");
		for (NetConnection client : clients) {
			client.disconnect();
		}
		clients.clear();
		clients = null;
		log.write(LogSource.NetServer, LogPriority.Info, "Clearing network ",
				"listener");
		listener = null;
		log.write(LogSource.NetServer, LogPriority.Info, "Clearing logging ",
				"system");
		log = null;
		initialized = false;
		return success;
	}
	
	/*
	 * Accept new connections to this network server while it is running and
	 * still available
	 */
	private void listen() {
		log.write(LogSource.NetServer, LogPriority.Info, "Accepting clients");
		while (accepting) {
			Socket socket = null;
			try {
				socket = acceptor.accept();
			} catch (IOException e) {
				log.write(LogSource.NetServer, LogPriority.Warning, "Failed ",
						"to read new client connection");
				break;
			}
			log.write(LogSource.NetServer, LogPriority.Info, "Received new ",
					"client connection at ",
					socket.getInetAddress().getHostAddress());
			if (clients.size() >= maximumClientCount) {
				log.write(LogSource.NetServer, LogPriority.Warning, "Rejected ",
						"new client connection, not enough space");
				try {
					socket.close();
				} catch (IOException e) {
					log.write(LogSource.NetServer, LogPriority.Warning,
							"Failed to close socket");
				}
				continue;
			}
			NetConnection client = new NetConnection();
			if (!client.initialize(log, this, nextClientID++)) {
				log.write(LogSource.NetServer, LogPriority.Warning, "Failed ",
						"to initialize new client");
				continue;
			}
			clients.add(client);
			if (!client.connectToClient(socket)) {
				log.write(LogSource.NetServer, LogPriority.Warning, "Failed ",
						"to connect to new client");
				client.disconnect();
				continue;
			}
		}
		accepting = false;
		log.write(LogSource.NetServer, LogPriority.Info, "Stopped accepting ",
				"clients");
	}
	
	/*
	 * Get the log for this network server to write messages to
	 * @return LogManager - This network server's log
	 */
	public LogManager getLog() {
		return log;
	}
	/*
	 * Set the log for this network server to write messages to
	 * @param LogManager log - This network server's new log
	 */
	public void setLog(LogManager log) {
		this.log = log;
	}
	/*
	 * Get the parent network listener for this network server's callback
	 * functions
	 * @return NetListener - This network server's listener
	 */
	public NetListener getListener() {
		return listener;
	}
	/*
	 * Set the parent network listener for this network server's callback
	 * functions
	 * @param NetListener listener - This network server's new listener
	 */
	public void setListener(NetListener listener) {
		this.listener = listener;
	}
	/*
	 * Test whether this network server is currently accepting new client
	 * connections
	 * @return boolean - Whether this network server is accepting
	 */
	public boolean isAccepting() {
		return accepting;
	}
	/*
	 * Get the next unque identifier to be assigned to a client connection
	 * @return int - The next unique identifier for a client connection
	 */
	public int getNextClientID() {
		return nextClientID;
	}
	/*
	 * Get the number of clients currently connected to this network server
	 * @return int - This network server's current client count
	 */
	public int getClientCount() {
		return clients.size();
	}
	/*
	 * Get the unique identifiers of the set of clients currently connected to
	 * this network server
	 * @return List<Integer> - The current set of connect client identifiers
	 */
	public List<Integer> getClientIDs() {
		List<Integer> clientIDs = new ArrayList<Integer>();
		for (NetConnection client : clients) {
			clientIDs.add(client.getID());
		}
		return clientIDs;
	}
	/*
	 * Get the address of a client by its unqiue identifier
	 * @param int clientID - The unique identifier of the client to find
	 * @return String - The address of the client or empty string if not found
	 */
	public String getClientAddress(int clientID) {
		NetConnection client = getClient(clientID);
		if (client == null) {
			return "";
		}
		return client.getAddress();
	}
	/*
	 * Disconnect a client from this network server by its unique identifier
	 * @param int clientID - The unique identifier of the client to disconnect
	 * @return boolean - Whether the client could be found and was disconnected
	 * successfully
	 */
	public boolean removeClient(int clientID) {
		log.write(LogSource.NetServer, LogPriority.Info, "Removing client ",
				clientID);
		NetConnection client = getClient(clientID);
		if (client == null) {
			log.write(LogSource.NetServer, LogPriority.Warning, "Failed to ",
					"remove client ", clientID, ", not found");
			return false;
		}
		boolean success = true;
		client.disconnect();
		if (!client.destroy()) {
			log.write(LogSource.NetServer, LogPriority.Warning, "Failed to ",
					"remove client ", clientID, ", could not disconnect");
			success = false;
		}
		clients.remove(client);
		return success;
	}
	/*
	 * Get the maximum number of clients for this network server to connect to
	 * simultaneously
	 * @return int - The maximum number of clients for this network server
	 */
	public int getMaximumClientCount() {
		return maximumClientCount;
	}
	/*
	 * Set the maximum number of clients for this network server to connect to
	 * simultaneously
	 * @param int maximumClientCount - The new maximum number of client for this
	 * network server
	 */
	public void setMaximumClientCount(int maximumClientCount) {
		this.maximumClientCount = maximumClientCount;
	}
	
	/*
	 * Get a client's network connection by its unique identifier
	 * @param int clientID - The unique identifier of the client to find
	 * @return NetConnection - The client's connection or null if not found
	 */
	private NetConnection getClient(int clientID) {
		for (NetConnection client : clients) {
			if (client.getID() == clientID) {
				return client;
			}
		}
		log.write(LogSource.NetServer, LogPriority.Warning, "Failed to ",
				"retrieve client ", clientID);
		return null;
	}
	
}