package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer betNumber;
	private Event event;
	private Question question;
	private String option;
	private int bettedAmount;
	private int possibleRevenue;
	private int returnRate;
	private boolean result;
	private String userName;
	
	public Bet() {
		super();
	}
	
	public Bet(Event event, Question question, String option, int bettedAmount, String username) {
		super();
		this.event = event;
		this.question = question;
		this.option = option;
		this.bettedAmount = bettedAmount;
		this.possibleRevenue = bettedAmount*returnRate;
		this.userName = username;
	}

	public int getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(int returnRate) {
		this.returnRate = returnRate;
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

	public String getOption() {
		return option;
	}

	public int getBettedAmount() {
		return bettedAmount;
	}

	public int getPossibleRevenue() {
		return possibleRevenue;
	}
	
}
