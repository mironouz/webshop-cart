package service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("products")
public class ProductService {

    public ProductService() {

    }

    @GET
    @Produces("text/plain")
    public String getXml() {
        return "hello";
    }

}