package de.moleon.planer.client.libgdx.monitor.sections;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool.Poolable;

import de.moleon.planer.client.libgdx.monitor.MonitorImpl;
import de.moleon.planer.utils.SimpleGrid;

/**
 * @author David Humann (Moldiy)
 */
public class MonitorSection extends Actor implements Poolable {

	public static SimpleGrid GRID = new SimpleGrid();

	public static final File SAVE_FILE = new File(System.getProperty("user.home") + "/Space Explorer Planer/");
	public static final File SECTIONS_FILE = new File(SAVE_FILE, "/sections/");

	static {
		GRID.setFieldSize(800, 800);
		if (!SAVE_FILE.exists()) {
			SAVE_FILE.mkdirs();
		}
		if (!SECTIONS_FILE.exists()) {
			SECTIONS_FILE.mkdirs();
		}
	}

	private int[] sectionPos;

	private final Color[][] colors = new Color[GRID.fieldWidth][GRID.fieldHeight];

	private Texture texture;

	private Pixmap pixmap;

	public MonitorSection() {
		super.setTouchable(Touchable.enabled);
		super.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println(x + " : " + y);
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println(x + " : " + y);
//				colors[(int)x][(int)y] = Color.BLACK;
				super.clicked(event, x, y);
			}

			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
//				System.out.println(x + " : " + y);
				return super.mouseMoved(event, x, y);
			}
		});
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (this.texture != null) {
			batch.draw(this.texture, super.getX(), super.getY());
		}
		batch.draw(MonitorImpl.img, super.getX(), super.getY(), super.getWidth(), super.getHeight());
		super.draw(batch, parentAlpha);
	}

	public void setPixel(int x, int y, Color color) {
//		int pixelX = (int) (x - this.worldPos[0]);
//		int pixelY = (int) (y - this.worldPos[1]);

//		this.colors[pixelX][pixelY] = color;
//
//		this.pixmap.setColor(color);
//		this.pixmap.drawPixel(pixelX, 400 - pixelY);
//
//		this.updateTexture();
	}

//	public Color getPixel(int x, int y) {
//		int pixelX = (int) (x - this.worldPos[0]);
//		int pixelY = (int) (y - this.worldPos[1]);

//		Color color = this.colors[pixelX][pixelY];
//		if (color == null)
//			return Color.WHITE;
//		return color;
//	}

	public Color[][] getPixels() {
		return this.colors;
	}

//	public void laodSection(int pixelX, int pixelY) {
//		this.sectionPos = MonitorSection.GRID.getField(pixelX, pixelY);
//		this.worldPos = MonitorSection.GRID.getWorldPosition(this.sectionPos);
//		File file = new File(SECTIONS_FILE, "/section." + sectionPos[0] + "." + sectionPos[1]);
//		if (file.exists()) {
//			this.pixmap = new Pixmap(Gdx.files.absolute(file.getAbsolutePath()));
//			// LOAD
//		} else {
//			this.pixmap = new Pixmap(400, 400, Format.RGBA8888);
//		}
//	}
	
//	public void laodSectionFromField(float fieldX, float fieldY) {
//		this.sectionPos = new int[] {(int) fieldX, (int) fieldY};
//		this.worldPos = MonitorSection.GRID.getWorldPosition(this.sectionPos);
//		File file = new File(SECTIONS_FILE, "/section." + sectionPos[0] + "." + sectionPos[1]);
//		if (file.exists()) {
//			this.pixmap = new Pixmap(Gdx.files.absolute(file.getAbsolutePath()));
//			// LOAD
//		} else {
//			this.pixmap = new Pixmap(400, 400, Format.RGBA8888);
//		}
//	}

	public void updateTexture() {
		if (this.texture != null) {
			this.texture.dispose();
		}
		this.texture = new Texture(this.pixmap);
	}

	/**
	 * @return the sectionPos
	 */
	public int[] getSectionPos() {
		return sectionPos;
	}

	@Override
	public void reset() {
		PixmapIO.writePNG(new FileHandle(new File(SECTIONS_FILE, "/section." + this.sectionPos[0] + "." + this.sectionPos[1])), this.pixmap);
		
		this.texture.dispose();
		this.pixmap.dispose();

		this.pixmap = null;
		this.texture = null;

		for (int x = 0; x < 400; x++) {
			for (int y = 0; y < 400; y++) {
				this.colors[x][y] = null;
			}
		}
	}

}
