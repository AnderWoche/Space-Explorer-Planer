package de.moleon.planer.server.supers;

import java.io.IOException;
import java.net.ServerSocket;

import de.moleon.planer.server.listeners.ConnectionListener;

/**
 * Copyright � 2020 Leon R�sing - All Rights Reserved
 */
public class Server {
	
	private ServerSocket serverSocket;
	private ConnectionListener connectionListener;
	
	private int port;
	
	public Server(int port) {
		this.port = port;
	}
	
	/**
	 * This method starts the server
	 */
	public void start() {
		try {
			this.serverSocket = new ServerSocket(this.port);
			
			this.connectionListener = new ConnectionListener(this.serverSocket);
			this.connectionListener.thread = new Thread(this.connectionListener);
			this.connectionListener.thread.start();
			
			System.out.println("[Server] The server has started!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
}
