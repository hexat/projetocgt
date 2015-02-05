package cgt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cgt.screen.CGTWindow;


public class CGTGame implements Serializable {
    private static CGTGame instance = null;

    private List<CGTScreen> screens = new ArrayList<CGTScreen>();
    private List<CGTGameWorld> worlds = new ArrayList<CGTGameWorld>();
	private String startWindowId;

	private CGTGame() {
        setStartWindowId(null);
	}

    public static CGTGame get() {
        if (instance == null) {
            instance = new CGTGame();
        }
        return instance;
    }

	public CGTWindow getStartWindow() {
		return getWindow(startWindowId);
	}

    public void setStartWindowId(String startWindowId) {
        this.startWindowId = startWindowId;
    }

    public CGTScreen createScreen(String id) {
        if (id == null) return null;
        boolean ok = true;
        for (int i = 0; i < screens.size() && ok; i++) {
            if (screens.get(i).getId().equals(id)) {
                ok = false;
            }
        }
        if (ok) {
            CGTScreen screen = new CGTScreen();
            screen.setId(id);
            screens.add(screen);
            return screen;
        }
        return null;
    }

    public CGTGameWorld createWorld(String id) {
        if (id == null) return null;
        boolean ok = true;

        for (int i = 0; i < worlds.size() && ok; i++) {
            if (worlds.get(i).getId().equals(id)) {
                ok = false;
            }
        }

        if (ok) {
            CGTGameWorld world = new CGTGameWorld();
            world.setId(id);
            worlds.add(world);
            return world;
        }
        return null;
    }

    public CGTScreen getScreen(String id) {
        if (id == null) return null;
        for (CGTScreen s : screens) {
            if (s.getId().equals(id)) {
                return s;
            }
            return s;
        }
        return null;
    }

    public CGTGameWorld getWorld(String id) {
        if (id == null) return null;
        for (CGTGameWorld w : worlds) {
            if (w.getId().equals(id)) {
                return w;
            }
        }
        return null;
    }

    public CGTWindow getWindow(String id) {
        if (id == null) return null;
        CGTWindow res = getWorld(id);
        if (res == null) res = getScreen(id);
        return res;
    }

	public void saveGame(File file){
		try {

			FileOutputStream saveConfig = new FileOutputStream(file);
			ObjectOutputStream obj = new ObjectOutputStream(saveConfig);
			obj.writeObject(this);
			obj.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static CGTGame getSavedGame(InputStream io) {
		CGTGame cgtGame = new CGTGame();

		try {
			ObjectInputStream objLeitura = new ObjectInputStream(io);
			cgtGame = (CGTGame) objLeitura.readObject();
			objLeitura.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cgtGame;
	}

    public boolean removeWorld(CGTGameWorld world) {
        return worlds.remove(world);
    }

    public boolean removeScreen(CGTScreen screen) {
        return screens.remove(screen);
    }
}


