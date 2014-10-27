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

import ca.tonsaker.workschedu.positions.Positions;

import java.awt.Font;
import java.awt.Color;

public class AddEmployeeFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -827334320917688682L;
	private JPanel contentPane;
	private JTextField textField_name;
	private JFormattedTextField textField_username;
	private JTextField textField_email;

	private JButton btn_Cancel;
	private JButton btn_Add;
	
	private JComboBox<String> comboBox_1;
	private JLabel lbl_requiredName;
	private JLabel lbl_requiredUsername;

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 400, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_name = new JLabel("Employee Name:");
		lbl_name.setBounds(37, 14, 80, 14);
		contentPane.add(lbl_name);
		textField_name = new JTextField();
		textField_name.setToolTipText("Employee's name (First and Last initial (or however you like))");
		textField_name.setBounds(127, 11, 200, 20);
		contentPane.add(textField_name);
		textField_name.setColumns(10);
		lbl_requiredName = new JLabel("*");
		lbl_requiredName.setForeground(Color.RED);
		lbl_requiredName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbl_requiredName.setToolTipText("Required");
		lbl_requiredName.setBounds(337, 14, 17, 14);
		contentPane.add(lbl_requiredName);
		
		JLabel lbl_username = new JLabel("Username:");
		lbl_username.setBounds(65, 45, 52, 14);
		contentPane.add(lbl_username);
		textField_username = new JFormattedTextField();
		textField_username.setToolTipText("Employee's Username (Usually numbers)");
		textField_username.setBounds(127, 42, 200, 20);
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
		contentPane.add(textField_username);
		textField_username.setColumns(10);
		lbl_requiredUsername = new JLabel("*");
		lbl_requiredUsername.setForeground(Color.RED);
		lbl_requiredUsername.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbl_requiredUsername.setToolTipText("Required");
		lbl_requiredUsername.setBounds(337, 45, 17, 14);
		contentPane.add(lbl_requiredUsername);
		
		JLabel lbl_email = new JLabel("Email:");
		lbl_email.setBounds(89, 76, 28, 14);
		contentPane.add(lbl_email);
		textField_email = new JTextField();
		textField_email.setToolTipText("Employee's Email (Optional)");
		textField_email.setBounds(127, 73, 200, 20);
		contentPane.add(textField_email);
		textField_email.setColumns(10);
		
		btn_Add = new JButton("Add and Exit");
		btn_Add.setToolTipText("Add employee to list and exit this window");
		btn_Add.setBounds(10, 137, 107, 23);
		btn_Add.addActionListener(this);
		contentPane.add(btn_Add);
		
		btn_Cancel = new JButton("Cancel");
		btn_Cancel.addActionListener(this);
		btn_Cancel.setBounds(295, 137, 89, 23);
		contentPane.add(btn_Cancel);
		
		try {
			JLabel lbl_position = new JLabel("Position:");
			lbl_position.setBounds(76, 112, 41, 14);
			contentPane.add(lbl_position);
			String[] positions = Positions.loadPositions();
			if(positions != null){
				comboBox_1 = new JComboBox<>(positions);
				comboBox_1.setBounds(127, 106, 200, 20);
				comboBox_1.setToolTipText("The position that the employee usually works");
				contentPane.add(comboBox_1);
			}else{
				comboBox_1 = new JComboBox<>();
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
		System.out.println("Selected: " + e.getActionCommand());
		Object src = e.getSource();
		
		if(src == btn_Add){
			if(!addEmployee()) return;
			this.dispose();
		}else if(src == btn_Cancel){
			this.dispose();
		}
	}
	
	private boolean usernameExists(){
		String path = System.getenv("APPDATA")+"\\WorkSchedU\\Employees\\user"+textField_username.getText()+".json";
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
		if(!usernameExists() && !textField_username.getText().equals("")){
			Employee e = new Employee();
			e.setDate("IGNORE");
			String email = textField_email.getText().trim();
			if(email.indexOf('@') > -1){
				e.setEmail(email);
			}else{
				e.setEmail("NONE");
			}
			if(comboBox_1 != null){
				e.setPosition(comboBox_1.getSelectedItem().toString());
			}
			if(textField_username.getText().equals("")){
				JOptionPane.showMessageDialog(this, "You didn't add a username!", "Missing Fields", JOptionPane.WARNING_MESSAGE);
				return false;
			}else{
				e.setUsername(textField_username.getText());
			}
			e.setUsername(textField_username.getText());
			if(textField_name.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this, "You didn't add a name!", "Missing Fields", JOptionPane.WARNING_MESSAGE);
				return false;
			}else{
				e.setName(textField_name.getText());
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
			JOptionPane.showMessageDialog(this, "Username "+ textField_username.getText() +" already exists!", "Username Exists", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
}
