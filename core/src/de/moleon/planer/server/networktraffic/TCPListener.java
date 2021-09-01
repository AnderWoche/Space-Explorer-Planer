package de.moleon.planer.server.networktraffic;

import java.io.IOException;

import de.moleon.planer.server.supers.ClientConnection;

/**
 * Copyright � 2020 Leon R�sing - All Rights Reserved
 */
public class TCPListener implements Runnable {
	
	private ClientConnection clientConnection;
	public Thread thread;

	public boolean running;

	public TCPListener(ClientConnection clientConnection) {
		this.clientConnection = clientConnection;
	}

	/**
	 * This methods waiting for incoming networktraffic
	 */
	@Override
	public void run() {
		this.running = true;
		
		while (this.running) {
			if (this.clientConnection.isConnected()) {
				try {
					String[] in = this.clientConnection.getDis().readUTF().split(" ");
					
					if(in[0].equals("SET")) {
						for (int i = 1; i < in.length; i++) {
							long xy = Long.parseLong(in[i]); i++;
							int color = Integer.parseInt(in[i]);					
							
							for (int j = 0; j < ClientConnection.list.size(); j++) {
								ClientConnection cc = ClientConnection.list.get(j);
								
								if(cc.isConnected()) {
									cc.getTcpSender().send("SET " + xy + " " + color);
								}
							}
						}
					}
					
				} catch (IOException e) {
					this.clientConnection.error();
				} catch(NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
