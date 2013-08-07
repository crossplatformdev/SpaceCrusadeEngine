package pw.whacka.spacecrusade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class SelectionHandler extends Graphic {

	public InputHandler inputHandler;
	public Array<Unit> selection;
	public boolean thereIsSelection;
	
	private Vector3 position;
	private Vector3 destination;

	public SelectionHandler() {
		super(SelectionHandler.sprite = "hud/cursor_normal.png",
				SelectionHandler.FRAME_ROWS = 1,
				SelectionHandler.FRAME_COLS = 1,
				SelectionHandler.animationPeriod = 0.0f,
				SelectionHandler.posX = Gdx.input.getX(),
				SelectionHandler.posY = Gdx.input.getY(),
				SelectionHandler.width = 10,
				SelectionHandler.height = 10,
				SelectionHandler.rotation = 35
			);
		inputHandler = new InputHandler();
		selection = new Array<Unit>();
		thereIsSelection = false;
		position = new Vector3();	
		destination =  new Vector3();
		
		
		shape.x = Gdx.input.getX();
		shape.y = Gdx.input.getY();

		shape.x = Gdx.input.getX();
		shape.y = 480 - Gdx.input.getY();
		shape.width = 10;
		shape.height = 10; 
	}

	public SelectionHandler(InputHandler input, Array<Unit> unitArray) {
		inputHandler = input;
		selection = unitArray;
		thereIsSelection = false;
	}

	public Graphic select(Unit grafico) {
		onSelect();
		thereIsSelection = true;
		return grafico;		
	}
	public void selectUnits (Array<Unit> unitArray) {
		for (Unit unit : unitArray){							// Por cada unidad de un jugador			
			if (inputHandler.isHit == false){						// y Mantienes apretado el click
				if (shape.overlaps(unit.shape)){	// que se solape con la selection
					selection.add(unit);				// añadir unidad a la selection
					sound.play();
					
				}
			}
		}
		thereIsSelection = true;
	}
	public void unSelect(Unit unit){
		if (inputHandler.isTouched()){
			if (!(shape.overlaps(unit.shape))){
					selection.removeValue(unit, true);				
			}
			thereIsSelection = false;		
		}
	}

	private void onSelect() {
		// TODO Auto-generated method stub
		
	}

	public void getInput(Array<Unit> unitArray) {
		
		
	}
}
