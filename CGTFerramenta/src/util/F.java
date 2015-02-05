package util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.Config;
import cgt.CGTGame;
import cgt.CGTGameWorld;
import cgt.CGTScreen;

/**
 * Created by infolev on 05/02/15.
 */
public class F {
    private static CGTGame instance = null;

    public static CGTGame getGame() {
        if (instance == null) {
            instance = new CGTGame();
            File file = new File(Config.BASE);
            try {
                FileUtils.deleteDirectory(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            file.mkdirs();
        }

        return instance;
    }

}
