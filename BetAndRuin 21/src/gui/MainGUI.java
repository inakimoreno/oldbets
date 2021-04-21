package gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import businessLogic.BlFacade;
import domain.Event;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel mainPane;
	protected JLabel selectOptionLbl;
	private JButton browseQuestionsBtn;
	JButton createQuestionBtn;
	private JPanel localePane;
	private JRadioButton euskaraRbtn;
	private JRadioButton castellanoRbtn;
	private JRadioButton englishRbtn;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public JLabel currentUserLabel;
	JButton logOutButton;
	
	private BlFacade businessLogic;

	public BlFacade getBusinessLogic(){
		return businessLogic;
	}

	public void setBussinessLogic (BlFacade afi){
		businessLogic = afi;
	}


	public MainGUI() {
		super();
				
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				}
				catch (Exception e1) {
					System.out.println("Error: " + e1.toString() + " , likely problems "
							+ "with Business Logic or Data Accesse");
				}
				System.exit(1);
			}
		});
		
		MainGUI mainGui = this;

		this.setBounds(100, 100, 713, 499);
		//setBounds(100, 100, 551, 393);
		this.initializeMainPane(mainGui);
		this.setContentPane(mainPane);
		
		
		
		loginButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("login"));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginGUI loginGUI = new LoginGUI(businessLogic, mainGui);
				mainGui.setEnabled(false);
				loginGUI.setVisible(true);
			}
		});
		registerButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("registerButton")); //$NON-NLS-1$ //$NON-NLS-2$
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegisterGUI registerGUI = new RegisterGUI(businessLogic, mainGui);
				mainGui.setEnabled(false);
				registerGUI.setVisible(true);
			}
		});
		
		currentUserLabel = new JLabel();
		currentUserLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("usernameLabel"));
		
		logOutButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				businessLogic.setCurrentUser(null);
				currentUserLabel.setText("Guest");
				logOutButton.setVisible(false);
				loginButton.setVisible(true);
				registerButton.setVisible(true);
			}
		});
		logOutButton.setVisible(false);
		GroupLayout gl_mainPane = new GroupLayout(mainPane);
		gl_mainPane.setHorizontalGroup(
			gl_mainPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(browseQuestionsBtn, GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
				.addGroup(gl_mainPane.createSequentialGroup()
					.addComponent(selectOptionLbl, GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(currentUserLabel)
					.addGap(74))
				.addComponent(loginButton, GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
				.addComponent(registerButton, GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
				.addGroup(gl_mainPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(localePane, GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
					.addContainerGap())
				.addComponent(logOutButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
				.addComponent(createQuestionBtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
		);
		gl_mainPane.setVerticalGroup(
			gl_mainPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPane.createSequentialGroup()
					.addGroup(gl_mainPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(selectOptionLbl, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
						.addComponent(currentUserLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(browseQuestionsBtn, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(logOutButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(createQuestionBtn, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(23)
					.addComponent(localePane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(78))
		);
		mainPane.setLayout(gl_mainPane);

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
		//this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	MainGUI maingui = this;
	JButton registerButton;
	JButton loginButton;
	private void initializeMainPane(MainGUI mainGui) {
		mainPane = new JPanel();

		selectOptionLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
				getString("SelectUseCase"));
		selectOptionLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		initializeBrowseQuestionsBtn(mainGui);
		initializeCreateQuestionBtn(mainGui);

		initializeLocalePane();
	}
	
	private void initializeBrowseQuestionsBtn(MainGUI mainGui) {
		
		browseQuestionsBtn = new JButton();
		browseQuestionsBtn.setText(ResourceBundle.getBundle("Etiquetas").
				getString("BrowseQuestions"));
		browseQuestionsBtn.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				BrowseQuestionsGUI findQuestionsWindow = new BrowseQuestionsGUI(businessLogic, mainGui);
				mainGui.setEnabled(false);
				findQuestionsWindow.setVisible(true);
			}
		});
	}

	private void initializeCreateQuestionBtn(MainGUI mainGui) {
		createQuestionBtn = new JButton();
		createQuestionBtn.setVisible(false);
		createQuestionBtn.setText(ResourceBundle.getBundle("Etiquetas").
				getString("CreateQuestion"));
		createQuestionBtn.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				CreateQuestionGUI createQuestionWindow = new CreateQuestionGUI(businessLogic,
						new Vector<Event>(), mainGui);
				mainGui.setEnabled(false);
				createQuestionWindow.setBusinessLogic(businessLogic);
				createQuestionWindow.setVisible(true);
			}
		});
	}

	private void initializeLocalePane() {
		localePane = new JPanel();

		initializeEuskaraRbtn();
		localePane.add(euskaraRbtn);

		initializeCastellanoRbtn();
		localePane.add(castellanoRbtn);

		initializeEnglishRbtn();
		localePane.add(englishRbtn);
	}

	private void initializeEuskaraRbtn() {
		euskaraRbtn = new JRadioButton("Euskara");
		euskaraRbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				System.out.println("Locale: " + Locale.getDefault());
				redraw();
			}
		});
		buttonGroup.add(euskaraRbtn);
	}

	private void initializeCastellanoRbtn() {
		castellanoRbtn = new JRadioButton("Castellano");
		castellanoRbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				System.out.println("Locale: " + Locale.getDefault());
				redraw();
			}
		});
		buttonGroup.add(castellanoRbtn);
	}

	private void initializeEnglishRbtn() {
		englishRbtn = new JRadioButton("English");
		englishRbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				System.out.println("Locale: " + Locale.getDefault());
				redraw();				}
		});
		buttonGroup.add(englishRbtn);
	}

	private void redraw() {
		selectOptionLbl.setText(ResourceBundle.getBundle("Etiquetas").
				getString("SelectUseCase"));
		browseQuestionsBtn.setText(ResourceBundle.getBundle("Etiquetas").
				getString("BrowseQuestions"));
		createQuestionBtn.setText(ResourceBundle.getBundle("Etiquetas").
				getString("CreateQuestion"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}
}