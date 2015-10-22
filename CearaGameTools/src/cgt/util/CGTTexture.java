package cgt.util;

import java.io.FileNotFoundException;
import java.io.Serializable;

import cgt.unit.LabelID;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CGTTexture {
	private CGTFile file;
	private Texture textureGDX;
	private TextureRegion textureRegion;

	public CGTTexture() {
		file = null;
		textureGDX = null;
		textureRegion = null;
	}
	
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
}
 
