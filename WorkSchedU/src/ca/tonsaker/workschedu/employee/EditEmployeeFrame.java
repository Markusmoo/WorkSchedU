package ca.tonsaker.workschedu.employee;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import ca.tonsaker.workschedu.HomeScreen;
import ca.tonsaker.workschedu.ScheduleTable;
import ca.tonsaker.workschedu.TableRenderer;
import ca.tonsaker.workschedu.positions.Positions;
import ca.tonsaker.workschedu.utilities.SchedUDayEdit;
import ca.tonsaker.workschedu.utilities.Utilities;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JFormattedTextField;

import java.awt.Color;

public class EditEmployeeFrame extends JFrame implements ActionListener,ChangeListener{
	
	//TODO BUG: Does not save info if it is the first time the date is created.
	//TODO Load total hours week at init.
	//TODO BUG: TO time sometimes does not start at 12pm
	//TODO Fix ScheduleTable selection bug
	//TODO Position not saved

	/**
	 * 
	 */
	private static final long serialVersionUID = 3181660544850116194L;
	private JPanel contentPane;
	
	private SchedUDayEdit dayEditor;
	private ScheduleTable table;
	
	private JTextField textField_week;
	private JTextField textField_date;
	private JTextField textField_totalHoursWeek;
	private JTextField textField_email;
	private JFormattedTextField textField_username;
	
	private JComboBox<Employee> comboBox_employee;
	private JComboBox<String> comboBox_position;
	
	private String date;
	private String week;
	
	private Employee[] employeeArray;
	private Employee selectedEmployee;
	
	private JButton btn_save;
	private JButton btn_saveAndExit;
	private JButton btn_cancel;
	
	private JTextField textField_name;
	
	private String[] positions;
	 
