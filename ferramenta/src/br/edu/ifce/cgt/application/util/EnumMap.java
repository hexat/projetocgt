package br.edu.ifce.cgt.application.util;

/**
 * Created by luanjames on 24/02/15.
 */
public class EnumMap<K extends Enum> {
    private K key;
    private String value;

    public EnumMap(K key, String value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
