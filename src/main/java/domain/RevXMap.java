package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RevXMap<K, V> {

    private final Map<K, V> internalMap = new HashMap<>();

    private RevXMap(){}

    public Optional<V> get(K key){
        return Optional.ofNullable(this.internalMap.get(key));
    }

    public void put(K key, V value){
        this.internalMap.put(key, value);
    }

    public static <K, V> RevXMap<K, V> newInstance(){
        return new RevXMap<>();
    }
}
