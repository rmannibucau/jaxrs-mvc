package com.github.rmannibucau.jaxrs.mvc.provider;

import com.github.rmannibucau.jaxrs.mvc.api.ModelView;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.MessageBodyWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public abstract class BaseMessageBodyWriter implements MessageBodyWriter<ModelView> {
    @Override
    public boolean isWriteable(final Class<?> rawType, final Type genericType,
                               final Annotation[] annotations, final MediaType mediaType) {
        return ModelView.class.equals(rawType);
    }

    @Override
    public long getSize(final ModelView t, final Class<?> rawType,
                        final Type genericType, final Annotation[] annotations,
                        final MediaType mediaType) {
        return -1;
    }
}
