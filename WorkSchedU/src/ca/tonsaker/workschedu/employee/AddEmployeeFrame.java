package ca.tonsaker.workschedu.employee;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JComboBox;

import ca.tonsaker.workschedu.settings.Utilities;

import java.awt.Font;
import java.awt.Color;

public class AddEmployeeFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;
	private JFormattedTextField textField_1;
	private JTextField textField_2;

	private JButton btnCancel;
	private JButton btnAdd;
	
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox_1;
	private JLabel lblOptionalName;
	private JLabel lblOptionalUser;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public AddEmployeeFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Add New Employee");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 400, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEmployeeName = new JLabel("Employee Name:");
		lblEmployeeName.setBounds(37, 14, 80, 14);
		contentPane.add(lblEmployeeName);
		textField = new JTextField();
		textField.setToolTipText("Employee's name (First and Last)");
		textField.setBounds(127, 11, 200, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		lblOptionalName = new JLabel("*");
		lblOptionalName.setForeground(Color.RED);
		lblOptionalName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOptionalName.setToolTipText("Optional");
		lblOptionalName.setBounds(337, 14, 17, 14);
		contentPane.add(lblOptionalName);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(65, 45, 52, 14);
		contentPane.add(lblUsername);
		textField_1 = new JFormattedTextField();
		textField_1.setToolTipText("Employee's Username (Usually numbers)");
		textField_1.setBounds(127, 42, 200, 20);
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
		textField_1.setDocument(doc);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		lblOptionalUser = new JLabel("*");
		lblOptionalUser.setForeground(Color.RED);
		lblOptionalUser.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOptionalUser.setToolTipText("Optional");
		lblOptionalUser.setBounds(337, 45, 17, 14);
		contentPane.add(lblOptionalUser);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(89, 76, 28, 14);
		contentPane.add(lblEmail);
		textField_2 = new JTextField();
		textField_2.setToolTipText("Employee's Email (Optional)");
		textField_2.setBounds(127, 73, 200, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		btnAdd = new JButton("Add and Exit");
		btnAdd.setToolTipText("Add employee to list and exit this window");
		btnAdd.setBounds(10, 137, 107, 23);
		btnAdd.addActionListener(this);
		contentPane.add(btnAdd);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(295, 137, 89, 23);
		contentPane.add(btnCancel);
		
		try {
			JLabel lblPosition = new JLabel("Position:");
			lblPosition.setBounds(76, 112, 41, 14);
			contentPane.add(lblPosition);
			String[] positions = Utilities.loadPositions();
			if(positions != null){
				comboBox_1 = new JComboBox(positions);
				comboBox_1.setBounds(127, 106, 200, 20);
				comboBox_1.setToolTipText("The position that the employee usually works");
				contentPane.add(comboBox_1);
			}else{
				comboBox_1 = new JComboBox();
				comboBox_1.setBounds(127, 106, 200, 20);
				comboBox_1.setToolTipText("The position that the employee usually works");
				comboBox_1.setEnabled(false);
				contentPane.add(comboBox_1);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == btnAdd){
			if(!addEmployee()) return;
			this.dispose();
		}else if(src == btnCancel){
			this.dispose();
		}
	}
	
	private boolean usernameExists(){
		String path = System.getenv("APPDATA")+"\\WorkSchedU\\Employees\\user"+textField_1.getText()+".json";
		FileInputStream file;
		try {
			file = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			return false;
		}
		try {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean addEmployee(){
		if(!usernameExists() && !textField_1.getText().equals("")){
			Employee e = new Employee();
			e.setDate("IGNORE");
			String email = textField_2.getText().trim();
			if(email.indexOf('@') > -1){
				e.setEmail(email);
			}else{
				e.setEmail("NONE");
			}
			if(textField_1.getText().equals("")){
				JOptionPane.showMessageDialog(this, "You didn't add a username!", "Missing Fields", JOptionPane.WARNING_MESSAGE);
				return false;
			}else{
				e.setUsername(textField_1.getText());
			}
			e.setUsername(textField_1.getText());
			if(textField.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "You didn't add a name!", "Missing Fields", JOptionPane.WARNING_MESSAGE);
				return false;
			}else{
				e.setName(textField.getText());
			}
			try {
				e.save();
			} catch (IOException error) {
				error.printStackTrace();
				JOptionPane.showMessageDialog(this, "Unknown Error Occured", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return true;
		}else{
			JOptionPane.showMessageDialog(this, "Username "+ textField_1.getText() +" already exists!", "Username Exists", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
}
