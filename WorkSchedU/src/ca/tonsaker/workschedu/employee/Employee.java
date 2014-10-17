package ca.tonsaker.workschedu.employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import ca.tonsaker.workschedu.ScheduleTable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Employee { 
	//TODO remember to implement user hours accordingly to week
	//TODO Create DeleteEmployeeFrame.java
	
	//http://www.apache.org/licenses/LICENSE-2.0
	//http://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/index.html
	//http://www.javacreed.com/simple-gson-example/
	
	private ScheduleTable scdTable;
	
	@Expose private String EMPLOYEE_NAME = "";
	@Expose private String EMPLOYEE_EMAIL = "";
	@Expose private String EMPLOYEE_USERNAME = "";
	
	//TODO Go off by date
	@Expose private Week[] weeks; //TODO Debug Should be done in add employee menu
			private int referenceWeekIdx;
	
	public String toString(){
		return this.EMPLOYEE_NAME;
				/*"Employee [EMPLOYEE_NAME="+this.EMPLOYEE_NAME
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
				+"]";*/
	}

	public boolean save() throws IOException{
		String path = System.getenv("APPDATA")+"\\WorkSchedU\\Employees\\user"+this.EMPLOYEE_USERNAME+".json";
		Writer writer = new OutputStreamWriter(new FileOutputStream(path));
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		
		writer.write(gson.toJson(this));
		writer.flush();
		writer.close();
		
		System.out.println("Employee JSON file successfully saved to: "+path); 
		return true;
	}
	
	public static Employee load(String username) throws FileNotFoundException{
		Employee e = new Employee();
		String path = System.getenv("APPDATA")+"\\WorkSchedU\\Employees\\user"+username+".json";
		FileInputStream file = new FileInputStream(path);
		
		Reader reader = new InputStreamReader(file);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

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
			
				FileInputStream file = new FileInputStream(listOfFiles[i]);
				
				Reader reader = new InputStreamReader(file);
				Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		
				Employee temp_employee = gson.fromJson(reader, Employee.class);
				if(temp_employee != null){
					temp_employeesArray[i] = temp_employee;
					System.out.println("Loaded file " + listOfFiles[i].getName());
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
	
	public void setDate(String dateByWeek){
		int idx = 0;
		for(Week i : weeks){
			if(i != null && i.DATE != null){
				if(i.DATE.equals(dateByWeek)){
					referenceWeekIdx = idx;
					return;
				}
			}else{
				weeks[idx] = new Week(dateByWeek);
				return;
			}
			idx++;
		}
		Week[] w = new Week[weeks.length+1];
		System.arraycopy(weeks, 0, w, 0, weeks.length);
		weeks = w;
		weeks[idx] = new Week(dateByWeek);
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
			case 0: scdTable.setCell(1, 1, time); weeks[referenceWeekIdx].SUNDAY_HOURS = time; break;
			case 1: scdTable.setCell(1, 2, time); weeks[referenceWeekIdx].MONDAY_HOURS = time; break;
			case 2: scdTable.setCell(1, 3, time); weeks[referenceWeekIdx].TUESDAY_HOURS = time; break;
			case 3: scdTable.setCell(1, 4, time); weeks[referenceWeekIdx].WEDNESDAY_HOURS = time; break;
			case 4: scdTable.setCell(1, 5, time); weeks[referenceWeekIdx].THURSDAY_HOURS = time; break;
			case 5: scdTable.setCell(1, 6, time); weeks[referenceWeekIdx].FRIDAY_HOURS = time; break;
			case 6: scdTable.setCell(1, 7, time); weeks[referenceWeekIdx].SATURDAY_HOURS = time; break;
			default: scdTable.setCell(1, 1, time); weeks[referenceWeekIdx].SUNDAY_HOURS = time; break;
		}
		
		return scdTable;
	}
	
	public double getTotalDayHours(int day){
		switch(day){
			case 0: return weeks[referenceWeekIdx].TOTAL_HOURS_SUNDAY;
			case 1: return weeks[referenceWeekIdx].TOTAL_HOURS_MONDAY;
			case 2: return weeks[referenceWeekIdx].TOTAL_HOURS_TUESDAY;
			case 3: return weeks[referenceWeekIdx].TOTAL_HOURS_WEDNESDAY;
			case 4: return weeks[referenceWeekIdx].TOTAL_HOURS_THURSDAY;
			case 5: return weeks[referenceWeekIdx].TOTAL_HOURS_FRIDAY;
			case 6: return weeks[referenceWeekIdx].TOTAL_HOURS_SATURDAY;
			default: return weeks[referenceWeekIdx].TOTAL_HOURS_SUNDAY;
		}
	}
	
	public double getTotalWeekHours(){
		return weeks[referenceWeekIdx].TOTAL_HOURS_WEEK =
				weeks[referenceWeekIdx].TOTAL_HOURS_SUNDAY+
				weeks[referenceWeekIdx].TOTAL_HOURS_MONDAY+
				weeks[referenceWeekIdx].TOTAL_HOURS_TUESDAY+
				weeks[referenceWeekIdx].TOTAL_HOURS_WEDNESDAY+
				weeks[referenceWeekIdx].TOTAL_HOURS_THURSDAY+
				weeks[referenceWeekIdx].TOTAL_HOURS_FRIDAY+
				weeks[referenceWeekIdx].TOTAL_HOURS_SATURDAY;
	}
	
	public double convertTimeHoursToDouble24Hour(String time){ //TODO Check if messes up with 12am/pm due to.. 12am, 1am, 2am, ext.
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