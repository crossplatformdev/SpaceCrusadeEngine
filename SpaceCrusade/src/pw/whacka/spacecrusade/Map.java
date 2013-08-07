package pw.whacka.spacecrusade;

import com.badlogic.gdx.Gdx;

public class Map extends Graphic {
	
	Map () {
		super(Map.sprite = "game/background.png", Map.FRAME_ROWS = 1, Map.FRAME_COLS = 2,
				Map.animationPeriod = 0.50f, Map.posX = 0,
						Map.posY = 0, Map.width = 1400, Map.height = 480, Map.rotation = 0);
	}
	
	Map (String spriteSheet) {
		super(Map.sprite = spriteSheet, Map.FRAME_ROWS = 1,Map.FRAME_COLS = 2,
			Map.animationPeriod = 0.50f, Map.posX = 0,
			Map.posY = 0, Map.width = 1400, Map.height = 480, Map.rotation = 0);
	}


	public void advanceMap() {
		shape.x -= 200 * Gdx.graphics.getDeltaTime();
	}

}
