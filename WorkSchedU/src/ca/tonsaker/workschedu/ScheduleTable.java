package ca.tonsaker.workschedu;

import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class ScheduleTable extends JTable{
	
	//TODO Add date beside schedule headers.  Example:  Monday, Oct. 5:
	
	public static int NAME = 0;
	public static int MONDAY = 1;
	public static int TUESDAY = 2;
	public static int WEDNESDAY = 3;
	public static int THURSDAY = 4;
	public static int FRIDAY = 5;
	public static int SATURDAY = 6;
	public static int SUNDAY = 7;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2268763594596342047L;

	@SuppressWarnings("rawtypes")
	public ScheduleTable(int numRows) {
		super();
		setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		DefaultTableModel dfTable = new DefaultTableModel(
				new Object[][] {
					{"Names:", "Monday:", "Tuesday:", "Wednesday:", "Thursday:", "Friday:", "Saturday:", "Sunday:"}
				},
				new String[] {
					"Names:", "Monday:", "Tuesday:", "Wednesday:", "Thursday:", "Friday:", "Saturday:", "Sunday:"
				}
			){
				/**
				 * 
				 */
				private static final long serialVersionUID = -4834569138576084865L;
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
		
		for(int i = numRows; i>0; i--){
			dfTable.addRow(new Vector());
		}
		
		this.setModel(dfTable);
	}
	
	public void setCell(int rowIndex, int columnIndex, Object aValue){
		this.dataModel.setValueAt(aValue, rowIndex, columnIndex);
	}
	
	public void setDefaultRenderer(TableRenderer tblRender){
		super.setDefaultRenderer(Object.class, tblRender);
	}
}
