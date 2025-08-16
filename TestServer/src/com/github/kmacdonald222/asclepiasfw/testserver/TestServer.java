package com.github.kmacdonald222.asclepiasfw.testserver;

import com.github.kmacdonald222.asclepiasfw.networking.NetServer;

public class TestServer {
	
	public static void main(String[] args) {
		NetServer server = new NetServer();
		System.out.println("Initializing network server");
		if (!server.initialize()) {
			System.out.println("Failed to initialize network server");
		}
		System.out.println("Destroying network server");
		if (!server.destroy()) {
			System.out.println("Failed to destroy network server");
		}
	}

}
