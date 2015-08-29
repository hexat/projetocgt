package br.edu.ifce.cgt.application.vo;

import javafx.scene.Node;

public interface DrawableObject<T> {
    T getObject();

    Node getPane();

    void drawObject();

    void drawConfigurationPanel();

    void onCreate();
}
