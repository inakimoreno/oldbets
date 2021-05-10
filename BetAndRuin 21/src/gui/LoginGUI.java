package gui;


import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BlFacade;
import domain.User;
import exceptions.UserAlreadyExists;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


@SuppressWarnings("serial")
public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField usernameInput;
	private JPasswordField passwordInput;

	private BlFacade businessLogic;
	
	JTextPane messagePane = new JTextPane();
	private JButton registerButton;
	private MainGUI mainGui;
	
	/**
	 * Create the frame.
	 */
	public LoginGUI(BlFacade businessLogic, MainGUI mainGui/*, BrowseQuestionsGUI browse*/) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		//this.browse = browse;
		this.mainGui = mainGui;
		this.businessLogic = businessLogic;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel usernameLoginLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("usernameLoginLabel"));
		
		usernameInput = new JTextField();
		usernameInput.setColumns(10);
		
		JLabel passwordLoginLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("passwordLoginLabel"));
		
		passwordInput = new JPasswordField();
		
		JButton loginButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("login"));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logIn();
				
			}
		});
		
		registerButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		}
		);
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(passwordLoginLabel)
						.addComponent(usernameLoginLabel))
					.addGap(35)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(registerButton)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(loginButton))
						.addComponent(passwordInput, Alignment.LEADING)
						.addComponent(usernameInput, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
						.addComponent(messagePane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(88, Short.MAX_VALUE))
		);
		messagePane.setBackground(SystemColor.menu);
		messagePane.setEditable(false);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(51)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(usernameInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(usernameLoginLabel))
					.addGap(40)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordLoginLabel)
						.addComponent(passwordInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(messagePane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(loginButton)
						.addComponent(registerButton))
					.addContainerGap(41, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void close() {
		mainGui.setEnabled(true);
		this.setVisible(false);
	}
	
	@SuppressWarnings("deprecation")
	private void logIn(){
		User user = new User();
		user = businessLogic.getUser(usernameInput.getText(), passwordInput.getText());
		if(user==null) {
			messagePane.setText(ResourceBundle.getBundle("Etiquetas").getString("wrongLogin"));
		}
		else {
			mainGui.loginButton.setVisible(false);
			mainGui.logOutButton.setVisible(true);
			businessLogic.setCurrentUser(user);
			mainGui.registerButton.setVisible(false);
			if(user.isAdmin()) {
				mainGui.createQuestionBtn.setVisible(true);
				mainGui.setOutcomeButton.setVisible(true);
			}
			else
				mainGui.createQuestionBtn.setVisible(false);
			mainGui.currentUserLabel.setText(user.getUsername());
			close();
		}
	}
	
}
