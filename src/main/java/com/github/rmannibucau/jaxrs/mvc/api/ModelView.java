package com.github.rmannibucau.jaxrs.mvc.api;

import java.util.HashMap;
import java.util.Map;

public class ModelView {
    private final String name;
    private final Map<String, Object> attributes = new HashMap<String, Object>();

    public ModelView(final String name) {
        this.name = name;
    }

    public ModelView set(final String name, final Object value) {
        attributes.put(name, value);
        return this;
    }

    public Map<String, Object> attributes() {
        return attributes;
    }

    public String template() {
        return name;
    }
}
