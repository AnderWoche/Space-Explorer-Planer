package de.moleon.planer.server.listeners;

import java.io.IOException;
import java.net.ServerSocket;

import de.moleon.planer.server.supers.ClientConnection;

/**
 * Copyright � 2020 Leon R�sing - All Rights Reserved
 */
public class ConnectionListener implements Runnable {
	
	private ServerSocket serverSocket;
	public Thread thread;
	
	private boolean running;
	
	public ConnectionListener(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	/**
	 * This method is waiting for connections
	 */
	@Override
	public void run() {
		this.running = true;
		
		while(this.running) {
			try {
				new ClientConnection(this.serverSocket.accept());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
