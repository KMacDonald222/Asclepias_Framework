package com.github.kmacdonald222.asclepiasfw.testnetworking;

import com.github.kmacdonald222.asclepiasfw.data.Vector2D;
import com.github.kmacdonald222.asclepiasfw.networking.NetMessage;

public class PositionMessage extends NetMessage {

	private static final long serialVersionUID = -7014060887266053520L;
	
	public double x = 0.0d;
	public double y = 0.0d;
	
	public PositionMessage(int messageID, Vector2D position) {
		super(NetMessageTypes.POSITION, messageID);
		x = position.x;
		y = position.y;
	}
	
	@Override
	public String toString() {
		return "PositionMessage(messageID=" + Integer.toString(messageID)
				+ ",x=" + Double.toString(x) + ",y=" + Double.toString(y) + ")";
	}
	
}
