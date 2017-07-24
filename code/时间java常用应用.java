1.java里面获取指定时间年，月，日的总结，java jsp获取指定时间
/**
* 计算指定日期的上一天
* 
* @param dateTime
*            日期，格式为：yyyy-MM-dd
* @return
*/
	public static String getBeforeDay(String dateTime) {
		/* Calendar now = Calendar.getInstance(); */
		SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = simpledate.parse(dateTime);
		} catch (ParseException ex) {
			System.out.println("日期格式不符合要求：" + ex.getMessage());
			return null;
		}
		now.setTime(date);
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		int day = now.get(Calendar.DAY_OF_MONTH) - 1;
		now.set(year, month, day);
		String time = simpledate.format(now.getTime());
		return time;
	}

	/**
	 * 计算指定日期的下一天
	 * 
	 * @param dateTime
	 *            日期，格式为：yyyy-MM-dd
	 * @return
	 */
	public static String getNextDay(String dateTime) {
		Calendar now = Calendar.getInstance();
		SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = simpledate.parse(dateTime);
		} catch (ParseException ex) {
			System.out.println("日期格式不符合要求：" + ex.getMessage());
			return null;
		}
		now.setTime(date);
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		int day = now.get(Calendar.DAY_OF_MONTH) + 1;
		now.set(year, month, day);
		String time = simpledate.format(now.getTime());
		return time;
	}

	/**
	 * 得到指定月的天数
	 * 
	 * @param _year
	 * @param _month
	 * @return
	 */
	public static int getMaxDayOfMonth(int _year, int _month) {
		Calendar now = Calendar.getInstance();
		int year = 0;
		int month = 0;
		if (_month == 1) {
			year = _year - 1;
			month = 12;
		} else {
			year = _year;
			month = _month - 1;
		}

		now.set(Calendar.YEAR, year);
		now.set(Calendar.MONTH, month);
		return now.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 计算时间差
	 * 
	 * @param beginTime
	 *            开始时间，格式：yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 *            结束时间，格式：yyyy-MM-dd HH:mm:ss
	 * @return 从开始时间到结束时间之间的时间差（秒）
	 */
	public static long getTimeDifference(String beginTime, String endTime) {
		long between = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date end = null;
		Date begin = null;
		try {// 将截取到的时间字符串转化为时间格式的字符串
			end = sdf.parse(endTime);
			begin = sdf.parse(beginTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		return between;
	}

	/**
	 * 计算时间差
	 * 
	 * @param time
	 *            指定的时间，格式为：yyyy-MM-dd HH:mm:ss
	 * @return 当前时间和指定时间的时间差（秒）
	 */
	public static long getTimeDifference(String time) {
		long between = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String systemTime = sdf.format(new Date()).toString();
		Date end = null;
		Date begin = null;
		try {// 将截取到的时间字符串转化为时间格式的字符串
			end = sdf.parse(time);
			begin = sdf.parse(systemTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		between = Math.abs(end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		return between;
	}
以上是在用java程序开发中常常用到计算时间的方法总结