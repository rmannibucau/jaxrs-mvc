package com.github.rmannibucau.jaxrs.mvc;

import com.github.rmannibucau.jaxrs.mvc.api.ModelView;
import com.github.rmannibucau.jaxrs.mvc.provider.VelocityMessageBodyWriter;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.local.LocalConduit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import static org.junit.Assert.assertEquals;

public class VelocityTest {
    private static Server server;

    @BeforeClass
    public static void startServer() {
        final JAXRSServerFactoryBean serverFactoryBean = new JAXRSServerFactoryBean();
        serverFactoryBean.setResourceClasses(VelocityResource.class);
        serverFactoryBean.setProvider(new VelocityMessageBodyWriter());
        serverFactoryBean.setAddress("local://localhost:1234");

        server = serverFactoryBean.create();
        server.start();
    }

    @AfterClass
    public static void shutdownServer() {
        server.stop();
        server.destroy();
    }

    @Test
    public void get() {
        final WebClient webClient = WebClient.create("local://localhost:1234");
        WebClient.getConfig(webClient).getRequestContext().put(LocalConduit.DIRECT_DISPATCH, Boolean.TRUE);
        assertEquals("Method => GET", webClient.accept("text/plain").path("velocity").get(String.class).trim());
    }

    @Path("/velocity")
    public static class VelocityResource {
        @GET
        public ModelView get() {
            return new ModelView("get.vm").set("name", "GET");
        }
    }
}
