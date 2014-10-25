package ca.tonsaker.workschedu.employee;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import ca.tonsaker.workschedu.HomeScreen;
import ca.tonsaker.workschedu.ScheduleTable;
import ca.tonsaker.workschedu.TableRenderer;
import ca.tonsaker.workschedu.positions.Positions;
import ca.tonsaker.workschedu.utilities.Utilities;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Calendar;

import javax.swing.JFormattedTextField;

public class EditEmployeeFrame extends JFrame implements ActionListener{
	
	//TODO Loading wrong date on update, ?loads correct date on init?

	/**
	 * 
	 */
	private static final long serialVersionUID = 3181660544850116194L;
	private JPanel contentPane;
	private HomeScreen homeScreen;
	private JTextField textField;
	private JTextField textField_1;
	private ScheduleTable table;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JFormattedTextField textField_5;
	
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox_2;
	
	private String date;
	private String week;
	
	private Employee[] employeeArray;
	private Employee[] TEMP_EMPLOYEES;
	private Employee selectedEmployee;
	
	public static final String[] MIN_30_TIMES = new String[] { "1:00am",
		"1:30am", "2:00am", "2:30am", "3:00am", "3:30am", "4:00am",
		"4:30am", "5:00am", "5:30am", "6:00am", "6:30am", "7:00am",
		"7:30am", "8:00am", "8:30am", "9:00am", "9:30am", "10:00am",
		"10:30am", "11:00am", "11:30am", "12:00pm","12:30pm", "1:00pm", 
		"1:30pm", "2:00pm", "2:30pm", "3:00pm", "3:30pm",
		"4:00pm", "4:30pm", "5:00pm", "5:30pm", "6:00pm", "6:30pm",
		"7:00pm", "7:30pm", "8:00pm", "8:30pm", "9:00pm", "9:30pm",
		"10:00pm", "10:30pm", "11:00pm", "11:30pm", "12:00am", "12:30am"};
	
