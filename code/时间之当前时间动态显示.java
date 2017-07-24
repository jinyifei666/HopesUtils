package time.auto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class TimeActivity extends Activity {

	private String DEFAULT_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
	private TextView timeTV;
	private String time;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		handler.post(updateThread);
		// 将将要执行的线程对象添加到线程队列当中，此时将会把该线程对象添加到handler对象的线程队列当中
		timeTV = (TextView) findViewById(R.id.timeTV);
	}
	// 创建Handler对象
	Handler handler = new Handler();
	// 新建一个线程对象
	Runnable updateThread = new Runnable() {
		// 将要执行的操作写在线程对象的run方法当中
		public void run() {
			handler.postDelayed(updateThread, 1000);
			// 调用Handler的postDelayed()方法
			// 这个方法的作用是：将要执行的线程对象放入到队列当中，待时间结束后，运行制定的线程对象
			// 第一个参数是Runnable类型：将要执行的线程对象
			// 第二个参数是long类型：延迟的时间，以毫秒为单位
			SimpleDateFormat dateFormatter = new SimpleDateFormat(
					DEFAULT_TIME_FORMAT);
			time = dateFormatter.format(Calendar.getInstance().getTime());
			timeTV.setText(time);
		}
	};

}