package de.moleon.planer.client.network.traffic;

import java.io.IOException;

import de.moleon.planer.client.network.supers.ServerConnection;

/**
 * Copyright © 2020 Leon Rüsing - All Rights Reserved
 */
public class TCPListener implements Runnable {

	private ServerConnection serverConnection;
	public Thread thread;

	private boolean running;

	public TCPListener(ServerConnection serverConnection) {
		this.serverConnection = serverConnection;
	}

	/**
	 * This methods waiting for incoming networktraffic
	 */
	@Override
	public void run() {
		this.running = true;
		
		while (this.running) {
			if (this.serverConnection.isConnected()) {
				try {
					String[] in = this.serverConnection.getDis().readUTF().split(" ");
					
					if(in[0].equals("SET")) {
						for (int i = 1; i < in.length; i++) {
							/*
							int x = Integer.parseInt(in[i++]);
							int y = Integer.parseInt(in[i++]);
							int color = Integer.parseInt(in[i]);					
							
							switch (color) {
							case 0:
								break;
							case 1:
								break;
							default:
								break;
							}
							*/
						}
					}
					
				} catch (IOException e) {
					this.serverConnection.error();
				}
			}
		}
	}
}
