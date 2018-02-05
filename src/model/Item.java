package model;

import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlType(propOrder = {"name", "price", "in_stock"})
public class Item {

	private static AtomicInteger counter = new AtomicInteger(0);

	private int id;
	private String name;
	private int price;
	private boolean in_stock;

	public Item(String name, int price, boolean in_stock) {
		id = counter.incrementAndGet();
		this.name = name;
		this.price = price;
		this.in_stock = in_stock;
	}

}
