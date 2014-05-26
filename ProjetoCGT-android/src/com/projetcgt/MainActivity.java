package com.projetcgt;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.projetocgt.StarAssault;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        //Configuracoes da OpenGL
        //cfg.useGL20 = false;
        //config.useAccelerometer = true;
        //cfg.useCompass = false;
        //cfg.useWakelock = true;
        initialize(new StarAssault(), config);
    }
}