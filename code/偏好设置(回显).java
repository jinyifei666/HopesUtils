package com.itheima.sp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText nameET;
	private EditText phoneET;
	private EditText emailET;
	private SharedPreferences sp;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // 获取3个EditText
        nameET = (EditText) findViewById(R.id.nameET);
        phoneET = (EditText) findViewById(R.id.phoneET);
        emailET = (EditText) findViewById(R.id.emailET);
        
        // 得到一个SharedPrefereneces对象
        sp = getSharedPreferences("config", MODE_PRIVATE);
        
        // 获取以前存储的数据, 把数据放到EditText中
        nameET.setText(sp.getString("name", ""));
        phoneET.setText(sp.getString("phone", ""));
        emailET.setText(sp.getString("email", ""));
    }
    
    public void save(View view) {
    	// 得到要保存的数据
    	String name = nameET.getText().toString();
    	String phone = phoneET.getText().toString();
    	String email = emailET.getText().toString();
    	
    	// 用SharedPrefereneces存储数据
    	Editor editor = sp.edit();			// 得到编辑器
    	editor.putString("name", name);		// 存储数据
    	editor.putString("phone", phone);
    	editor.putString("email", email);
    	editor.commit();					// 提交修改
    	
    	// 弹出提示
    	Toast.makeText(this, "保存成功", 0).show();
    }
}