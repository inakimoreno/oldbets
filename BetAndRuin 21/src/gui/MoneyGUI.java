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
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MoneyGUI extends JFrame {

	private JPanel contentPane;
	private JTextField depositAmount;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private BlFacade businessLogic;

	/**
	 * Create the frame.
	 */
	public MoneyGUI(BlFacade businessLogic) {
		this.businessLogic = businessLogic;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		balance.setText(String.valueOf(businessLogic.getBalance()));
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				if(Integer.parseInt(depositAmount.getText())>0) {
					if(depositRadioButton.isSelected()) {
						System.out.println("depositing");
						if(businessLogic.getCreditCard(businessLogic.getCurrentUser().getCreditCard().getNumber()).getBalance()-Integer.parseInt(depositAmount.getText())>=0) {
							System.out.println("ok");
							businessLogic.substractMoneyCreditCard(businessLogic.getCurrentUser().getCreditCard().getNumber(),Integer.parseInt(depositAmount.getText()));
							businessLogic.addBalance(Integer.parseInt(depositAmount.getText()));
						}else {
							//error not enough money in bank
						}
					}else if(withdrawRadioButton.isSelected()) {
						System.out.println("withdrawing");
						if(businessLogic.getBalance()-Integer.parseInt(depositAmount.getText())>=0) {
							System.out.println("ok");
							businessLogic.substractBalance(Integer.parseInt(depositAmount.getText()));
							businessLogic.addMoneyCreditCard(businessLogic.getCurrentUser().getCreditCard().getNumber(),Integer.parseInt(depositAmount.getText()));
						}else {
							//error not enough money in balance
						}
					}else {

					}
					balance.setText(String.valueOf(businessLogic.getBalance()));
				}
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(depositRadioButton)
								.addComponent(balanceLabel))
							.addPreferredGap(ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(balance)
								.addComponent(withdrawRadioButton))
							.addGap(39)
							.addComponent(depositAmount, 92, 92, 92))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addGap(62)
							.addComponent(cancelButton)
							.addPreferredGap(ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
							.addComponent(doneButton)
							.addGap(56)))
					.addGap(84))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(170)
					.addComponent(title)
					.addContainerGap(279, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(title)
					.addGap(32)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(balance)
						.addComponent(balanceLabel))
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(depositAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(depositRadioButton)
						.addComponent(withdrawRadioButton))
					.addGap(59)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cancelButton)
						.addComponent(doneButton))
					.addContainerGap(42, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void close() {
		this.setVisible(false);
	}
}
