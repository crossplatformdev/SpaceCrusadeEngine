package pw.whacka.spacecrusade;

import com.badlogic.gdx.ApplicationListener;

public class Application implements ApplicationListener {

	private Game game;

	public void create() {
		game = new Game();
	}

	public void dispose() {
		game.dispose();
	}

	public void render() {
		game.render();
	}

	public void resize(int width, int height) {
	}

	public void pause() {
	}

	public void resume() {
	}
}
