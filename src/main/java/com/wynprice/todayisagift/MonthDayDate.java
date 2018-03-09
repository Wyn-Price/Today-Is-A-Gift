package com.wynprice.todayisagift;

public class MonthDayDate 
{
	private final int month;
	private final int day;
	
	public MonthDayDate(int month, int day) {
		this.month = month;
		this.day = day;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getDay() {
		return day;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MonthDayDate)) {
			return false;
		} else {
			MonthDayDate other = (MonthDayDate)obj;
			return other.day == day && other.month == month;
		}
	}
	
	@Override
	public int hashCode() {
		return month * 31 + day;
	}
	
	@Override
	public String toString() {
		return month + "/" + day;
	}
}
