package service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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

    @DELETE
    @Produces({"text/plain"})
	public String clearCart() {
    	List<Item> cart = c.getCart();
    	cart.clear();
    	c.setCart(cart);
    	return "The cart was cleared";
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

    @POST
    public Response createItem(@FormParam("name") String name,
    						@FormParam("price") int price) {
    	List<Item> cart = c.getCart();
    	cart.add(new Item(name, price, true));
    	c.setCart(cart);
    	// I am not sure that it is a good idea to call get request from post in REST
    	// maybe it is better to do in client, for example in JS code
    	return Response.seeOther(URI.create("products")).build();
    }

    @Path("{id}")
    @PUT
    public String updateItem(@FormParam("name") String name,
    						@FormParam("price") int price,
    						@PathParam("id") int id) {

    	if(c.deleteItemById(id)) {
    		List<Item> cart = c.getCart();
    		cart.add(new Item(name, price, true));
    		c.setCart(cart);
    		return "item " + id + " was successfuly updated";
    	}
    	return "item " + id + " was not found";
	}
}