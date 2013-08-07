package pw.whacka.spacecrusade;

import com.gdxuser.util.MeshHelper;

public class Boat extends MeshHelper {

	public Boat() {
		load();
	}

	public Boat load(){
		load("data/3d/boat.obj");
		return this;
	}
}
