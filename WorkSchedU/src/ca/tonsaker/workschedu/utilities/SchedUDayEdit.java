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
import javax.swing.JToggleButton;
import java.awt.Color;

public class SchedUDayEdit extends JPanel implements ChangeListener,ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8248029176554152083L;

	//private JComboBox<String> comboBox_dayOfWeek; //Unparsable combobox
	private JComboBox comboBox_dayOfWeek; //Parsable combobox TODO
	private JTextField textField_totalHoursDay;
	private JSpinner spinner_fromTime;
	private JSpinner spinner_toTime;
	
	private JToggleButton tglbtn_onOff;
	
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
		"10:00pm", "10:30pm", "11:00pm", "11:30pm", "12:00am", "12:30am", "00:00"};

	public SchedUDayEdit(Employee employee, JTextField textField_weekHours) {
		super();
		
		this.employee = employee;
		this.textField_weekHours = textField_weekHours;
		
		setLayout(null);
		
		JLabel label_dayOfWeek = new JLabel("Day:");
		label_dayOfWeek.setBounds(9, 39, 23, 14);
		add(label_dayOfWeek);
		comboBox_dayOfWeek = new JComboBox(DAYS_OF_WEEK); //Add <> for unparsable combobox TODO
		comboBox_dayOfWeek.setToolTipText("Which day to schedule for");
		comboBox_dayOfWeek.setBounds(42, 36, 82, 20);
		comboBox_dayOfWeek.addActionListener(this);
		add(comboBox_dayOfWeek);
		
		int tmp_day = comboBox_dayOfWeek.getSelectedIndex();
		tmp_day = Utilities.convertIndexToCalendarDay(tmp_day);
		
		JLabel label_fromTime = new JLabel("From:");
		label_fromTime.setBounds(9, 104, 28, 14);
		add(label_fromTime);
		spinner_fromTime = new JSpinner();
		spinner_fromTime.setModel(new SpinnerListModel(MIN_30_TIMES));
		spinner_fromTime.setToolTipText("What time the employee works from that day");
		spinner_fromTime.setBounds(42, 101, 82, 20);
		spinner_fromTime.setValue(employee.getFromTime(tmp_day));
		spinner_fromTime.addChangeListener(this);
		add(spinner_fromTime);
		
		JLabel label_toTime = new JLabel("To:");
		label_toTime.setBounds(19, 135, 16, 14);
		add(label_toTime);
		spinner_toTime = new JSpinner();
		spinner_toTime.setModel(new SpinnerListModel(MIN_30_TIMES));
		spinner_toTime.setToolTipText("What time employee works til that day");
		spinner_toTime.setBounds(42, 132, 82, 20);
		spinner_toTime.setValue(employee.getToTime(tmp_day));
		spinner_toTime.addChangeListener(this);
		add(spinner_toTime);
		
		JLabel label_totalHoursFromToTime = new JLabel("Total Hours:");
		label_totalHoursFromToTime.setBounds(9, 166, 59, 14);
		add(label_totalHoursFromToTime);
		textField_totalHoursDay = new JTextField();
		textField_totalHoursDay.setToolTipText("Total number of hours working that day");
		textField_totalHoursDay.setEditable(false);
		textField_totalHoursDay.setColumns(10);
		textField_totalHoursDay.setBounds(78, 163, 46, 20);
		add(textField_totalHoursDay);
		
		employee.setsScheduleTime(tmp_day, (String) spinner_fromTime.getValue(), (String) spinner_toTime.getValue());
		textField_totalHoursDay.setText(Double.toString(employee.getTotalDayHours(tmp_day)));
		
		JLabel label_title = new JLabel("SchedU Day Edit");
		label_title.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_title.setBounds(19, 11, 91, 14);
		add(label_title);
		
		JLabel lbl_onOff = new JLabel("On/Off:");
		lbl_onOff.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lbl_onOff.setBounds(10, 72, 28, 14);
		add(lbl_onOff);
		tglbtn_onOff = new JToggleButton("Off");
		tglbtn_onOff.setForeground(Color.RED);
		tglbtn_onOff.setBounds(42, 67, 82, 23);
		tglbtn_onOff.addActionListener(this);
		add(tglbtn_onOff);
		
		setVisible(true);
	}
	
	public void setEmployee(Employee employee){
		this.employee = employee;
	}
	
	public void update(){
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		int day = Utilities.convertIndexToCalendarDay(comboBox_dayOfWeek.getSelectedIndex());
		
		if(src == comboBox_dayOfWeek){
			System.out.println(employee.isWorking(day));
			tglbtn_onOff.setSelected(employee.isWorking(day));
		}else if(src == tglbtn_onOff){
			if(tglbtn_onOff.isSelected()){
				employee.setsScheduleTime(day, "12:00pm", "12:00pm");
				spinner_fromTime.setValue(employee.getFromTime(day));
				spinner_toTime.setValue(employee.getToTime(day));
				spinner_fromTime.setEnabled(true);
				spinner_toTime.setEnabled(true);
			}else{
				employee.setsScheduleTime(day, "00:00", "00:00");
				spinner_fromTime.setEnabled(false);
				spinner_toTime.setEnabled(false);
				spinner_fromTime.setValue("00:00");
				spinner_toTime.setValue("00:00");
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Object src = e.getSource();
		
		int day = Utilities.convertIndexToCalendarDay(comboBox_dayOfWeek.getSelectedIndex());
		
		if(src == spinner_fromTime && spinner_fromTime.isEnabled()){
			employee.setsScheduleTime(day, spinner_fromTime.getValue().toString(), spinner_toTime.getValue().toString());
		}else if(src == spinner_toTime && spinner_toTime.isEnabled()){
			employee.setsScheduleTime(day, spinner_fromTime.getValue().toString(), spinner_toTime.getValue().toString());
		}
		
	}
	
	/*public void update(){
		int day = comboBox_dayOfWeek.getSelectedIndex();
		day = Utilities.convertIndexToCalendarDay(day);
		
		if(tglbtn_onOff.isSelected()){
			spinner_fromTime.setValue(employee.getFromTime(day));
			spinner_toTime.setValue(employee.getToTime(day));
		}else{
			spinner_fromTime.setValue("00:00");
			spinner_toTime.setValue("00:00");
		}
		if(employee.getFromTime(day).equals("00:00") && employee.getToTime(day).equals("00:00") && !tglbtn_onOff.isSelected()){
			spinner_fromTime.setEnabled(false);
			spinner_toTime.setEnabled(false);
			System.out.println("Disabled Spinners update");
		}else{
			spinner_fromTime.setEnabled(true);
			spinner_toTime.setEnabled(true);
			System.out.println("Enabled Spinners update");
		}
		textField_totalHoursDay.setText(Double.toString(employee.getTotalDayHours(day)));
		
		if(textField_weekHours != null){
			textField_weekHours.setText(Double.toString(employee.getTotalWeekHours()));
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Object src = e.getSource();
		int tmp_day = comboBox_dayOfWeek.getSelectedIndex();
		
		if(src == spinner_fromTime){
			tmp_day = Utilities.convertIndexToCalendarDay(tmp_day);
			employee.setsScheduleTime(tmp_day, (String) spinner_fromTime.getValue(), (String) spinner_toTime.getValue());
			update();
		}else if(src == spinner_toTime){
			tmp_day = Utilities.convertIndexToCalendarDay(tmp_day);
			employee.setsScheduleTime(tmp_day, (String) spinner_fromTime.getValue(), (String) spinner_toTime.getValue());
			update();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		int tmp_day = comboBox_dayOfWeek.getSelectedIndex();
		
		if(src == comboBox_dayOfWeek){
			update();
		}else if(src == tglbtn_onOff){
			if(tglbtn_onOff.isSelected()){
				spinner_fromTime.setEnabled(true);
				spinner_toTime.setEnabled(true);
				System.out.println("Enabled Spinners action");
				employee.setsScheduleTime(tmp_day, (String) spinner_fromTime.getValue(), (String) spinner_toTime.getValue());
				tglbtn_onOff.setText("ON");
				tglbtn_onOff.setForeground(Color.GREEN);
				update();
			}else{
				spinner_fromTime.setEnabled(false);
				spinner_toTime.setEnabled(false);
				System.out.println("Disabled Spinners action");
				employee.setsScheduleTime(tmp_day, "00:00", "00:00");
				tglbtn_onOff.setText("OFF");
				tglbtn_onOff.setForeground(Color.RED);
				update();
			}
		}
	}*/
}
