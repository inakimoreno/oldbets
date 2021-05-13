package gui;

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
import domain.User;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PastBetsGUI extends JFrame {

	private JPanel contentPane;
	private JTable betsTable;
	
	private DefaultTableModel betsTableModel;
	
	private BlFacade businessLogic;
	private User currentUser;

	/**
	 * Create the frame.
	 */
	public PastBetsGUI(BlFacade businessLogic, User currentUser) {
		this.currentUser = currentUser;
		PastBetsGUI prfGui = this;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//brwGui.setEnabled(true);
				prfGui.setVisible(false);
			
			}
		});

		this.businessLogic = businessLogic;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 879, 557);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel profileHeaderLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("myProfile"));
		profileHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		profileHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JScrollPane ongoingBetsScrollPane = new JScrollPane();
		
		JLabel pastBetsLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("pastBetsLabel"));
		
		JButton closeButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//brwGui.setEnabled(true);
				close();
			}
		});
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(profileHeaderLabel, GroupLayout.DEFAULT_SIZE, 843, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(ongoingBetsScrollPane, GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap(734, Short.MAX_VALUE)
							.addComponent(closeButton, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(pastBetsLabel)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(profileHeaderLabel)
					.addGap(177)
					.addComponent(pastBetsLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ongoingBetsScrollPane, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(closeButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addGap(101))
		);
		
		betsTable = new JTable();
		betsTableModel = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						ResourceBundle.getBundle("Etiquetas").getString("Event"),
						ResourceBundle.getBundle("Etiquetas").getString("Question"),
						ResourceBundle.getBundle("Etiquetas").getString("option"),
						ResourceBundle.getBundle("Etiquetas").getString("Betted"),
						ResourceBundle.getBundle("Etiquetas").getString("Revenue"),
						ResourceBundle.getBundle("Etiquetas").getString("Outcome")
				}
			); 
		betsTable.setModel(betsTableModel);
		betsTable.getColumnModel().getColumn(0).setPreferredWidth(203);
		betsTable.getColumnModel().getColumn(1).setPreferredWidth(204);
		betsTable.getColumnModel().getColumn(2).setPreferredWidth(121);
		betsTable.getColumnModel().getColumn(3).setPreferredWidth(82);
		betsTable.getColumnModel().getColumn(4).setPreferredWidth(97);
		betsTable.getColumnModel().getColumn(5).setPreferredWidth(97);
		ongoingBetsScrollPane.setViewportView(betsTable);
		contentPane.setLayout(gl_contentPane);
		
		ArrayList<Bet> myBets = businessLogic.getBets(currentUser);
		for(Bet b: myBets) {
			if(b.getQuestion().isAnswered()) {
				Vector<Object> bet = new Vector<Object>();
				bet.add(b.getEvent().getDescription() + b.getEvent().getEventDate().toString());
				bet.add(b.getQuestion().getQuestion());
				bet.add(b.getOption().getName());
				bet.add(b.getBettedAmount());
				bet.add(b.getPossibleRevenue());
				if(b.getResult()) {
					bet.add("Successful");
				}else {
					bet.add("Unsuccessful");
				}
				betsTableModel.addRow(bet);
				System.out.println(bet);
			}
		}
	}
	private void close() {
		this.setVisible(false);
	}
}
