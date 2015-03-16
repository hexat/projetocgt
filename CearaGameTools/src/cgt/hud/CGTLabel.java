package cgt.hud;

import java.io.Serializable;

import cgt.util.CGTFile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class CGTLabel extends HUDComponent implements Serializable {
	private String text;
	private int size;
	private CGTFile file;
	private int color;
	private Label label;

	public CGTLabel() {
		this.file = null;
		this.text = "";
		size = 10;
		color = 255;
	}
	
	public CGTLabel (String file, String text){
		this.file = new CGTFile(file);
		this.text = text;
		size = 10;
		color = 255;
	}
	
	public CGTLabel(String file, String text, int size){
		this.file = new CGTFile(file);
		this.text = text;
		this.size = size;
		color = 1;
	}
	
	public CGTLabel(String file,String text,int size, int color) {
		this.file = new CGTFile(file);
		this.text = text;
		this.size = size;
		this.color = color;
	}
	
	public Label getLabelGDX(){
		if(label == null){
			BitmapFont font12;
			if (file != null) {
				FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
						file.getFileHandle());
				FreeTypeFontParameter parameter = new FreeTypeFontParameter();
				parameter.size = size/10;
				font12 = generator.generateFont(parameter);
				generator.dispose();
			} else {
				font12 = new BitmapFont();
				font12.setScale(size/10.0f);
			}
			
			LabelStyle style = new LabelStyle(font12, new Color(color));
			label = new Label(text,style);
		}
		return label;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public CGTFile getFile() {
		return file;
	}

	public void setFile(CGTFile file) {
		this.file = file;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getColorValue() {
		return color;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public void setup(){
		super.setup();
		
		label.setX(this.getX());
		label.setY(this.getY());
		
		label.setWidth(this.getWidth());
		label.setHeight(this.getHeight());
	}
	
	public void draw(Batch batch, float parentAlpha){
		label.draw(batch, parentAlpha);
	}
}

