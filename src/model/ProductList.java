package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "products")
public class ProductList {

	private ArrayList<Product> products = new ArrayList<>();

	// Deserialization constructor.
	public ProductList() {
	
	}

	public ProductList(ArrayList<Product> products) {
		this.products = products;
	}

	// This method is automatically called thanks to the @XmlElement on the getter.
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	// Specifies that the XML element representing the list of products will be
	// named <products>.
	@XmlElement(name = "product")
	public ArrayList<Product> getProducts() {
		return products;
	}
}
