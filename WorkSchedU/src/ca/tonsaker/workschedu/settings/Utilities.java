package ca.tonsaker.workschedu.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
				Positions.savePositions(new String[]{"Crew"}, new int[]{0});
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
}
