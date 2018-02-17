package app;

import java.util.Set;
import javax.ws.rs.core.Application;

public class App extends Application{
	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(org.glassfish.jersey.moxy.json.MoxyJsonFeature.class);
        resources.add(org.glassfish.jersey.moxy.xml.MoxyXmlFeature.class);
        resources.add(service.ProductService.class);
        return resources;
    }
}
