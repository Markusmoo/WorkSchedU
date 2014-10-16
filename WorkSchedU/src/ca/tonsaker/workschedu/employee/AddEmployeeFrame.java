package ca.tonsaker.workschedu.employee;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddEmployeeFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddEmployeeFrame frame = new AddEmployeeFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddEmployeeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEmployeeName = new JLabel("Employee Name:");
		lblEmployeeName.setBounds(40, 40, 80, 14);
		contentPane.add(lblEmployeeName);
		
		textField = new JTextField();
		textField.setBounds(130, 37, 200, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(130, 68, 200, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(74, 71, 46, 14);
		contentPane.add(lblUsername);
	}
}
