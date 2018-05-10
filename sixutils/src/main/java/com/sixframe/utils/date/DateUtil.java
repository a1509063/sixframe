package com.sixframe.utils.date;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtil {
	public static boolean isDate(String date) {
		if (date.length() != 8)
			return false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			dateFormat.parse(date);
		} catch (Exception e) {
			return false;
		}

		String year = date.substring(0, 4);
		int y = Integer.parseInt(year);
		if (y < 1975 || y > 2100)
			return false;

		String month = date.substring(4, 6);
		int m = Integer.parseInt(month);
		if (m < 1 || m > 12)
			return false;

		String day = date.substring(6);
		int d = Integer.parseInt(day);
		if (d < 1)
			return false;

		String lastday = getMonthLastDate(date);
		int ld = Integer.parseInt(lastday.substring(6));
		if (d > ld)
			return false;// 大于本月最后一天，返回假

		return true;
	}

	public static boolean isTime(String time) {
		if (time.length() != 6)
			return false;

		String hour = time.substring(0, 2);
		int h = Integer.parseInt(hour);
		if (h < 0 || h > 24)
			return false;

		String miet = time.substring(2, 4);
		int m = Integer.parseInt(miet);
		if (m < 0 || m > 59)
			return false;
		if (h == 24 && m != 0)
			return false;

		String sd = time.substring(4);
		int s = Integer.parseInt(sd);
		if (s < 0 || s > 59)
			return false;
		if (h == 24 && s != 0)
			return false;

		return true;
	}

	/**
	 * 把YYYY-MM-DD个串格式化成YYYYMMDD
	 * 
	 * @param date
	 * @return
	 */
	public static String format2DB(String date) {
		if (date == null || "".equals(date))
			return "";
		return date.replaceAll("-", "");
	}

	public static String formatTime2DB(String time) {
		if (time == null || "".equals(time))
			return "";
		return time.replaceAll(":", "");
	}

	// 把YYYYMMDD个串格式化成YYYYMMDDYYYY-MM-DD
	public static String formatFromDB(String date) {
		if (date == null || "".equals(date))
			return "";
		StringBuffer buf = new StringBuffer(date);
		return buf.insert(6, '-').insert(4, '-').toString();
	}

	public static String formatTimeFromDB(String time) {
		if (time == null || "".equals(time))
			return "";
		StringBuffer buf = new StringBuffer(time);
		return buf.insert(6, ':').insert(4, ':').toString();
	}

	public static String formatDateTimeFromDB(String date, String time) {
		if (date == null || "".equals(date) || date.length() < 8)
			date = "        ";
		if (time == null || "".equals(time) || date.length() < 6)
			time = "      ";
		StringBuffer buf = new StringBuffer(date);
		buf.insert(6, '-').insert(4, '-');
		StringBuffer buf1 = new StringBuffer(time);
		buf1.insert(2, ':').insert(5, ':');
		return buf.toString() + " " + buf1.toString();
	}

	// 提取下拉框的年份
	public static List<String> getYear() {
		List<String> vRet = new ArrayList<String>();
		int iCurrYear = Integer.parseInt((getSysDate()).substring(0, 4));
		for (int i = 0; i <= iCurrYear - 2005 + 3; i++) {
			vRet.add(Integer.toString(2005 + i));
		}
		return vRet;
	}

	// 根据给定的年份和季度提取对应的季未日期
	public static String getQuarterOfLastDate(String ogYear, int iValue) {
		String strRetDate = "";
		switch (iValue) {
		case 1:
			strRetDate = ogYear + "0331";
			break;
		case 2:
			strRetDate = ogYear + "0630";
			break;
		case 3:
			strRetDate = ogYear + "0930";
			break;
		case 4:
			strRetDate = ogYear + "1231";
			break;
		}

		return strRetDate;
	}

	// 根据参数ogdate，得到ogdate这个月的最后一个日期，例如：
	// getLastDate("200308")=20030831
	// 参数ogdate必须是6位（到月）或8位（到日）
	public static String getMonthLastDate(String ogdate) {
		if (ogdate.length() == 6)
			ogdate = ogdate + "01";
		else {// 把ogdate变成前6位加01的串，如20030805-->20030801
			ogdate = ogdate.substring(0, 6) + "01";
		}
		ogdate = getNextDateByMonth(ogdate, 1);
		ogdate = getNextDateByNum(ogdate, -1);
		return ogdate;
	}

	// 根据参数ogdate，得到ogdate这个月的最后一个工作日，例如：
	// getMonthLastDateNoWeekend("200308")=20030829
	// 参数ogdate必须是6位（到月）或8位（到日）
	public static String getMonthLastWorkDate(String ogdate) {
		String sDate = getMonthLastDate(ogdate);
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		java.util.Date date = simpledateformat.parse(sDate, new ParsePosition(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int iWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (iWeek == 7)
			sDate = getNextDateByNum(sDate, -1);
		if (iWeek == 1)
			sDate = getNextDateByNum(sDate, -2);
		date = calendar.getTime();
		return sDate;
	}

	// 得到+i以后的日期，i可以是负数
	public static String getNextDateByNum(String s, int i) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		java.util.Date date = simpledateformat.parse(s, new ParsePosition(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(5, i);
		date = calendar.getTime();
		s = simpledateformat.format(date);
		return s;
	}

	// 得到+i以后的日期，i可以是负数
	public static String getNextDateByNum1(String s, int i) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		java.util.Date date = simpledateformat.parse(s, new ParsePosition(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(5, i);
		date = calendar.getTime();
		s = simpledateformat.format(date);

		int iDate = Integer.parseInt(s.substring(0, 4));
		if (iDate < Integer.parseInt(getSysDate().substring(0, 4))) {
			s = Integer.toString(iDate) + "1231";
		}

		return s;
	}

	// 得到+i以后的月，i可以是负数
	public static String getNextDateByMonth(String s, int i) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		java.util.Date date = simpledateformat.parse(s, new ParsePosition(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(2, i);
		date = calendar.getTime();
		s = simpledateformat.format(date);
		return s;
	}

	/** 得到+i以后的年，i可以是负数 */
	public static String getNextDateByYear(String s, int i) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		java.util.Date date = simpledateformat.parse(s, new ParsePosition(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, i);
		date = calendar.getTime();
		s = simpledateformat.format(date);
		return s;
	}

	public static String getSysTime() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("kkmmss");
		String s = simpledateformat.format(Calendar.getInstance().getTime());
		return s;
	}

	// String sRegisterDate=DataCanculate.getSysDate();得到了系统当前日期
	public static String getSysDate() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		String s = simpledateformat.format(Calendar.getInstance().getTime());
		return s;
	}

	public static String getCNDate(String fdate) {
		if (fdate == null || "".equals(fdate))
			return "";
		String cur_date = fdate;
		cur_date = cur_date.substring(0, 4) + "年" + cur_date.substring(4, 6) + "月" + cur_date.substring(6) + "日";
		return cur_date;
	}

	public static String getCNTime(String ftime) {
		String cur_time = ftime;
		cur_time = cur_time.substring(0, 2) + "时" + cur_time.substring(2, 4) + "分" + cur_time.substring(4) + "秒";
		return cur_time;
	}

	/**
	 * 计算开始日期到结束日期之间的天数
	 */
	public static int getDatePeriod(String startDate, String endDate) {
		String[] date1 = startDate.split("-");
		String[] date2 = endDate.split("-");

		GregorianCalendar gc1 = new GregorianCalendar(Integer.parseInt(date1[0]), Integer.parseInt(date1[1]),
				Integer.parseInt(date1[2]));

		GregorianCalendar gc2 = new GregorianCalendar(Integer.parseInt(date2[0]), Integer.parseInt(date2[1]),
				Integer.parseInt(date2[2]));

		long longDate1 = gc1.getTimeInMillis();
		long longDate2 = gc2.getTimeInMillis();
		long period = longDate2 - longDate1;

		period /= 24 * 60 * 60 * 1000;

		return (int) period;
	}

	/**
	 * 计算两个日期相差的天数
	 * 
	 * @param startDate
	 *            格式：yyyyMMdd
	 * @param endDate
	 *            格式：yyyyMMdd
	 * @return 返回两日期相差的天数
	 */
	public static int dateMargin(String startDate, String endDate) {
		String d1 = format2DB(startDate);
		String d2 = format2DB(endDate);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(df.parse(d1, new ParsePosition(0)));
		Date end = df.parse(d2, new ParsePosition(0));

		int margin = 0;
		int step = startDate.compareTo(endDate) > 0 ? -1 : 1;
		while (calendar.getTime().compareTo(end) != 0) {
			calendar.add(Calendar.DATE, step);
			margin += step;

		}
		return margin;

	}

	/**
	 * 计算两个时间的差（单位：秒）
	 * 
	 * @param begin_dt
	 *            YYYYMMDDhhmmss
	 * @param end_dt
	 *            YYYYMMDDhhmmss
	 * @return 差（秒）
	 */
	public static int getSecsDiff(String begin_dt, String end_dt) {
		if (begin_dt == null || end_dt == null)
			return 0;
		if (begin_dt.length() == 8)
			begin_dt = begin_dt + "000000";
		if (begin_dt.length() == 6)
			begin_dt = getSysDate() + begin_dt;
		if (end_dt.length() == 8)
			end_dt = end_dt + "000000";
		if (end_dt.length() == 6)
			end_dt = getSysDate() + end_dt;

		int iBYYYY = Integer.parseInt(begin_dt.substring(0, 4));
		int iBMM = Integer.parseInt(begin_dt.substring(4, 6));
		int iBDD = Integer.parseInt(begin_dt.substring(6, 8));
		int iBhh = Integer.parseInt(begin_dt.substring(8, 10));
		int iBmm = Integer.parseInt(begin_dt.substring(10, 12));
		int iBss = Integer.parseInt(begin_dt.substring(12, 14));
		int iEYYYY = Integer.parseInt(end_dt.substring(0, 4));
		int iEMM = Integer.parseInt(end_dt.substring(4, 6));
		int iEDD = Integer.parseInt(end_dt.substring(6, 8));
		int iEhh = Integer.parseInt(end_dt.substring(8, 10));
		int iEmm = Integer.parseInt(end_dt.substring(10, 12));
		int iEss = Integer.parseInt(end_dt.substring(12, 14));
		Calendar BeginDate = new GregorianCalendar(iBYYYY, iBMM, iBDD, iBhh, iBmm, iBss);
		Calendar EndDate = new GregorianCalendar(iEYYYY, iEMM, iEDD, iEhh, iEmm, iEss);
		long lBegin = BeginDate.getTime().getTime();
		long lEnd = EndDate.getTime().getTime();
		// long lDiff = (lEnd > lBegin) ? (lEnd - lBegin) : (lBegin - lEnd);
		long lDiff = lBegin - lEnd;
		BeginDate = null;
		EndDate = null;
		return (int) (lDiff / 1000);
	}

	/**
	 * 判断传入日期是否为工作日（参数必须是14位或8位）
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isWorkDay(String date) {
		if (date == null)
			return false;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			Date newDate = dateFormat.parse(date);
			calendar.setTime(newDate);
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		}
		if (Calendar.SATURDAY == calendar.get(Calendar.DAY_OF_WEEK)
				|| Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
			return false;
		}
		return true;
	}

	/**
	 * 判断传入日期是否为周五
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isWeekEndFRIDAY(String date) {
		if (date == null)
			return false;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			Date newDate = dateFormat.parse(date);
			calendar.setTime(newDate);
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		}
		if (Calendar.FRIDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断传入日期是否为周一
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isWeekStartMONDAY(String date) {
		if (date == null)
			return false;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			Date newDate = dateFormat.parse(date);
			calendar.setTime(newDate);
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return false;
		}
		if (Calendar.MONDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
			return true;
		}
		return false;
	}
}