	public EditEmployeeFrame(HomeScreen homeScreen) {
		
		//Frame stuff
		setTitle("Employee SchedU Manager");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		week = homeScreen.weekSpinner.getValue().toString();
		date = homeScreen.dateTextField.getText();
		loadEmployees();
		
		JLabel lbl_employee = new JLabel("Employee:");
		lbl_employee.setBounds(10, 14, 50, 14);
		contentPane.add(lbl_employee);
		comboBox_employee = new JComboBox<>();
		comboBox_employee.setToolTipText("Employee's Name");
		comboBox_employee.setModel(new DefaultComboBoxModel<Employee>(employeeArray));
		comboBox_employee.addActionListener(this);
		comboBox_employee.setBounds(66, 11, 164, 20);
		contentPane.add(comboBox_employee);

		JLabel lbl_week = new JLabel("Week:");
		lbl_week.setBounds(454, 14, 31, 14);
		contentPane.add(lbl_week);
		textField_week = new JTextField();
		textField_week.setToolTipText("Week scheduling for");
		textField_week.setEditable(false);
		textField_week.setBounds(495, 11, 41, 20);
		textField_week.setText(week);
		contentPane.add(textField_week);
		textField_week.setColumns(10);

		JLabel lbl_date = new JLabel("Date:");
		lbl_date.setBounds(546, 14, 31, 14);
		contentPane.add(lbl_date);
		textField_date = new JTextField();
		textField_date.setToolTipText("Date scheduling for");
		textField_date.setEditable(false);
		textField_date.setBounds(587, 11, 86, 20);
		textField_date.setText(date);
		contentPane.add(textField_date);
		textField_date.setColumns(10);

		JButton btnClearEntries = new JButton("Clear Entries");
		btnClearEntries
				.setToolTipText("Clears all scheduled times (this week only)");
		btnClearEntries.setBounds(568, 297, 105, 23);
		contentPane.add(btnClearEntries);

		JLabel lbl_totalHoursWorkingWeek = new JLabel("Total Hours:");
		lbl_totalHoursWorkingWeek.setBounds(541, 245, 61, 14);
		contentPane.add(lbl_totalHoursWorkingWeek);
		textField_totalHoursWeek = new JTextField();
		textField_totalHoursWeek.setToolTipText("Total number of hours working this week");
		textField_totalHoursWeek.setEditable(false);
		textField_totalHoursWeek.setBounds(612, 242, 61, 20);
		contentPane.add(textField_totalHoursWeek);
		textField_totalHoursWeek.setColumns(10);

		JLabel lbl_email = new JLabel("Email:");
		lbl_email.setBounds(10, 171, 31, 14);
		contentPane.add(lbl_email);
		textField_email = new JTextField();
		textField_email.setToolTipText("Employee's Email Address (Optional)");
		textField_email.setBounds(43, 168, 187, 20);
		contentPane.add(textField_email);
		textField_email.setColumns(10);

		JLabel lbl_username = new JLabel("Employee Username:");
		lbl_username.setBounds(10, 42, 105, 14);
		contentPane.add(lbl_username);
		textField_username = new JFormattedTextField();
		textField_username.setEditable(false);
		textField_username.setToolTipText("The username the employee clocks in with (Usually numbers)");
		textField_username.setBounds(115, 39, 115, 20);
		contentPane.add(textField_username);
		textField_username.setColumns(10);
		@SuppressWarnings("serial")
		Document doc = new PlainDocument() {
		    @Override
		    public void insertString(int offs, String str, AttributeSet attr)
		    throws BadLocationException {
		        String newstr = str.replaceAll(" ", "");
		        super.insertString(offs, newstr, attr);
		    }

		    @Override
		    public void replace(int offs, int len, String str, AttributeSet attr) 
		    throws BadLocationException {
		        String newstr = str.replaceAll(" ", "");
		        super.replace(offs, len, newstr, attr);
		    }
		};
		textField_username.setDocument(doc);
		
		JLabel lbl_position = new JLabel("Position:");
		lbl_position.setBounds(444, 172, 41, 14);
		contentPane.add(lbl_position);
		try {
			positions = Positions.loadPositions();
			comboBox_position = new JComboBox<String>(positions);
			comboBox_position.setToolTipText("The position in which the Employee works");
			comboBox_position.setBounds(495, 168, 178, 20);
			contentPane.add(comboBox_position);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			comboBox_position = new JComboBox<String>();
			comboBox_position.setToolTipText("The position in which the Employee works");
			comboBox_position.setBounds(396, 69, 178, 20);
			contentPane.add(comboBox_position);
		}
		
		btn_save = new JButton("Save");
		btn_save.setBounds(10, 437, 89, 23);
		btn_save.addActionListener(this);
		contentPane.add(btn_save);
		
		btn_saveAndExit = new JButton("Save and Exit");
		btn_saveAndExit.setBounds(478, 437, 99, 23);
		btn_saveAndExit.addActionListener(this);
		contentPane.add(btn_saveAndExit);

		btn_cancel = new JButton("Cancel");
		btn_cancel.setBounds(595, 437, 89, 23);
		btn_cancel.addActionListener(this);
		contentPane.add(btn_cancel);
		
		JLabel lbl_name = new JLabel("Name:");
		lbl_name.setBounds(10, 143, 31, 14);
		contentPane.add(lbl_name);
		
		textField_name = new JTextField();
		textField_name.setToolTipText("Employee's name (First and Last initial (or however you like))");
		textField_name.setColumns(10);
		textField_name.setBounds(43, 140, 187, 20);
		contentPane.add(textField_name);
		
		JLabel lbl_requiredName = new JLabel("*");
		lbl_requiredName.setToolTipText("Required");
		lbl_requiredName.setForeground(Color.RED);
		lbl_requiredName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbl_requiredName.setBounds(240, 143, 17, 14);
		contentPane.add(lbl_requiredName);
		
		if(comboBox_employee.getSelectedItem() instanceof Employee){
			selectedEmployee = (Employee) comboBox_employee.getSelectedItem();
		}
		
		table = selectedEmployee.updateTable();
		table.setBounds(10, 199, 674, 32);
		table.setDefaultRenderer(new TableRenderer(Utilities.getDayOfWeek(),
				Utilities.getWeek(), table, homeScreen.weekSpinner));
		table.setRowSelectionAllowed(false);
		table.setSelectionMode(0);
		contentPane.add(table);
		
		updateEmployeeFrame(selectedEmployee);
		
		dayEditor = new SchedUDayEdit(selectedEmployee, textField_totalHoursWeek);
		dayEditor.setBounds(10, 232, 134, 185);
		contentPane.add(dayEditor);
		
		this.setVisible(true);
	}
	
