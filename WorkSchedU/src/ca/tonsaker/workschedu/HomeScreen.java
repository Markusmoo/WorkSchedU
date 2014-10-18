package ca.tonsaker.workschedu;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.JSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;

import ca.tonsaker.workschedu.employee.EditEmployeeFrame;
import ca.tonsaker.workschedu.settings.Utilities;

public class HomeScreen extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6101442872983046258L;
	
	private int YEAR = 0;
	public JTextField dateTextField;
	public JSpinner weekSpinner;
	
	
	//TODO Testing Values.  Make employee object storing total week hours, add save and load data with strings.
	private ScheduleTable table;
	
	public class JSpinnerListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {}

	}
	
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
		YEAR = Utilities.getYear();
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		setTitle("WorkSchedU");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		//TODO Debug
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("(Mouse: x:"+arg0.getX()+" y:"+arg0.getY()+")");
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
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
		
		JMenu mnEmployees = new JMenu("Employees");
		menuBar.add(mnEmployees);
		
		JMenuItem mntmAddEmployee = new JMenuItem("Add Employee");
		mnEmployees.add(mntmAddEmployee);
		
		JMenuItem mntmEditEmployeeSchedule = new JMenuItem("Edit Employee SchedU");
		mntmEditEmployeeSchedule.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
			    System.out.println("Selected: " + e.getActionCommand());
			    openEmployeeManager();
			  }
			
		});
		mnEmployees.add(mntmEditEmployeeSchedule);
		
		JMenuItem mntmRemoveEmployee = new JMenuItem("Remove Employee");
		mnEmployees.add(mntmRemoveEmployee);
		
		JMenuItem mntmViewEmployeeInfo = new JMenuItem("View Employee Info");
		mnEmployees.add(mntmViewEmployeeInfo);
		
		JMenu mnPositions = new JMenu("Positions");
		menuBar.add(mnPositions);
		
		JMenuItem mntmAddPosition = new JMenuItem("Add Position");
		mnPositions.add(mntmAddPosition);
		
		JMenuItem mntmRemovePosition = new JMenuItem("Remove Position");
		mnPositions.add(mntmRemovePosition);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.setIcon(new ImageIcon(HomeScreen.class.getResource("/com/sun/java/swing/plaf/windows/icons/Question.gif")));
		mnHelp.add(mntmHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setIcon(new ImageIcon(HomeScreen.class.getResource("/com/sun/java/swing/plaf/windows/icons/Inform.gif")));
		mnHelp.add(mntmAbout);
		getContentPane().setLayout(null);
		
		weekSpinner = new JSpinner();
		weekSpinner.setModel(new SpinnerNumberModel(Utilities.getWeek(), 0, Utilities.getWeeksOfYear(YEAR)+1, 1));
		weekSpinner.setBounds(57, 10, 47, 20);
		weekSpinner.addChangeListener(new JSpinnerListener(){

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
		
		table = new ScheduleTable(29);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultRenderer(new TableRenderer(Utilities.getDayOfWeek(), Utilities.getWeek(), table, weekSpinner));
		table.setBounds(20, 41, 754, 480);
		getContentPane().add(table);
		
		//new EmployeeFrame(this); TODO debug
	}
	
	public void openEmployeeManager(){
		new EditEmployeeFrame(this);
	}
}
