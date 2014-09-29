package cgt.HUD;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class CGTLabel extends HUDComponent {
	private Label label;

	public CGTLabel(Label label) {
		this.label = label;
		// TODO Auto-generated constructor stub
	}
	
	public void autosize(){
		super.autosize();
		
		label.setX(this.getX());
		label.setY(this.getY());
		
		label.setWidth(this.getWidth());
		label.setHeight(this.getHeight());
		
		

	}
	
	public void draw(Batch batch, float parentAlpha){
		label.draw(batch, parentAlpha);
	}
}

