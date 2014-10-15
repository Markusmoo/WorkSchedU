package ca.tonsaker.workschedu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class SchedulePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -765570225566072406L;
	
	//Grid
	private int horizontalLines = 0;
	private int verticalLines = 0;
	
	private String[] headers = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

	public SchedulePanel(int columns) {
		super(new GridBagLayout());
		this.setBackground(Color.WHITE);
	}
	
	public void setVerticalColumns(int num){
		verticalLines = num;
		repaint();
	}
	
	public void setHorizontalColumns(int num){
		horizontalLines = num;
		repaint();
	}
	
	/*public void addVerticalColumns(int num){
		verticalLines += num;
		repaint();
	}*/
	
	public void addHorizontalColumns(int num){
		horizontalLines += num;
		repaint();
	}
	
	/*public void removeVerticalColumns(int num){
		verticalLines -= num;
		repaint();
	}*/
	
	public void removeHorizontalColumns(int num){
		horizontalLines -= num;
		repaint();
	}
	
	public void paint(Graphics g){
		//Set background to white
		Color orgColour = g.getColor();
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(orgColour);
		
		//Draw vertical lines
		if(verticalLines > 0){
			int verticalLineSpace = this.getWidth() / verticalLines;
			for(int x = 0; x < verticalLines; x++){
				g.drawLine(x*verticalLineSpace, 0, x*verticalLineSpace, this.getHeight());
			}
		}
		
		//Draw horizontal lines
		if(horizontalLines > 0){
			int horizontalLineSpace = this.getHeight() / horizontalLines;
			for(int y = 0; y < horizontalLines; y++){
				g.drawLine(0, y*horizontalLineSpace, this.getWidth(), y*horizontalLineSpace);
			}
		}
	}
}
