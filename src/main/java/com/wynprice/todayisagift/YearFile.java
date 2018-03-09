package com.wynprice.todayisagift;

import java.util.HashMap;
import java.util.Random;

public class YearFile 
{
	
	private final HashMap<MonthDayDate, String[]> dayMessageList = new HashMap<>();
	
	public YearFile(String file) {
		String[] allDays = file.split("###");
		for(int i = 1; i < allDays.length; i++ ) {
			String current = allDays[i];
			String[] allDayMessages = current.split("\n");
			String[] currentDate = allDayMessages[0].split("/");
			String[] dayMessages = new String[allDayMessages.length - 1];
			for(int i1 = 1; i1 < allDayMessages.length; i1++) {
				dayMessages[i1 - 1] = allDayMessages[i1];
			}
			dayMessageList.put(new MonthDayDate(Integer.valueOf(currentDate[0]), Integer.valueOf(currentDate[1])), dayMessages);
		}
	}
	
	public String getToday(int month, int day, long rand) {
		MonthDayDate date = new MonthDayDate(month, day);
		String[] ret = dayMessageList.get(date);
		return ret == null ? "Unregisted date: " + date : ret[new Random(rand).nextInt(ret.length)];
	}
}
