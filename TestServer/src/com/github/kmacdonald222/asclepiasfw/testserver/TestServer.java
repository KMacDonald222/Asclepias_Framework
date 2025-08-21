package com.github.kmacdonald222.asclepiasfw.testserver;

import java.util.Scanner;

import com.github.kmacdonald222.asclepiasfw.app.AppConfig;
import com.github.kmacdonald222.asclepiasfw.logging.LogManager;
import com.github.kmacdonald222.asclepiasfw.logging.LogPriority;
import com.github.kmacdonald222.asclepiasfw.logging.LogSource;
import com.github.kmacdonald222.asclepiasfw.networking.NetListener;
import com.github.kmacdonald222.asclepiasfw.networking.NetMessage;
import com.github.kmacdonald222.asclepiasfw.networking.NetServer;
import com.github.kmacdonald222.asclepiasfw.testnetworking.ConnectionMessage;
import com.github.kmacdonald222.asclepiasfw.testnetworking.DisconnectionMessage;

public class TestServer implements NetListener {
	
	private boolean initialized = false;
	private LogManager log = null;
	private NetServer server = null;
	private Thread updateThread = null;
	private boolean updating = false;
	private int nextMessageID = 0;
	
	public static void main(String[] args) {
		TestServer testServer = new TestServer();
		if (!testServer.initialize()) {
			System.exit(1);
		}
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String line = scanner.nextLine();
			if (line.equals("stop")) {
				break;
			}
		}
		testServer.destroy();
		scanner.close();
	}
	
	public boolean initialize() {
		if (initialized) {
			return false;
		}
		AppConfig config = new AppConfig();
		log = new LogManager();
		if (!log.initialize(config.log.consoleOutputEnabled,
				config.log.outputFileNames)) {
			return false;
		}
		log.write(LogSource.NetListener, LogPriority.Info, "Initialized ",
				"logging system");
		server = new NetServer();
		if (!server.initialize(log, this, 2773, 3)) {
			log.write(LogSource.NetListener, LogPriority.Error, "Failed ",
					"to start server");
			System.exit(1);
		}
		log.write(LogSource.NetListener, LogPriority.Info, "Started ",
				"server");
		updateThread = new Thread(this::update);
		updateThread.start();
		log.write(LogSource.NetListener, LogPriority.Info, "Started ",
				"update loop");
		initialized = true;
		return initialized;
	}
	@Override
	public boolean netConnected(int ID) {
		log.write(LogSource.NetListener, LogPriority.Info, "Connected ",
				"client with ID ", ID);
		ConnectionMessage newConnectionMessage = new ConnectionMessage(
				nextMessageID++, ID);
		if (!server.broadcast(newConnectionMessage, ID)) {
			log.write(LogSource.NetListener, LogPriority.Warning, "New client ",
					"broadcast not successful");
		}
		for (int clientID : server.getClientIDs()) {
			if (clientID != ID) {
				ConnectionMessage oldConnectionMessage = new ConnectionMessage(
						nextMessageID++, clientID);
				if (!server.send(oldConnectionMessage, ID)) {
					log.write(LogSource.NetListener, LogPriority.Warning,
							"Failed to notify client ", clientID, " of new ",
							"connection");
				}
			}
		}
		return true;
	}
	@Override
	public void netMessageReceived(NetMessage message) {
		log.write(LogSource.NetListener, LogPriority.Info, "Received: ",
				message);
		server.broadcast(message, message.getReceiverID());
	}
	@Override
	public void netDisconnected(int ID) {
		log.write(LogSource.NetListener, LogPriority.Info,
				"Disconnected client with ID ", ID);
		DisconnectionMessage message = new DisconnectionMessage(nextMessageID++,
				ID);
		server.broadcast(message, ID);
	}
	public boolean destroy() {
		if (!initialized) {
			return false;
		}
		boolean success = true;
		log.write(LogSource.NetListener, LogPriority.Info, "Stopping ",
				"update thread");
		updating = false;
		try {
			updateThread.join();
		} catch (InterruptedException e) {
			log.write(LogSource.NetListener, LogPriority.Warning,
					"Failed to stop update loop");
			success = false;
		}
		updateThread = null;
		log.write(LogSource.NetListener, LogPriority.Info, "Destroying ",
				"server");
		if (!server.destroy()) {
			log.write(LogSource.NetListener, LogPriority.Warning,
					"Failed to destroy server");
			success = false;
		}
		server = null;
		log.write(LogSource.NetListener, LogPriority.Info, "Destroying ",
				"logging system");
		if (!log.destroy()) {
			success = false;
		}
		log = null;
		initialized = false;
		return success;
	}
	
	private void update() {
		updating = true;
		while (updating) {
			server.update(-1);
		}
	}

}
