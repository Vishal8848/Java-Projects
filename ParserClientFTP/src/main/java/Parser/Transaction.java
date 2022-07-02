package Parser;

import java.util.Date;
import java.sql.Timestamp;

public class Transaction {

	public boolean type;						// Transaction Type: true - Debit | false - Credit
	public String ID;							// Transaction ID: Alphanumeric String
	public String currency; 					// Currency: String
	public String accounts[] = new String[2];	// Account Number: [ A/C No.1, A/C No.2 ] 
	public double amount;						// Amount: Double
	public Date timestamp;						// Date and Time: Timestamp
	
	Transaction(boolean type, String ID, String currency, String[] accounts, double amount, String timestamp) {
		
		this.type = type;
		this.ID = ID;
		this.currency = currency;
		this.accounts[0] = accounts[0];
		this.accounts[1] = accounts[1];
		this.amount = amount;
		Timestamp time = new Timestamp(Long.parseLong(timestamp));
		this.timestamp = (Date) time;
	}
}
