/*
 * File:		NetConnection.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.08.07
 * Purpose:		Define the interface class between client and server sides of
 * 				the Asclepias Framework's networking system
 */

package com.github.kmacdonald222.asclepiasfw.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import com.github.kmacdonald222.asclepiasfw.logging.LogManager;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;

// Network connection bridge/interface class between client and server sides of
// the Asclepias Framework's networking system
public class NetConnection {
	
	// Whether this network connection has been initialized
	private boolean initialized = false;
	// The log for this network connection to write messages to
	private LogManager log = null;
	// Listener for this network connection to call back to
	private NetListener listener = null;
	// Unique identifier for this network connection
	private int ID = 0;
	// Thread for awaiting connections to remote server as client connection
	private Thread connectThread = null;
	// Whether the connection thread is currently running
	private boolean connecting = false;
	// Java network socket handle
	private Socket socket = null;
	// The address of the remote end of this network connection's socket
	// (empty when disconnected)
	private String address = "";
	// The port of the remote end of this network connection's socket (0 when
	// disconnected)
	private int port = 0;
	// The timeout for the socket when attempting connection to a remote server
	// (0 when disconnected)
	private int timeout = 0;
	// Whether this network connection is currently connected to a remote host
	private boolean connected = false;
	// Thread-safe queue of network connection events for callback functions
	private LinkedBlockingQueue<Boolean> connectEvents = null;
	// Input stream for reading messages from remote host
	private ObjectInputStream input = null;
	// Thread for reading messages from remote host when connected
	private Thread inputThread = null;
	// Thread-safe queue of incoming messages for callback functions
	private LinkedBlockingQueue<NetMessage> messagesIn = null;
	// Output stream for writing messages to remote host
	private ObjectOutputStream output = null;
	// Thread for writing messages to remote host when connected and messages
	// are present
	private Thread outputThread = null;
	// Thread-safe queue of outgoing messages
	private LinkedBlockingQueue<NetMessage> messagesOut = null;
	// Whether the output thread is currently running
	private boolean writing = false;
	
