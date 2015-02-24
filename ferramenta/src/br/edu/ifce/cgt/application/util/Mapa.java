package br.edu.ifce.cgt.application.util;

/**
 * Created by luanjames on 24/02/15.
 */
public class Mapa<K, V> {
    private K key;
    private V value;

    public Mapa(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
