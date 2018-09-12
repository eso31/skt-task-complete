package com.skytask.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ProductMapper {

    private final static ObjectMapper mapper = new ObjectMapper();

    public static <K> List<K> json2List(String json, Class<K> myType) throws IOException {
        if (json == null)
            return Collections.emptyList();
        CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, myType);
        List<String> productList = mapper.readValue(json, type);
        return (List<K>) productList;
    }

    public static <K> String list2Json(List<K> list) throws IOException {
        return mapper.writeValueAsString(list);
    }
}