	/*
	 * Initialize this network connection's memory
	 * @param LogManager log - Copy of a log for this network connection to
	 * write messages to
	 * @param NetListener listener - The parent of this network connection with
	 * callback functions for network events
	 * @param int ID - The unique identifier for this network connection
	 * @return boolean - Whether this network connection was initialized
	 * successfully
	 */
	public boolean initialize(LogManager log, NetListener listener, int ID) {
		if (initialized) {
			return false;
		}
		this.log = log;
		log.write(LogSource.NetConnection, LogPriority.Info, "Set logging ",
				"system");
		this.listener = listener;
		log.write(LogSource.NetConnection, LogPriority.Info, "Set network ",
				"connection listener");
		this.ID = ID;
		connectEvents = new LinkedBlockingQueue<Boolean>();
		messagesIn = new LinkedBlockingQueue<NetMessage>();
		messagesOut = new LinkedBlockingQueue<NetMessage>();
		log.write(LogSource.NetConnection, LogPriority.Info, "Initialized ",
				"event queues");
		initialized = true;
		return initialized;
	}
	/*
	 * Start the connection attempt process to a remote server (for use by the
	 * NetClient class only)
	 * @param String address - The address of the remote server
	 * @param int port - The port to connect to the remote server on
	 * @param int timeout - The connection timeout in milliseconds
	 */
	public void connectToServer(String address, int port, int timeout) {
		log.write(LogSource.NetConnection, LogPriority.Info, "Initiating ",
				"connection to server at ", address, ":", port, " with ",
				"timeout ", timeout);
		if (connecting || connected) {
			log.write(LogSource.NetConnection, LogPriority.Warning, "Already ",
					"connected to a server");
			disconnect();
		}
		this.address = address;
		this.port = port;
		this.timeout = timeout;
		log.write(LogSource.NetConnection, LogPriority.Info, "Set address, ",
				"port, and timeout");
		connectThread = new Thread(this::connect);
		connectThread.start();
	}
	/*
	 * Attempt to connect to a remote client (for use by the NetServer class
	 * only)
	 * @param Socket socket - The socket to connect to the client with
	 * @return boolean - Whether this network connection was successfully
	 * connected to the remote client
	 */
	public boolean connectToClient(Socket socket) {
		this.socket = socket;
		address = socket.getInetAddress().getHostAddress();
		port = 0;
		timeout = 0;
		log.write(LogSource.NetConnection, LogPriority.Info, "Initiating ",
				"connection to client ", ID, " at ", address);
		try {
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			log.write(LogSource.NetConnection, LogPriority.Warning, "Failed ",
					"to open input stream from client ", ID);
			disconnect();
			return false;
		}
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();
		} catch (IOException e) {
			log.write(LogSource.NetConnection, LogPriority.Warning, "Failed ",
					"to open output stream to client ", ID);
			disconnect();
			return false;
		}
		log.write(LogSource.NetConnection, LogPriority.Info, "Opened input ",
				"and output streams");
		connected = true;
		try {
			connectEvents.put(true);
		} catch (InterruptedException e) {
			log.write(LogSource.NetConnection, LogPriority.Warning, "Failed ",
					"to push connect event");
			disconnect();
			return false;
		}
		inputThread = new Thread(this::read);
		inputThread.start();
		return true;
	}
	/*
	 * Publish incoming messages and connection events to this network
	 * connection's parent listener
	 * @param int maximumMessages - The maximum number of incoming messages to
	 * publish
	 */
	public void update(int maximumMessages) {
		int i = 0;
		while (!messagesIn.isEmpty()
				&& (maximumMessages == -1 || i < maximumMessages)) {
			listener.netMessageReceived(messagesIn.poll());
		}
		while (!connectEvents.isEmpty()) {
			if (connectEvents.poll()) {
				if (!listener.netConnected(ID)) {
					disconnect();
				}
			} else {
				listener.netDisconnected(ID);
			}
		}
	}
	/*
	 * Attempt to start the sending process for an outgoing message
	 * @param NetMessage message - The message to send
	 * @return boolean - Whether the sending process was started successfully
	 */
	public boolean send(NetMessage message) {
		if (!connected) {
			log.write(LogSource.NetConnection, LogPriority.Warning, "Failed ",
					"to write message ", message, ", disconnected");
			return false;
		}
		try {
			messagesOut.put(message);
		} catch (InterruptedException e) {
			log.write(LogSource.NetConnection, LogPriority.Warning, "Failed ",
					"to write message ", message, ", interrupted");
			return false;
		}
		if (!writing) {
			writing = true;
			outputThread = new Thread(this::write);
			outputThread.start();
		}
		return true;
	}
	/*
	 * Disconnect this network connection from any remote host it is currently
	 * connected to
	 */
	public void disconnect() {
		if (!connected) {
			return;
		}
		log.write(LogSource.NetConnection, LogPriority.Info, "Disconnecting ",
				"network connection ", ID);
		connected = false;
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				log.write(LogSource.NetConnection, LogPriority.Warning,
						"Failed to close output stream");
			}
		}
		output = null;
		log.write(LogSource.NetConnection, LogPriority.Info, "Destroyed ",
				"output stream");
		if (outputThread != null) {
			outputThread.interrupt();
			try {
				outputThread.join();
			} catch (InterruptedException e) {
				log.write(LogSource.NetConnection, LogPriority.Warning,
						"Failed to join message output thread");
			}
		}
		outputThread = null;
		messagesOut.clear();
		writing = false;
		log.write(LogSource.NetConnection, LogPriority.Info, "Destroyed ",
				"message output thread and cleared message queue");
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				log.write(LogSource.NetConnection, LogPriority.Warning,
						"Failed to close input stream");
			}
		}
		input = null;
		log.write(LogSource.NetConnection, LogPriority.Info, "Destroyed input ",
				"stream");
		if (inputThread != null) {
			inputThread.interrupt();
			try {
				inputThread.join();
			} catch (InterruptedException e) {
				log.write(LogSource.NetConnection, LogPriority.Warning,
						"Failed to join message input thread");
			}
		}
		inputThread = null;
		messagesIn.clear();
		log.write(LogSource.NetConnection, LogPriority.Info, "Destroyed ",
				"message input thread and cleared message queue");
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				log.write(LogSource.NetConnection, LogPriority.Warning,
						"Failed to close socket");
			}
		}
		socket = null;
		log.write(LogSource.NetConnection, LogPriority.Info, "Destroyed ",
				"socket");
		if (connectThread != null) {
			connectThread.interrupt();
			try {
				connectThread.join();
			} catch (InterruptedException e) {
				log.write(LogSource.NetConnection, LogPriority.Warning,
						"Failed to join connection thread");
			}
		}
		connectThread = null;
		connecting = false;
		log.write(LogSource.NetConnection, LogPriority.Info, "Destroyed ",
				"connection thread and cleared connection state");
		try {
			connectEvents.put(false);
		} catch (InterruptedException e) {
			log.write(LogSource.NetConnection, LogPriority.Warning, "Failed ",
					"to push disconnect event");
		}
		address = "";
		port = 0;
		timeout = 0;
	}
	/*
	 * Disconnect this network connection from any remote host it is connected
	 * to and free its memory
	 * @return boolean - Whether this network connection was destroyed
	 * successfully
	 */
	public boolean destroy() {
		if (!initialized) {
			return false;
		}
		boolean success = true;
		log.write(LogSource.NetConnection, LogPriority.Info, "Destroying ",
				"network connection ", ID);
		if (connected) {
			log.write(LogSource.NetConnection, LogPriority.Warning, "Still ",
					"connected to host");
			disconnect();
		}
		log.write(LogSource.NetConnection, LogPriority.Info, "Freeing network ",
				"listener");
		listener = null;
		log.write(LogSource.NetConnection, LogPriority.Info, "Clearing ",
				"connection state memory and events");
		ID = 0;
		connectEvents.clear();
		connectEvents = null;
		messagesIn = null;
		log.write(LogSource.NetConnection, LogPriority.Info, "Freeing logging ",
				"system");
		log = null;
		initialized = false;
		return success;
	}
	
	/*
	 * Attempt to connect this network connection to the remote server whose
	 * information is in memory, if successful, start the message input
	 * thread and, if unsuccessful, disconnect
	 */
	private void connect() {
		connecting = true;
		log.write(LogSource.NetConnection, LogPriority.Info, "Connecting ",
				"socket");
		socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(address, port), timeout);
		} catch (Exception e) {
			log.write(LogSource.NetConnection, LogPriority.Warning, "Failed ",
					"to connect socket to server");
			disconnect();
			return;
		}
		log.write(LogSource.NetConnection, LogPriority.Info, "Opening output ",
				"stream to server");
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();
		} catch (IOException e) {
			log.write(LogSource.NetConnection, LogPriority.Warning, "Failed ",
					"to open output stream to server");
			disconnect();
			return;
		}
		log.write(LogSource.NetConnection, LogPriority.Info, "Opening input ",
				"stream from server");
		try {
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			log.write(LogSource.NetConnection, LogPriority.Warning, "Failed ",
					"to open input stream from server");
			disconnect();
			return;
		}
		connected = true;
		connecting = false;
		try {
			connectEvents.put(true);
		} catch (InterruptedException e) {
			log.write(LogSource.NetConnection, LogPriority.Warning, "Failed ",
					"to push connect event");
			disconnect();
			return;
		}
		inputThread = new Thread(this::read);
		inputThread.start();
	}
	/*
	 * Read incoming messages from the connected remote host until the
	 * connection fails or is disconnected locally
	 */
	private void read() {
		log.write(LogSource.NetConnection, LogPriority.Info, "Starting ",
				"message input thread");
		while (true) {
			NetMessage message = null;
			try {
				message = (NetMessage)input.readObject();
			} catch (ClassNotFoundException e) {
				log.write(LogSource.NetConnection, LogPriority.Error,
						"Message class not available");
				break;
			} catch (IOException e) {
				log.write(LogSource.NetConnection, LogPriority.Warning,
						"Failed to read new message from host");
				break;
			}
			message.setReceiverID(ID);
			try {
				messagesIn.put(message);
			} catch (InterruptedException e) {
				log.write(LogSource.NetConnection, LogPriority.Warning,
						"Failed to push new message to incoming queue");
				continue;
			}
		}
		log.write(LogSource.NetConnection, LogPriority.Info, "Finished ",
				"message input thread");
		if (connected) {
			inputThread = null;
			disconnect();
		}
	}
	/*
	 * Write outgoing messages to the connected remote host until the output
	 * queue is empty or, on connection failure, disconnect
	 */
	private void write() {
		while (!messagesOut.isEmpty()) {
			NetMessage message = messagesOut.poll();
			try {
				output.writeObject(message);
				output.flush();
			} catch (IOException e) {
				log.write(LogSource.NetConnection, LogPriority.Warning,
						"Failed to write message, ", message, ", disconnected");
				outputThread = null;
				disconnect();
				break;
			}
		}
		writing = false;
	}
	
	/*
	 * Get this network connection's copy of the logging system
	 * @return LogManager - This network connection's log
	 */
	public LogManager getLog() {
		return log;
	}
	/*
	 * Set this network connection's copy of the logging system
	 * @param LogManager log - This network connection's new log
	 */
	public void setLog(LogManager log) {
		this.log = log;
	}
	/*
	 * Get this network connection's parent network listener
	 * @return NetListener - This network connection's listener
	 */
	public NetListener getListener() {
		return listener;
	}
	/*
	 * Set this network connection's parent network listener
	 * @param NetListener listener - This network connection's new network
	 * listener
	 */
	public void setListener(NetListener listener) {
		this.listener = listener;
	}
	/*
	 * Get this network connection's unique identifier
	 * @return int - This network connection's ID
	 */
	public int getID() {
		return ID;
	}
	/*
	 * Set this network connection's unique identifier
	 * @param int ID - This network connection's new ID
	 */
	public void setID(int ID) {
		this.ID = ID;
	}
	/*
	 * Test whether this network connection is currently attempting to connect
	 * to a remote server
	 * @return boolean - Whether this network connection is connecting
	 */
	public boolean isConnecting() {
		return connecting;
	}
	/*
	 * Get the address of the remote host this network connection is currently
	 * connected to
	 * @return String - This network connection's address
	 */
	public String getAddress() {
		return address;
	}
	/*
	 * Get the port this network connection is currently connected on
	 * @return int - This network connection's port
	 */
	public int getPort() {
		return port;
	}
	/*
	 * Test whether this network connection is currently connected to a remote
	 * host
	 * @return boolean - Whether this network connection is connected
	 */
	public boolean isConnected() {
		return connected;
	}

}
