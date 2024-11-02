package dao.xml;

import java.util.ArrayList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

import model.Amount;
import model.Product;

public class SaxReader extends DefaultHandler {

	ArrayList<Product> products;
	Product product;
	String value;
	String parsedElement;
	int counter = 1;

	public ArrayList<Product> getProducts() {
		return products;
	}

	@Override
	public void startDocument() throws SAXException {
		this.products = new ArrayList<>();
		 Product.setTotalProducts(1);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		switch (qName) {
		case "product":
			this.product = new Product(attributes.getValue("name") != null ? attributes.getValue("name") : "empty");
			break;
		case "wholeSalerPrice":
			this.product.setCurrency(attributes.getValue("currency"));
			break;
		}
		this.parsedElement = qName;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		value = new String(ch, start, length);
		switch (parsedElement) {
		case "wholeSalerPrice":
			this.product.setWholesalerPrice(new Amount(Float.valueOf(value)));
			break;
		case "stock":
			this.product.setStock(Integer.valueOf(value));
			break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		// We add the products objects to our ArrayList.
		if (qName.equals("product")) 
			
			this.products.add(product);
			this.parsedElement = "";
	}

	@Override
	public void endDocument() throws SAXException {
	}
}
