package cgt.util;

import java.io.FileNotFoundException;
import java.io.Serializable;

import cgt.unit.LabelID;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CGTTexture implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7131290727646321147L;
	private CGTFile file;
	private String filepath;
	private Texture textureGDX;
	private TextureRegion textureRegion;
	
	
	public CGTTexture(CGTFile file) {
		this.file = file;
	}
	
	public CGTTexture(String filepath) {
		file = new CGTFile(filepath);
	}

	public void setImage(String filepath){
		file = new CGTFile(filepath);
	}
	
	public void setImage(CGTFile file){
		this.file = file;
	}
	
	public CGTFile getFile() {
		return file;
	}
	
	@Override
	public String toString() {
		return "Texture [file=" + file + "]";
	}

	/**
	 * This method will be used only GDX
	 * @return
	 */
	public Texture getTextureGDX() {
		if (textureGDX == null) {
			textureGDX = new Texture(file.getFileHandle());
		}
		
		return textureGDX;
	}
	public TextureRegion getTextureRegion() {
		if (textureRegion == null) {
			textureRegion = new TextureRegion(getTextureGDX());
		}
		return textureRegion;
	}
	public void setTextureRegion( TextureRegion txtRegion ) {
		textureRegion = txtRegion;
	}
}
 
