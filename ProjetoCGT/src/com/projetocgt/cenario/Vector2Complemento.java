package com.projetocgt.cenario;

import com.badlogic.gdx.math.Vector2;

public class Vector2Complemento  {

	public final static Vector2Complemento X = new Vector2Complemento(1, 0);
	public final static Vector2Complemento Y = new Vector2Complemento(0, 1);
	public final static Vector2Complemento Zero = new Vector2Complemento(0, 0);
	static public final float FLOAT_ROUNDING_ERROR = 0.000001f; // 32 bits
	
	private Vector2 vector= new Vector2();
	
	/** the x-component of this vector **/
	public float x;
	/** the y-component of this vector **/
	public float y;

	/** Constructs a new vector at (0,0) */
	public Vector2Complemento () {
	}

	/** Constructs a vector with the given components
	 * @param x The x-component
	 * @param y The y-component */
	public Vector2Complemento (float x, float y) {
		this.x = x;
		this.y = y;
	}


	

	public float dot (Vector2 v) {
		return x * v.x + y * v.y;
	}

	/** Returns true if the value is zero.
	 * @param tolerance represent an upper bound below which the value is considered zero. */
	static public boolean isZero (float value, float tolerance) {
		return Math.abs(value) <= tolerance;
	}
	
	/** Returns true if the value is zero (using the default tolerance as upper bound) */
	static public boolean isZero (float value) {
		return Math.abs(value) <= FLOAT_ROUNDING_ERROR;
	}
	
	public boolean isCollinear (Vector2 vector, float epsilon) {
		return isZero(dot(vector) - 1, epsilon);
	}

	public boolean isCollinear (Vector2 vector) {
		return isZero(dot(vector) - 1);
	}

	public boolean isCollinearOpposite (Vector2 vector, float epsilon) {
		return isZero(dot(vector) + 1, epsilon);
	}

	public boolean isCollinearOpposite (Vector2 vector) {
		return isZero(dot(vector) + 1);
	}

	public boolean isPerpendicular (Vector2 vector) {
		return isZero(dot(vector));
	}

	public boolean isPerpendicular (Vector2 vector, float epsilon) {
		return isZero(dot(vector), epsilon);
	}

	public boolean hasSameDirection (Vector2 vector) {
		return dot(vector) > 0;
	}

	public boolean hasOppositeDirection (Vector2 vector) {
		return dot(vector) < 0;
	}
}
