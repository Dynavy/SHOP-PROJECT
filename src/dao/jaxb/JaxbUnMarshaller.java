package dao.jaxb;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import model.Product;
import model.ProductList;

public class JaxbUnMarshaller {

	// Initializes and unmarshals the XML data into a ProductList object
	public ProductList init() {
		ProductList products = null;
		try {
			
			// Create a JAXB context (instance) for marshalling and unmarshalling ProductList objects.
			JAXBContext context = JAXBContext.newInstance(ProductList.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			// Make sure that the ID of the products start at 1.
			Product.setTotalProducts(1);
			// Store products on our products List.
			products = (ProductList) unmarshaller.unmarshal(new File("./jaxb/inputInventory.xml"));

			// Iterate through the products and calculate public price.
			for (Product product : products.getProducts()) {
				product.publicPriceCalculation();
			}

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return products;
	}
}