package fr.saphyr.ce.utils;

public interface Pair<K, V> {

    K getKey();

    V getValue();

    static <K, V> Pair<K, V> of(K key, V value) {
        return new Pair<K, V>() {
            @Override
            public K getKey() {
                return key;
            }

            @Override
            public V getValue() {
                return value;
            }
        };
    }

}
