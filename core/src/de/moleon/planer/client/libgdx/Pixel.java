package de.moleon.planer.client.libgdx;

import com.badlogic.gdx.graphics.Color;

/**
 * @author David Humann (Moldiy)
 */
public class Pixel {

	private int x;
	private int y;
	
	private Color color;
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
}
