package client;

import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

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
			}
			Client client = ClientBuilder.newClient(new ClientConfig());
			WebTarget target = client.target(UriBuilder.fromUri("http://localhost:8080/webshop-cart").build());
			String response = null;
			switch(method) {
				case "get": response = get(target, path, accept_type);
					  		break;
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
}
