package de.moleon.planer.server.networktraffic;

import java.io.IOException;

import de.moleon.planer.server.supers.ClientConnection;

/**
 * Copyright � 2020 Leon R�sing - All Rights Reserved
 */
public class TCPSender {
	
	private ClientConnection clientConnection;
	//private StringBuilder stringBuilder;

	public TCPSender(ClientConnection clientConnection) {
		this.clientConnection = clientConnection;
	}
	
	/**
	 * This method can send a string to client
	 */
	public void send(String string) {
		if(this.clientConnection.isConnected()) {
			try {
				this.clientConnection.getDos().writeUTF(string);
				this.clientConnection.getDos().flush();
			} catch (IOException e) {
				this.clientConnection.error();
			}
		}
	}
}
