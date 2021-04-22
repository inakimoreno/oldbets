package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BlFacade;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private JTextField fullName;
	private JTextField email;
	private JTextField username;
	private JPasswordField password;
	private JPasswordField confPassword;

	private BlFacade businessLogic;
	
	/**
	 * Create the frame.
	 */
	public RegisterGUI(BlFacade businessLogic, MainGUI mainGui) {

		this.businessLogic = businessLogic;
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel registrationLabel = new JLabel("REGISTRATION");
		registrationLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		JLabel nameLabel = new JLabel("Full name :");
		
		JLabel eMailLabel = new JLabel("E-mail address :");
		
		JLabel usernameLabel = new JLabel("Username :");
		
		JLabel passLabel = new JLabel("Password :");
		
		JLabel confPasswordLabel = new JLabel("Confirm password :");
		
		fullName = new JTextField();
		fullName.setColumns(10);
		
		email = new JTextField();
		email.setColumns(10);
		
		username = new JTextField();
		username.setColumns(10);
		
		password = new JPasswordField();
		
		confPassword = new JPasswordField();
		
		RegisterGUI regGui = this;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainGui.setEnabled(true);
				regGui.setVisible(false);
			}
		});
		
		JButton creditCardLinkButton = new JButton("Fill credit card informations");
		creditCardLinkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(password.getText().equals(confPassword.getText())) {
					if(!fullName.getText().equals("")&&!email.getText().equals("")&&!username.getText().equals("")&&!password.getText().equals("")) {
						CreditCardGUI credcardGUI = new CreditCardGUI(businessLogic, fullName.getText(), email.getText(), username.getText(), password.getText(), mainGui, regGui);
						regGui.setEnabled(false);
						credcardGUI.setVisible(true);
					}else {
						///You must fill all text fields
					}
				}else {
					//////// Passwords do not match
				}
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(148)
					.addComponent(registrationLabel)
					.addContainerGap(147, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(eMailLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(email, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(nameLabel)
							.addGap(18)
							.addComponent(fullName, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(confPasswordLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(confPassword, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(passLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(password, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(usernameLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(username, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(108)
					.addComponent(creditCardLinkButton)
					.addContainerGap(113, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(registrationLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(nameLabel)
						.addComponent(fullName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(eMailLabel)
						.addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(usernameLabel)
						.addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(passLabel)
						.addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(confPasswordLabel)
						.addComponent(confPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(creditCardLinkButton)
					.addContainerGap(103, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}