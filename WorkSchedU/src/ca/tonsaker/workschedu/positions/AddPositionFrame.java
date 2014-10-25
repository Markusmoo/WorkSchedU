package ca.tonsaker.workschedu.positions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class AddPositionFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8564566401149282972L;
	private JPanel contentPane;
	
	private JComboBox<String> comboBox;
	private JTextField textField;
	private JButton btnCancel;
	private JButton btnAddAndExit;

	/**
	 * Create the frame.
	 */
	public AddPositionFrame() {
		setTitle("Add New Position");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 400, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPosition = new JLabel("Position:");
		lblPosition.setBounds(71, 45, 41, 14);
		contentPane.add(lblPosition);
		textField = new JTextField();
		textField.setBounds(122, 42, 200, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		try {
			JLabel lblSortAfter = new JLabel("Sort After:");
			lblSortAfter.setBounds(60, 80, 52, 14);
			contentPane.add(lblSortAfter);
			String[] positions = Positions.loadPositions();
			if(positions != null){
				comboBox = new JComboBox<>(positions);
				comboBox.setBounds(122, 77, 200, 20);
				comboBox.setToolTipText("Sort this position on the spreadsheet after this position");
				contentPane.add(comboBox);
			}else{
				comboBox = new JComboBox<>();
				comboBox.setBounds(122, 77, 200, 20);
				comboBox.setToolTipText("Sort this position on the spreadsheet after this position");
				comboBox.setEnabled(false);
				contentPane.add(comboBox);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		btnAddAndExit = new JButton("Add and Exit");
		btnAddAndExit.setBounds(10, 137, 107, 23);
		btnAddAndExit.addActionListener(this);
		contentPane.add(btnAddAndExit);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(295, 137, 89, 23);
		btnCancel.addActionListener(this);
		contentPane.add(btnCancel);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Selected: " + e.getActionCommand());
		Object src = e.getSource();
		
		if(src == btnAddAndExit){
			if(!addPosition()) return;
			this.dispose();
		}else if(src == btnCancel){
			this.dispose();
		}
	}
	
	public boolean addPosition(){ //Create algorithm that allows you to add a pos to a certain spot in the array
		if(textField.getText().equals("")){
			JOptionPane.showMessageDialog(this, "You didn't add a position!", "Missing Fields", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		try {
			String[] poses = Positions.loadPositions();
			String[] newPoses = new String[poses.length+1];
			int idx = 0;
			for(String p : poses){
				newPoses[idx] = p;
				if(p.equals(comboBox.getSelectedItem())){
					idx++;
					newPoses[idx] = textField.getText();
				}
				idx++;
			}
			Positions.savePositions(newPoses);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Unknown Error Occured", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
}
