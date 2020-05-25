package de.moleon.planer.client.network.traffic;

import java.io.IOException;

import com.badlogic.gdx.graphics.Color;

import de.moleon.planer.client.libgdx.monitor.PixelChangeListener;
import de.moleon.planer.client.network.supers.ServerConnection;

/**
 * Copyright © 2020 Leon Rüsing - All Rights Reserved
 */
public class TCPSender {
	
	private ServerConnection serverConnection;
	private PixelChangeListener pixelChangeListener = new PixelChangeListener() {

		@Override
		public void pixelChanged(int x, int y, Color color) {
			send("SET " + x + " " + y + " " + color);
		}
	};
	
	public TCPSender(ServerConnection serverConnection) {
		this.serverConnection = serverConnection;
	}
	
	/**
	 * This method can send strings to server
	 */
	public void send(String string) {
		if(this.serverConnection.isConnected()) {
			try {
				this.serverConnection.getDos().writeUTF(string);
				this.serverConnection.getDos().flush();
			} catch (IOException e) {
				this.serverConnection.error();
			}
		}
	}
	
	public PixelChangeListener getPixelChangeListener() {
		return pixelChangeListener;
	}
}
