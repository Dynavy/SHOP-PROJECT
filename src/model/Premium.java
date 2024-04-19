package model;

public class Premium extends Client {

	// Declare variables:

	private int points = 0;

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void premiumPoints(Amount amount) {

		// We get the value of the sales.
		double totalMoneySpent = amount.getValue();

		// Every 10€, adds one point and then substract 10. This repeats until the amount.getValue() is < 10. 
		for (double i = totalMoneySpent; i >= 10; i -= 10) {
			this.points++;
		}

		if (this.points > 0) {
			// Show to the user the premium points if available.
			System.out.println(ORANGE + "Total premium points: " + this.points + "." + "\n" + RESET);
		} else {
			System.err.println("You don't have any extra premium point.");
		}

	}

}