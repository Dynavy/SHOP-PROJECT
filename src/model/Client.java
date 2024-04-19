package model;

import main.Payable;

public class Client extends Person implements Payable {
	
	final String RESET = "\u001B[0m";
	final String AZUL_CLARO = "\u001B[94m";
	final String ORANGE = "\u001B[38;5;208m";
	final String VERDE_CLARO = "\u001B[92m";
	final String AMARILLO = "\u001B[33m";
	final String MAGENTA = "\u001B[35m";
	
	private final int MEMBER_ID = 456;
	private final double BALANCE = 50.00;
	
	// We declare the variables.
	private int memberID = MEMBER_ID;
	// The variable 'balance' is initialized with the value of the constant 'BALANCE', which has been transformed into an 'Amount' object.
	private Amount balance = new Amount (BALANCE);

	// Constructor for the class Premium.
	public Client() {

	}
		
	public Amount getBalance() {
		return balance;
	}

	// Constructor for the class shop.
	public Client(String client) {
		super(client);
		this.balance = new Amount(BALANCE);
		this.memberID = MEMBER_ID;
	}

	// Method to for the case 6.
	public boolean pay(Amount amount) {

		double resultadoFinal = 0.0;

		// We always substracts the value of the constant BALANCE to the product price.
		resultadoFinal = BALANCE - amount.getValue();
		Amount remainingMoney = new Amount (resultadoFinal);
		System.out.println(VERDE_CLARO + "\nRemaining money: " + remainingMoney + RESET);

		// Positive numbers returns true, negative numbers returns false.
		if (resultadoFinal >= 0) {
			return true;
		} else {
			return false;
		}

	}

}