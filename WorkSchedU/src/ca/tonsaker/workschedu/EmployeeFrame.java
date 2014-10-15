package ca.tonsaker.workschedu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import java.awt.Font;

public class EmployeeFrame extends JFrame {

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
	private JTextField textField_5;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EmployeeFrame(HomeScreen homeScreen) {
		setTitle("Employee SchedU Manager");
		this.homeScreen = homeScreen;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox comboBox = new JComboBox();
		comboBox.setToolTipText("Employee's Name");
		comboBox.setModel(new DefaultComboBoxModel(new String[] {
				"This is the", "Format that the", "JComboBox uses" }));
		comboBox.setBounds(66, 11, 164, 20);
		contentPane.add(comboBox);

		JLabel lblEmployee = new JLabel("Employee:");
		lblEmployee.setBounds(10, 14, 50, 14);
		contentPane.add(lblEmployee);

		JLabel lblWeek = new JLabel("Week:");
		lblWeek.setBounds(355, 14, 31, 14);
		contentPane.add(lblWeek);

		textField = new JTextField();
		textField.setToolTipText("Week scheduling for");
		textField.setEditable(false);
		textField.setBounds(396, 11, 41, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Date:");
		lblNewLabel.setBounds(447, 14, 31, 14);
		contentPane.add(lblNewLabel);

		textField_1 = new JTextField();
		textField_1.setToolTipText("Date scheduling for");
		textField_1.setEditable(false);
		textField_1.setBounds(488, 11, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		table = new ScheduleTable(1);
		table.setBounds(10, 100, 564, 32);
		table.setDefaultRenderer(new TableRenderer(homeScreen.getDayOfWeek(),
				homeScreen.getWeek(), table, homeScreen.spinner));
		contentPane.add(table);

		JButton btnSaveAndExit = new JButton("Save and Exit");
		btnSaveAndExit.setBounds(379, 327, 99, 23);
		contentPane.add(btnSaveAndExit);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(485, 327, 89, 23);
		contentPane.add(btnCancel);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setToolTipText("Which day to schedule for");
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "Sunday",
				"Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
				"Saturday" }));
		comboBox_1.setBounds(43, 171, 82, 20);
		contentPane.add(comboBox_1);

		JLabel lblDay = new JLabel("Day:");
		lblDay.setBounds(10, 174, 23, 14);
		contentPane.add(lblDay);

		JSpinner spinner = new JSpinner();
		spinner.setToolTipText("What time the employee works from that day");
		spinner.setModel(new SpinnerListModel(new String[] { "1:00am",
				"1:30am", "2:00am", "2:30am", "3:00am", "3:30am", "4:00am",
				"4:30am", "5:00am", "5:30am", "6:00am", "6:30am", "7:00am",
				"7:30am", "8:00am", "8:30am", "9:00am", "9:30am", "10:00am",
				"10:30am", "11:00am", "11:30am", "12:00am", "12:30am",
				"1:00pm", "1:30pm", "2:00pm", "2:30pm", "3:00pm", "3:30pm",
				"4:00pm", "4:30pm", "5:00pm", "5:30pm", "6:00pm", "6:30pm",
				"7:00pm", "7:30pm", "8:00pm", "8:30pm", "9:00pm", "9:30pm",
				"10:00pm", "10:30pm", "11:00pm", "11:30pm", "12:00pm",
				"12:30pm" }));
		spinner.setBounds(43, 202, 82, 20);
		contentPane.add(spinner);

		JLabel lblFrom = new JLabel("From:");
		lblFrom.setBounds(10, 202, 46, 14);
		contentPane.add(lblFrom);

		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(10, 233, 46, 14);
		contentPane.add(lblTo);

		JSpinner spinner_1 = new JSpinner();
		spinner_1.setToolTipText("What time employee works til that day");
		spinner_1.setModel(new SpinnerListModel(new String[] { "1:00am",
				"1:30am", "2:00am", "2:30am", "3:00am", "3:30am", "4:00am",
				"4:30am", "5:00am", "5:30am", "6:00am", "6:30am", "7:00am",
				"7:30am", "8:00am", "8:30am", "9:00am", "9:30am", "10:00am",
				"10:30am", "11:00am", "11:30am", "12:00am", "12:30am",
				"1:00pm", "1:30pm", "2:00pm", "2:30pm", "3:00pm", "3:30pm",
				"4:00pm", "4:30pm", "5:00pm", "5:30pm", "6:00pm", "6:30pm",
				"7:00pm", "7:30pm", "8:00pm", "8:30pm", "9:00pm", "9:30pm",
				"10:00pm", "10:30pm", "11:00pm", "11:30pm", "12:00pm",
				"12:30pm" }));
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

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(10, 327, 89, 23);
		contentPane.add(btnSave);

		JLabel lblScheduDayEdit = new JLabel("SchedU Day Edit");
		lblScheduDayEdit.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblScheduDayEdit.setBounds(20, 146, 105, 14);
		contentPane.add(lblScheduDayEdit);

		JButton btnClearEntries = new JButton("Clear Entries");
		btnClearEntries
				.setToolTipText("Clears all scheduled times (this week only)");
		btnClearEntries.setBounds(469, 170, 105, 23);
		contentPane.add(btnClearEntries);

		textField_3 = new JTextField();
		textField_3.setToolTipText("Total number hours working this week");
		textField_3.setEditable(false);
		textField_3.setBounds(513, 143, 61, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		JLabel lblTotalHours_1 = new JLabel("Total Hours:");
		lblTotalHours_1.setBounds(442, 146, 61, 14);
		contentPane.add(lblTotalHours_1);

		textField_4 = new JTextField();
		textField_4.setToolTipText("Employee's Email Address");
		textField_4.setBounds(43, 42, 187, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 45, 31, 14);
		contentPane.add(lblEmail);

		JLabel lblEmployeeUsername = new JLabel("Employee Username:");
		lblEmployeeUsername.setBounds(10, 73, 105, 14);
		contentPane.add(lblEmployeeUsername);

		textField_5 = new JTextField();
		textField_5.setToolTipText("The username the employee clocks in with");
		textField_5.setBounds(115, 70, 115, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		this.setVisible(true);
	}
}
