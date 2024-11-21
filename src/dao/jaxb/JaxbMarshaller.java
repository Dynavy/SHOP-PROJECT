package dao.jaxb;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import model.Product;
import model.ProductList;

public class JaxbMarshaller {

	// Method to marshal a ProductList object to XML
	public boolean init(ProductList productList) {
		try {

			JAXBContext context = JAXBContext.newInstance(ProductList.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String baseFileName = "jaxb/inventory_" + currentDate;
			String fileName = baseFileName + ".xml";
			int counter = 0;

			while (new File(fileName).exists()) {
				fileName = baseFileName + "_" + "(" + counter + ")" + ".xml";
				counter++;
			}

			marshaller.marshal(productList, new File(fileName));
			return true;
		} catch (JAXBException e) {
			e.printStackTrace();
			return false;
		}

	}
}
