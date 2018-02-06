package service;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.glassfish.jersey.server.mvc.Viewable;

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

    @GET
    @Produces("text/html")
    public Viewable getCartHtml() {
    	return new Viewable("/products.jsp", c);
    }

    @Path("{id}")
    @GET
    @Produces({"application/xml", "application/json"})
	public Item getItem(@PathParam("id") int id) {
    	return c.findById(id);
    }

    @Path("{id}")
    @GET
    @Produces("text/html")
    public Viewable getItemHtml(@PathParam("id") int id) {
    	return new Viewable("/product.jsp", c.findById(id));
    }

    @Path("{id}")
    @DELETE
    @Produces({"text/plain"})
	public String deleteItem(@PathParam("id") int id) {
    	if(c.deleteItemById(id)) {
    		return "item " + id + " successfuly deleted";
    	}
    	return "item " + id + " was not found";
    }


}