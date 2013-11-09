package com.github.rmannibucau.jaxrs.mvc.provider;

import com.github.rmannibucau.jaxrs.mvc.api.ModelView;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.JdkLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces({ MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
public class VelocityMessageBodyWriter extends BaseMessageBodyWriter {
    private static final String UTF_8 = "UTF-8";

    private final VelocityEngine velocity;

    public VelocityMessageBodyWriter() {
        velocity = new VelocityEngine();
        velocity.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, JdkLogChute.class.getName());
        velocity.setProperty(RuntimeConstants.ENCODING_DEFAULT, UTF_8);
        velocity.setProperty(RuntimeConstants.INPUT_ENCODING, UTF_8);
        velocity.setProperty(RuntimeConstants.OUTPUT_ENCODING, UTF_8);
        velocity.setProperty(RuntimeConstants.RUNTIME_REFERENCES_STRICT, Boolean.TRUE.toString());
        velocity.setProperty(RuntimeConstants.RUNTIME_REFERENCES_STRICT_ESCAPE, Boolean.TRUE.toString());
        velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "mvc");
        velocity.setProperty("mvc." + RuntimeConstants.RESOURCE_LOADER + ".class", ClasspathResourceLoader.class.getName());
    }

    @Override
    public void writeTo(final ModelView modelView, final Class<?> rawType, final Type genericType,
                        final Annotation[] annotations, final MediaType mediaType,
                        final MultivaluedMap<String, Object> httpHeaders,
                        final OutputStream entityStream) throws IOException {
        final OutputStreamWriter writer = new OutputStreamWriter(entityStream);
        velocity.getTemplate(modelView.template()).merge(new VelocityContext(modelView.attributes()), writer);
        writer.flush();
    }
}
