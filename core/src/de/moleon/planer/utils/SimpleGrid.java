package de.moleon.planer.utils;

import com.badlogic.gdx.utils.Array;

/**
 * 
 * @author Moldiy (Humann)
 *
 */
public class SimpleGrid {

	public int fieldWidth = 100;
	public int fieldHeight = 100;

	public SimpleGrid() {

	}

	public void setFieldSize(int fieldWidth, int fieldHeight) {
		this.fieldWidth = fieldWidth;
		this.fieldHeight = fieldHeight;
	}

	public int[] getField(float x, float y) {
		int[] cords = new int[2];
		int rest = (int) (x % fieldWidth);
		if (rest < 0) {
			cords[0] = (int) (x / fieldWidth);
		} else {
			cords[0] = (int) (x / fieldWidth + 1);
		}
		rest = (int) (y % fieldHeight);
		if (rest < 0) {
			cords[1] = (int) (y / fieldHeight);
		} else {
			cords[1] = (int) (y / fieldHeight + 1);
		}
		return cords;
	}
	
	public float[] getWorldPosition(int[] field) {
		return new float[] { ((field[0] * fieldWidth) - fieldWidth), ((field[1] * fieldHeight) - fieldHeight) };
	}
	
	public float[] getFieldWorldPositionFromWorldPosition(float x, float y) {
		return this.getWorldPosition(this.getField(x, y));
	}

	public Array<int[]> getFieldsAround(int fieldX, int fieldY, int raduis) {
		Array<int[]> returnCords = new Array<int[]>();

		for (int regionToCheckX = -raduis; regionToCheckX <= raduis; regionToCheckX++) {
			for (int regionToCheckY = -raduis; regionToCheckY <= raduis; regionToCheckY++) {

				int[] cords = new int[2];
				cords[0] = fieldX + regionToCheckX;
				cords[1] = fieldY + regionToCheckY;

				returnCords.add(cords);
			}
		}
		return returnCords;
	}
	
	public Array<int[]> getFieldsAroundToFillScreen(float x, float y, int screenWidth, int screenHeight) {
		int raduis = (screenWidth / this.fieldWidth) / 2;
		int raduisHeight = (screenHeight / this.fieldHeight) / 2;
		if(raduisHeight > raduis) raduis = raduisHeight;
		
		int[] fieldCords = this.getField(x, y);
		
		return getFieldsAround(fieldCords[0], fieldCords[1], raduis);
	}


}
