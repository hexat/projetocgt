package com.projetcgt;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.projetocgt.StarAssault;

public class Main {

	public static void main(String[] args) {
		LwjglApplicationConfiguration cgf = new LwjglApplicationConfiguration();
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		cgf.vSyncEnabled = true;
		cgf.width = Integer.parseInt(args[0]);
		cgf.height = Integer.parseInt(args[1]);
		if(cgf.width == 0)
			cgf.width = 960;
		if(cgf.height == 0)
			cgf.height = 680;

		new LwjglApplication(StarAssault.getInstance(),cgf);
	}
}
