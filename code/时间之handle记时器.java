package time.auto;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimeActivity extends Activity {
	private long startTime;
	private TextView timeTV;
	private String time;
	Handler handler;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	//建立一个新线程.
	Runnable updateThread = new Runnable() {
		public void run() {
			if (handler != null) {
				//表示每隔多久,向handle发送这段代码
				handler.postDelayed(updateThread, 1000);
				//实现秒记时器
				long longtime = (System.currentTimeMillis() - startTime) / 1000;
				time = String.format("%02d:%02d:%02d", longtime / 3600,
						longtime / 60, longtime % 60);
				timeTV.setText(time);
			}
		}
	};
//定义按钮事件
	public void start(View view) {
		Button button = (Button) view;
//通过判断按钮显示实现,去实现时间的切换.
		if ("录像".equals(button.getText()) && handler == null) {
			//每次开始,则新键一个handler去,且重新获得当前时间.
			handler = new Handler();
			startTime = System.currentTimeMillis();
			//向handler发送代码
			handler.post(updateThread);
			button.setText("停止");
		} else {
			//一旦停止,则让handle为空
			handler = null;
			button.setText("录像");
		}
	}
}
