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
	
	public CreditCard() {
		
	}
	
	public CreditCard(String holderName, String cardNumber, String validityDate, int CVV) {
		this.holderName = holderName;
		this.cardNumber = cardNumber;
		this.validityDate = validityDate;
		this.CVV= CVV;
	}
	
}
