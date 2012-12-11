package ho.module.transfer.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HTWeek {

	private int season;
	private int week;

	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public static HTWeek getHTWeekByDate(Date date) {
		HTWeek htw = new HTWeek();
		Calendar origin = new GregorianCalendar();
		origin.set(1997, 8, 22, 0, 0, 0);
		origin.set(Calendar.MILLISECOND, 0);
		long msDiff = date.getTime() - origin.getTimeInMillis();
		long dayDiff = msDiff / 1000 / 60 / 60 / 24;
		int season = (int) Math.floor(dayDiff / (16 * 7)) + 1;
		int week = (int) Math.floor((dayDiff % (16 * 7)) / 7) + 1;

		htw.setSeason(season);
		htw.setWeek(week);

		return htw;
	}

}
