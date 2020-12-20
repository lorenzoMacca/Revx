package domain.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

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

    public Stream<V> getValuesAsStream() {
        return this.internalMap.values().stream();
    }
}
