package com.yizhuoyan.common;

import org.springframework.lang.NonNull;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 视图对象
 */
public class ViewObjectMap implements Map<String, Object> {
    private final List<StringKeyValueEntry> data;

    public ViewObjectMap(int initialCapacity) {
        this.data = new ArrayList<>(initialCapacity);
    }
    public static ViewObjectMap of(Map<? extends String, ?> m) {
        final ViewObjectMap viewObjectMap = new ViewObjectMap(m.size());
        viewObjectMap.putAll(m);
        return viewObjectMap;
    }
    public static ViewObjectMap of(int size) {
        return new ViewObjectMap(size);
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public boolean isEmpty() {
        return this.data.size()==0;
    }

    @Override
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object get(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull ViewObjectMap put(String key, Object value) {
        if (key != null && (key = key.trim()).length() != 0 && value != null) {
            this.data.add(new StringKeyValueEntry(key, value));
        }
        return this;
    }
    public <X>ViewObjectMap put(String key,X embedValue, Function<X,ViewObjectMap> transfer) {
        final ViewObjectMap embed = transfer.apply(embedValue);
        if(embed==null)return this;
        return this.put(key, embed);
    }
    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection values() {
        throw new UnsupportedOperationException();
    }
    private class KeyValueIterator implements  Iterator<Entry<String, Object>>{
        private final List<StringKeyValueEntry> list;
        private int  size;

        public KeyValueIterator(List<StringKeyValueEntry> list) {
            this.list = list;
            this.size=list.size()-1;
        }
        @Override
        public boolean hasNext() {
            return size>=0;
        }
        @Override
        public Entry<String, Object> next() {
            return list.get(size--);
        }
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return  new AbstractSet<Entry<String,Object>>(){
            @Override
            public Iterator<Entry<String, Object>> iterator() {
                return new KeyValueIterator(data);
            }
            @Override
            public int size() {
                return data.size();
            }
        };
    }
}
