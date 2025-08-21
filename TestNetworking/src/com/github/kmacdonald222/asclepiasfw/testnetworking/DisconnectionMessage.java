package com.github.kmacdonald222.asclepiasfw.testnetworking;

import com.github.kmacdonald222.asclepiasfw.networking.NetMessage;

public class DisconnectionMessage extends NetMessage {
	
	private static final long serialVersionUID = -5317922811838990627L;
	
	public int clientID = 0;

	public DisconnectionMessage(int messageID, int clientID) {
		super(NetMessageTypes.DISCONNECTION, messageID);
		this.clientID = clientID;
	}
	
	@Override
	public String toString() {
		return "DisconnectionMessage(messageID=" + Integer.toString(messageID)
				+ ",clientID=" + Integer.toString(clientID) + ")";
	}

}
