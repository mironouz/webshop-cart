package client;

import java.util.Scanner;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import model.Item;

public class Main {
	public static void main(String[] args) {

		try(Scanner sc = new Scanner(System.in)) {
			String server = "http://localhost:8080/webshop-cart";
			System.out.println("Enter http method, path and accept type(example: get products json)");
			while(true) {
				String[] tokens = sc.nextLine().split(" ");
				if(tokens.length != 3) {
					System.out.println("Wrong usage!");
				}
				else {
					try{
						String response = sendRequest(tokens, server, sc);
						System.out.println(response);
					}
					catch(NotFoundException e) {
						System.out.println("Error 404: not found");
					}
					catch(NotAllowedException e) {
						System.out.println("Error 405: method not allowed");
					}
					catch(NotAcceptableException e) {
						System.out.println("Error 406: not acceptable");
					}
				}
			}
		}
	}


	private static String sendRequest(String[] tokens, String server, Scanner sc) {
		String method = tokens[0].toLowerCase();
		String path = tokens[1].toLowerCase();
		String accept_type = null;
		switch(tokens[2].toLowerCase()) {
			case "json": 	accept_type = "application/json";
						 	break;
			case "xml": 	accept_type = "application/xml";
							break;
			case "html":	accept_type = "text/html";
							break;
			default:		accept_type = "json";
		}
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(UriBuilder.fromUri(server).build());
		String response = null;
		switch(method) {
			case "get": 	response = get(target, path, accept_type);
				  			break;
			case "delete":	response = delete(target, path);
				  			break;
			case "post":	response = post(target, path, sc);
							break;
			case "put": 	response = put(target, path, sc);
							break;
			default:		response = "Please, choose the correct http method!";
		}
		return response;
	}

	private static String get(WebTarget target, String path, String accept_type) {
		return getBase(target, path, accept_type).get(String.class);
	}

	private static String delete(WebTarget target, String path) {
		return getBase(target, path, "text/plain").get(String.class);
	}

	private static String post(WebTarget target, String path, Scanner sc) {
		Item item = createItem(sc);
		return getBase(target, path, "text/plain").post(Entity.entity(item, "application/xml"), String.class);
	}

	private static String put(WebTarget target, String path, Scanner sc) {
		Item item = createItem(sc);
		return getBase(target, path, "text/plain").put(Entity.entity(item, "application/xml"), String.class);
	}

	private static Item createItem(Scanner sc) {
		System.out.println("Input name and price of item");
		String[] tokens = sc.nextLine().split(" ");
		String name = tokens[0];
		int price = Integer.parseInt(tokens[1]);
		return new Item(name, price, true);
	}

	private static Builder getBase(WebTarget target, String path, String accept_type) {
		return target.path("rest").
				 path(path).
				 request().
				 accept(accept_type);
	}
}