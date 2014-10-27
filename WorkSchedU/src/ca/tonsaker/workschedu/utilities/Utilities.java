package ca.tonsaker.workschedu.utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JComponent;
import javax.swing.JRootPane;

import ca.tonsaker.workschedu.employee.Employee;
import ca.tonsaker.workschedu.positions.Positions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Utilities {
	
	public static String getDate(int week, int year){

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.YEAR, year);

		Date date = calendar.getTime();
		//System.out.println(dateFormat.format(date)); //TODO Debug
		return dateFormat.format(date);
	}
	
	public static int getDayOfWeek(){
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public static int getWeek(){
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	public static int getWeeksOfYear(int year) {
		Calendar cal = new GregorianCalendar();
		cal.set(year, Calendar.JANUARY, 1);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		
		return cal.getWeeksInWeekYear();
	}
	
	public static int getYear(){
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public static String getDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static void initializeFiles(){
		try{
			Positions.loadPositions();
		}catch(Exception e){
			try {
				Positions.savePositions(new String[]{"None"});
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		try {
			Employee.load();
		} catch (Exception e) {
			try {
				new Employee().save();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static int convertCalendarDayToIndex(int dayOfWeek){
		switch(dayOfWeek){
			case(Calendar.MONDAY): return 0;
			case(Calendar.TUESDAY): return 1;
			case(Calendar.WEDNESDAY): return 2;
			case(Calendar.THURSDAY): return 3;
			case(Calendar.FRIDAY): return 4;
			case(Calendar.SATURDAY): return 5;
			case(Calendar.SUNDAY): return 6;
			default: return 0;
		}
	}
	
	public static int convertIndexToCalendarDay(int index){
		switch(index){
			case(0): return Calendar.MONDAY;
			case(1): return Calendar.TUESDAY;
			case(2): return Calendar.WEDNESDAY;
			case(3): return Calendar.THURSDAY;
			case(4): return Calendar.FRIDAY;
			case(5): return Calendar.SATURDAY;
			case(6): return Calendar.SUNDAY;
			default: return 0;
		}
	}
	
	public static double getTotalHours(String time){
		try {
			String timeFrom = time.substring(0, time.indexOf("-"));
			String timeTo = time.substring(time.indexOf("-")+1);
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
			Date dateObj1 = sdf.parse(timeFrom);
		    Date dateObj2 = sdf.parse(timeTo);

		    long diff = dateObj2.getTime() - dateObj1.getTime();
		    double diffInHours = diff / ((double) 1000 * 60 * 60);
		    
		    return diffInHours;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static double getTotalHours(String timeFrom, String timeTo){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
			Date dateObj1 = sdf.parse(timeFrom);
		    Date dateObj2 = sdf.parse(timeTo);

		    long diff = dateObj2.getTime() - dateObj1.getTime();
		    double diffInHours = diff / ((double) 1000 * 60 * 60);
		    
		    return diffInHours;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * @deprecated
	 */
	public static double convertTimeHoursToDouble24(String time){ //TODO Check if messes up with 12am/pm due to.. 12am, 1am, 2am, ext.
		double newTime = 0;
		time = time.trim();
		if(time.endsWith("pm")){
			time = time.substring(0, time.lastIndexOf("pm"));
			time.replaceAll(":", "");
			newTime = Integer.parseInt(time)*2;
		}else if(time.endsWith("am")){
			time = time.substring(0, time.lastIndexOf("am"));
			time.replaceAll(":", "");
			newTime = Integer.parseInt(time)*2;
		}else{
			System.out.println("Error: Time could not be converted to Double-24Hours. Value: "+time);
		}
		System.out.println("Time: "+time+" New Time: "+newTime); //TODO Debug
		return newTime;
	}
	
	public static void darkenPane(boolean b, JComponent comp){
		if(b){
			comp.getRootPane().getGlassPane().setVisible(true);
		}else{
			comp.getRootPane().getGlassPane().setVisible(false);
		}
	}
	
	public static void setDarkeningPane(JRootPane root){
		root.setGlassPane(new JComponent() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = -6217779820295728816L;

			public void paintComponent(Graphics g) {
		        g.setColor(new Color(0, 0, 0, 100));
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		});
	}
}
