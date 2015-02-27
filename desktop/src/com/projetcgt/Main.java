package com.projetcgt;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.projetocgt.StarAssault;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cgf = new LwjglApplicationConfiguration();
		cgf.vSyncEnabled = true;
		cgf.width = 960;
		cgf.height = 640;
		new LwjglApplication(StarAssault.getInstance(),cgf);
	}
}
