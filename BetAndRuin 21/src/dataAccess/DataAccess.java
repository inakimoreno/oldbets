package dataAccess;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Bet;
import domain.CreditCard;
import domain.Event;
import domain.Option;
import domain.Question;
import domain.User;
import exceptions.EventAlreadyExists;
import exceptions.QuestionAlreadyExist;
import exceptions.UserAlreadyExists;

/**
 * Implements the Data Access utility to the objectDb database
 */
public class DataAccess  {

	protected EntityManager  db;
	protected EntityManagerFactory emf;

	ConfigXML config = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode)  {
		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + 
				config.isDataAccessLocal() + " getDatabBaseOpenMode: " + config.getDataBaseOpenMode());
		open(initializeMode);
	}

	public DataAccess()  {	
		this(false);
	}


	/**
	 * This method initializes the database with some trial events and questions. 
	 * It is invoked by the business logic when the option "initialize" is used 
	 * in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){

		db.getTransaction().begin();

		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) { month = 0; year += 1;}  

			Event ev1 = new Event(1, "Atlï¿½tico-Athletic", UtilDate.newDate(year, month, 17));
			Event ev2 = new Event(2, "Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event(3, "Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event(4, "Alavï¿½s-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event(5, "Espaï¿½ol-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event(7, "Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event(8, "Girona-Leganï¿½s", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event(10, "Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event(11, "Atletico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event(12, "Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event(13, "Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event(14, "Alavï¿½s-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event(15, "Espaï¿½ol-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));


			Event ev17 = new Event(17, "Mï¿½laga-Valencia", UtilDate.newDate(year, month + 1, 28));
			Event ev18 = new Event(18, "Girona-Leganï¿½s", UtilDate.newDate(year, month + 1, 28));
			Event ev19 = new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year, month + 1, 28));
			Event ev20 = new Event(20, "Betis-Real Madrid", UtilDate.newDate(year, month + 1, 28));
			/*
			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			
			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("ï¿½Quiï¿½n ganarï¿½ el partido?", 1);
				q2 = ev1.addQuestion("ï¿½Quiï¿½n meterï¿½ el primer gol?", 2);
				q3 = ev11.addQuestion("ï¿½Quiï¿½n ganarï¿½ el partido?", 1);
				q4 = ev11.addQuestion("ï¿½Cuï¿½ntos goles se marcarï¿½n?", 2);
				q5 = ev17.addQuestion("ï¿½Quiï¿½n ganarï¿½ el partido?", 1);
				q6 = ev17.addQuestion("ï¿½Habrï¿½ goles en la primera parte?", 2);
			}
			else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1);
				q2 = ev1.addQuestion("Who will score first?", 2);
				q3 = ev11.addQuestion("Who will win the match?", 1);
				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
				q5 = ev17.addQuestion("Who will win the match?", 1);
				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
			}			
			else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);
			}

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);
			 */
			/*db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);*/

			db.getTransaction().commit();
			System.out.println("The database has been initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum, ArrayList<Option> options) 
			throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event = " + event + " question = " +
				question + " minimum bet = " + betMinimum + "options="+options);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.doesQuestionExist(question)) throw new QuestionAlreadyExist(
				ResourceBundle.getBundle("Etiquetas").getString("ErrorQuestionAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum, options);
		db.persist(ev);
		// in questions property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;
	}

	public void createEvent(String eventName, Date date) throws EventAlreadyExists{
		Vector<Event> eventList = getEvents(date);
		for(Event ev: eventList) {
			if(ev.getDescription().equals(eventName))
				throw new EventAlreadyExists(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventAlreadyExists"));
		}
		
		/*int year = date.getYear();
		int month = date.getMonth();
		int day = date.getDay();
		System.out.println(day);
		date = UtilDate.newDate(year + 1900 , month, day);*/
		db.getTransaction().begin();
		Event ev = new Event(eventName, date);
		db.persist(ev);
		db.getTransaction().commit();
		
	}
	
	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", 
				Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev:events){
			System.out.println(ev.toString());		 
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates in a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	

		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);


		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev "
				+ "WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d:dates){
			System.out.println(d.toString());		 
			res.add(d);
		}
		return res;
	}
	
	/*public Vector<Bet> getBets (User user){
		Vector<Bet> bets = new Vector<Bet>();
		this.getUser(user.getUsername(), user.getPassword());
		
		return bets;
	}
	*/
	
	public void open(boolean initializeMode){

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + 
				config.isDataAccessLocal() + " getDatabBaseOpenMode: " + config.getDataBaseOpenMode());

		String fileName = config.getDataBaseFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (config.isDataAccessLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", config.getDataBaseUser());
			properties.put("javax.persistence.jdbc.password", config.getDataBasePassword());

			emf = Persistence.createEntityManagerFactory("objectdb://" + config.getDataAccessNode() +
					":"+config.getDataAccessPort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}
	}

	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion => event = " + event + 
				" question = " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.doesQuestionExist(question);
	}

	
	public void createUser(String username, String password, String fullName, String email, CreditCard creditCard, boolean isAdmin) throws UserAlreadyExists{

		User us = new User(username, password, isAdmin, fullName, email, creditCard);

		if (us.equals(getUser(username, password))||existsUser(username)) throw new UserAlreadyExists(
				ResourceBundle.getBundle("Etiquetas").getString("ErrorUserAlreadyExists"));

		db.getTransaction().begin();
		db.persist(us);
		db.persist(creditCard);
		db.getTransaction().commit();
	}
	
	public boolean existsUser(String username) {
		TypedQuery<User> us = db.createQuery("SELECT us FROM User us "
				+ "WHERE us.username =\""+username+"\" ", User.class);
		try {
		us.getSingleResult();
		}catch(NoResultException e) {
			System.out.println("Username or password exception: "+e.getMessage());
			return false;
		}
		return true;
	}
	
	public User getUser(String username, String password){
		User ret=null;
		TypedQuery<User> us = db.createQuery("SELECT us FROM User us "
				+ "WHERE us.username =\""+username+"\" AND us.password =\""+password+"\"", User.class);
		try {
		ret = us.getSingleResult();
		}catch(NoResultException e) {
			System.out.println("Username or password exception: "+e.getMessage());
			return null;
		}
		
		return ret;
	}
	
	public void addBetToUser(User us, Event ev, Question qu, Option opt, float amount) {
		Bet bet = new Bet(ev,qu,opt,amount,us.getUsername());
		User user = getUser(us.getUsername(),us.getPassword());
		storeBet(bet);
		db.getTransaction().begin();
		user.addBet(bet);
		db.getTransaction().commit();	
	}
	
	public void storeBet(Bet bet) {
		db.getTransaction().begin();
		db.persist(bet);
		db.getTransaction().commit();
	}
	
	public void close(){
		db.close();
		System.out.println("DataBase is closed");
	}
}