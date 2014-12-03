package com.projetcgt;

import android.content.Intent;
import android.os.Bundle;
import cgt.util.ScreenHandleInterface;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.projetocgt.StarAssault;

public class MainActivity extends AndroidApplication implements ScreenHandleInterface {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        //Configuracoes da OpenGL
        //cfg.useGL20 = false;
        //config.useAccelerometer = true;
        //cfg.useCompass = false;
        //cfg.useWakelock = true;

        StarAssault game = StarAssault.getInstance();
        game.setScreenHandle(this);

        initialize(StarAssault.getInstance(), config);
    }

	@Override
	public void showVideoTutorial() {
        Intent intent = new Intent(this, VideoScreen.class);
        startActivity(intent);      
        finish();
	}

	@Override
	public void showVideoWin() {
		// TODO Auto-generated method stub
		
	}
}