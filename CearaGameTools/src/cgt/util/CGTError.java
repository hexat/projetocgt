package cgt.util;

import cgt.policy.ErrorValidate;

/**
 * Created by luanjames on 25/02/15.
 */
public class CGTError {
    private ErrorValidate validate;
    private String id;

    public CGTError(ErrorValidate validate, String id) {
        this.validate = validate;
        this.id = id;
    }

    public ErrorValidate getValidate() {
        return validate;
    }

    public void setValidate(ErrorValidate validate) {
        this.validate = validate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return validate.name() + " -> " + id;
    }
}
