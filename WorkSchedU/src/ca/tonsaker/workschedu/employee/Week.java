package ca.tonsaker.workschedu.employee;

import com.google.gson.annotations.Expose;

public class Week {
	
	//TODO Go off by date
	//TODO Update APPDATA
	@Expose public String DATE;
	
	@Expose public double TOTAL_HOURS_MONDAY;
	@Expose public double TOTAL_HOURS_TUESDAY;
	@Expose public double TOTAL_HOURS_WEDNESDAY;
	@Expose public double TOTAL_HOURS_THURSDAY;
	@Expose public double TOTAL_HOURS_FRIDAY;
	@Expose public double TOTAL_HOURS_SATURDAY;
	@Expose public double TOTAL_HOURS_SUNDAY;
	@Expose public double TOTAL_HOURS_WEEK;
	
	//From and To hours.  Example: 7:00am-3:30pm
	@Expose public String MONDAY_HOURS;
	@Expose public String TUESDAY_HOURS;
	@Expose public String WEDNESDAY_HOURS;
	@Expose public String THURSDAY_HOURS;
	@Expose public String FRIDAY_HOURS;
	@Expose public String SATURDAY_HOURS;
	@Expose public String SUNDAY_HOURS;

	public Week(String date){
		if(DATE == null){
			MONDAY_HOURS = "1:00am-1:00am";
			TUESDAY_HOURS = "1:00am-1:00am";
			WEDNESDAY_HOURS = "1:00am-1:00am";
			THURSDAY_HOURS = "1:00am-1:00am";
			FRIDAY_HOURS = "1:00am-1:00am";
			SATURDAY_HOURS = "1:00am-1:00am";
			SUNDAY_HOURS = "1:00am-1:00am";
			DATE = date;
		}
	}
}
