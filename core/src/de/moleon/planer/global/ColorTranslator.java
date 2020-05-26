package de.moleon.planer.global;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

/**
 * Copyright © 2020 Leon Rüsing - All Rights Reserved
 */
public class ColorTranslator {
	
	private static final ColorTranslator instance = new ColorTranslator();
	
	private ArrayList<ColorEntry> entrys = new ArrayList<ColorEntry>();
	
	private ColorTranslator() {
		
	}
	
	/**
	 * This method loads the color entrys
	 */
	public void load() {
		new ColorEntry(Color.WHITE, 0);
		new ColorEntry(Color.BLACK, 1);
	}
	
	/**
	 * This method returns an id by color
	 */
	public int getIDByColor(Color color) {
		for (int i = 0; i < entrys.size(); i++) {
			ColorEntry colorEntry = entrys.get(i);
			
			if(colorEntry.getColor() == color) {
				return colorEntry.getId();
			}
		}
		
		return -1;
	}
	
	/**
	 * This method returns a color by id
	 */
	public Color getColorByID(int id) {
		for (int i = 0; i < entrys.size(); i++) {
			ColorEntry colorEntry = entrys.get(i);
			
			if(colorEntry.getId() == id) {
				return colorEntry.getColor();
			}
		}
		
		return null;
	}
	
	/**
	 * This method returns the global instance of 'ColorTranslator'
	 */
	public static ColorTranslator getInstance() {
		return instance;
	}
}
