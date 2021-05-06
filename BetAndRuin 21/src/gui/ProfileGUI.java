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

public class ProfileGUI extends JFrame {

	private JPanel contentPane;
	private JTable betsTable;
	
	private DefaultTableModel betsTableModel;
	
	private BlFacade businessLogic;

	/**
	 * Create the frame.
	 */
	public ProfileGUI(BlFacade businessLogic, BrowseQuestionsGUI brwGui) {
		ProfileGUI prfGui = this;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				brwGui.setEnabled(true);
				prfGui.setVisible(false);
			
			}
		});

		this.businessLogic = businessLogic;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 859, 536);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel profileHeaderLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("myProfile"));
		profileHeaderLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		profileHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JScrollPane ongoingBetsScrollPane = new JScrollPane();
		
		JLabel ongoingBetsLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("pongoingBetsLabel"));
		
		
		JLabel fullNameLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("FullName")); //$NON-NLS-1$ //$NON-NLS-2$
		
		JLabel usernameLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("usernameLoginLabel"));
		
		JLabel emailLabel = new JLabel("Email");
		
		JButton closeButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				brwGui.setEnabled(true);
				close();
			}
		});
		
		JTextPane fullNameTextPane = new JTextPane();
		fullNameTextPane.setEditable(false);
		fullNameTextPane.setBackground(SystemColor.menu);
		
		
		JTextPane usernameTextPane = new JTextPane();
		usernameTextPane.setEditable(false);
		usernameTextPane.setBackground(SystemColor.menu);
		
		
		
		JTextPane emailTextPane = new JTextPane();
		emailTextPane.setBackground(SystemColor.menu);
		
		JButton WalletButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ProfileGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		WalletButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MoneyGUI wallet = new MoneyGUI(businessLogic);
				wallet.setVisible(true);
			}
		});
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(profileHeaderLabel, GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(ongoingBetsScrollPane, GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap(714, Short.MAX_VALUE)
							.addComponent(closeButton, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(usernameLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(fullNameLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(emailLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(ongoingBetsLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(27)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(emailTextPane, GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
										.addComponent(fullNameTextPane, GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
										.addComponent(usernameTextPane, GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(WalletButton)))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(profileHeaderLabel)
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(fullNameTextPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(fullNameLabel))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(usernameTextPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(usernameLabel))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(emailTextPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(emailLabel))
					.addGap(44)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(ongoingBetsLabel)
						.addComponent(WalletButton))
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
						ResourceBundle.getBundle("Etiquetas").getString("Revenue")
				}
			); 
		betsTable.setModel(betsTableModel);
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
			betsTableModel.addRow(bet);
			System.out.println(bet);
			
		}
		fullNameTextPane.setText(this.businessLogic.getCurrentUser().getFullName());
		usernameTextPane.setText(this.businessLogic.getCurrentUser().getUsername());
		emailTextPane.setText(this.businessLogic.getCurrentUser().getEmail());
	}
	private void close() {
		this.setVisible(false);
	}
}
