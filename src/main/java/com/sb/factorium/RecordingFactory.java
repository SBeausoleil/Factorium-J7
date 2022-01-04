package com.sb.factorium;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A RecordingFactory is a factory that keeps an accessible record of everything it has created.
 * @param <T>
 */
public class RecordingFactory<K, T> implements IFactory<K, T> {

    protected K defaultKey;
    protected Map<K, Generator<T>> generators;
    protected List<T> created;

    public RecordingFactory(K defaultKey, Map<K, Generator<T>> generators) {
        if (!generators.containsKey(defaultKey)) {
            throw new IllegalArgumentException("The default key is not present in the generators map.");
        }

        this.defaultKey = defaultKey;
        this.generators = generators;
        this.created = new ArrayList<>();
    }

    @Override
    public Generator<T> getDefaultGenerator() {
        return generators.get(defaultKey);
    }

    @Override
    public T generate(Modifier... modifiers) {
        T result = getDefaultGenerator().generate();
        created.add(result);
        return result;
    }

    @Override
    public List<T> generate(int nItems, Modifier... modifiers) {
        List<T> result = getDefaultGenerator().generate(nItems, modifiers);
        created.addAll(result);
        return result;
    }

    @Override
    public Map<K, Generator<T>> getGenerators() {
        return generators;
    }

    public List<T> getCreated() {
        return created;
    }
}
