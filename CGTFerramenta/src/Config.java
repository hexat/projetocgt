import cgt.CGTGameWorld;
import cgt.controller.ConfigGameObjectController;
import cgt.controller.ConfigPersonagemController;
import cgt.controller.ConfigWorldController;
import cgt.util.CGTTexture;


public class Config {
	private CGTGameWorld game;
	private ConfigWorldController world;
	private ConfigPersonagemController actor;
	private ConfigGameObjectController gameObj;
	
	
	
	public Config() {
		game = CGTGameWorld.getInstance();
		game.setBackground(new CGTTexture(world.getTextTxtProcurarBack()));
		
	}
	
	
	

}
