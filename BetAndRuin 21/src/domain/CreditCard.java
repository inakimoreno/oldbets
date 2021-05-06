package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class CreditCard implements Serializable{

	
	private String holderName;
	@Id
	@XmlID
	private String cardNumber;
	
	private String validityDate;
	
	private int CVV;
	private Integer balance = 1000;
	public CreditCard() {
		
	}
	
	public CreditCard(String holderName, String cardNumber, String validityDate, int CVV) {
		this.holderName = holderName;
		this.cardNumber = cardNumber;
		this.validityDate = validityDate;
		this.CVV= CVV;
	}
	
	public void takeBalance(Integer amount) {
			this.balance-=amount;
	}
	
	public void addBalance(Integer amount) {
		this.balance += amount;
	}

	public int getBalance() {
		return this.balance;
	}
	
	public String getNumber() {
		return this.cardNumber;
	}
	
}
