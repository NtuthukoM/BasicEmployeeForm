import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class EmployeeForm {
	
	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JPanel formPanel;
	//Form view:
	JTextField tffName;
	JTextField tfLname;
	JTextField tfDesignation;
    JComboBox listComboDepartment;
	JComboBox listComboGender;
	
	JButton btnSave;
	JButton btnCancel;
	//Table view:
	private JPanel tableViewPanel;
	private JPanel navPanel;
	
	public EmployeeForm() {
		prepareGUI();
	}
	
	public static void main(String[] args) {
			EmployeeForm form = new EmployeeForm();
			form.showForm();
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
		
		formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(6, 2, 20, 20));
		mainFrame.getContentPane().add(formPanel, BorderLayout.CENTER);
		mainFrame.setVisible(true);
	}
	
	private void showForm() {
		tffName = new JTextField(100);
		tfLname = new JTextField(100);
		tfDesignation = new JTextField(100);
		
		formPanel.add(new JLabel("First Name"));
		formPanel.add(tffName);
		formPanel.add(new JLabel("Last Name"));
		formPanel.add(tfLname);
		formPanel.add(new JLabel("Gender"));
		final DefaultComboBoxModel panelName = new DefaultComboBoxModel();
		panelName.addElement("");
		panelName.addElement("Male");
		panelName.addElement("Female");
		listComboGender = new JComboBox(panelName);
		listComboGender.setSelectedIndex(0);
		JScrollPane listComboScrollPane = new JScrollPane(listComboGender);
		
		formPanel.add(listComboScrollPane);
		formPanel.add(new JLabel("Designation"));
		formPanel.add(tfDesignation);
		formPanel.add(new JLabel("Department"));
		
		final DefaultComboBoxModel panelDepartment = new DefaultComboBoxModel();
		panelDepartment.addElement("");
		panelDepartment.addElement("Human Resources (HR)");
		panelDepartment.addElement("Marketing and Sales");
		panelDepartment.addElement("Finance");
		panelDepartment.addElement("Operations/Production");
		panelDepartment.addElement("Research and Development (R&D)");
		panelDepartment.addElement("Customer Service");
		panelDepartment.addElement("Information Technology (IT)");
		panelDepartment.addElement("Administration/Legal");
		listComboDepartment = new JComboBox(panelDepartment);
		listComboDepartment.setSelectedIndex(0);
		JScrollPane listComboScrollPaneDepartment = new JScrollPane(listComboDepartment);

		formPanel.add(listComboScrollPaneDepartment);
		formPanel.add(new JLabel(""));
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnSave = new JButton("Save");
		btnCancel = new JButton("Cancel");
		buttons.add(btnSave);
		buttons.add(btnCancel);
		formPanel.add(buttons);
		mainFrame.setVisible(true);
		
		//Listeners:
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = tffName.getText();
				if(ValidateForm()) {
					Employee emp = new Employee();
					emp.Fname = tffName.getText();
					emp.Lname = tfLname.getText();
					emp.Designation = tfDesignation.getText();
					emp.Gender = (String)listComboGender.getItemAt(listComboGender.getSelectedIndex());
					emp.Department = (String)listComboDepartment.getItemAt(listComboDepartment.getSelectedIndex());
					
					EmployeeManager manager = new EmployeeManager();
					manager.SaveEmployee(emp);
					JOptionPane.showMessageDialog(formPanel, "Employee saved", 
                                          "INFORMATION", 
                                          JOptionPane.INFORMATION_MESSAGE);	
					ResetForm();
				}

			}
		}); 
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open table, close and reset current form.
				ResetForm();
			}
		}); 
	}
	
	private void ResetForm() {
		tffName.setText("");
		tfLname.setText("");
		tfDesignation.setText("");
		listComboDepartment.setSelectedIndex(0);
		listComboGender.setSelectedIndex(0);
	}
	
	private Boolean ValidateForm() {
		if(tffName.getText() == null || tffName.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(formPanel, "Frist Name is required", 
                                   "ERROR", JOptionPane.ERROR_MESSAGE);			
			return false;
		}
		if(tfLname.getText() == null || tfLname.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(formPanel, "Last Name is required", 
                                   "ERROR", JOptionPane.ERROR_MESSAGE);			
			return false;
		}
		
		if( listComboGender.getSelectedIndex() == 0 || listComboGender.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(formPanel, "Gender is required", 
                                   "ERROR", JOptionPane.ERROR_MESSAGE);			
			return false;			
		}
		
		if(tfDesignation.getText() == null || tfDesignation.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(formPanel, "Designation is required", 
                                   "ERROR", JOptionPane.ERROR_MESSAGE);			
			return false;
		}
		
		if( listComboDepartment.getSelectedIndex() == 0 || listComboDepartment.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(formPanel, "Department is required", 
                                   "ERROR", JOptionPane.ERROR_MESSAGE);			
			return false;			
		}
		
		return true;
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
	
	class Employee
	{
		public String Fname;
		public String Lname;
		public String Gender;
		public String Designation;
		public String Department;
	}
	
	class EmployeeManager {
		private String fileName = "data.txt";
		private ArrayList<Employee> list;
		
		public EmployeeManager(){
			ReadEmployees();
		}
		
		public ArrayList ReadEmployees() {
			list = new ArrayList<Employee>();
			//load employees:
			
			return list;
		}
		
		public void SaveEmployee(Employee emp) {
			//save employee:
			try{
				FileWriter fw = new FileWriter(fileName);
				BufferedWriter bfw = new BufferedWriter(fw);
				bfw.write(emp.Fname + "|" + emp.Lname + "|" + emp.Gender + "|" + emp.Designation + "|" + emp.Department);
				bfw.newLine();
				bfw.close();
				
			}catch(Exception exc){
				 exc.getStackTrace(); 
			}
		}
		
		
	}
	
}