//序列化存储相对而言比较安全,操作也比较简单,只要把对象实现序列化接口就可以,并可以存集合,数组.用Object流就可以,但局限性在于只能在一个应用下.
public class CallLogDao {
	private  ContentResolver resolver ;
	private  Uri uri=Uri.parse("content://call_log/calls");
	public  CallLogDao(Context context){
		resolver = context.getContentResolver();
	}	
	//扩展:用于提供备份的进度数据,具体以什么方式展现进度由调用者决定,这就是回调函数.
	public interface BackUpProcessListener{
		void beforeBackup(int max);
		void onProcessUpdate(int process);
	}
	
	//读取短信,并序列化到SD卡中
	public void backUpSms(BackUpProcessListener listener) throws Exception{
		Uri uri = Uri.parse("content://sms/");
		Cursor cursor = resolver.query(uri, new String[]{"address","date","type","body"} , null, null, null);
		listener.beforeBackup(cursor.getCount());
		int total = 0;
		List<MySms> list=new ArrayList<MySms>();
		while(cursor.moveToNext()){
			MySms sms=new MySms();
			String address = cursor.getString(0);
			String date  =cursor.getString(1);
			String type  =cursor.getString(2);
			String body  =cursor.getString(3);
			sms.setAddress(address);
			sms.setBody(body);
			sms.setDate(date);
			sms.setType(type);
			list.add(sms);
			total++;
			listener.onProcessUpdate(total);
		}
		
			File file=new File(Environment.getExternalStorageDirectory(), "sms.dat");
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath()));
			out.writeObject(list); // !!!!!!这个方法可以写集合、数组
			out.close();
			cursor.close();
	}
	
	/**
	 * 反序列化读取短信
	 * 注:反序列化的关健在于,之前序列化的对象的类必须存在,且包名都必须一样,这也是反序列化的局限性,所以要在同一个应用下,这是没有问题的.
	 * @throws Exception
	 */
	public void writeSms() throws Exception{
		File file=new File(Environment.getExternalStorageDirectory(), "sms.dat");
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
		//获得集合对象
		Object object = in.readObject();
		List<MySms> sms=(List<MySms>) object;
			for (int i = 0; i < sms.size(); i++) {
				//获得对象
				MySms mysms=sms.get(i);
				System.out.println(mysms.getAddress());
				System.out.println(mysms.getBody());
			}
			in.close();
		}
}
