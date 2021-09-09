package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BlFacade;
import domain.User;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MoneyGUI extends JFrame {

	private JPanel contentPane;
	private JTextField depositAmount;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private BlFacade businessLogic;
	private User currentUser;

	JTextPane alertTextPane;
	/**
	 * Create the frame.
	 */
	public MoneyGUI(BlFacade businessLogic, User currentUser, ProfileGUI prfGui) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				prfGui.setEnabled(true);
				close();
			}
		});
		this.businessLogic = businessLogic;
		this.currentUser = currentUser;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel title = new JLabel("Your wallet");
		title.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		
		JLabel balanceLabel = new JLabel("Current balance :");
		
		JLabel balance = new JLabel("");
		
		depositAmount = new JTextField();
		depositAmount.setColumns(10);
		
		balance.setText(String.valueOf(this.businessLogic.getBalance(currentUser)));
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prfGui.setEnabled(true);
				close();
			}
		});
		
		JRadioButton depositRadioButton = new JRadioButton("Deposit money");
		buttonGroup.add(depositRadioButton);
		
		JRadioButton withdrawRadioButton = new JRadioButton("Withdraw money");
		buttonGroup.add(withdrawRadioButton);
		
		JButton doneButton = new JButton("Accept");
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(Float.parseFloat(depositAmount.getText())>0) {
					if(depositRadioButton.isSelected()) {
						System.out.println("depositing");
						if(businessLogic.getCreditCard(currentUser.getCreditCard().getNumber()).getBalance()-Float.parseFloat(depositAmount.getText())>=0) {
							alertTextPane.setText(ResourceBundle.getBundle("Etiquetas").getString("moneyDepositedWallet"));
							businessLogic.substractMoneyCreditCard(currentUser.getCreditCard().getNumber(),Float.parseFloat(depositAmount.getText()));
							businessLogic.addBalance(Float.parseFloat(depositAmount.getText()),currentUser);
						}else {
							//error not enough money in bank
							alertTextPane.setText(ResourceBundle.getBundle("Etiquetas").getString("notEnoughFundsBank"));
						}
					}else if(withdrawRadioButton.isSelected()) {
						System.out.println("withdrawing");
						if(businessLogic.getBalance(currentUser)-Float.parseFloat(depositAmount.getText())>=0) {
							alertTextPane.setText(ResourceBundle.getBundle("Etiquetas").getString("moneyDepositedBank"));
							businessLogic.substractBalance(Float.parseFloat(depositAmount.getText()),currentUser);
							businessLogic.addMoneyCreditCard(currentUser.getCreditCard().getNumber(),Float.parseFloat(depositAmount.getText()));
						}else {
							//error not enough money in balance
							alertTextPane.setText(ResourceBundle.getBundle("Etiquetas").getString("notEnoughFundsWallet"));
						}
					}else {

					}
					balance.setText(String.valueOf(businessLogic.getBalance(currentUser)));
				}
			}
		});
		
		alertTextPane = new JTextPane();
		alertTextPane.setBackground(SystemColor.menu);
		
		JButton allButton = new JButton("All");
		allButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(withdrawRadioButton.isSelected())
					depositAmount.setText(Float.toString(businessLogic.getBalance(currentUser)));
			
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(depositRadioButton)
								.addComponent(balanceLabel))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
									.addComponent(balance)
									.addGap(91))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(allButton)
										.addComponent(withdrawRadioButton))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(depositAmount, 92, 92, 92))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(62)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(alertTextPane, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(cancelButton)
									.addGap(18)
									.addComponent(doneButton)))
							.addGap(95)))
					.addGap(75))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(170)
					.addComponent(title)
					.addContainerGap(170, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(title)
							.addGap(32)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(balance)
								.addComponent(balanceLabel))
							.addGap(26)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(depositRadioButton)
								.addComponent(withdrawRadioButton)
								.addComponent(depositAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(21)
							.addComponent(alertTextPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(doneButton)
								.addComponent(cancelButton)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(131)
							.addComponent(allButton)))
					.addContainerGap(42, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void close() {
		this.setVisible(false);
	}
}
