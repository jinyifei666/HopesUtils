短信的读取与写入
//在com..android.provider.telephony下的databases下的mmsmms.db.有两张比较重要的表:threads表和sms表
//threads表对应的是联系人,每一行数据代表一个联系人,其中message_count表示有多少条短信,用thread_id约束sms表,每个thread_id对应多条sms数据
//其是sms表中:address表示号码 person列1表示在联系人中已存,0表示这个号没有存,date表示收到的时间,read列中1表示已读,0表示未读,
//type中1表示收短信,2表示发短信,body表示短信内容.
public class SmsUtils {
	private Context context;

	public SmsUtils(Context context) {
		this.context = context;
	}
	/**
	 *定义一个接口 ,把ui里需要的数据定义为接口里方法的参数
	 *在把接口的对象作为工具类的参数,并里工具类里调用两个接口方法,把工具类里的数据当方法的参数.
	 *在把接口的对象作为工具类的参数,并里工具类里调用两个接口方法,把工具类里的数据当方法的参数.
	 *使用者可以作为progressbar,也可以作其它用,这样藕合性就低了,复用性就高了.
	 * @author fada
	 *
	 */
	public interface BackUpProcessListener{
		void beforeBackup(int max);
		void onProcessUpdate(int process);
	}
	
	//OutputStream表示需要一个输出流,定义xml文件写放的位置.
	public void backUpSms(OutputStream os, BackUpProcessListener listener) throws Exception{
		Uri uri = Uri.parse("content://sms/");
		XmlSerializer  serializer = Xml.newSerializer();
		serializer.setOutput(os, "utf-8");
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, "smss");
		Cursor cursor = context.getContentResolver().query(uri, new String[]{"address","date","type","body"} , null, null, null);
		listener.beforeBackup(cursor.getCount());
		int total = 0;
		while(cursor.moveToNext()){
			String address = cursor.getString(0);
			String date  =cursor.getString(1);
			String type  =cursor.getString(2);
			String body  =cursor.getString(3);
			serializer.startTag(null, "sms");
			
			serializer.startTag(null, "address");
			serializer.text(address);
			serializer.endTag(null, "address");

			serializer.startTag(null, "date");
			serializer.text(date);
			serializer.endTag(null, "date");
			
			serializer.startTag(null, "type");
			serializer.text(type);
			serializer.endTag(null, "type");
			
			serializer.startTag(null, "body");
			serializer.text(body);
			serializer.endTag(null, "body");
			
			serializer.endTag(null, "sms");
			
			os.flush();
			total++;
			listener.onProcessUpdate(total);
			Thread.sleep(1000);
		}
		cursor.close();
		serializer.endTag(null, "smss");
		serializer.endDocument();
		os.flush();
		os.close();
		
	}
	
	public void restoreSms(){
		//读取xml文件. 把每一条短信的数据获取出来,插入到系统的数据库
	}
	
}
