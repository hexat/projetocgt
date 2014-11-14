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
	
	private float width;
	private float height;
	private String filepath;
	private Texture textureGDX;
	private TextureRegion textureRegion;
	
	
	public CGTTexture(CGTFile file) {
		this.file = file;
	}
	
	public CGTTexture(String filepath) {
		
		this.setFilepath(filepath);
		file = new CGTFile(filepath);
	}
	
	public CGTTexture(String filepath, LabelID label) throws FileNotFoundException {
	
		file = new CGTFile(filepath);
	}
	
	/*private void writeObject(java.io.ObjectOutputStream out)throws IOException{
	    out.writeObject(file);
	    out.writeObject(width);
	    out.writeObject(height);
	    ImageIO.write(image,"png",ImageIO.createImageOutputStream(out));
	}*/

//	public BufferedImage getBufferedImage (){
//		if (image == null) {
//			try {
//				image = ImageIO.read(file.getFile());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			width = getWidth();
//			height = getHeight();
//		}
//		return image;
//	}
	
	public void setImage(String filepath){
		file = new CGTFile(filepath);
	}
	
	public void setImage(CGTFile file){
		this.file = file;
	}
	
	public CGTFile getFile() {
		return file;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	@Override
	public String toString() {
		return "Texture [file=" + file + ", width="
				+ width + ", height=" + height + "]";
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
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
 
