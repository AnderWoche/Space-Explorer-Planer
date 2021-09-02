package de.moleon.planer.client.libgdx;

import java.util.Map;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pools;

import com.badlogic.gdx.utils.viewport.ExtendViewport;
import de.moleon.planer.client.libgdx.monitor.MonitorImpl;
import de.moleon.planer.client.libgdx.monitor.sections.MonitorSection;

public class SpaceExplorerPlaner extends Game {

	private final InputMultiplexer inputMultiplexer = new InputMultiplexer();

	private MonitorImpl monitor;
	private OrthographicCamera camera;

	@Override
	public void create() {
		this.monitor = new MonitorImpl(new ExtendViewport(1280, 720));

		this.camera = new OrthographicCamera();
		this.camera.position.set(0, 0, 0);

		this.monitor.getViewport().setCamera(this.camera);

//		ServerConnection serverConnection = new ServerConnection("catchadventure.ddns.net", 6334);
//		serverConnection.connect(this.monitior);

		Gdx.input.setInputProcessor(this.monitor);
	}

	private Vector2 lastPosition = new Vector2();
	private Vector2 calculateVec = new Vector2();

	private volatile Map.Entry<Long, Color>[] propertyEntries = null;

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClear(
				GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		Gdx.gl.glHint(GL20.GL_LINE_LOOP, GL20.GL_NICEST);

		this.monitor.act();
		this.monitor.draw();

		Vector3 vec = this.camera.unproject(Pools.obtain(Vector3.class).set(Gdx.input.getX(), Gdx.input.getY(), 0));
		int x = (int) vec.x;
		int y = (int) vec.y;

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			this.calculateVec.set(x, y);
			float length;
			do {
				this.calculateVec.sub(lastPosition);
				length = this.calculateVec.len();
				this.calculateVec.nor();
				this.calculateVec.scl(1.3F);

				lastPosition.add(calculateVec);

				this.calculateVec.set(x + this.calculateVec.x, y + this.calculateVec.y);

//				this.drawPixel((int) lastPosition.x, (int) lastPosition.y, Color.BLACK);

			} while (length > 2);

			// Color color = this.monitior.getPixel(x, y);

			// if(color == Color.WHITE) {
			// this.drawPixel(x, y, Color.BLACK);
			// }

		}
		this.lastPosition.set(x, y);
		Pools.free(vec);

		for(MonitorSection loadedSection : this.monitor.getLoadedSections().values()) {
//			if()
		}

	}

	private boolean containsSection(MonitorSection monitorSection) {
		for (MonitorSection loadedSection : this.monitor.getLoadedSections().values()) {
			if (loadedSection == monitorSection) {
				return true;
			}
		}
		return false;
	}

//	public void drawPixel(int x, int y, Color color) {
//		this.monitior.setPixelWithNotifyListener(x, y, Color.BLACK);
//	}

	@Override
	public void resize(int width, int height) {
		this.monitor.getViewport().update(width, height);
		this.camera.update();
		super.resize(width, height);
	}

	@Override
	public void dispose() {
	}

	public static SpaceExplorerPlaner getInstance() {
		return (SpaceExplorerPlaner) Gdx.app.getApplicationListener();
	}
}
