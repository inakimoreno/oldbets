package businessLogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import domain.Bet;
import domain.CreditCard;
import domain.Event;
import domain.Option;
import domain.Question;
import domain.User;
import exceptions.EventAlreadyExists;
import exceptions.EventFinished;
import exceptions.PastDate;
import exceptions.QuestionAlreadyExist;
import exceptions.UserAlreadyExists;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BlFacade  {

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
	@WebMethod
	Question createQuestion(Event event, String question, float betMinimum, ArrayList<Option> options) 
			throws EventFinished, QuestionAlreadyExist;
	
	@WebMethod
	public void createEvent(String eventName, Date date) throws PastDate, EventAlreadyExists;
		
	/**
	 * This method retrieves all the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates in a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	
	@WebMethod public void createUser(String username, String password, String fullName, String email, CreditCard creditCard, boolean isAdmin) throws UserAlreadyExists;
	
	
	@WebMethod public User getUser(String username, String password);
	
	/*
	@WebMethod public void setCurrentUser(User currentUser);
	
	
	@WebMethod public User getCurrentUser();
	*/
	
	@WebMethod public boolean addBetToUser(User user,Event ev, Question qu, Option option, String amount);
	
	
	@WebMethod public ArrayList<Bet> getBets(User user);
	
	
	@WebMethod public CreditCard getCreditCard(String cardNumber);
	
	
	@WebMethod public void addMoneyCreditCard(String cardNumber, float amount);
	
	
	@WebMethod public void substractMoneyCreditCard(String cardNumber, float amount);
	
	
	@WebMethod public void addBalance(float amount,User user);
	
	
	@WebMethod public void substractBalance(float amount,User user);
	
	
	@WebMethod public float getBalance(User user);
	
	
	@WebMethod public void setOutcome(Question qu, Option opt);
	
	
	@WebMethod public void updateBets(Event ev, Question qu, Option opt);
	
	
	@WebMethod public void pay();
	
	
	@WebMethod public void setAnswered(Question qu);
	
	
	@WebMethod public List<User> getAllUsers();
	
	
	@WebMethod public List<Bet> getBetsUser(User user);
}