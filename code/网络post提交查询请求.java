package com.itheima.webservice;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;

import android.accounts.NetworkErrorException;
import android.util.Xml;

public class QueryService {

	public String query(String num) throws Exception {
		InputStream in = QueryService.class.getClassLoader().getResourceAsStream("template.xml");
		String data = new String(Util.read(in));	// 读取模板文件, 得到要发送的xml
		in.close();
		data = data.replace("phoneNum", num);		// 用号码替换占位符
		
		URL url = new URL("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx");		// 创建Webservice的Url地址
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(3000);
		conn.setRequestMethod("POST");
		
		conn.setRequestProperty("Host", "webservice.webxml.com.cn");
		conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
		conn.setRequestProperty("Content-Length", data.getBytes().length + "");
		conn.setDoOutput(true);
		conn.getOutputStream().write(data.getBytes());
		
		int code = conn.getResponseCode();
		if (code == 200) {
			return parseAddress(conn.getInputStream());		// 获取服务端发回的XML, 解析出地址
		}
		throw new NetworkErrorException("服务器出错: " + code);
	}

	private String parseAddress(InputStream inputStream) throws Exception {
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inputStream, "UTF-8");
		for (int type = parser.getEventType(); type != XmlPullParser.END_DOCUMENT; type = parser.next()) {
			if (type == XmlPullParser.START_TAG && parser.getName().equals("getMobileCodeInfoResult")) {
				return parser.nextText();
			}
		}
		return null;
	}

}
