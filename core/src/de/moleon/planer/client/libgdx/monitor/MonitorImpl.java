package de.moleon.planer.client.libgdx.monitor;

import java.nio.ByteBuffer;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.moleon.planer.client.libgdx.PixelChangeListener;
import de.moleon.planer.client.libgdx.monitor.sections.MonitorSection;
import de.moleon.planer.utils.SimpleGrid;

/**
 * @author David Humann (Moldiy)
 */
public class MonitorImpl extends Stage {
	
	private Pool<ByteBuffer> byteBufferPool;
	
	private Array<PixelChangeListener> pixelChangeListenerArray = new Array<>();
	
	private Pool<MonitorSection> monitorSectionPool;
	private HashMap<Long, MonitorSection> loadedSections = new HashMap<>();

	public static final Texture img = new Texture(Gdx.files.internal("badlogic.jpg"));

	InputMultiplexer inputMultiplexer = new InputMultiplexer();

	public MonitorImpl(Viewport viewport) {
		super(viewport);

		this.monitorSectionPool = new Pool<MonitorSection>() {
			protected MonitorSection newObject() {
				return new MonitorSection();
			}
		};
		this.byteBufferPool = new Pool<ByteBuffer>() {
			@Override
			protected ByteBuffer newObject() {
				return ByteBuffer.allocate(8);
			}
		};

		this.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println(x + " : " + y);
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		inputMultiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

//	public MonitorSection getOrLoadMonitorSectionFromPixelCords(int pixelX, int pixelY) {
//		int[] field = MonitorSection.GRID.getField(pixelX, pixelY);
//		long sectionCord = this.convertToLongID(field[0], field[1]);
//
//		MonitorSection monitorSection = this.loadedSections.get(sectionCord);
//
//		if(monitorSection == null) {
//			monitorSection = monitorSectionPool.obtain();
//			super.addActor(monitorSection);
//			monitorSection.laodSection(pixelX, pixelY);
//			this.loadedSections.put(sectionCord, monitorSection);
//		}
//
//		return monitorSection;
//	}
	
//	public MonitorSection getOrLoadMonitorSectionFromField(int fieldX, int fieldY) {
//		int[] field = MonitorSection.GRID.getField(fieldX, fieldY);
//		long sectionCord = this.convertToLongID(field[0], field[1]);
//
//		MonitorSection monitorSection = this.loadedSections.get(sectionCord);
//
//		if(monitorSection == null) {
//			monitorSection = monitorSectionPool.obtain();
//			monitorSection.laodSectionFromField(fieldX, fieldY);
//			this.loadedSections.put(sectionCord, monitorSection);
//		}
//
//		return monitorSection;
//	}
	
//	public synchronized void setPixel(int x, int y, Color color) {
//		MonitorSection monitorSection = this.getOrLoadMonitorSectionFromPixelCords(x, y);
//		monitorSection.setPixel(x, y, color);
//	}
//
//	public void setPixel(long xy, Color color) {
//		int[] cords = this.convertToIntID(xy);
//		MonitorSection monitorSection = this.getOrLoadMonitorSectionFromPixelCords(cords[0], cords[1]);
//		monitorSection.setPixel(cords[0], cords[1], color);
//	}
	
//	public void setPixelWithNotifyListener(int x, int y, Color color) {
//		this.setPixel(x, y, color);
//		this.notifyPixelChangedListener(this.convertToLongID(x, y), color);
//	}
//
//	public void setPixelWithNotifyListener(long xy, Color color) {
//		this.setPixel(xy, color);
//		this.notifyPixelChangedListener(xy, color);
//	}

	public void registerChangeListener(PixelChangeListener pixelChangeListener) {
		this.pixelChangeListenerArray.add(pixelChangeListener);
	}
	
	private synchronized void notifyPixelChangedListener(long xy, Color color) {
		for(PixelChangeListener pixelChangeListener : this.pixelChangeListenerArray) {
			pixelChangeListener.pixelChanged(xy, color);
		}
	}

//	public Color getPixel(int x, int y) {
//		MonitorSection monitorSection = this.getOrLoadMonitorSectionFromPixelCords(x, y);
//		Color color = monitorSection.getPixel(x, y);
//
//		if(color == null) {
//			return Color.WHITE;
//		}
//
//		return color;
//	}
	
//	public Color getPixel(long xy) {
//		int[] intCords = this.convertToIntID(xy);
//		MonitorSection monitorSection = this.getOrLoadMonitorSectionFromPixelCords(intCords[0], intCords[1]);
//		Color color = monitorSection.getPixel(intCords[0], intCords[1]);
//
//		if(color == null) {
//			return Color.WHITE;
//		}
//
//		return color;
//	}
	
	public long convertToLongID(int x, int y) {
		ByteBuffer buffer = this.byteBufferPool.obtain();
		buffer.rewind();
		buffer.putInt(x);
		buffer.putInt(y);
		
		buffer.rewind();
		
		long cordinates = buffer.getLong();
		
		this.byteBufferPool.free(buffer);
		
		return cordinates;
	}
	
	public int[] convertToIntID(long cord) {
		ByteBuffer buffer = this.byteBufferPool.obtain();
		buffer.rewind();
		buffer.putLong(cord);
		
		buffer.rewind();
		
		int cordinatesX = buffer.getInt();
		int cordinatesY = buffer.getInt();
		
		this.byteBufferPool.free(buffer);
		
		return new int[] {cordinatesX, cordinatesY};
	}
	
	/**
	 * @return the byteBufferPool
	 */
	public Pool<ByteBuffer> getByteBufferPool() {
		return this.byteBufferPool;
	}
	
	/**
	 * @return the loadedSections
	 */
	public HashMap<Long, MonitorSection> getLoadedSections() {
		return loadedSections;
	}

	public void cameraPosChanged() {
		Camera camera = super.getCamera();
		int[] gridXY = MonitorSection.GRID.getField(camera.position.x, camera.position.y);
		Array<int[]> cords = MonitorSection.GRID.getFieldsAround(gridXY[0] - 1, gridXY[1] - 1, 2);

		super.clear();

		float width = MonitorSection.GRID.fieldWidth;
		float height = MonitorSection.GRID.fieldHeight;

		for(int[] xy : cords) {
			MonitorSection image = new MonitorSection();
			image.setSize(width, height);
			image.setPosition(xy[0] * width, xy[1] * height);
			this.inputMultiplexer.addProcessor(image);
			super.addActor(image);
		}

//		for(int addX = -Gdx.graphics.getWidth() / 2; addX < Gdx.graphics.getWidth() / 2; addX += 400) {
//			for(int addY = -Gdx.graphics.getHeight() / 2; addY < Gdx.graphics.getHeight() / 2; addY += 400) {
//				Image image = new Image(new TextureRegion(new Texture(Gdx.files.internal("badlogic.jpg"))));
//				image.setSize(400, 400);
//				image.
//				super.addActor(image);
//			}
//		}
	}

	float time = 0F;

	@Override
	public void act(float delta) {
		time += delta;
		if(time > 0.25F) {
			time = 0F;
			cameraPosChanged();
		}
		super.act(delta);
	}

	private final Vector2 camPos = new Vector2();
	private final Vector2 pressedPoint = new Vector2();

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {

			OrthographicCamera camera = (OrthographicCamera) super.getCamera();

			this.pressedPoint.set(screenX * camera.zoom, screenY * camera.zoom);

			this.camPos.set(camera.position.x, camera.position.y);

		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {

			Vector2 vecLenght = Pools.obtain(Vector2.class);
			vecLenght.set(pressedPoint.x, pressedPoint.y);

			OrthographicCamera camera = (OrthographicCamera) super.getCamera();

			vecLenght.sub(screenX * camera.zoom, screenY * camera.zoom);

			float x = (this.camPos.x + vecLenght.x);
			float y = this.camPos.y + -vecLenght.y;

			camera.position.set(x, y, 0);
			camera.update();

			Pools.free(vecLenght);
		}
		return false;
	}

	public boolean scrolled(int amount) {
		OrthographicCamera camera = (OrthographicCamera) super.getCamera();
		if (amount == 1) {
			if (camera.zoom < 3) {
				camera.zoom += 0.03F;
				camera.update();
			}
		} else if (amount == -1) {
			if (camera.zoom > 1) {
				camera.zoom -= 0.03F;
				camera.update();
			}
		}
		return false;
	}
}
