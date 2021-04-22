package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BlFacade;
import configuration.UtilDate;
import domain.Option;
import domain.Question;
import domain.User;

import javax.swing.JComboBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.SystemColor;

public class BrowseQuestionsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//private MainGUI maingui;

	private BlFacade businessLogic;

	private final JLabel eventDateLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("EventDate"));
	private final JLabel questionLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("Questions")); 
	private final JLabel eventLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("Events")); 

	private JButton closeBtn = new JButton(ResourceBundle.getBundle("Etiquetas").
			getString("Close"));

	// Code for JCalendar
	private JCalendar calendar = new JCalendar();
	private Calendar previousCalendar;
	private Calendar currentCalendar;
	private JScrollPane eventScrollPane = new JScrollPane();
	private JScrollPane questionScrollPane = new JScrollPane();

	private Vector<Date> datesWithEventsInCurrentMonth = new Vector<Date>();

	protected JTable eventTable= new JTable();
	protected JTable questionTable = new JTable();
 
	
	private DefaultTableModel eventTableModel;
	private DefaultTableModel questionTableModel;

	private String[] eventColumnNames = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] questionColumnNames = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QuestionN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Question")
	};@Override
	public Font getFont() {
		// TODO Auto-generated method stub
		return super.getFont();
	}
	

	JButton bettingButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("bettingButton"));
	
	JLabel usernameLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("usernameLabel"));
	
	JButton profileButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("myProfile")); //$NON-NLS-1$ //$NON-NLS-2$
	
	JComboBox<String> optionsComboBox = new JComboBox<String>();;
	
	JTextPane betMessagePane = new JTextPane();
	
	public void setBusinessLogic(BlFacade bl) {
		businessLogic = bl;
	}


	public BrowseQuestionsGUI(BlFacade bl, MainGUI mainGui) {
		BrowseQuestionsGUI brwGui = this;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainGui.setEnabled(true);
				brwGui.setVisible(false);
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		businessLogic = bl;
		bettingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int i = eventTable.getSelectedRow();
				domain.Event ev = (domain.Event)eventTableModel.getValueAt(i,2); // obtain ev object
				Vector<Question> queries = ev.getQuestions();
				
				for(Question query:queries) {
					System.out.println(query.getOptions());
				}
				
				int j = questionTable.getSelectedRow();
				Question qu = queries.get(j);
				
				Option option = null;
				
				for(Option opt:qu.getOptions()) {
					if(opt.getName().equals(optionsComboBox.getSelectedItem().toString())) {
						option = opt;
					}
				}
				
				if(!businessLogic.addBetToUser(ev,qu,option,betAmountField.getText())) 
					betMessagePane.setText(ResourceBundle.getBundle("Etiquetas").getString("invalidBetAmount"));
				else 
					betMessagePane.setText(ResourceBundle.getBundle("Etiquetas").getString("successfulBet"));
				
			}
		});
		bettingButton.setVisible(false);
		if(businessLogic.getCurrentUser()!=null) {
			usernameLabel.setText(businessLogic.getCurrentUser().getUsername());
		}
		try {
			jbInit(mainGui, brwGui);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	BrowseQuestionsGUI browse = this;
	protected JTextField betAmountField;
	protected final JLabel betLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("betLabel")); //$NON-NLS-1$ //$NON-NLS-2$
	private void jbInit(MainGUI mainGui, BrowseQuestionsGUI brwGui) throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 525));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("BrowseQuestions"));

		
		
		eventDateLbl.setBounds(new Rectangle(40, 15, 140, 25));
		questionLbl.setBounds(23, 250, 406, 14);
		eventLbl.setBounds(293, 19, 53, 16);

		this.getContentPane().add(eventDateLbl, null);
		this.getContentPane().add(questionLbl);
		this.getContentPane().add(eventLbl);

		closeBtn.setBounds(new Rectangle(273, 430, 130, 30));

		closeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainGui.setEnabled(true);
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(closeBtn, null);

		calendar.setBounds(new Rectangle(40, 50, 225, 150));

		datesWithEventsInCurrentMonth = businessLogic.getEventsMonth(calendar.getDate());
		CreateQuestionGUI.paintDaysWithEvents(calendar, datesWithEventsInCurrentMonth);

		// Code for JCalendar
		this.calendar.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent propertyChangeEvent) {

				if (propertyChangeEvent.getPropertyName().equals("locale")) {
					calendar.setLocale((Locale) propertyChangeEvent.getNewValue());
				}
				else if (propertyChangeEvent.getPropertyName().equals("calendar")) {
					previousCalendar = (Calendar) propertyChangeEvent.getOldValue();
					currentCalendar = (Calendar) propertyChangeEvent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, calendar.getLocale());
					Date firstDay = UtilDate.trim(new Date(calendar.getCalendar().getTime().getTime()));

					int previousMonth = previousCalendar.get(Calendar.MONTH);
					int currentMonth = currentCalendar.get(Calendar.MONTH);

					if (currentMonth != previousMonth) {
						if (currentMonth == previousMonth + 2) {
							// Si en JCalendar est� 30 de enero y se avanza al mes siguiente, 
							// devolver�a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este c�digo se dejar� como 1 de febrero en el JCalendar
							currentCalendar.set(Calendar.MONTH, previousMonth + 1);
							currentCalendar.set(Calendar.DAY_OF_MONTH, 1);
						}						

						calendar.setCalendar(currentCalendar);
						datesWithEventsInCurrentMonth = businessLogic.getEventsMonth(calendar.
								getDate());
					}

					CreateQuestionGUI.paintDaysWithEvents(calendar,datesWithEventsInCurrentMonth);

					try {
						eventTableModel.setDataVector(null, eventColumnNames);
						eventTableModel.setColumnCount(3); // another column added to allocate ev objects

						Vector<domain.Event> events = businessLogic.getEvents(firstDay);

						if (events.isEmpty() ) eventLbl.setText(ResourceBundle.getBundle("Etiquetas").
								getString("NoEvents") + ": " + dateformat1.format(currentCalendar.
										getTime()));
						else eventLbl.setText(ResourceBundle.getBundle("Etiquetas").
								getString("Events") + ": " + dateformat1.format(currentCalendar.
										getTime()));
						for (domain.Event ev : events){
							Vector<Object> row = new Vector<Object>();
							System.out.println("Events " + ev);
							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); 	// ev object added in order to obtain it with 
							eventTableModel.addRow(row);		
						}
						eventTable.getColumnModel().getColumn(0).setPreferredWidth(25);
						eventTable.getColumnModel().getColumn(1).setPreferredWidth(268);
						eventTable.getColumnModel().removeColumn(eventTable.getColumnModel().
								getColumn(2)); // not shown in JTable
					}
					catch (Exception e1) {
						questionLbl.setText(e1.getMessage());
					}
				}
			} 
		});

		this.getContentPane().add(calendar, null);

		eventScrollPane.setBounds(new Rectangle(292, 50, 346, 150));
		questionScrollPane.setBounds(new Rectangle(23, 274, 406, 116));

		eventTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = eventTable.getSelectedRow();
				domain.Event ev = (domain.Event)eventTableModel.getValueAt(i,2); // obtain ev object
				Vector<Question> queries = ev.getQuestions();
				
				questionTableModel.setDataVector(null, questionColumnNames);

				if (queries.isEmpty())
					questionLbl.setText(ResourceBundle.getBundle("Etiquetas").
							getString("NoQuestions") + ": " + ev.getDescription());
				else 
					questionLbl.setText(ResourceBundle.getBundle("Etiquetas").
							getString("SelectedEvent") + " " + ev.getDescription());

				for (domain.Question q : queries) {
					Vector<Object> row = new Vector<Object>();
					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					questionTableModel.addRow(row);	
				}
				questionTable.getColumnModel().getColumn(0).setPreferredWidth(25);
				questionTable.getColumnModel().getColumn(1).setPreferredWidth(268);
			}
		});
		
		
		
		JLabel minBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice"));
		minBet.setBounds(492, 226, 146, 14);
		minBet.setVisible(false);
		getContentPane().add(minBet);
		
		
		optionsComboBox.setBounds(492, 275, 130, 20);
		optionsComboBox.setVisible(false);
		getContentPane().add(optionsComboBox);
		
		
		eventScrollPane.setViewportView(eventTable);
		eventTableModel = new DefaultTableModel(null, eventColumnNames);

		eventTable.setModel(eventTableModel);
		eventTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		eventTable.getColumnModel().getColumn(1).setPreferredWidth(268);
		questionTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				optionsComboBox.removeAllItems();
				optionsComboBox.setVisible(true);
				bettingButton.setVisible(true);
				bettingButton.setEnabled(false);
				betMessagePane.setText(ResourceBundle.getBundle("Etiquetas").getString("loginToBet"));
				
				int i = eventTable.getSelectedRow();
				domain.Event ev = (domain.Event)eventTableModel.getValueAt(i,2); // obtain ev object
				Vector<Question> queries = ev.getQuestions();
				
				for(Question query:queries) {
					System.out.println(query.getOptions());
				}
				
				int j = questionTable.getSelectedRow();
				Question qu = queries.get(j);
				
				
				for(Option option:qu.getOptions()) {
					optionsComboBox.addItem(option.getName());
				}

				minBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice")+" "+qu.getBetMinimum());
				minBet.setVisible(true);
				
				//bettingOptionsLabel.setVisible(true);
				
				if(businessLogic.getCurrentUser()!=null) {
					bettingButton.setEnabled(true);
					betAmountField.setVisible(true);
					betLabel.setVisible(true);
					betMessagePane.setText("");
				}
			}
		});

		questionScrollPane.setViewportView(questionTable);
		questionTableModel = new DefaultTableModel(null, questionColumnNames);

		questionTable.setModel(questionTableModel);
		questionTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		questionTable.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.getContentPane().add(eventScrollPane, null);
		this.getContentPane().add(questionScrollPane, null);
		bettingButton.setBounds(492, 430, 130, 30);
		
		getContentPane().add(bettingButton);	
		
		
		usernameLabel.setBounds(450, 20, 89, 14);
		getContentPane().add(usernameLabel);
		
		betAmountField = new JTextField();
		betAmountField.setText(ResourceBundle.getBundle("Etiquetas").getString("betAmount")); //$NON-NLS-1$ //$NON-NLS-2$
		betAmountField.setBounds(492, 343, 130, 20);
		getContentPane().add(betAmountField);
		betAmountField.setVisible(false);
		betAmountField.setColumns(10);
		betLabel.setBounds(492, 318, 200, 14);
		betLabel.setVisible(false);
		
		getContentPane().add(betLabel);
		
		
		betMessagePane.setEditable(false);
		betMessagePane.setBackground(SystemColor.menu);
		betMessagePane.setBounds(450, 374, 200, 53);
		getContentPane().add(betMessagePane);
		profileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProfileGUI profile = new ProfileGUI(businessLogic, brwGui);
				brwGui.setEnabled(false);
				profile.setVisible(true);
			}
		});
		
		
		profileButton.setBounds(519, 16, 119, 23);
		profileButton.setVisible(false);
		if(businessLogic.getCurrentUser()!=null)
			profileButton.setVisible(true);
		getContentPane().add(profileButton);
		
		
		//bettingOptionsLabel.setBounds(492, 250, 146, 14);
		//bettingOptionsLabel.setVisible(false);
		//getContentPane().add(bettingOptionsLabel);
		
		
	}

	
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}