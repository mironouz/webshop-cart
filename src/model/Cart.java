package model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Cart {

	@XmlElement(name="item")
	private List<Item> cart;

	public Item findById(int id) {
		for(Item i : cart) {
			if (i.getId() == id) {
				return i;
			}
		}
		return null;
	}

	public boolean deleteItemById(int id) {
		for(Item i : cart) {
			if (i.getId() == id) {
				cart.remove(i);
				return true;
			}
		}
		return false;
	}

}