	@SuppressWarnings({ "unchecked", "rawtypes" })  
	public EditEmployeeFrame(HomeScreen homeScreen) {
		
		//Frame stuff
		setTitle("Employee SchedU Manager");
		this.homeScreen = homeScreen;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		loadEmployees();
		week = homeScreen.weekSpinner.getValue().toString();
		date = homeScreen.dateTextField.getText();
		
		JLabel lblEmployee = new JLabel("Employee:");
		lblEmployee.setBounds(10, 14, 50, 14);
		contentPane.add(lblEmployee);
		comboBox = new JComboBox();
		comboBox.setToolTipText("Employee's Name");
		comboBox.setModel(new DefaultComboBoxModel(employeeArray));
		comboBox.addActionListener(this);
		comboBox.setBounds(66, 11, 164, 20);
		contentPane.add(comboBox);

		JLabel lblWeek = new JLabel("Week:");
		lblWeek.setBounds(454, 14, 31, 14);
		contentPane.add(lblWeek);
		textField = new JTextField();
		textField.setToolTipText("Week scheduling for");
		textField.setEditable(false);
		textField.setBounds(495, 11, 41, 20);
		textField.setText(week);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Date:");
		lblNewLabel.setBounds(546, 14, 31, 14);
		contentPane.add(lblNewLabel);
		textField_1 = new JTextField();
		textField_1.setToolTipText("Date scheduling for");
		textField_1.setEditable(false);
		textField_1.setBounds(587, 11, 86, 20);
		textField_1.setText(date);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		table = new ScheduleTable(1);
		table.setBounds(10, 100, 674, 32);
		table.setDefaultRenderer(new TableRenderer(Utilities.getDayOfWeek(),
				Utilities.getWeek(), table, homeScreen.weekSpinner));
		table.setRowSelectionAllowed(false);
		contentPane.add(table);

		JLabel lblDay = new JLabel("Day:");
		lblDay.setBounds(10, 174, 23, 14);
		contentPane.add(lblDay);
		JComboBox comboBox_1 = new JComboBox(new String[] {"Monday", 
				"Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});
		comboBox_1.setToolTipText("Which day to schedule for");
		comboBox_1.setBounds(43, 171, 82, 20);
		contentPane.add(comboBox_1);

		JLabel lblFrom = new JLabel("From:");
		lblFrom.setBounds(10, 202, 46, 14);
		contentPane.add(lblFrom);
		JSpinner spinner = new JSpinner();
		spinner.setToolTipText("What time the employee works from that day");
		spinner.setModel(new SpinnerListModel(MIN_30_TIMES));
		spinner.setBounds(43, 202, 82, 20);
		contentPane.add(spinner);

		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(10, 233, 46, 14);
		contentPane.add(lblTo);
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setToolTipText("What time employee works til that day");
		spinner_1.setModel(new SpinnerListModel(MIN_30_TIMES));
		spinner_1.setBounds(43, 233, 82, 20);
		contentPane.add(spinner_1);

		JLabel lblTotalHours = new JLabel("Total Hours:");
		lblTotalHours.setBounds(10, 267, 61, 14);
		contentPane.add(lblTotalHours);
		textField_2 = new JTextField();
		textField_2.setToolTipText("Total hours that day");
		textField_2.setEditable(false);
		textField_2.setBounds(75, 264, 50, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		JLabel lblScheduDayEdit = new JLabel("SchedU Day Edit");
		lblScheduDayEdit.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblScheduDayEdit.setBounds(20, 146, 105, 14);
		contentPane.add(lblScheduDayEdit);

		JButton btnClearEntries = new JButton("Clear Entries");
		btnClearEntries
				.setToolTipText("Clears all scheduled times (this week only)");
		btnClearEntries.setBounds(568, 193, 105, 23);
		contentPane.add(btnClearEntries);

		textField_3 = new JTextField();
		textField_3.setToolTipText("Total number hours working this week");
		textField_3.setEditable(false);
		textField_3.setBounds(612, 143, 61, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		JLabel lblTotalHours_1 = new JLabel("Total Hours:");
		lblTotalHours_1.setBounds(541, 146, 61, 14);
		contentPane.add(lblTotalHours_1);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 45, 31, 14);
		contentPane.add(lblEmail);
		textField_4 = new JTextField();
		textField_4.setToolTipText("Employee's Email Address");
		textField_4.setBounds(43, 42, 187, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);

		JLabel lblEmployeeUsername = new JLabel("Employee Username:");
		lblEmployeeUsername.setBounds(10, 73, 105, 14);
		contentPane.add(lblEmployeeUsername);
		textField_5 = new JFormattedTextField();
		textField_5.setEditable(false);
		textField_5.setToolTipText("The username the employee clocks in with (Usually numbers)");
		textField_5.setBounds(115, 70, 115, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
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
		textField_5.setDocument(doc);
		
		JLabel lblPosition = new JLabel("Position:");
		lblPosition.setBounds(444, 73, 41, 14);
		contentPane.add(lblPosition);
		try {
			comboBox_2 = new JComboBox(Positions.loadPositions());
			comboBox_2.setToolTipText("The position in which the Employee works");
			comboBox_2.setBounds(495, 69, 178, 20);
			contentPane.add(comboBox_2);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			comboBox_2 = new JComboBox();
			comboBox_2.setToolTipText("The position in which the Employee works");
			comboBox_2.setBounds(396, 69, 178, 20);
			contentPane.add(comboBox_2);
		}
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(10, 437, 89, 23);
		btnSave.addActionListener(this);
		contentPane.add(btnSave);
		
		JButton btnSaveAndExit = new JButton("Save and Exit");
		btnSaveAndExit.setBounds(478, 437, 99, 23);
		contentPane.add(btnSaveAndExit);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(595, 437, 89, 23);
		contentPane.add(btnCancel);
		
		if(comboBox.getSelectedItem() instanceof Employee){
			selectedEmployee = (Employee) comboBox.getSelectedItem();
			updateEmployeeFrame(selectedEmployee);
		}
		this.setVisible(true);
	}
	
	private void loadEmployees(){
		try {
			employeeArray = Employee.load();
			TEMP_EMPLOYEES = employeeArray;
			for(Employee i : employeeArray){
				i.setDate(date);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
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
		textField_4.setText(employee.getEmail());
		textField_5.setText(employee.getUsername());
		comboBox_2.setSelectedItem(employee.getPosition());
		
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == comboBox){
			if(comboBox.getSelectedItem() instanceof Employee){
				selectedEmployee = (Employee) comboBox.getSelectedItem();
				updateEmployeeFrame(selectedEmployee);
			}
		}
	}
}
