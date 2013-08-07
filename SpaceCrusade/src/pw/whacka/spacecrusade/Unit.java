package pw.whacka.spacecrusade;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Unit extends Graphic {

	private Array<Ammo> ammo;
	public Iterator<Ammo> ammoIterator;


	public Unit() {
		super(sprite = "game/heli.png", FRAME_ROWS = 1, FRAME_COLS = 5,
				animationPeriod = 0.020f, posX = 400 - width / 2,
				posY = 240 - height / 2, width = 128, height = 50, rotation);

		ammo = new Array<Ammo>();
		ammoIterator = ammo.iterator();
	}

	public Unit(String spriteSheet, float angle) {
		super(sprite = spriteSheet, FRAME_ROWS = 1, FRAME_COLS = 5,
				animationPeriod = 0.020f, posX = 400 - width / 2,
				posY = 240 - height / 2, width = 128, height = 50, rotation = angle);

		ammo = new Array<Ammo>();
		ammoIterator = ammo.iterator();

	}

	public void dispose() {
		super.dispose();
	}

	public void moveUnit() {

		float x = Gdx.input.getX();
		float y = Gdx.input.getY();
		float dx = Gdx.input.getDeltaX();
		float dy = Gdx.input.getDeltaY();

		// Input left to shapeArray. (Backwards)
		if ( shape.x < x ) {
			shape.x += dx;
			if (rotation > -15.0f) rotation -= 1.0f;
		} else if (shape.x > x) {
			shape.x += dx;
		if (rotation < 15.0f) rotation += 1.0f;
		// Input above to shapeArray
		}
		if (shape.y > y) {
			shape.y -= dy;
		} else if (shape.y < y) {
			shape.y -= dy;
		}
	}
	public void moveUnit(Circle touchPos) {

		// Input left to shapeArray. (Backwards)
		if ( shape.x < touchPos.x ) {
			shape.x += 250 * Gdx.graphics.getDeltaTime();
		} else if (shape.x > touchPos.x) {
			shape.x -= 250 * Gdx.graphics.getDeltaTime();
		}
		if (shape.y > touchPos.y) {
			shape.y -= 250 * Gdx.graphics.getDeltaTime();
		} else if (shape.y < touchPos.y) {
			shape.y += 250 * Gdx.graphics.getDeltaTime();
		}
	}

	// Cada arma en su propia clase, 2 ataques con cada arma.
	public void fire(Rectangle helicopter) {
		Ammo next = new Ammo(helicopter.x + 70, helicopter.y + 30);
		ammo.add(next);
	}

	public void fireSpecial(Rectangle helicopter) {
		Ammo next = new Ammo(helicopter.x + 70, helicopter.y + 25, rotation);
		ammo.add(next);
		next = new Ammo(helicopter.x + 70, helicopter.y + 30, rotation);
		ammo.add(next);
		next = new Ammo(helicopter.x + 70, helicopter.y + 35, rotation);
		ammo.add(next);
		stateTime = TimeUtils.nanoTime();
	}

	/**
	 * @return the ammo
	 */
	public Array<Ammo> getAmmo() {
		return ammo;
	}

	/**
	 * @param ammo
	 *            the ammo to set
	 */
	public void setMissiles(Array<Ammo> ammos) {
		this.ammo = ammos;
	}

}
