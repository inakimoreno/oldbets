package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bet implements Serializable{

	private static final long serialVersionUID = 1L;
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@XmlID
	@Id
	@GeneratedValue
	private Integer betNumber;
	private Event event;
	private Question question;
	private Option option;
	private float bettedAmount;
	private float possibleRevenue;
	private boolean result;
	private boolean paid;
	
	public Bet() {
		super();
	}
	
	public Bet(Event event, Question question, Option option, float bettedAmount, String username) {
		super();
		this.event = event;
		this.question = question;
		this.option = option;
		this.bettedAmount = bettedAmount;
		this.possibleRevenue = bettedAmount*this.option.getReturnRate();
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Event getEvent() {
		return event;
	}

	public Question getQuestion() {
		return question;
	}

	public Option getOption() {
		return option;
	}

	public Float getBettedAmount() {
		return bettedAmount;
	}

	public float getPossibleRevenue() {
		return possibleRevenue;
	}
	
	public void setPaid() {
		paid = true;
	}
	
	public boolean isPaid() {
		return paid;
	}
}
