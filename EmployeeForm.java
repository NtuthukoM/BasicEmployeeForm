import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import javax.swing.table.DefaultTableModel;

public class EmployeeForm {
	
	private JFrame mainFrame;
	private JPanel formPanel;
	private JPanel ui;
	EmployeeManager manager = new EmployeeManager();
	private JTable table;
	private DefaultTableModel model;
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
	
	public EmployeeForm() {
		prepareGUI();
	}
	
	public static void main(String[] args) {
			EmployeeForm form = new EmployeeForm();
			form.createEmpUI();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Employee Details");
		mainFrame.setSize(560, 400);
		
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		
		});

		mainFrame.setVisible(true);
	}
	
	private void createFormView() {
		
		formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(6, 2, 20, 20));
		
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
		btnCancel = new JButton("Back");
		buttons.add(btnSave);
		buttons.add(btnCancel);
		formPanel.add(buttons);
		
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
						//Column Names:
		String[] columns = {"Name", "Last Name", "Gender", "Designation", "Department"};
		ArrayList<Employee> emps = manager.ReadEmployees();
		String[][] data = new String[emps.size()][5];
		for(int i = 0; i < emps.size(); i++) {
			
			Employee emp = emps.get(i);
			
			data[i][0] = emp.Fname;
			data[i][1] = emp.Lname;
			data[i][2] = emp.Gender;
			data[i][3] = emp.Designation;
			data[i][4] = emp.Department;
		}
		model.setDataVector(data, columns);
				CardLayout cardLayout = (CardLayout)(ui.getLayout());
				cardLayout.show(ui, "table");
			mainFrame.setTitle("Employee Details");				
			}
		}); 
	}
	
	private void createTableView() {
		tableViewPanel = new JPanel(new BorderLayout());
		
		//Column Names:
		String[] columns = {"Name", "Last Name", "Gender", "Designation", "Department"};
		ArrayList<Employee> emps = manager.ReadEmployees();
		String[][] data = new String[emps.size()][5];
		for(int i = 0; i < emps.size(); i++) {
			
			Employee emp = emps.get(i);
			
			data[i][0] = emp.Fname;
			data[i][1] = emp.Lname;
			data[i][2] = emp.Gender;
			data[i][3] = emp.Designation;
			data[i][4] = emp.Department;
		}
		
		model = new DefaultTableModel(data, columns);
	    table = new JTable(model);
		JScrollPane sp = new JScrollPane(table);
		tableViewPanel.add(sp, BorderLayout.CENTER);
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton btnNew = new JButton("Add New");
		buttons.add(btnNew);
		tableViewPanel.add(buttons, BorderLayout.SOUTH);
		
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout)(ui.getLayout());
				mainFrame.setTitle("Add New Employee");
				cardLayout.show(ui, "form");
			}
		}); 
	}
	
	
	private void createEmpUI(){
		createFormView();
		createTableView();
		
		ui = new JPanel();
		CardLayout layout = new CardLayout();
		layout.setHgap(10);
		layout.setVgap(10);
		ui.setLayout(layout);
		ui.add("form", formPanel);
		ui.add("table", tableViewPanel);
		layout.show(ui, "table");
		
		mainFrame.getContentPane().add(ui, BorderLayout.CENTER);
		
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
			try{
				FileReader fr = new FileReader(fileName);
				BufferedReader br = new BufferedReader(fr);
				String line = br.readLine();
				while(!line.isEmpty()){
					Employee emp = new Employee();
					String[] arr = line.split("[|]");
					emp.Fname = arr[0]; 
					emp.Lname = arr[1];
					emp.Gender = arr[2];
					emp.Designation = arr[3];
					emp.Department = arr[4];
					list.add(emp);
					line = br.readLine();
				}
				br.close();
				
			}catch(Exception exc)
			{
				exc.getStackTrace(); 
			}
			return list;
		}
		
		public void SaveEmployee(Employee emp) {
			//save employee:
			try{
				FileWriter fw = new FileWriter(fileName, true);
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