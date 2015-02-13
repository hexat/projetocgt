package cgt.game;

import java.io.Serializable;

import cgt.core.CGTGameObject;
import cgt.util.CGTTexture;


public class CGTSpriteSheet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6320820212756348146L;
    private String id;
	private CGTTexture texture;
	private int columns;
	private float velocity;
	private int rows;
	
	
	/**
	 * 
	 * @param texture
	 */
	protected CGTSpriteSheet(CGTTexture texture) {
		setTexture(texture);
		this.columns = 1;
		this.velocity = 1;
		this.rows = 1;
	}

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

	public void setVelocity(float vel) {
		velocity = vel;
	}
	 
	public float getVelocity() {
		return velocity;
	}
	 
	public CGTTexture getTexture() {
		return texture;
	}

	public void setTexture(CGTTexture cGTTexture) {
		this.texture = cGTTexture;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "CGTSpriteSheet [texture=" + texture + ", columns=" + columns
				+ ", velocity=" + velocity + ", rows=" + rows + "]";
	}
}
 
