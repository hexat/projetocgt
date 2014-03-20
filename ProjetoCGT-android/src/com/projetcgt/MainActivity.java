package com.projetcgt;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.projetocgt.StarAssault;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        //Configuracoes da OpenGL
        //cfg.useGL20 = false;
        //cfg.useAccelerometer = false;
        //cfg.useCompass = false;
        //cfg.useWakelock = true;
        cfg.useGL20 = true;
        initialize(new StarAssault(), cfg);
    }
}