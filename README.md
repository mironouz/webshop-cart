# REST JAVA-RX APP

### it is available on http://java-webshop.herokuapp.com/

# Current api:

1. @GET __rest/products__ (XML, JSON, HTML)
2. @GET __rest/products/id__ (XML, JSON, HTML)
3. @DELETE __rest/products__
4. @DELETE __rest/products/id__
5. @POST __rest/products__ (with form parameters "name" and "price" or JSON/XML data)
6. @PUT __rest/products/id__ (with form parameters "name" and "price" or JSON/XML data)

__Examples of JSON and XML data for POST and PUT requests:__

```json
{
	"in_stock": true,
	"name": "bread",
	"price": 20
}
```
```xml
<item id="1">
	<name>bread</name>
	<price>20</price>
	<in_stock>true</in_stock>
</item>
```

# JAVA Client

Java console application is available in client package under src folder.
It can be used to send requests and get responses in any format(HTML, JSON, XML).
It reads the command from standard input which should be __http-method path format__ form.

Examples:
* get products json
* get products/1 xml
* post products xml
* put products/2 html
