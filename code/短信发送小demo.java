package com.itheima.sms;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText numET;
	private EditText contentET;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Activity创建的时候, 获取两个文本框
        numET = (EditText) findViewById(R.id.numET);
        contentET = (EditText) findViewById(R.id.contentET);
    }
    
    public void sendSms(View view) {
    	// 点击按钮时, 获取文本框中的文本
    	String num = numET.getText().toString().trim();
    	String content = contentET.getText().toString();
    	
    	if (num.length() == 0) {
    		Toast.makeText(getApplicationContext(), R.string.num_err, Toast.LENGTH_SHORT).show();
    		return;
    	}
    	if (content.length() == 0) {
    		Toast.makeText(getApplicationContext(), R.string.insert_content, Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	try {
			// 发送短信
			SmsManager manager = SmsManager.getDefault();				// 获取短信管理器
			ArrayList<String> list = manager.divideMessage(content);	// 将短信分段 因为短信可能超过字数,那么就会分割成两段.
			//int i=SmsMessage.MAX_USER_DATA_BYTES;// 这个可以得到一条短信的最大长度
			for (String sms : list) {									// 循环遍历
				manager.sendTextMessage(num, null, sms, null, null);	// 发送短信
			}
			
			// 清空文本, 弹出提示
			contentET.setText("");
			Toast.makeText(getApplicationContext(), R.string.send_success, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}