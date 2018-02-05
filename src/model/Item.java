package model;

import javax.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlType(propOrder = {"name", "price", "in_stock"})
public class Item {

	private String name;
	private int price;
	private boolean in_stock;

}
