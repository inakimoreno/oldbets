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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


@SuppressWarnings("serial")
public class LoginRegisterGUI extends JFrame {

	private JPanel contentPane;
	private JTextField usernameInput;
	private JPasswordField passwordInput;

	private BlFacade businessLogic;
	
	JTextPane messagePane = new JTextPane();
	private JButton registerButton;
	private BrowseQuestionsGUI browse;
	
	/**
	 * Create the frame.
	 */
	public LoginRegisterGUI(BlFacade businessLogic, BrowseQuestionsGUI browse) {
		/*addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				close();
			}
		});*/
		this.browse = browse;
		//this.browse.setEnabled(false);
		this.businessLogic = businessLogic;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
		
		registerButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("registerButton"));
		registerButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					if(!usernameInput.getText().equals("")&&!passwordInput.getText().equals("")) {
						businessLogic.createUser(usernameInput.getText(), passwordInput.getText());	
						logIn();
					}else {
						messagePane.setText(ResourceBundle.getBundle("Etiquetas").getString("emptyFiledRegisterError"));
					}
				}catch(UserAlreadyExists a) {
					messagePane.setText(ResourceBundle.getBundle("Etiquetas").getString("userInUse"));
				}
				
			}
		});
		
		
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
		this.setVisible(false);
		//this.browse.setEnabled(true);
	}
	
	@SuppressWarnings("deprecation")
	private void logIn(){
		User user = new User();
		user = businessLogic.getUser(usernameInput.getText(), passwordInput.getText());
		if(user==null) {
			messagePane.setText(ResourceBundle.getBundle("Etiquetas").getString("wrongLogin"));
		}
		else {
			businessLogic.setCurrentUser(user);
			browse.usernameLabel.setText(user.getUsername());
			browse.loginButton.setVisible(false);
			if(browse.questionTable.getSelectedRow()!=-1) {
				browse.bettingButton.setEnabled(true);
				browse.betMessagePane.setText("");
				browse.betAmountField.setVisible(true);
				browse.betLabel.setVisible(true);
			}
			close();
		}
	}
	
}
