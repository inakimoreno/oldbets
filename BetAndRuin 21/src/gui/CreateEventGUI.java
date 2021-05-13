package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import businessLogic.BlFacade;
import configuration.UtilDate;
import domain.Event;
import domain.Option;
import exceptions.EventAlreadyExists;
import exceptions.EventFinished;
import exceptions.PastDate;
import exceptions.QuestionAlreadyExist;
import javax.swing.JList;
import javax.swing.SwingConstants;

public class CreateEventGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private BlFacade businessLogic;
	DefaultListModel<Event> eventModel = new DefaultListModel<Event>();

	private JLabel listOfEventsLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("EventsForTheDay"));
	private JLabel eventLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("Event"));
	private JLabel eventDateLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("EventDate"));

	private JTextField eventText = new JTextField();
	private JCalendar calendar = new JCalendar();
	private Calendar previousCalendar = null;
	private Calendar currentCalendar = null;

	private JScrollPane eventScrollPane = new JScrollPane();

	private JButton createBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
	private JButton closeBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel msgLbl = new JLabel();
	private JLabel errorLbl = new JLabel();

	private Vector<Date> datesWithEventsInCurrentMonth = new Vector<Date>();

	
	private ArrayList<Option> options = new ArrayList<>();
	
	public void setBusinessLogic(BlFacade bl) {
		businessLogic = bl;		
	}

	public CreateEventGUI(BlFacade bl, Vector<domain.Event> v, MainGUI mainGui) {
		CreateEventGUI createEventGui = this; 
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainGui.setEnabled(true);
				createEventGui.setVisible(false);
			}
		});
		businessLogic = bl;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		try {
			jbInit(v, mainGui);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<domain.Event> v, MainGUI mainGui) throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(646, 415));
		listOfEventsLbl.setBounds(new Rectangle(290, 18, 277, 20));
		eventLbl.setBounds(new Rectangle(29, 244, 75, 20));
		eventText.setBounds(new Rectangle(100, 244, 429, 20));

		calendar.setBounds(new Rectangle(40, 50, 225, 150));
		eventScrollPane.setBounds(new Rectangle(25, 44, 346, 116));

		createBtn.setBounds(new Rectangle(100, 335, 130, 30));

		createBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		closeBtn.setBounds(new Rectangle(275, 335, 130, 30));
		closeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainGui.setEnabled(true);
				jButtonClose_actionPerformed(e);
			}
		});
		msgLbl.setHorizontalAlignment(SwingConstants.CENTER);

		msgLbl.setBounds(new Rectangle(154, 211, 305, 20));
		msgLbl.setForeground(Color.red);

		errorLbl.setBounds(new Rectangle(100, 287, 305, 20));
		errorLbl.setForeground(Color.red);

		this.getContentPane().add(msgLbl, null);
		this.getContentPane().add(errorLbl, null);

		this.getContentPane().add(closeBtn, null);
		this.getContentPane().add(createBtn, null);
		this.getContentPane().add(eventText, null);
		this.getContentPane().add(eventLbl, null);
		this.getContentPane().add(listOfEventsLbl, null);

		this.getContentPane().add(calendar, null);

		datesWithEventsInCurrentMonth = businessLogic.getEventsMonth(calendar.getDate());
		paintDaysWithEvents(calendar,datesWithEventsInCurrentMonth);

		eventDateLbl.setBounds(new Rectangle(40, 15, 140, 25));
		eventDateLbl.setBounds(40, 16, 140, 25);
		getContentPane().add(eventDateLbl);
		
		JList eventList = new JList(eventModel);
		eventList.setEnabled(false);
		eventList.setBounds(384, 213, 225, 153);
		getContentPane().add(eventList);
		
		JScrollPane eventscrollPane = new JScrollPane(eventList);
		eventscrollPane.setEnabled(false);
		eventscrollPane.setBounds(300, 49, 229, 150);
		getContentPane().add(eventscrollPane);


		// Code for JCalendar
		this.calendar.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					calendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					currentCalendar = (Calendar) propertychangeevent.getOldValue();
					previousCalendar = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: "+currentCalendar.getTime());
					System.out.println("calendarAct: "+previousCalendar.getTime());
					DateFormat dateformat1 = DateFormat.getDateInstance(1, calendar.getLocale());

					int monthAnt = currentCalendar.get(Calendar.MONTH);
					int monthAct = previousCalendar.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente, 
							// devolverá 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							previousCalendar.set(Calendar.MONTH, monthAnt + 1);
							previousCalendar.set(Calendar.DAY_OF_MONTH, 1);
						}

						calendar.setCalendar(previousCalendar);

						datesWithEventsInCurrentMonth = businessLogic.getEventsMonth(calendar.getDate());
					}

					paintDaysWithEvents(calendar,datesWithEventsInCurrentMonth);

					Date firstDay = UtilDate.trim(previousCalendar.getTime());

					try {
						Vector<domain.Event> events = businessLogic.getEvents(firstDay);

						if (events.isEmpty())
							listOfEventsLbl.setText(ResourceBundle.getBundle("Etiquetas").
									getString("NoEvents") + ": " + dateformat1.
									format(previousCalendar.getTime()));
						else
							listOfEventsLbl.setText(ResourceBundle.getBundle("Etiquetas").
									getString("Events") + " : " + dateformat1.
									format(previousCalendar.getTime()));
						
						System.out.println("Events " + events);
						eventModel.removeAllElements(); 
						for (domain.Event ev : events)
							eventModel.addElement(ev);
						eventList.repaint();

						if (events.size() == 0)
							createBtn.setEnabled(true);
						else
							createBtn.setEnabled(true);

					} catch (Exception e1) {

						errorLbl.setText(e1.getMessage());
					}
				}
			}
		});
	}

	public static void paintDaysWithEvents(JCalendar jCalendar, 
			Vector<Date> datesWithEventsCurrentMonth) {
		// For each day with events in current month, the background color for that day is changed.

		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;

		for (Date d:datesWithEventsCurrentMonth){
			calendar.setTime(d);
			System.out.println(d);

			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish

			Component o = jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
	}

	private void jButtonCreate_actionPerformed(ActionEvent e) {
		Calendar cal = calendar.getCalendar();
		
		Date date = UtilDate.newDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		
		//domain.Event event = ((domain.Event) eventComboBox.getSelectedItem());

		try {
			errorLbl.setText("");
			msgLbl.setText("");

			// Displays an exception if the query field is empty
			String inputEvent = eventText.getText();

			if (inputEvent.length() > 0) {
					businessLogic.createEvent(inputEvent, date);
					options.clear();
					msgLbl.setText(ResourceBundle.getBundle("Etiquetas").getString("EventCreated"));
			} else
				msgLbl.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorEvent"));
		} catch (PastDate e1) {
			msgLbl.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorPastDate"));
		} catch (EventAlreadyExists e1) {
			msgLbl.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventAlreadyExists"));
		} catch (java.lang.NumberFormatException e1) {
			errorLbl.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}