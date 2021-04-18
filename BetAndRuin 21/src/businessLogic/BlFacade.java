package businessLogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import domain.Bet;
import domain.Event;
import domain.Option;
import domain.Question;
import domain.User;
import exceptions.EventFinished;
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
	
	
	@WebMethod public void createUser(String username, String password) throws UserAlreadyExists;
	
	
	@WebMethod public User getUser(String username, String password);
	
	
	@WebMethod public void setCurrentUser(User currentUser);
	
	
	@WebMethod public User getCurrentUser();
	
	
	@WebMethod public boolean addBetToUser(Event ev, Question qu, Option option, String amount);
	
	@WebMethod public ArrayList<Bet> getBets();
	
	//@WebMethod public ArrayList<String> getQuestionOptions(Question qu);
}