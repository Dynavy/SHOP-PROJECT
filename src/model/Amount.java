package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Amount {
	private double value;
	private final String currency = "€";

	@Override
	public String toString() {
		return value + currency;
	}

	public Amount(double value) {
		this.value = value;
	}

	// Constructor for MongoDB getInventory().
	public Amount(Double wholeSalerPrice, String currency) {
	    this.value = (wholeSalerPrice != null) ? wholeSalerPrice : 0.0;
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
		return currency;
	}
}