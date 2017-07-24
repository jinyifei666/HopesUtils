内容提供者之获取通话记录
//联系人读写权限
<uses-permission android:name="android.permission.WRITE_CONTACTS"/>
<uses-permission android:name="android.permission.READ_CONTACTS"/>
//要用内容提供者最重要的要获取Uri 就是这个内容提供者的名称authorities,这个是定义内容提供者时定义的,要获取就要查对应的内容提供得的Manifest.xml中的定义
//D:\Android\Android_src\packages\providers\ContactsProvider下的AndroidManifest.xml中查找发现有:
 <provider android:name="CallLogProvider"
            android:authorities="call_log"//这个就是内容提供者的名称
            android:syncable="false" android:multiprocess="false"
            android:readPermission="android.permission.READ_CONTACTS"
            android:writePermission="android.permission.WRITE_CONTACTS">
   </provider>
   //获取了之后,我们就是要查其数据库中定义的表了,那具体是哪张表,这个就要到出数据库文件来看下了
   //所有与联系人相关的数据都保存在data/data/com.android.providers.contects/databases下的数据文件中.
   //而calls表就是通话记录的表,其中number列表示电话号码 date列表示时间long型 duration表示通话时长
	// type表示通话类型,1表示呼入,2表示呼出.未接表示3.name表示联系人的名字
   //一般而言,内容提供者都会定义表的匹配器UriMatcher,方便查询表中的数据 ,如下:CallLogProvider类中:
   private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(CallLog.AUTHORITY, "calls", CALLS);//这就表示这张表的配器是calls
        sURIMatcher.addURI(CallLog.AUTHORITY, "calls/#", CALLS_ID);
        sURIMatcher.addURI(CallLog.AUTHORITY, "calls/filter/*", CALLS_FILTER);
    }
	
//dao代码实现
public class CallLogDao {
	private  ContentResolver resolver ;
	private  Uri uri=Uri.parse("content://call_log/calls");
	public  CallLogDao(Context context){
		resolver = context.getContentResolver();
	}
	/**
	 * 查询所有呼叫记录
	 * @return 所有联系人通话记录的集合
	 */
public   List<MyCallLog> getCallog(){
	List<MyCallLog> list=new ArrayList<MyCallLog>();
	//除uri后面有四个参数,各表示String[] projection(列名的集合), String selection(表示where后面的如:_id=?), 
	//String[] selectionArgs(前面占位符的数组), String sortOrder(排序和分页));
	Cursor cursor = resolver.query(uri, new String[]{"number","date","duration","type","name"}, null, null, null);
	while (cursor.moveToNext()) {
		MyCallLog callog=new MyCallLog();
		String phone = cursor.getString(0);
		callog.setPhone(phone);
		String time = cursor.getString(1);
		callog.setTime(time);
		String duration = cursor.getString(2);
		callog.setDuration(duration);
		String type = cursor.getString(3);
		callog.setType(type);
		String name = cursor.getString(4);
		callog.setType(name);
		list.add(callog);
	}
	return list;
}
/**
 * 通过号码查询某一个联系人记录
 * @param incomingNumber
 * @return 这个联系人通话记录的集合
 */
public   List<MyCallLog> findCallogByNumber(String incomingNumber){
	List<MyCallLog> list=new ArrayList<MyCallLog>();
	Cursor cursor = resolver.query(uri, new String[]{"date","duration","type","name"}, "number=?", new String[]{incomingNumber}, null);
	while (cursor.moveToNext()) {
		MyCallLog callog=new MyCallLog();
		callog.setPhone(incomingNumber);
		String time = cursor.getString(0);
		callog.setTime(time);
		String duration = cursor.getString(1);
		callog.setDuration(duration);
		String type = cursor.getString(2);
		callog.setType(type);
		String name = cursor.getString(3);
		callog.setType(name);
		list.add(callog);
	}
	return list;
}
	 // 通过号码删除呼叫记录	 
	public void deleteCallLog(String incomingNumber) {
		Uri uri = Uri.parse("content://call_log/calls");
		resolver.delete(uri, "number=?", new String[] { incomingNumber });
	}
}
   
