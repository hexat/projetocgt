package cgt.game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import cgt.core.CGTEnemy;
import cgt.core.CGTGameObject;
import cgt.screen.CGTWindow;
import cgt.util.CGTError;
import com.badlogic.gdx.utils.Json;


public class CGTGame implements Serializable {
    private static CGTGame instance = null;

    private boolean debug;
    private final SpriteSheetDB spriteDB;
    private final List<CGTScreen> screens = new ArrayList<>();
    private final List<CGTGameWorld> worlds = new ArrayList<>();
	private String startWindowId;

    private CGTGame() {
        setStartWindowId(null);
        spriteDB = new SpriteSheetDB();
        debug = false;
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
            if (startWindowId == null) startWindowId = id;
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

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isDebug() {
        return debug;
    }

    public CGTWindow getWindow(String id) {
        if (id == null) return null;
        CGTWindow res = getWorld(id);
        if (res == null) res = getScreen(id);
        return res;
    }

	public void saveGame(File file){
		try {
			FileWriter obj = new FileWriter(file);
			new Json().toJson(this, new FileWriter(file));
			obj.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static CGTGame getSavedGame(InputStream io) {
        instance = new Json().fromJson(CGTGame.class, io);
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

    public List<CGTGameWorld> getWorlds() {
        List<CGTGameWorld> res = new ArrayList<CGTGameWorld>();
        res.addAll(worlds);
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

    public List<String> objectIds() {
        List<String> res = new ArrayList<String>();
        for (CGTGameWorld w : worlds) {
            res.addAll(w.getObjectIds());
        }
        return res;
    }

    public CGTGameObject findObject(String id) {
        CGTGameObject res = null;
        for (CGTGameWorld w : worlds ) {
            res = w.getObjectByLabel(id);
            if (res != null) {
                return res;
            }
        }
        return res;
    }

    public List<String> getEnemiesGroup() {
        List<String> result = new ArrayList<String>();

        for (CGTGameWorld w : worlds) {
            for (CGTEnemy e : w.getEnemies()) {
                if (!result.contains(e.getGroup())) {
                    result.add(e.getGroup());
                }
            }
        }

        return result;
    }

    public boolean remove(CGTGameObject object) {
        for (CGTGameWorld world : worlds) {
            if (world.removeObject(object)) {
                return true;
            }
        }
        return false;
    }
}


