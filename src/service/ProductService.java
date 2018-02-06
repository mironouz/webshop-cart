package service;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


import model.Cart;
import model.Item;

@Path("products")
public class ProductService {

	static Cart c;

    public ProductService() {

    }

    static {
    	Item a = new Item("bread", 20, true);
    	Item b = new Item("butter", 30, false);
    	c = new Cart(new CopyOnWriteArrayList<Item>(Arrays.asList(a,b)));
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public Cart getCart() {
        return c;
    }

    @Path("{id}")
    @GET
    @Produces({"application/xml", "application/json"})
	public Item getItem(@PathParam("id") int id) {
    	return c.findById(id);
    }


}