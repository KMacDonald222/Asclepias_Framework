/*
 * File:		NetMessage.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.08.07
 * Purpose:		Defines an abstract message to be sent via network connections
 * 				with type and unique IDs
 */

package com.github.kmacdonald222.asclepiasfw.networking;

import java.io.Serializable;

// Abstract message to be sent via network connections
public class NetMessage implements Serializable {
	
	// Base serial version identifier to be modified by inheriting classes
	private static final long serialVersionUID = 1L;
	// Type identifier for this message
	protected int typeID = 0;
	// Unique identifier for this message
	protected int messageID = 0;
	// Identifier for the network connection receiving this message
	protected int receiverID = 0;
	
	/*
	 * Construct a blank network message with type and unique identifiers
	 * @param int typeID - The type identifier for this message
	 * @param int messageID - The unique identifier for this message
	 */
	public NetMessage(int typeID, int messageID) {
		this.typeID = typeID;
		this.messageID = messageID;
	}
	/*
	 * Convert this network message to a string containing its type and unique
	 * identifiers
	 * @param String - This network message's string
	 */
	@Override
	public String toString() {
		return "NetMessage(" + Integer.toString(typeID) + ","
				+ Integer.toString(messageID) + ")";
	}
	
	/*
	 * Get the type identifier of this network message
	 * @return int - The type identifier of this network message
	 */
	public int getTypeID() {
		return typeID;
	}
	/*
	 * Get the unique identifier of this network message
	 * @return int - The unique identifier of this network message
	 */
	public int getMessageID() {
		return messageID;
	}
	/*
	 * Get the identifier of the network connection which received this message
	 * @return int - The receiver's identifier
	 */
	public int getReceiverID() {
		return receiverID;
	}
	/*
	 * Set the identifier of the network connection which received this message
	 * @param int receiverID - The receiver's identifier
	 */
	public void setReceiverID(int receiverID) {
		this.receiverID = receiverID;
	}

}
