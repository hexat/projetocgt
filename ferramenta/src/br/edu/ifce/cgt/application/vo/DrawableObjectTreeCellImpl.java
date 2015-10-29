package br.edu.ifce.cgt.application.vo;

import javafx.scene.control.TreeCell;

public class DrawableObjectTreeCellImpl extends TreeCell<DrawableObject> {

    public DrawableObjectTreeCellImpl() {

    }

    @Override
    public void startEdit() {
        super.startEdit();
        getItem().drawConfigurationPanel();
        getItem().drawObject();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
    }

    @Override
    protected void updateItem(DrawableObject item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty) {
            setText(item.toString());
        } else {
            setText(null);
        }
    }
}