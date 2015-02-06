package cgt.screen;

import java.io.Serializable;

public abstract class CGTWindow implements Serializable {
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
