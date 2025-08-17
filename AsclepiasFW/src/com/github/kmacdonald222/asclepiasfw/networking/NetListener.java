/*
 * File:		NetListener.java
 * Author:		Keegan MacDonald (KMacDonald222)
 * Created:		2025.08.09
 * Purpose:		Defines an abstract interface for networking callback functions
 */

package com.github.kmacdonald222.asclepiasfw.networking;

// Abstract interface for networking callback functions
public interface NetListener {

	/*
	 * A network connection has successfully connected to a remote network
	 * connection
	 * @param int ID - The identifier of the newly connected network connection
	 * @return boolean - Whether the connection should be accepted
	 */
	public default boolean netConnected(int ID) {
		return true;
	}
	/*
	 * A network connection has received a new message from the remote
	 * connection
	 * @param NetMessage message - The message received
	 */
	public default void netMessageReceived(NetMessage message) {
	}
	/*
	 * A network connection has been disconnected from a remote network
	 * connection
	 * @param int ID - The identifier of the disconnected network connection
	 */
	public default void netDisconnected(int ID) {
	}
	
}
