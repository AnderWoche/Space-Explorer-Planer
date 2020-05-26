package de.moleon.planer.client.libgdx;

import com.badlogic.gdx.graphics.Color;

public interface PixelChangeListener {
	
	/**
	 * This method will call when a pixel is changed
	 */
	public void pixelChanged(long xy, Color color);
}
