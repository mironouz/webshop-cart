package client;

import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import model.Item;

public class Main {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);;

		System.out.println("Enter http method, path and accept type(example: get products json)");
		while(true) {
			String[] tokens = sc.nextLine().split(" ");
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
			Client client = ClientBuilder.newClient(new ClientConfig());
			WebTarget target = client.target(UriBuilder.fromUri("http://localhost:8080/webshop-cart").build());
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

			System.out.println(response);

		}
	}

	private static String get(WebTarget target, String path, String accept_type) {
		return target.path("rest").
				 path(path).
				 request().
				 accept(accept_type).
				 get(String.class);
	}

	private static String delete(WebTarget target, String path) {
		return target.path("rest").
				 path(path).
				 request().
				 accept("text/plain").
				 delete(String.class);
	}

	private static String post(WebTarget target, String path, Scanner sc) {
		System.out.println("Input name and price of item");
		String[] tokens = sc.nextLine().split(" ");
		String name = tokens[0];
		int price = Integer.parseInt(tokens[1]);
		Item item = new Item(name, price, true);
		return target.path("rest").
				 path(path).
				 request().
				 accept("text/plain").
				 post(Entity.entity(item, "application/xml"), String.class);
	}

	private static String put(WebTarget target, String path, Scanner sc) {
		System.out.println("Input name and price of item");
		String[] tokens = sc.nextLine().split(" ");
		String name = tokens[0];
		int price = Integer.parseInt(tokens[1]);
		Item item = new Item(name, price, true);
		return target.path("rest").
				 path(path).
				 request().
				 accept("text/plain").
				 put(Entity.entity(item, "application/xml"), String.class);
	}
}
