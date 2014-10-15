package ca.tonsaker.workschedu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class Employee { 
	//http://www.apache.org/licenses/LICENSE-2.0
	//http://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/index.html
	//http://www.javacreed.com/simple-gson-example/
	
	private ScheduleTable scdTable;
	
	@Expose public String EMPLOYEE_NAME;
	@Expose public String EMPLOYEE_EMAIL;
	@Expose public String EMPLOYEE_USERNAME;
	
	@Expose public double TOTAL_HOURS_WEEK;
	@Expose public double TOTAL_HOURS_SUNDAY;
	@Expose public double TOTAL_HOURS_MONDAY;
	@Expose public double TOTAL_HOURS_TUESDAY;
	@Expose public double TOTAL_HOURS_WEDNESDAY;
	@Expose public double TOTAL_HOURS_THURSDAY;
	@Expose public double TOTAL_HOURS_FRIDAY;
	@Expose public double TOTAL_HOURS_SATURDAY;
	
	//From and To hours.  Example: 7:00am-3:30pm
	@Expose public String SUNDAY_HOURS;
	@Expose public String MONDAY_HOURS;
	@Expose public String TUESDAY_HOURS;
	@Expose public String WEDNESDAY_HOURS;
	@Expose public String THURSDAY_HOURS;
	@Expose public String FRIDAY_HOURS;
	@Expose public String SATURDAY_HOURS;
	
	public String toString(){
		return "Employee [EMPLOYEE_NAME="+this.EMPLOYEE_NAME
				+", EMPLOYEE_EMAIL="+this.EMPLOYEE_EMAIL
				+", EMPLOYEE_USERNAME="+this.EMPLOYEE_USERNAME
				+", TOTAL_HOURS_WEEK="+this.TOTAL_HOURS_WEEK
				+", TOTAL_HOURS_SUNDAY="+this.TOTAL_HOURS_SUNDAY
				+", TOTAL_HOURS_MONDAY="+this.TOTAL_HOURS_MONDAY
				+", TOTAL_HOURS_TUESDAY="+this.TOTAL_HOURS_TUESDAY
				+", TOTAL_HOURS_WEDNESDAY="+this.TOTAL_HOURS_WEDNESDAY
				+", TOTAL_HOURS_THURSDAY="+this.TOTAL_HOURS_THURSDAY
				+", TOTAL_HOURS_FRIDAY="+this.TOTAL_HOURS_FRIDAY
				+", TOTAL_HOURS_SATURDAY="+this.TOTAL_HOURS_SATURDAY
				+", SUNDAY_HOURS="+this.SUNDAY_HOURS
				+", MONDAY_HOURS="+this.MONDAY_HOURS
				+", TUESDAY_HOURS="+this.TUESDAY_HOURS
				+", WEDNESDAY_HOURS="+this.WEDNESDAY_HOURS
				+", THURSDAY_HOURS="+this.THURSDAY_HOURS
				+", FRIDAY_HOURS="+this.FRIDAY_HOURS
				+", SATURDAY_HOURS="+this.SATURDAY_HOURS
				+"]";
	}

	public boolean save() throws IOException{
		String path = System.getenv("APPDATA")+"\\WorkSchedU\\Employees\\user"+this.EMPLOYEE_USERNAME+".json";
		Writer writer = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		
		gson.toJson(this, writer);
		
		System.out.println("Employee JSON file successfully saved to: "+path); 
		return true;
	}
	
	public static Employee load(String username) throws FileNotFoundException{
		Employee e = new Employee();
		String path = System.getenv("APPDATA")+"\\WorkSchedU\\Employees\\user"+username+".json";
		FileInputStream file = new FileInputStream(path);
		
		Reader reader = new InputStreamReader(file);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		e = gson.fromJson(reader, Employee.class);
		return e;
	}
	
	public static Employee[] load() throws FileNotFoundException{
		String path = System.getenv("APPDATA")+"\\WorkSchedU\\Employees\\";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		
		Employee[] temp_employeesArray = new Employee[listOfFiles.length];
		int empIdx = 0;
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && listOfFiles[i].getName().substring(listOfFiles[i].getName().indexOf('.')).equals(".json")){
				System.out.println("File " + listOfFiles[i].getName());
			
				FileInputStream file = new FileInputStream(listOfFiles[i]);
				
				Reader reader = new InputStreamReader(file);
				Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		
				Employee temp_employee = gson.fromJson(reader, Employee.class);
				if(temp_employee != null){
					temp_employeesArray[i] = temp_employee;
						empIdx++;
				}else{
					try {
						throw new Exception("Json File "+path+listOfFiles[i].getName()+" is an invalid Employee.class json file");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		Employee[] employeesArray = new Employee[empIdx];
		int empIdx2 = 0;
		for(Employee e : temp_employeesArray){
			if(e != null){
				employeesArray[empIdx2] = e;
				empIdx2++;
			}
		}
		return employeesArray;
	}
	
	public void setName(String name){
		EMPLOYEE_NAME = name;
	}
	
	public void setEmail(String email){
		EMPLOYEE_EMAIL = email;
	}
	
	public void setUsername(String user){
		EMPLOYEE_USERNAME = user;
	}
	
	public String getName(){
		return EMPLOYEE_NAME;
	}
	
	public String getEmail(){
		return EMPLOYEE_EMAIL;
	}
	
	public String getUsername(){
		return EMPLOYEE_USERNAME;
	}

	public ScheduleTable updateTable(){
		scdTable.repaint();
		return scdTable;
	}
	
	public ScheduleTable setsScheduleTime(int day, String fromTime12, String toTime12){
		String time;
		time = fromTime12 + "-" + toTime12;
		switch(day){
			case 0: scdTable.setCell(1, 1, time); SUNDAY_HOURS = time; break;
			case 1: scdTable.setCell(1, 2, time); MONDAY_HOURS = time; break;
			case 2: scdTable.setCell(1, 3, time); TUESDAY_HOURS = time; break;
			case 3: scdTable.setCell(1, 4, time); WEDNESDAY_HOURS = time; break;
			case 4: scdTable.setCell(1, 5, time); THURSDAY_HOURS = time; break;
			case 5: scdTable.setCell(1, 6, time); FRIDAY_HOURS = time; break;
			case 6: scdTable.setCell(1, 7, time); SATURDAY_HOURS = time; break;
			default: scdTable.setCell(1, 1, time); SUNDAY_HOURS = time; break;
		}
		
		return scdTable;
	}
	
	public double getTotalDayHours(int day){
		switch(day){
			case 0: return TOTAL_HOURS_SUNDAY;
			case 1: return TOTAL_HOURS_MONDAY;
			case 2: return TOTAL_HOURS_TUESDAY;
			case 3: return TOTAL_HOURS_WEDNESDAY;
			case 4: return TOTAL_HOURS_THURSDAY;
			case 5: return TOTAL_HOURS_FRIDAY;
			case 6: return TOTAL_HOURS_SATURDAY;
			default: return TOTAL_HOURS_SUNDAY;
		}
	}
	
	public double getTotalWeekHours(){
		return TOTAL_HOURS_WEEK =
				TOTAL_HOURS_SUNDAY+
				TOTAL_HOURS_MONDAY+
				TOTAL_HOURS_TUESDAY+
				TOTAL_HOURS_WEDNESDAY+
				TOTAL_HOURS_THURSDAY+
				TOTAL_HOURS_FRIDAY+
				TOTAL_HOURS_SATURDAY;
	}
	
	public double convertTimeHoursToDouble24Hour(String time){
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
}
