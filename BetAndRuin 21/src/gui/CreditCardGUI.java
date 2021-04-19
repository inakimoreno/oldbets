package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class CreditCardGUI extends JFrame {

	private JPanel contentPane;
	private JTextField holderName;
	private JTextField cardNumber;
	private JTextField validity;
	private JTextField cvv;


	/**
	 * Create the frame.
	 */
	public CreditCardGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel titleLabel = new JLabel("Please fill in your credit card informations");
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		JLabel holderLabel = new JLabel("Holder's name :");
		
		JLabel cardNumbLabel = new JLabel("Credit card number :");
		
		JLabel validLabel = new JLabel("Validity date :");
		
		JLabel cvvLabel = new JLabel("CVV :");
		
		holderName = new JTextField();
		holderName.setColumns(10);
		
		cardNumber = new JTextField();
		cardNumber.setColumns(10);
		
		validity = new JTextField();
		validity.setColumns(10);
		
		cvv = new JTextField();
		cvv.setColumns(10);
		
		JButton confirmButton = new JButton("Confirm");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(26)
							.addComponent(titleLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addComponent(holderLabel)
									.addGap(18)
									.addComponent(holderName, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addComponent(cardNumbLabel)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cardNumber, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(validLabel)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(validity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
									.addComponent(cvvLabel)
									.addGap(18)
									.addComponent(cvv, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
							.addGap(20)))
					.addGap(14))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(158)
					.addComponent(confirmButton)
					.addContainerGap(165, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(titleLabel)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(holderLabel)
						.addComponent(holderName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cardNumbLabel)
						.addComponent(cardNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(validLabel)
						.addComponent(validity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cvvLabel)
						.addComponent(cvv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
					.addComponent(confirmButton)
					.addGap(37))
		);
		contentPane.setLayout(gl_contentPane);
	}
}