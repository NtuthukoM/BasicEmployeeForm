import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EmployeeForm {
	
	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JLabel msglabel;
	
	public EmployeeForm() {
		prepareGUI();
	}
	
	public static void main(String[] args) {
			EmployeeForm form = new EmployeeForm();
			form.showCardLayoutDemo();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Employee Details");
		JPanel panel = new JPanel();
		LayoutManager layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
		panel.setLayout(layout);
		
		mainFrame.setSize(560, 400);
		
		
		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);
		statusLabel.setSize(350, 100);
		
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		
		});
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		
		panel.add(headerLabel);
		panel.add(controlPanel);
		panel.add(statusLabel);
		mainFrame.getContentPane().add(panel, BorderLayout.CENTER);
		mainFrame.setVisible(true);
	}

	private void showCardLayoutDemo() {
		headerLabel.setText("Layout in action: CardLayout");
		
		final JPanel panel = new JPanel();
		panel.setBackground(Color.CYAN);
		panel.setSize(300, 300);
		
		CardLayout layout = new CardLayout();
		layout.setHgap(10);
		layout.setVgap(10);
		panel.setLayout(layout);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(new JButton("OK"));
		buttonPanel.add(new JButton("Cancel"));
		
		JPanel textBoxPanel = new JPanel(new FlowLayout());
		textBoxPanel.add(new JLabel("Name:"));
		textBoxPanel.add(new JTextField(20));
		
		panel.add("Button", buttonPanel);
		panel.add("Text", textBoxPanel);
		final DefaultComboBoxModel panelName = new DefaultComboBoxModel();
		
		panelName.addElement("Button");
		panelName.addElement("Text");
		final JComboBox listCombo = new JComboBox(panelName);
		
		
		listCombo.setSelectedIndex(0);
		JScrollPane listComboScrollPane = new JScrollPane(listCombo);
		JButton showButton = new JButton("Show");
		
		showButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = "";
				if(listCombo.getSelectedIndex() != -1) {
					CardLayout cardLayout = (CardLayout)(panel.getLayout());
					data = (String)listCombo.getItemAt(listCombo.getSelectedIndex());
					cardLayout.show(panel, (String)listCombo.getItemAt(listCombo.getSelectedIndex()));
				}
				statusLabel.setText(data);
			}
		});
		controlPanel.add(listComboScrollPane);
		controlPanel.add(showButton);
		controlPanel.add(panel);
		mainFrame.setVisible(true);
	}
	
}