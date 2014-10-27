package ca.tonsaker.workschedu.utilities;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.tonsaker.workschedu.employee.Employee;

public class SchedUDayEdit extends JPanel implements ChangeListener,ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8248029176554152083L;

	private JComboBox<String> comboBox_dayOfWeek;
	private JTextField textField_totalHoursDay;
	private JSpinner spinner_fromTime;
	private JSpinner spinner_toTime;
	
	private JTextField textField_weekHours;
	
	private Employee employee;
	
	public static final String[] DAYS_OF_WEEK = new String[] {"Monday", 
		"Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	public static final String[] MIN_30_TIMES = new String[] { "1:00am",
		"1:30am", "2:00am", "2:30am", "3:00am", "3:30am", "4:00am",
		"4:30am", "5:00am", "5:30am", "6:00am", "6:30am", "7:00am",
		"7:30am", "8:00am", "8:30am", "9:00am", "9:30am", "10:00am",
		"10:30am", "11:00am", "11:30am", "12:00pm","12:30pm", "1:00pm", 
		"1:30pm", "2:00pm", "2:30pm", "3:00pm", "3:30pm",
		"4:00pm", "4:30pm", "5:00pm", "5:30pm", "6:00pm", "6:30pm",
		"7:00pm", "7:30pm", "8:00pm", "8:30pm", "9:00pm", "9:30pm",
		"10:00pm", "10:30pm", "11:00pm", "11:30pm", "12:00am", "12:30am"};

	public SchedUDayEdit(Employee employee, JTextField textField_weekHours) {
		super();
		
		this.employee = employee;
		this.textField_weekHours = textField_weekHours;
		
		setLayout(null);
		
		JLabel label_dayOfWeek = new JLabel("Day:");
		label_dayOfWeek.setBounds(9, 39, 23, 14);
		add(label_dayOfWeek);
		comboBox_dayOfWeek = new JComboBox<>(DAYS_OF_WEEK);
		comboBox_dayOfWeek.setToolTipText("Which day to schedule for");
		comboBox_dayOfWeek.setBounds(42, 36, 82, 20);
		comboBox_dayOfWeek.addActionListener(this);
		add(comboBox_dayOfWeek);
		
		int tmp_day = comboBox_dayOfWeek.getSelectedIndex();
		tmp_day = Utilities.convertIndexToCalendarDay(tmp_day);
		
		JLabel label_fromTime = new JLabel("From:");
		label_fromTime.setBounds(9, 67, 28, 14);
		add(label_fromTime);
		spinner_fromTime = new JSpinner();
		spinner_fromTime.setModel(new SpinnerListModel(MIN_30_TIMES));
		spinner_fromTime.setToolTipText("What time the employee works from that day");
		spinner_fromTime.setBounds(42, 64, 82, 20);
		spinner_fromTime.setValue(employee.getFromTime(tmp_day));
		spinner_fromTime.addChangeListener(this);
		add(spinner_fromTime);
		
		JLabel label_toTime = new JLabel("To:");
		label_toTime.setBounds(19, 98, 16, 14);
		add(label_toTime);
		spinner_toTime = new JSpinner();
		spinner_toTime.setModel(new SpinnerListModel(MIN_30_TIMES));
		spinner_toTime.setToolTipText("What time employee works til that day");
		spinner_toTime.setBounds(42, 95, 82, 20);
		spinner_toTime.setValue(employee.getToTime(tmp_day));
		spinner_toTime.addChangeListener(this);
		add(spinner_toTime);
		
		JLabel label_totalHoursFromToTime = new JLabel("Total Hours:");
		label_totalHoursFromToTime.setBounds(9, 137, 59, 14);
		add(label_totalHoursFromToTime);
		textField_totalHoursDay = new JTextField();
		textField_totalHoursDay.setToolTipText("Total number of hours working that day");
		textField_totalHoursDay.setEditable(false);
		textField_totalHoursDay.setColumns(10);
		textField_totalHoursDay.setBounds(78, 134, 46, 20);
		add(textField_totalHoursDay);
		
		employee.setsScheduleTime(tmp_day, (String) spinner_fromTime.getValue(), (String) spinner_toTime.getValue());
		textField_totalHoursDay.setText(Double.toString(employee.getTotalDayHours(tmp_day)));
		
		JLabel label_title = new JLabel("SchedU Day Edit");
		label_title.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_title.setBounds(19, 11, 91, 14);
		add(label_title);
		
		setVisible(true);
	}
	
	public void setEmployee(Employee employee){
		this.employee = employee;
	}
	
	public void update(){
		int day = comboBox_dayOfWeek.getSelectedIndex();
		day = Utilities.convertIndexToCalendarDay(day);
		
		spinner_fromTime.setValue(employee.getFromTime(day));
		spinner_toTime.setValue(employee.getToTime(day));
		textField_totalHoursDay.setText(Double.toString(employee.getTotalDayHours(day)));
		
		if(textField_weekHours != null){
			textField_weekHours.setText(Double.toString(employee.getTotalWeekHours()));
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Object src = e.getSource();
		
		if(src == spinner_fromTime){
			int tmp_day = comboBox_dayOfWeek.getSelectedIndex();
			tmp_day = Utilities.convertIndexToCalendarDay(tmp_day);
			employee.setsScheduleTime(tmp_day, (String) spinner_fromTime.getValue(), (String) spinner_toTime.getValue());
			update();
		}else if(src == spinner_toTime){
			int tmp_day = comboBox_dayOfWeek.getSelectedIndex();
			tmp_day = Utilities.convertIndexToCalendarDay(tmp_day);
			employee.setsScheduleTime(tmp_day, (String) spinner_fromTime.getValue(), (String) spinner_toTime.getValue());
			update();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if(src == comboBox_dayOfWeek){
			update();
		}
	}

}
