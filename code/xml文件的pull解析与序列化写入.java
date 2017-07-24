package com.itheima.xml;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class PersonService {

	/**
	 * 通过一个关联XML文件的输入流, 解析出一个List集合, 其中包含若干Person对象
	 * @param in	关联XML文件的输入流
	 * @return		包含Person对象的集合, 如果输入流关联的XML不包含Person数据, 那么会得到一个size为0的集合
	 */
	public List<Person> getPersons(InputStream in) throws Exception{
		List<Person> persons = new ArrayList<Person>();
		Person p = null;
		
		XmlPullParser parser = Xml.newPullParser();		// 得到解析器对象
		parser.setInput(in, "UTF-8");					// 设置输入流
		
		for (int type = parser.getEventType(); type != XmlPullParser.END_DOCUMENT; type = parser.next()) {	// 解析XML创建Person对象, 装入集合
			if (type == XmlPullParser.START_TAG) {				// 如果是开始标签事件
				if ("person".equals(parser.getName())){			// 开始的是person
					p = new Person();							// 创建Person对象
					String id = parser.getAttributeValue(0);	// 获取id属性
					p.setId(Integer.parseInt(id));				// 设置为Person的id
					persons.add(p);								// 装入集合
				} else if ("name".equals(parser.getName())){	// 开始的是name
					String name = parser.nextText();			// 获取下一个文本
					p.setName(name);							// 设置为Person的name
				} else if ("age".equals(parser.getName())){		// 开始的是age
					String age = parser.nextText();				// 获取下一个文本
					p.setAge(Integer.parseInt(age));			// 设置为Person的age
				}
			}
		}
		
		return persons;
	}
	//方式二:用while循环
		/**
	 * 解析服务器返回的更新信息
	 * @param is 服务器返回的流
	 * @return 如果发生异常 返回null;
	 */
	public static UpdateInfo getUpdateInfo(InputStream is) {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(is, "utf-8");
			int type = parser.getEventType();
			UpdateInfo info = null;
			while(type!=XmlPullParser.END_DOCUMENT){
				switch (type) {
				case XmlPullParser.START_TAG:
					if("info".equals(parser.getName())){
						info = new UpdateInfo();
					}else if("version".equals(parser.getName())){
						info.setVersion(parser.nextText());
					}else if("description".equals(parser.getName())){
						info.setDescription(parser.nextText());
					}else if("apkurl".equals(parser.getName())){
						info.setApkurl(parser.nextText());
					}
					break;
				}
				type = parser.next();
			}
			return info;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
	

	/**
	 * 向指定的输出流写出XML数据, XML数据为指定集合中的Person信息,写完后就要以把输出流中的xml作处理了,写成一个xml文本或其它
	 * @param fos		输出流.
	 * @param persons	Person集合
	 * @throws Exception 
	 */
	public void writePersons(FileOutputStream fos, List<Person> persons) throws Exception {
		XmlSerializer serializer = Xml.newSerializer();		// 获取序列化工具
		serializer.setOutput(fos, "UTF-8");					// 设置输出流
		
		serializer.startDocument("UTF-8", true);	// 开始文档
		serializer.startTag(null, "persons");		// 开始标签
		
		for (Person p : persons) {
			serializer.startTag(null, "person");
			serializer.attribute(null, "id", p.getId().toString());		// 设置属性
			
			serializer.startTag(null, "name");
			serializer.text(p.getName());			// 设置文本
			serializer.endTag(null, "name");
			
			serializer.startTag(null, "age");
			serializer.text(p.getAge().toString());
			serializer.endTag(null, "age");
			
			serializer.endTag(null, "person");
		}		
		serializer.endTag(null, "persons");			// 结束标签
		serializer.endDocument();					// 结束文档
	}
	
}
