package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Amount {
	private double value;
	private final String currency  = "€";

	@Override
	public String toString() {
		return value + currency ;
	}

	public Amount(double value) {
		this.value = value;
	}
	
	// Deserialization constructor.
	public Amount() {
	  
    }

	@XmlValue
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@XmlAttribute(name = "currency")
	public String getCurrency() {
		return currency ;
	}
}