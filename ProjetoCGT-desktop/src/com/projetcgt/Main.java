package com.projetcgt;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.projetocgt.StarAssault;

public class Main {
	public static void main(String[] args) {
		 new LwjglApplication(new StarAssault(), "Star Assault", 900, 600, true);
	}
}
