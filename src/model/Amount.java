package model;

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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getCurrency() {
		return currency;
	}

}