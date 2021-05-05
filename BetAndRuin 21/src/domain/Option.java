package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Option {
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@XmlID
	@Id
	@GeneratedValue
	private Integer optionNumber;
	private String name;
	private float returnRate;
	boolean outcome;
	
	public Option() {
		
	}
	public Option(String name, float returnRate) {
		this.name = name;
		this.returnRate = returnRate;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setReturnRate(float returnRate) {
		this.returnRate = returnRate;
	}
	
	public void setOutcome(boolean outcome) {
		this.outcome = outcome;
	}
	
	public String getName() {
		return this.name;
	}
	
	public float getReturnRate() {
		return this.returnRate;
	}
	
	public boolean getOutcome() {
		return this.outcome;
	}
}
