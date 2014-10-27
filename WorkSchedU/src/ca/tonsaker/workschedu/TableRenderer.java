package ca.tonsaker.workschedu;

import java.awt.Component;
import java.util.Calendar;

import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableRenderer extends DefaultTableCellRenderer { 
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1790628053074311726L;
	
	int dayOfWeek;
	int week;
	ScheduleTable table;
	JSpinner weekSpinner;
	
	public TableRenderer(int dayOfWeek, int week, ScheduleTable table, JSpinner weekSpinner){
		super();
		if(dayOfWeek > Calendar.SUNDAY){
			this.dayOfWeek = dayOfWeek-1;
		}else if(dayOfWeek == Calendar.SUNDAY){
			this.dayOfWeek = 7;
		}
		
		this.week = week;
		this.table = table;
		this.weekSpinner = weekSpinner;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int column) { 
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 

		if (!table.isRowSelected(row)){
			if(row % 2 == 0){
				c.setBackground(new java.awt.Color(212, 212, 212));
			}else{
				c.setBackground(table.getBackground());
			}
			
			if(column == 0 && row % 2 == 0){
				c.setBackground(new java.awt.Color(180, 180, 180));
			}else if(column == 0){
				c.setBackground(new java.awt.Color(210, 210, 210));
			}
			
			if(row == 0 && column % 2 == 0){
				c.setBackground(new java.awt.Color(130, 130, 130));
			}else if(row == 0){
				c.setBackground(new java.awt.Color(160, 160, 160));
			}

			if(row == 0 && column == dayOfWeek && (int) weekSpinner.getValue() == week){ //TODO Remove comments
				c.setBackground(new java.awt.Color(208, 181, 12));
			}
		}
    
		return c; 
	}
} 
