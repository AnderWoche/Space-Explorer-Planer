package de.moleon.planer.client.network.traffic;

import java.io.IOException;

import com.badlogic.gdx.graphics.Color;

import de.moleon.planer.client.network.supers.ServerConnection;
import de.moleon.planer.global.ColorTranslator;

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
							long xy = Long.parseLong(in[i++]);
							int colorID = Integer.parseInt(in[i]);					
							
							Color color = ColorTranslator.getInstance().getColorByID(colorID);
							
							// Set the pixel here
						}
					}
					
				} catch (IOException e) {
					this.serverConnection.error();
				}
			}
		}
	}
}