	private void loadEmployees(){
		try {
			employeeArray = Employee.load();
			for(Employee i : employeeArray){
				i.setDate(date);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param username the username of employee
	 * @return
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private Employee loadEmployee(String username){
		try {
			Employee tmp = Employee.load(username);
			tmp.setDate(date);
			return tmp;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @return All names of employees stored in employeeArray
	 */
	public String[] getEmployeesNames(){
		String[] names = new String[employeeArray.length];
		for(int i = 0; i < employeeArray.length; i++){
			names[i] = employeeArray[i].getName();
		}
		return names;
	}
	
	public void updateEmployeeFrame(Employee employee){ //TODO Method stub
		System.out.println("Updating EditEmployeeFrame for User"+employee.getUsername()+" - "+employee);
		
		employee.setDate(date);
		textField_name.setText(employee.getName());
		textField_email.setText(employee.getEmail());
		textField_username.setText(employee.getUsername());
		int idx = 0;
		for(String str : positions){
			if(str.equals(employee.getPosition())){
				comboBox_position.setSelectedIndex(idx);
				break;
			}
			idx++;
		}
		
		table.setCell(1, ScheduleTable.NAME, employee.getName());
		table.setCell(1, ScheduleTable.MONDAY, employee.getFromToHours(Calendar.MONDAY));
		table.setCell(1, ScheduleTable.TUESDAY, employee.getFromToHours(Calendar.TUESDAY));
		table.setCell(1, ScheduleTable.WEDNESDAY, employee.getFromToHours(Calendar.WEDNESDAY));
		table.setCell(1, ScheduleTable.THURSDAY, employee.getFromToHours(Calendar.THURSDAY));
		table.setCell(1, ScheduleTable.FRIDAY, employee.getFromToHours(Calendar.FRIDAY));
		table.setCell(1, ScheduleTable.SATURDAY, employee.getFromToHours(Calendar.SATURDAY));
		table.setCell(1, ScheduleTable.SUNDAY, employee.getFromToHours(Calendar.SUNDAY));
		
		System.out.println("Update Successful!");
	}
	
	public boolean editEmployee(){
		String email = textField_email.getText().trim();
		if(email.indexOf('@') > -1){
			selectedEmployee.setEmail(email);
		}else{
			selectedEmployee.setEmail("NONE");
		}
		selectedEmployee.setPosition(comboBox_position.getSelectedItem().toString());
		if(textField_name.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this, "You didn't add a name!", "Missing Fields", JOptionPane.WARNING_MESSAGE);
			return false;
		}else{
			selectedEmployee.setName(textField_name.getText());
		}
		try {
			selectedEmployee.save();
		} catch (IOException error) {
			error.printStackTrace();
			JOptionPane.showMessageDialog(this, "Unknown Error Occured", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == comboBox_employee){
			if(comboBox_employee.getSelectedItem() instanceof Employee){
				selectedEmployee = (Employee) comboBox_employee.getSelectedItem();
				updateEmployeeFrame(selectedEmployee);
			}
		}else if(src == btn_save){
			editEmployee();
		}else if(src == btn_saveAndExit){
			if(!editEmployee()) return;
			this.dispose();
		}else if(src == btn_cancel){
			this.dispose();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
	}
}
