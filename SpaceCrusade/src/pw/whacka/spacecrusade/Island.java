package pw.whacka.spacecrusade;

import com.gdxuser.util.MeshHelper;

public class Island extends MeshHelper {

	public Island(){
		load();
	}
	
	public Island load(){
		load("data/3d/lily.obj");
		return this;
	}
	
}
