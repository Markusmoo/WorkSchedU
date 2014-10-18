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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Utilities {

	public static boolean savePositions(String[] positions, int[] sortNums) throws IOException{
		String path = System.getenv("APPDATA")+"\\WorkSchedU\\Settings\\positions.json";
		Writer writer = new OutputStreamWriter(new FileOutputStream(path));
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		
		Positions p = new Positions();
		p.POSITIONS = positions;
		p.SORT_NUM = sortNums;
		
		writer.write(gson.toJson(p));
		writer.flush();
		writer.close();
		
		System.out.println("Positions JSON file successfully saved to: "+path); 
		return true;
	}
	
	public static String[] loadPositions() throws FileNotFoundException{
		String path = System.getenv("APPDATA")+"\\WorkSchedU\\Settings\\positions.json";
		FileInputStream file = new FileInputStream(path);
		
		Reader reader = new InputStreamReader(file);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

		Positions p = new Positions();
		p = gson.fromJson(reader, Positions.class);
		return p.POSITIONS;
	}
	
	public static int[] loadPositionSort() throws FileNotFoundException{
		String path = System.getenv("APPDATA")+"\\WorkSchedU\\Settings\\positions.json";
		FileInputStream file = new FileInputStream(path);
		
		Reader reader = new InputStreamReader(file);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

		Positions p = new Positions();
		p = gson.fromJson(reader, Positions.class);
		return p.SORT_NUM;
	}
	
	public static String getDate(int week, int year){

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.YEAR, year);

		Date date = calendar.getTime();
		//System.out.println(dateFormat.format(date)); //TODO Debug
		return dateFormat.format(date);
	}
	
	public static int getDayOfWeek(){
		Calendar cal = Calendar.getInstance();
		
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public static int getWeek(){
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.WEEK_OF_YEAR);
	}
	
	public static int getWeeksOfYear(int year) {
		Calendar cal = new GregorianCalendar();
		cal.set(year, Calendar.JANUARY, 1);
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
}
