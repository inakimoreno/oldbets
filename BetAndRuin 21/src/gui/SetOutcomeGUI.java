package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import businessLogic.BlFacade;
import configuration.UtilDate;
import domain.Event;
import domain.Option;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SetOutcomeGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private BlFacade businessLogic;

	private JComboBox<Event> eventComboBox = new JComboBox<Event>();
	DefaultComboBoxModel<Event> eventModel = new DefaultComboBoxModel<Event>();

	private JLabel listOfEventsLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("ListEvents"));
	private JLabel queryLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("Question"));
	private JLabel eventDateLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("EventDate"));
	private JCalendar calendar = new JCalendar();
	private Calendar previousCalendar = null;
	private Calendar currentCalendar = null;

	private JScrollPane eventScrollPane = new JScrollPane();
	private JButton closeBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel msgLbl = new JLabel();
	private JLabel errorLbl = new JLabel();

	private Vector<Date> datesWithEventsInCurrentMonth = new Vector<Date>();

	
	private ArrayList<Option> options = new ArrayList<>();
	private final JButton confirmBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SetOutcomeGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JComboBox<Question> questionComboBox = new JComboBox<>();
	private final JComboBox<Option> optionComboBox = new JComboBox<>();
	
	public void setBusinessLogic(BlFacade bl) {
		businessLogic = bl;		
	}

	public SetOutcomeGUI(BlFacade bl, Vector<domain.Event> v, MainGUI mainGui) {
		SetOutcomeGUI createQuestionGui = this; 
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainGui.setEnabled(true);
				createQuestionGui.setVisible(false);
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
	DefaultComboBoxModel<Question> questionModel = new DefaultComboBoxModel<Question>();
	DefaultComboBoxModel<Option> optionModel = new DefaultComboBoxModel<Option>();

	private void jbInit(Vector<domain.Event> v, MainGUI mainGui) throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(646, 415));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestion"));
		
		eventComboBox.addActionListener(new ActionListener	() {
			public void actionPerformed(ActionEvent e) {
				
				Event ev = (Event)eventComboBox.getSelectedItem();
				System.out.println(ev.getQuestions());
				questionComboBox.removeAllItems();
				
				for(Question question:ev.getQuestions()) {
					if(!question.isAnswered())
						questionComboBox.addItem(question);
				}
			}
		});

		eventComboBox.setModel(eventModel);
		eventComboBox.setBounds(new Rectangle(275, 47, 250, 20));
		listOfEventsLbl.setBounds(new Rectangle(290, 18, 277, 20));
		queryLbl.setBounds(new Rectangle(25, 211, 75, 20));

		calendar.setBounds(new Rectangle(40, 50, 225, 150));
		eventScrollPane.setBounds(new Rectangle(25, 44, 346, 116));
		closeBtn.setBounds(new Rectangle(275, 335, 130, 30));
		closeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainGui.setEnabled(true);
				jButtonClose_actionPerformed(e);
			}
		});

		msgLbl.setBounds(new Rectangle(275, 182, 305, 20));
		msgLbl.setForeground(Color.red);

		errorLbl.setBounds(new Rectangle(100, 287, 305, 20));
		errorLbl.setForeground(Color.red);

		this.getContentPane().add(msgLbl, null);
		this.getContentPane().add(errorLbl, null);

		this.getContentPane().add(closeBtn, null);
		this.getContentPane().add(queryLbl, null);
		this.getContentPane().add(listOfEventsLbl, null);
		this.getContentPane().add(eventComboBox, null);

		this.getContentPane().add(calendar, null);

		datesWithEventsInCurrentMonth = businessLogic.getEventsMonth(calendar.getDate());
		paintDaysWithEvents(calendar,datesWithEventsInCurrentMonth);

		eventDateLbl.setBounds(new Rectangle(40, 15, 140, 25));
		eventDateLbl.setBounds(40, 16, 140, 25);
		getContentPane().add(eventDateLbl);
		
		JLabel optionsLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("option"));
		optionsLabel.setBounds(25, 250, 46, 14);
		getContentPane().add(optionsLabel);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				businessLogic.setOutcome(((Question)questionComboBox.getSelectedItem()), ((Option)optionComboBox.getSelectedItem()));
				businessLogic.updateBets((Event) eventComboBox.getSelectedItem(), (Question) questionComboBox.getSelectedItem(), (Option)optionComboBox.getSelectedItem());
				businessLogic.pay();
				businessLogic.setAnswered((Question)questionComboBox.getSelectedItem());
			}
		});
		confirmBtn.setBounds(94, 336, 117, 29);
		
		getContentPane().add(confirmBtn);
		
		questionComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionComboBox.removeAllItems();
				Question qu = (Question)questionComboBox.getSelectedItem();
				ArrayList<Option> options= new ArrayList<>();
				//System.out.println(qu.getQuestion());
				//System.out.println(qu.getOptions());
				//optionComboBox.setModel(optionModel);
				if(qu!=null) {
				for(Option option:qu.getOptions()) {
					optionComboBox.addItem(option);
				}
				}
				optionComboBox.repaint();
			}
		});
		questionComboBox.setBounds(100, 211, 165, 20);
		
		getContentPane().add(questionComboBox);
		optionComboBox.setBounds(100, 256, 165, 20);
		
		getContentPane().add(optionComboBox);


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
						eventComboBox.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events)
							eventComboBox.addItem(ev);
						eventComboBox.repaint();

						if (events.size() == 0)
							confirmBtn.setEnabled(false);
						else
							confirmBtn.setEnabled(true);

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

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}