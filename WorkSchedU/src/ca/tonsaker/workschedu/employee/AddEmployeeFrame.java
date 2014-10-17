package ca.tonsaker.workschedu.employee;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import java.awt.Font;

import javax.swing.ImageIcon;

public class AddEmployeeFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	private JButton btnCancel;
	private JButton btnAdd;
	
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
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Add New Employee");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEmployeeName = new JLabel("Employee Name:");
		lblEmployeeName.setBounds(37, 26, 80, 14);
		contentPane.add(lblEmployeeName);
		
		textField = new JTextField();
		textField.setBounds(127, 23, 200, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField(); //TODO Check if username exists
		textField_1.setBounds(127, 54, 200, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(65, 57, 52, 14);
		contentPane.add(lblUsername);
		
		btnAdd = new JButton("Add and Exit");
		btnAdd.setBounds(10, 128, 107, 23);
		btnAdd.addActionListener(this);
		contentPane.add(btnAdd);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(285, 128, 89, 23);
		contentPane.add(btnCancel);
		
		JLabel lblPosition = new JLabel("Position:");
		lblPosition.setBounds(76, 88, 41, 14);
		contentPane.add(lblPosition);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(127, 85, 200, 20);
		contentPane.add(comboBox);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == btnAdd){
			//TODO add new employee
			this.dispose();
		}else if(src == btnCancel){
			this.dispose();
		}
	}
}
