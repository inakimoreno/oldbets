package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class CreditCard implements Serializable{

	@Id
	private String holderName;
	@Id
	private String cardNumber;
	@Id
	private String validityDate;
	@Id
	private int CVV;
	
	public CreditCard(String holderName, String cardNumber, String validityDate, int CVV) {
		this.holderName = holderName;
		this.cardNumber = cardNumber;
		this.validityDate = validityDate;
		this.CVV= CVV;
	}
	
}
