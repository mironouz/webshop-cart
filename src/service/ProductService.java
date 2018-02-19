package service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
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

	private static Cart c;

    public ProductService() {

    }

    static {
    	Item a = new Item("bread", 20, true);
    	Item b = new Item("butter", 30, false);
    	c = new Cart(new CopyOnWriteArrayList<Item>(Arrays.asList(a,b)));
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public Response getCart() {
        return Response.ok(c).build();
    }

    @GET
    @Produces("text/html")
    public Response getCartHtml() {
    	return Response.ok(new Viewable("/products.jsp", c)).build();
    }

    @Path("{id : \\d+}")
    @GET
    @Produces({"application/xml", "application/json"})
	public Response getItem(@PathParam("id") int id) {
    	Item item = c.findById(id);
    	if(item == null) {
    		throw new NotFoundException();
    	}
    	return Response.ok(item).build();
    }

    @Path("{id : \\d+}")
    @GET
    @Produces("text/html")
    public Response getItemHtml(@PathParam("id") int id) {
    	return Response.ok(new Viewable("/product.jsp", c.findById(id))).build();
    }

    @DELETE
	public Response clearCart() {
    	List<Item> cart = c.getCart();
    	cart.clear();
    	c.setCart(cart);
    	return Response.noContent().build();
    }

    @Path("{id : \\d+}")
    @DELETE
	public Response deleteItem(@PathParam("id") int id) {
    	if(c.deleteItemById(id)) {
    		return Response.noContent().build();
    	}
    	throw new NotFoundException();
    }

    @POST
    @Produces("text/html")
    public Response createItemFromForm(@FormParam("name") String name,
    						@FormParam("price") int price) {
    	List<Item> cart = c.getCart();
    	cart.add(new Item(name, price, true));
    	c.setCart(cart);
    	// I am not sure that it is a good idea to call get request from post in REST
    	// maybe it is better to do in client, for example in JS code
    	return Response.seeOther(URI.create("products")).build();
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response createItem(Item item) {
    	List<Item> cart = c.getCart();
    	Item i = new Item(item.getName(), item.getPrice(), item.isIn_stock());
    	cart.add(i);
    	c.setCart(cart);
    	return Response.created(URI.create("products/" + i.getId())).build();
    }

    @Path("{id : \\d+}")
    @PUT
    public Response updateItemFromForm(@FormParam("name") String name,
    						@FormParam("price") int price,
    						@PathParam("id") int id) {

    	Item item = c.findById(id);
    	if(item != null) {
    		item.setName(name);
    		item.setPrice(price);
    		return Response.noContent().build();
    	}
    	throw new NotFoundException();
	}

    @Path("{id : \\d+}")
    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response updateItem(Item i, @PathParam("id") int id) {
    	Item item = c.findById(id);
    	if(item != null) {
    		item.setName(i.getName());
    		item.setPrice(i.getPrice());
    		item.setIn_stock(i.isIn_stock());
    		return Response.noContent().build();
    	}
    	throw new NotFoundException();
    }
}