package br.edu.ifce.cgt.application.vo;

public interface DrawableObject<T> {
    T getObject();

    void drawObject();

    void drawConfigurationPanel();

    void onCreate();
}
