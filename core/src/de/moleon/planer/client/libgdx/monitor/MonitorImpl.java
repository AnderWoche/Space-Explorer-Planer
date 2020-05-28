package de.moleon.planer.client.libgdx.monitor;

import java.nio.ByteBuffer;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import de.moleon.planer.client.libgdx.PixelChangeListener;
import de.moleon.planer.client.libgdx.monitor.sections.MonitorSection;

/**
 * @author David Humann (Moldiy)
 */
public class MonitorImpl extends Monitior {
	
	private Pool<ByteBuffer> byteBufferPool;
	
	private Array<PixelChangeListener> pixelChangeListenerArray = new Array<>();
	
	private Pool<MonitorSection> monitorSectionPool;
	private HashMap<Long, MonitorSection> loadedSections = new HashMap<>();
	
	public MonitorImpl() {
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
	}
	
	public MonitorSection getOrLoadMonitorSectionFromPixelCords(int pixelX, int pixelY) {
		int[] field = MonitorSection.GRID.getField(pixelX, pixelY);
		long sectionCord = this.convertToLongID(field[0], field[1]);
		
		MonitorSection monitorSection = this.loadedSections.get(sectionCord);
		
		if(monitorSection == null) {
			monitorSection = monitorSectionPool.obtain();
			monitorSection.laodSection(pixelX, pixelY);
			this.loadedSections.put(sectionCord, monitorSection);
		}
		
		return monitorSection;
	}
	
	public MonitorSection getOrLoadMonitorSectionFromField(int fieldX, int fieldY) {
		int[] field = MonitorSection.GRID.getField(fieldX, fieldY);
		long sectionCord = this.convertToLongID(field[0], field[1]);
		
		MonitorSection monitorSection = this.loadedSections.get(sectionCord);
		
		if(monitorSection == null) {
			monitorSection = monitorSectionPool.obtain();
			monitorSection.laodSectionFromField(fieldX, fieldY);
			this.loadedSections.put(sectionCord, monitorSection);
		}
		
		return monitorSection;
	}
	
	@Override
	public synchronized void setPixel(int x, int y, Color color) {
		MonitorSection monitorSection = this.getOrLoadMonitorSectionFromPixelCords(x, y);
		monitorSection.setPixel(x, y, color);
	}
	
	@Override
	public void setPixel(long xy, Color color) {
		int[] cords = this.convertToIntID(xy);
		MonitorSection monitorSection = this.getOrLoadMonitorSectionFromPixelCords(cords[0], cords[1]);
		monitorSection.setPixel(cords[0], cords[1], color);
	}
	
	public void setPixelWithNotifyListener(int x, int y, Color color) {
		this.setPixel(x, y, color);
		this.notifyPixelChangedListener(this.convertToLongID(x, y), color);
	}
	
	public void setPixelWithNotifyListener(long xy, Color color) {
		this.setPixel(xy, color);
		this.notifyPixelChangedListener(xy, color);
	}

	@Override
	public void registerChangeListener(PixelChangeListener pixelChangeListener) {
		this.pixelChangeListenerArray.add(pixelChangeListener);
	}
	
	private synchronized void notifyPixelChangedListener(long xy, Color color) {
		for(PixelChangeListener pixelChangeListener : this.pixelChangeListenerArray) {
			pixelChangeListener.pixelChanged(xy, color);
		}
	}

	@Override
	public Color getPixel(int x, int y) {
		MonitorSection monitorSection = this.getOrLoadMonitorSectionFromPixelCords(x, y);
		Color color = monitorSection.getPixel(x, y);
		
		if(color == null) {
			return Color.WHITE;
		}
		
		return color;
	}
	
	@Override
	public Color getPixel(long xy) {
		int[] intCords = this.convertToIntID(xy);
		MonitorSection monitorSection = this.getOrLoadMonitorSectionFromPixelCords(intCords[0], intCords[1]);
		Color color = monitorSection.getPixel(intCords[0], intCords[1]);
		
		if(color == null) {
			return Color.WHITE;
		}
		
		return color;
	}
	
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

}
