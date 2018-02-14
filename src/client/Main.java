package client;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ProcessingException;
import model.Item;

public class Main {
	public static void main(String[] args) {

		try(Scanner sc = new Scanner(System.in)) {
			String server = "http://java-webshop.herokuapp.com";
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
					catch(BadRequestException e) {
						System.out.println("Error 400: bad request");
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
					catch(InternalServerErrorException e) {
						System.out.println("Error 500: internal server error");
					}
					catch(ProcessingException e) {
						System.out.println("Service is not available!");
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
			default:		accept_type = "application/json";
		}
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(UriBuilder.fromUri(server).build());
		String response = null;
		switch(method) {
			case "get": 	response = get(target, path, accept_type);
							response = format(response, accept_type);
				  			break;
			case "delete":	response = delete(target, path);
				  			break;
			case "post":	response = post(target, path, sc, accept_type);
							break;
			case "put": 	response = put(target, path, sc, accept_type);
							break;
			default:		response = "Please, choose the correct http method!";
		}
		return response;
	}

	private static String get(WebTarget target, String path, String accept_type) {
		return getBase(target, path, accept_type).get(String.class);
	}

	private static String delete(WebTarget target, String path) {
		return getBase(target, path, "text/plain").delete(String.class);
	}

	private static String post(WebTarget target, String path, Scanner sc, String accept_type) {
		Object payload = getPayload(sc, accept_type);
		if(accept_type.equals("text/html")) {
			accept_type = "application/json";
		}
		return getBase(target, path, "text/plain").post(Entity.entity(payload, accept_type), String.class);
	}

	private static String put(WebTarget target, String path, Scanner sc, String accept_type) {
		Object payload = getPayload(sc, accept_type);
		if(accept_type.equals("text/html")) {
			accept_type = "application/json";
		}
		return getBase(target, path, "text/plain").put(Entity.entity(payload, accept_type), String.class);
	}

	private static Object getPayload(Scanner sc, String accept_type) {
		if(accept_type.equals("application/json") || accept_type.equals("application/xml")) {
			System.out.println("Please, input " + accept_type + " string in one line");
			return sc.nextLine();
		}
		else {
			System.out.println("Input name and price of item");
			String[] tokens = sc.nextLine().split(" ");
			String name = tokens[0];
			int price = Integer.parseInt(tokens[1]);
			return new Item(name, price, true);
		}
	}

	private static Builder getBase(WebTarget target, String path, String accept_type) {
		return target.path("rest").
				 path(path).
				 request().
				 accept(accept_type);
	}

	public static String format(String input, String accept_type) {
		if(accept_type.equals("application/xml")) {
			return formatXML(input);
		}
		return input;
	}

	public static String formatXML(String input) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(parseXml(input));
            transformer.transform(source, result);
            return result.getWriter().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return input;
        }
    }

    private static Document parseXml(String input) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(input));
            return db.parse(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
