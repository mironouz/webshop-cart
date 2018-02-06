package model;

import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"name", "price", "in_stock"})
public class Item {

	private static AtomicInteger counter = new AtomicInteger(0);

	@XmlAttribute private int id;
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
