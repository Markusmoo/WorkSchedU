package ca.tonsaker.workschedu;

import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ScheduleTable extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2268763594596342047L;

	@SuppressWarnings("rawtypes")
	public ScheduleTable(int numRows) {
		super();
		
		DefaultTableModel dfTable = new DefaultTableModel(
				new Object[][] {
					{"Names:", "Sunday:", "Monday:", "Tuesday:", "Wednesday:", "Thursday:", "Friday:", "Saturday:"}
				},
				new String[] {
					"Names:", "Sunday:", "Monday:", "Tuesday:", "Wednesday:", "Thursday:", "Friday:", "Saturday:"
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
