package de.moleon.client.planer;

import com.badlogic.gdx.graphics.Color;

public interface PixelChangeListener {
	
	/**
	 * This method will call when a pixel is changed
	 */
	public void pixelChanged(int x, int y, Color color);
}
