package com.zero.ci.network.http;

import java.util.LinkedHashMap;
import java.util.List;

public class LinkedMultiValueMap<K, V> extends BasicMultiValueMap<K, V> {
    public LinkedMultiValueMap() {
        super(new LinkedHashMap<K, List<V>>());
    }
}
