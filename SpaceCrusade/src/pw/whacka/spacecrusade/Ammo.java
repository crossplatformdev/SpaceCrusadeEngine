package pw.whacka.spacecrusade;

import com.badlogic.gdx.Gdx;

public class Ammo extends Graphic {

	public Ammo() {
		super( sprite = "game/misil.png", FRAME_ROWS = 3, FRAME_COLS = 1,
				animationPeriod = 0.033f, posX = 801, posY = 0,
				width = 21, height = 7, rotation = 0);
	}

	public Ammo(float x, float y) {
		super(sprite = "game/misil.png", FRAME_ROWS = 3, FRAME_COLS = 1,
				animationPeriod = 0.033f, posX = x, posY = y,
				width = 21, height = 7, rotation = 0);
	}

	public Ammo(float x, float y, float angle) {
		super(sprite = "game/misil.png", FRAME_ROWS = 3, FRAME_COLS = 1,
				animationPeriod = 0.033f, posX = x, posY = y,
				width = 21, height = 7, rotation = angle);
	}

	public void moveMissile() {
		shape.x += 750 * Gdx.graphics.getDeltaTime();
	}
}
