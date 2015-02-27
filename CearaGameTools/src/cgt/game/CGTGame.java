package cgt.game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cgt.policy.ErrorValidate;
import cgt.screen.CGTWindow;
import cgt.util.CGTError;


public class CGTGame implements Serializable {
    private static CGTGame instance = null;

    private final SpriteSheetDB spriteDB;
    private final List<CGTScreen> screens = new ArrayList<CGTScreen>();
    private final List<CGTGameWorld> worlds = new ArrayList<CGTGameWorld>();
	private String startWindowId;

    private CGTGame() {
        setStartWindowId(null);
        spriteDB = new SpriteSheetDB();
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
            if (startWindowId == null) startWindowId = id;
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
            if (startWindowId == null) startWindowId = id;
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
        instance = cgtGame;
		return instance;
	}

    public boolean removeWorld(CGTGameWorld world) {
        return worlds.remove(world);
    }

    public boolean removeScreen(CGTScreen screen) {
        return screens.remove(screen);
    }
    
    public boolean remove(CGTWindow window) {
    	for (CGTGameWorld w : worlds) {
    		if (w.getId().equals(window.getId())) {
    			worlds.remove(w);
    			return true;
    		}
    	}
    	for (CGTScreen s : screens) {
    		if (s.getId().equals(window.getId())) {
    			screens.remove(s);
    			return true;
    		}
    	}
    	return false;
    }

    public List<String> getIds() {
        List<String> res = new ArrayList<String>(screens.size()+worlds.size());

        for (CGTScreen s : screens) {
            res.add(s.getId());
        }
        for (CGTGameWorld w : worlds) {
            res.add(w.getId());
        }

        return res;
    }

    public List<CGTWindow> getWindows() {
        List<CGTWindow> res = new ArrayList<CGTWindow>();
        res.addAll(worlds);
        res.addAll(screens);
        return res;
    }

    public SpriteSheetDB getSpriteDB() {
        return spriteDB;
    }

    public List<CGTError> validate() {
        List<CGTError> res = new ArrayList<CGTError>();
        for (CGTWindow w : screens) {
            res.addAll(w.validate());
        }
        for (CGTWindow w : worlds) {
            res.addAll(w.validate());
        }
        return res;
    }
}


