//联系人读写权限
<uses-permission android:name="android.permission.WRITE_CONTACTS"/>
<uses-permission android:name="android.permission.READ_CONTACTS"/>
import java.util.ArrayList;
import java.util.Collections;
//注:删除联系人删除的是raw_contacts下的contact_id列的值
import android.content.ContentProviderOperation;
//联系人在contacts2数据库中数据库以三张表存在:data,raw_contacts,mimetypes 
//对地联系人而言,实际数据在data表示,但是受raw_contacts约束,有多个少联系人就多少行数据,里面raw_contacts里的contact_id列的每一个值对应一个联系人
//,data里的是联系人的所有数据,而通过mimetypes表示mimetype区别联系人的数据类型,是电话,还是名字....
public class ContactInfoProvider {
	private static List<ContactInfo> list;//定义一个集合来存储联系人,ContactInfo是一个联系人对象

	public static List<ContactInfo> getContactInfos(Context context) {
		//获得内容提供者的操作类
		ContentResolver resolver = context.getContentResolver();
		list = new ArrayList<ContactInfo>();//初始化集合.
		//用查寻方法查找id表raw_contacts
		Uri uri=Uri.parse("content://com.android.contacts/raw_contacts");
		//用id查找所有匹配的数据
		Uri datauri = Uri.parse("content://com.android.contacts/data");
		//删除联系人删除的是raw_contacts下的contact_id列的值
		Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);
		//从结果集中获得id
		while (cursor.moveToNext()) {
			String id = cursor.getString(0);//因为只有一列,且是第一列,所以可以一直用0
			if (id!=null) {
				ContactInfo info = new ContactInfo();//每次开始查建立一个对象
				//查到所有联系人数据
				Cursor cursorData = resolver.query(datauri, new String[]{"mimetype", "data1"}, "raw_contact_id=?", new String[]{id}, null);
				//遍历用数据类型筛选出需要的联系人数据保存入对象,
				String data1=null;
				while(cursorData.moveToNext()) {
					String mimetype = cursorData.getString(0);
					data1 = cursorData.getString(1);
					if (data1==null)//判断是否有数据
						break;
					//用数据类型表区分是姓名还是电话//查这个看到表来查
					
					if("vnd.android.cursor.item/name".equals(mimetype)){
						info.setName(data1);
					}else if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
						info.setPhone(data1);
					}
				}
				if (data1!=null){
				list.add(info);//将对象添加入集合.
				}
				cursorData.close();//关闭数据库连接
			}
		}
		cursor.close();//关闭数据库连接
		return list;
	}
	/**
 * 分页查询联系人
orderby="_id desc limit 30 offset "+?;具体从第?条开始查,注意,必须结合排序来查因为这个参数不能直接limit开头,desc表示倒序,也可以是其它排法
 * @param context 上下文
 * @param orderby 全查用null,如果要分页就加"_id limit 总共多少条  offset 从第几条开始"
 * @return
 */
	public static List<ContactInfo> getContactInfos(Context context,String orderby) {
		//获得内容提供者
		ContentResolver resolver = context.getContentResolver();
		list = new ArrayList<ContactInfo>();
		//用查寻方法查找id表raw_contacts
		Uri uri=Uri.parse("content://com.android.contacts/raw_contacts");
		//用id查找所有匹配的数据
		Uri datauri = Uri.parse("content://com.android.contacts/data");
			Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, orderby);
		return getList(resolver, datauri, cursor);
	}

	private static List<ContactInfo> getList(ContentResolver resolver,
			Uri datauri, Cursor cursor) {
		//从结果集中获得id
		while (cursor.moveToNext()) {
			String id = cursor.getString(0);//因为只有一列,且是第一列,所以可以一直用0
			if (id!=null) {
				Cursor cursorData = resolver.query(datauri, new String[]{"mimetype", "data1"}, "raw_contact_id=?", new String[]{id}, null);
				ContactInfo info = new ContactInfo();
				String data1=null;
				while(cursorData.moveToNext()) {
					String mimetype = cursorData.getString(0);
					data1 = cursorData.getString(1);
					if (data1==null)//判断是否有数据
						break;
						// 用数据类型表区分是姓名还是电话//查这个看到表来查
						if ("vnd.android.cursor.item/name".equals(mimetype)) {
							info.setName(data1);
						} else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
							info.setPhone(data1);
						}
				}
				if (data1!=null)
				list.add(info);
				cursorData.close();
			}
		}
		cursor.close();
		return list;
	}
	//联系人的批量写入
	public void testWrite() {
		ContentResolver resolver = getContext().getContentResolver();
		ContentValues values = new ContentValues();
		
		// 向raw_contacts表中插入一个id(自动生成)
		Uri resultUri = resolver.insert(rawContactsUri, values);
		long id = ContentUris.parseId(resultUri);
		
		// 用刚刚插入的id作为raw_contact_id列的值, 向data表中插入3条数据
		values.put("raw_contact_id", id);
		values.put("mimetype", "vnd.android.cursor.item/name");
		values.put("data1", "FLX");
		resolver.insert(dataUri, values);
		
		values.put("mimetype", "vnd.android.cursor.item/phone_v2");
		values.put("data1", "18600056789");
		resolver.insert(dataUri, values);
		
		values.put("mimetype", "vnd.android.cursor.item/email_v2");
		values.put("data1", "lkp@itcast.cn");
		resolver.insert(dataUri, values);
	}
	
	public void testWriteBatch() throws Exception {
		// 创建4个ContentProviderOperation, 代表4次insert操作
		ContentProviderOperation operation1 = ContentProviderOperation.newInsert(rawContactsUri) //
				.withValue("_id", null) //
				.build();
		ContentProviderOperation operation2 = ContentProviderOperation.newInsert(dataUri) //
				.withValueBackReference("raw_contact_id", 0) // 用同组的0号操作得到的返回值作为值插入
				.withValue("mimetype", "vnd.android.cursor.item/name") //
				.withValue("data1", "ZZH") //
				.build();
		ContentProviderOperation operation3 = ContentProviderOperation.newInsert(dataUri) //
				.withValueBackReference("raw_contact_id", 0) // 用同组的0号操作得到的返回值作为值插入
				.withValue("mimetype", "vnd.android.cursor.item/phone_v2") //
				.withValue("data1", "18600098765") //
				.build();
		ContentProviderOperation operation4 = ContentProviderOperation.newInsert(dataUri) //
				.withValueBackReference("raw_contact_id", 0) // 用同组的0号操作得到的返回值作为值插入
				.withValue("mimetype", "vnd.android.cursor.item/email_v2") //
				.withValue("data1", "zzh@itcast.cn") //
				.build();
		
		// 将4个操作对象装入集合
		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
		Collections.addAll(operations, operation1, operation2, operation3, operation4);
		
		ContentResolver resolver = getContext().getContentResolver();
		resolver.applyBatch("com.android.contacts", operations);
	}
}
