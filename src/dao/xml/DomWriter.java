package dao.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import model.Product;

public class DomWriter {

	// We create an instance of Document used to build the XML structure.
	private Document document;

	public DomWriter() {
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			System.out.println("ERROR generating document");
		}
	}

	public boolean generateXml(ArrayList<Product> products) {

		try {
			// Create the 'rootElement' and writes depending on the products list size.
			Element rootElement = document.createElement("products");
			rootElement.setAttribute("total", Integer.toString(products.size()));
			document.appendChild(rootElement);

			// Loop to iterate the products list and write with the elements.
			for (Product product : products) {

				// Creates a tag '<product>' with the name as an attribute.
				Element productElement = document.createElement("product");
				productElement.setAttribute("id", Integer.toString(product.getId()));

				// Creates a tag '<price>' with 'currency' and '€' as attributes.
				Element wholeSalerPriceElement = document.createElement("price");
				wholeSalerPriceElement.setAttribute("currency", "€");
				// The information is taken thanks to the WholeSalerPrice getter.
				wholeSalerPriceElement.setTextContent(String.valueOf(product.getWholesalerPrice().getValue()));

				// Creates a tag '<stock>' without any attributes.
				Element stockElement = document.createElement("stock");
				// The information is taken thanks to the stock getter.
				stockElement.setTextContent(String.valueOf(product.getStock()));

				// Append the tags of stock, publicPrice and wholeSalerPrice to the '<product>'
				// tag,
				productElement.appendChild(wholeSalerPriceElement);
				productElement.appendChild(stockElement);

				// Append the '<product>' element to the '<products>' tag.
				rootElement.appendChild(productElement);
			}

			// Create the file name with the current date
			String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String baseFileName = "xml/sales_" + currentDate;
			String fileName = baseFileName + ".xml";
			int counter = 1;

			// Loop to generate more than 1 XML document with the same name.
			while (new File(fileName).exists()) {
				fileName = baseFileName + "_" + "(" +counter + ")" + ".xml";
				counter++;
			}

			// Create FileWriter and PrintWriter.
			try (FileWriter fw = new FileWriter(fileName); PrintWriter pw = new PrintWriter(fw)) {

				// Logic to transform document to XML.
				Source source = new DOMSource(document);
				Result result = new StreamResult(pw);
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer = factory.newTransformer();
				transformer.transform(source, result);
			}
			// return true if the XML file has been written successfully.
			return true;
		} catch (IOException e) {
			System.out.println("Error when creating writer file: " + e.getMessage());
		} catch (TransformerException e) {
			System.out.println("Error transforming the document: " + e.getMessage());
		}

		// Return false if we were unable to write the XML.
		return false;
	}
}
