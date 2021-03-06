package cgt.screen;

import cgt.util.CGTError;

import java.io.Serializable;
import java.util.List;

public abstract class CGTWindow implements Serializable {
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract List<CGTError> validate();
}
