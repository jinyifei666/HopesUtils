//得到今天00:00:00的long值
Time time = new Time();
time.setToNow();//设置为今天
//time.set(monthDay, month, year);//设置为具体某一年某一月
//time.set(second, minute, hour, monthDay, month, year);//设置为具体某一年某一月某一小时,某一分,某一秒
//设定具体的时间.
time.hour = 0;
time.minute = 0;
time.second = 0;
// false 得到精准的时间    true 得到精准的日期
long firstSecondOfToday = time.toMillis(false);

1.常见应用:
//处理日期
String dateStr = null;
if((date - firstSecondOfToday) > 0 && (date - firstSecondOfToday) < DateUtils.DAY_IN_MILLIS ){
	//今天的信息   显示时间getTimeFormat
	dateStr = DateFormat.getTimeFormat(context).format(date);
	
} else {
	//显示日期getDateFormat
	dateStr = DateFormat.getDateFormat(context).format(date);
}
