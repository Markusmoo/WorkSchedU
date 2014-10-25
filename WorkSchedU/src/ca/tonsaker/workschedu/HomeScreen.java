package ca.tonsaker.workschedu;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;

import ca.tonsaker.workschedu.employee.AddEmployeeFrame;
import ca.tonsaker.workschedu.employee.EditEmployeeFrame;
import ca.tonsaker.workschedu.positions.AddPositionFrame;
import ca.tonsaker.workschedu.utilities.Utilities;

public class HomeScreen extends JFrame implements ActionListener,WindowListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6101442872983046258L;
	
	private int YEAR = 0;
	public JTextField dateTextField;
	public JSpinner weekSpinner;
	
	//Action Listeners
	JMenuItem mntmEditEmployeeSchedule;
	JMenuItem mntmAddEmployee;
	JMenuItem mntmRemoveEmployee;
	JMenuItem mntmAddPosition;
	JMenuItem mntmRemovePosition;
	
	
	//TODO Testing Values.  Make employee object storing total week hours, add save and load data with strings.
	//TODO Remove raw types from JComboBoxes
	private ScheduleTable table;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeScreen homeFrame = new HomeScreen();
					homeFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HomeScreen() {
		Utilities.initializeFiles();
		YEAR = Utilities.getYear();
		
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		setTitle("WorkSchedU");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		setResizable(false);
		
		Utilities.setDarkeningPane(getRootPane());
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmPrintSchedule = new JMenuItem("Print SchedU");
		mnFile.add(mntmPrintSchedule);
		
		JMenuItem mntmEmailSchedule = new JMenuItem("Email SchedU");
		mnFile.add(mntmEmailSchedule);
		
		JMenuItem mntmSaveSchedule = new JMenuItem("Save SchedU");
		mntmSaveSchedule.setIcon(new ImageIcon(HomeScreen.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		mnFile.add(mntmSaveSchedule);
		
		JMenuItem mntmLoadSchedule = new JMenuItem("Load SchedU");
		mntmLoadSchedule.setIcon(new ImageIcon(HomeScreen.class.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		mnFile.add(mntmLoadSchedule);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		
		//Employees JMenu
		JMenu mnEmployees = new JMenu("Employees");
		menuBar.add(mnEmployees);
		
		mntmAddEmployee = new JMenuItem("Add Employee");
		mntmAddEmployee.addActionListener(this);
		mnEmployees.add(mntmAddEmployee);
		
		mntmEditEmployeeSchedule = new JMenuItem("Edit Employee SchedU");
		mntmEditEmployeeSchedule.addActionListener(this);
		mnEmployees.add(mntmEditEmployeeSchedule);
		
		mntmRemoveEmployee = new JMenuItem("Remove Employee");
		mntmRemoveEmployee.addActionListener(this);
		mnEmployees.add(mntmRemoveEmployee);

		
		//Positions JMenu
		JMenu mnPositions = new JMenu("Positions");
		menuBar.add(mnPositions);
		
		mntmAddPosition = new JMenuItem("Add Position");
		mntmAddPosition.addActionListener(this);
		mnPositions.add(mntmAddPosition);
		
		mntmRemovePosition = new JMenuItem("Remove Position");
		mntmRemovePosition.addActionListener(this);
		mnPositions.add(mntmRemovePosition);
		
		
		//Help JMenu
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.setIcon(new ImageIcon(HomeScreen.class.getResource("/com/sun/java/swing/plaf/windows/icons/Question.gif")));
		mnHelp.add(mntmHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setIcon(new ImageIcon(HomeScreen.class.getResource("/com/sun/java/swing/plaf/windows/icons/Inform.gif")));
		mnHelp.add(mntmAbout);
		getContentPane().setLayout(null);
		
		table = new ScheduleTable(35);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultRenderer(new TableRenderer(Utilities.getDayOfWeek(), Utilities.getWeek(), table, weekSpinner));
		table.setBounds(20, 41, 854, 576);
		getContentPane().add(table);
		
		weekSpinner = new JSpinner();
		weekSpinner.setModel(new SpinnerNumberModel(Utilities.getWeek(), 0, Utilities.getWeeksOfYear(YEAR)+1, 1));
		weekSpinner.setBounds(57, 10, 47, 20);
		weekSpinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				int value = (Integer) weekSpinner.getValue();
				if(value > Utilities.getWeeksOfYear(YEAR)){
					YEAR++;
					value = 1;
					weekSpinner.setModel(new SpinnerNumberModel(value, 0, Utilities.getWeeksOfYear(YEAR)+1, 1));

				}else if(value < 1){
					YEAR--;
					value = Utilities.getWeeksOfYear(YEAR);
					weekSpinner.setModel(new SpinnerNumberModel(value, 0, Utilities.getWeeksOfYear(YEAR)+1, 1));
				}
				dateTextField.setText(Utilities.getDate(value, YEAR));
				//System.out.println("Weeks: "+getWeeksOfYear(YEAR)+" Year: "+YEAR); //For Debugging
				table.repaint();
			}
			
		});
		getContentPane().add(weekSpinner);
		
		JLabel lblWeek = new JLabel("Week:");
		lblWeek.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWeek.setBounds(10, 10, 37, 20);
		getContentPane().add(lblWeek);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setBounds(114, 10, 37, 20);
		getContentPane().add(lblDate);
		
		dateTextField = new JTextField();
		dateTextField.setEditable(false);
		dateTextField.setText(Utilities.getDate(Utilities.getWeek(), YEAR));
		dateTextField.setBounds(161, 10, 79, 20);
		getContentPane().add(dateTextField);
		dateTextField.setColumns(10);
	}
	
	public void darkenPane(boolean b){
		Utilities.darkenPane(b, getRootPane());
		setEnabled(!b);
		if(!b)
		this.toFront();
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Selected: " + e.getActionCommand());
		Object src = e.getSource();
		
		if(src == mntmEditEmployeeSchedule){
			EditEmployeeFrame frame = new EditEmployeeFrame(this);
			frame.addWindowListener(this);
		}else if(src == mntmAddEmployee){
			AddEmployeeFrame frame = new AddEmployeeFrame();
			frame.addWindowListener(this);
		}else if(src == mntmRemoveEmployee){
			//TODO STUB
		}else if(src == mntmAddPosition){
			AddPositionFrame frame = new AddPositionFrame();
			frame.addWindowListener(this);
		}else if(src == mntmRemovePosition){
			//TODO STUB
		}
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent e){
		darkenPane(false);
	}

	@Override
	public void windowClosing(WindowEvent arg0) {}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent e) {
		darkenPane(true);
	}
}
