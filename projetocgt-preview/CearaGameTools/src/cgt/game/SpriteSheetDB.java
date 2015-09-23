package cgt.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cgt.util.CGTTexture;

/**
 * Created by infolev on 06/02/15.
 */
public class SpriteSheetDB implements Serializable {
    private final List<CGTSpriteSheet> spriteSheets;

    protected SpriteSheetDB() {
        spriteSheets = new ArrayList<CGTSpriteSheet>();
    }

    private boolean add(CGTSpriteSheet spriteSheet) {
        for (CGTSpriteSheet s : spriteSheets) {
            if (s.getId().equals(spriteSheet.getId())) {
                return false;
            }
        }

        spriteSheets.add(spriteSheet);

        return true;
    }

    public CGTSpriteSheet create(String id, CGTTexture texture) {
        CGTSpriteSheet res = new CGTSpriteSheet(texture);
        res.setId(id);

        if (add(res)) {
            return res;
        }

        return null;
    }

    public CGTSpriteSheet find(String id) {
        for (CGTSpriteSheet s : spriteSheets) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public List<String> findAllId() {
        List<String> res = new ArrayList<String>(spriteSheets.size());
        for (CGTSpriteSheet s : spriteSheets) {
            res.add(s.getId());
        }
        return res;
    }

	public boolean delete(String id) {
		return spriteSheets.remove(find(id));
	}
}
