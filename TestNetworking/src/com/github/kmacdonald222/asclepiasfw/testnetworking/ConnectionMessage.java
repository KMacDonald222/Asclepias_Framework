package com.github.kmacdonald222.asclepiasfw.testnetworking;

import com.github.kmacdonald222.asclepiasfw.networking.NetMessage;

public class ConnectionMessage extends NetMessage {

	private static final long serialVersionUID = 4149138709214207979L;
	
	public int clientID = 0;

	public ConnectionMessage(int messageID, int clientID) {
		super(NetMessageTypes.CONNECTION, messageID);
		this.clientID = clientID;
	}
	
	@Override
	public String toString() {
		return "ConnectionMessage(messageID=" + Integer.toString(messageID)
				+ ",clientID=" + Integer.toString(clientID) + ")";
	}

}
