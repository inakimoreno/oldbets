package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLogic.BlFacade;
import domain.Bet;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class ProfileGUI extends JFrame {

	private JPanel contentPane;
	private JTable betsTable;
	
	private BlFacade businessLogic;

	/**
	 * Create the frame.
	 */
	public ProfileGUI(BlFacade businessLogic) {
		this.businessLogic = businessLogic;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 858, 632);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel profileHeaderLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("myProfile"));
		profileHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		profileHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel usernameLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("usernameLoginLabel"));
		
		JLabel emailLabel = new JLabel("Email");
		
		JScrollPane ongoingBetsScrollPane = new JScrollPane();
		
		JLabel ongoingBetsLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("pongoingBetsLabel"));
		
		JButton closeButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(usernameLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(emailLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(ongoingBetsLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(ongoingBetsScrollPane, GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE))
						.addComponent(profileHeaderLabel, GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(713, Short.MAX_VALUE)
							.addComponent(closeButton, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(profileHeaderLabel)
					.addGap(28)
					.addComponent(usernameLabel)
					.addGap(35)
					.addComponent(emailLabel)
					.addGap(166)
					.addComponent(ongoingBetsLabel)
					.addGap(18)
					.addComponent(ongoingBetsScrollPane, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
					.addComponent(closeButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
		);
		
		betsTable = new JTable();
		betsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					ResourceBundle.getBundle("Etiquetas").getString("Event"),
					ResourceBundle.getBundle("Etiquetas").getString("Question"),
					ResourceBundle.getBundle("Etiquetas").getString("option"),
					ResourceBundle.getBundle("Etiquetas").getString("Betted"),
					ResourceBundle.getBundle("Etiquetas").getString("Revenue")
			}
		));
		betsTable.getColumnModel().getColumn(0).setPreferredWidth(203);
		betsTable.getColumnModel().getColumn(1).setPreferredWidth(204);
		betsTable.getColumnModel().getColumn(2).setPreferredWidth(121);
		betsTable.getColumnModel().getColumn(3).setPreferredWidth(82);
		betsTable.getColumnModel().getColumn(4).setPreferredWidth(97);
		ongoingBetsScrollPane.setViewportView(betsTable);
		contentPane.setLayout(gl_contentPane);
		
		ArrayList<Bet> myBets = businessLogic.getBets();
		for(Bet b: myBets) {
			Vector<Object> bet = new Vector<Object>();
			bet.add(b.getEvent().getDescription() + b.getEvent().getEventDate().toString());
			bet.add(b.getQuestion().getQuestion());
			bet.add(b.getOption().getName());
			bet.add(b.getBettedAmount());
			bet.add(b.getPossibleRevenue());
			
			
		}
	}
	private void close() {
		this.setVisible(false);
	}
}
