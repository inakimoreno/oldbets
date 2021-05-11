package businessLogic;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import domain.User;
import domain.Option;
import domain.Bet;
import domain.CreditCard;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import exceptions.UserAlreadyExists;


/**
 * Implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BlFacade")
public class BlFacadeImplementation implements BlFacade {

	DataAccess dbManager;
	ConfigXML config = ConfigXML.getInstance();

	private User currentUser;
	
	public BlFacadeImplementation()  {		
		System.out.println("Creating BlFacadeImplementation instance");
		boolean initialize = config.getDataBaseOpenMode().equals("initialize");
		dbManager = new DataAccess(initialize);
		if (initialize)
			dbManager.initializeDB();
		dbManager.close();
	}
	
	public BlFacadeImplementation(DataAccess dam)  {
		System.out.println("Creating BlFacadeImplementation instance with DataAccess parameter");
		if (config.getDataBaseOpenMode().equals("initialize")) {
			dam.open(true);
			dam.initializeDB();
			dam.close();
		}
		dbManager = dam;		
	}


	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	@Override
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum, ArrayList<Option> options) 
			throws EventFinished, QuestionAlreadyExist {

		//The minimum bid must be greater than 0
		dbManager.open(false);
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").
					getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum, options);		
		dbManager.close();
		return qry;
	}

	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@Override
	@WebMethod	
	public Vector<Event> getEvents(Date date)  {
		dbManager.open(false);
		Vector<Event>  events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}


	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@Override
	@WebMethod
	public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date>  dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	@Override
	@WebMethod
	public void createUser(String username, String password, String fullName, String email, CreditCard creditCard, boolean isAdmin) throws UserAlreadyExists{
		dbManager.open(false);
		dbManager.createUser(username, password, fullName, email, creditCard, isAdmin);		
		dbManager.close();
	}
	
	@Override
	@WebMethod
	public User getUser(String username, String password) {
		dbManager.open(false);
		User user = dbManager.getUser(username, password);
		dbManager.close();
		return user;
	}

	@WebMethod
	public void close() {
		dbManager.close();
	}

	@Override
	@WebMethod
	public void setCurrentUser(User user) {
		this.currentUser=user;
	}
	
	@Override
	@WebMethod
	public User getCurrentUser() {
		return currentUser;
	}
	
	@Override
	@WebMethod
	public boolean addBetToUser(Event ev, Question qu, Option option, String amount) {
		float floatAmount;
		try {
		floatAmount = Float.parseFloat(amount);
		}catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return false;
		}
		if(floatAmount<qu.getBetMinimum()) {
			return false;
		}
		dbManager.open(false);
		dbManager.addBetToUser(currentUser, ev, qu, option, floatAmount);
		dbManager.close();
		return true;
	}
	
	@Override
	@WebMethod
	public ArrayList<Bet> getBets(){
		ArrayList<Bet> bets = new ArrayList<Bet>();
		dbManager.open(false);
		User usr = dbManager.getUser(this.currentUser.getUsername(), this.currentUser.getPassword());
		bets = (ArrayList<Bet>) usr.getBets().clone();
		dbManager.close();
		return bets;
	}
	
	@Override
	@WebMethod
	public void addBalance(float amount) {
		dbManager.open(false);
		dbManager.addBalance(amount, this.currentUser);
		dbManager.close();
	}
	
	@Override
	@WebMethod
	public void substractBalance(float amount) {
		dbManager.open(false);
		dbManager.substractBalance(amount, this.currentUser);
		dbManager.close();
	}
	
	@Override
	@WebMethod
	public CreditCard getCreditCard(String cardNumber) {
		dbManager.open(false);
		CreditCard cc = dbManager.getCreditCard(cardNumber);
		dbManager.close();
		return cc;
	}
	
	@Override
	@WebMethod
	public float getBalance() {
		dbManager.open(false);
		float balance = dbManager.getBalance(this.currentUser);
		dbManager.close();
		return balance;
	}
	
	@Override
	@WebMethod
	public void addMoneyCreditCard(String cardNumber, float amount) {
		dbManager.open(false);
		dbManager.sumCreditCard(cardNumber, amount);
		dbManager.close();
	}
	
	@Override
	@WebMethod
	public void substractMoneyCreditCard(String cardNumber, float amount) {
		dbManager.open(false);
		dbManager.substractCreditCard(cardNumber, amount);
		dbManager.close();
	}
	
	@Override
	@WebMethod
	public void setOutcome(Question qu, Option opt) {
		dbManager.open(false);
		dbManager.setOutcome(qu.getQuestionNumber(),opt);
		dbManager.close();
	}
	
	public void updateBets(Event ev, Question qu, Option opt) {
		dbManager.open(false);
		dbManager.updateBets(ev, qu, opt);
		dbManager.close();
	}
	
	public List<User> getAllUsers(){
		dbManager.open(false);
		List<User> result = dbManager.getAllUsers();
		dbManager.close();
		return result;
	}
	
	public List<Bet> getBets(User user){
		dbManager.open(false);
		List<Bet> result = dbManager.getBets(user);
		dbManager.close();
		return result;
	}
	
	public void pay() {
		
		for(User user:getAllUsers()) {
			
			System.out.println(user);
			for(Bet bet:getBets(user)) {
				System.out.println("-------------");
				if(bet.isResult()&&!bet.isPaid()) {
					dbManager.open(false);
					System.out.println(user.getBalance());
					dbManager.pay(bet,bet.getPossibleRevenue(), user);
					//dbManager.setPaid(bet);
					dbManager.close();
					System.out.println(user.getBalance());
				}
			}
			
		}
		
	}
	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod	
	public void initializeBD(){
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}
}