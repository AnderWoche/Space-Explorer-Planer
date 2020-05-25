package de.moleon.planer.client.libgdx;

import java.util.Set;
import java.nio.ByteBuffer;
import java.util.Map.Entry;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class SpaceExplorerPlaner extends ApplicationAdapter {
	
	private MonitorImpl monitior;
	
	private OrthographicCamera camera;
	
	private ShapeRenderer shapeRenderer;
	
	private ByteBuffer buffer = ByteBuffer.allocate(8);
	
	@Override
	public void create () {
		this.monitior = new MonitorImpl();
		this.camera = new OrthographicCamera();
		this.shapeRenderer = new ShapeRenderer();
		
		this.camera.position.set(0, 0, 0);
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.shapeRenderer.setProjectionMatrix(this.camera.combined);
		this.shapeRenderer.begin(ShapeType.Filled);
		
		Set<Entry<Long, Color>> pixels = this.monitior.getAllPixels();
		for(Entry<Long, Color> cordsAndColor : pixels) {
			long longCord = cordsAndColor.getKey();
			Color color = cordsAndColor.getValue();
			
			this.shapeRenderer.setColor(color);
			
			this.buffer.rewind();
			this.buffer.putLong(longCord);
			this.buffer.rewind();
			
			this.shapeRenderer.rect(this.buffer.getInt(), this.buffer.getInt(), 1, 1);
		}
		shapeRenderer.end();
	}
	
	@Override
	public void resize(int width, int height) {
		this.camera.viewportWidth = width;
		this.camera.viewportHeight = height;
		this.camera.update();
		super.resize(width, height);
	}
	
	@Override
	public void dispose () {
	}
	
	public static SpaceExplorerPlaner getMyGdxGame() {
		return (SpaceExplorerPlaner) Gdx.app.getApplicationListener();
	}
	
	public Monitior getMonitior() {
		return monitior;
	}
}
