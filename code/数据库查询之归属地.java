//通过数据库查询归属地
public class AddressDao {

	public static final String path = "/data/data/com.example.safe/files/address.db";

	public static String getAddress(String num) {
		String address="";
		SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);
		//有三种可能,一种是手机号码11位,一种是固话最短10位
		//如果是手机号码
		if (num.matches("^1[3458]\\d{9}$")) {
			Cursor cursor = database
					.rawQuery(
							"select location from data2 where id=(select outkey from data1 where id=?)",
							new String[] { num.substring(0, 7) });

			if (cursor.moveToNext()) {
				address = cursor.getString(0);
				database.close();
			}
			//否则有剩下两种可能
		} else {
			//一种小于10位的
			switch (num.length()) {
			case 3:
				address = "特殊号码";
				break;
			case 4:
				address = "模拟器";
				break;
			case 5:
				address = "特殊号码";
				break;
			case 7:
					address = "本地号码";
				break;
			case 8:
				address = "本地号码";
				break;
			default:
				//如果上面的情况都不满足,且号码又大于等于10位,且以0结尾那么就是固话了.
				if (num.length() >= 10 && num.startsWith("0")) {
					// 区号是三位的:去查前三位
					Cursor cursor = database.rawQuery(
							"select location from data2 where area =?",
							new String[] { num.substring(1, 3) });
					if (cursor.moveToNext()) {
						String text = cursor.getString(0);
						address = text.substring(0, text.length() - 2);
					}
					cursor.close();
					// 区号是四位的.去查前四位
					cursor = database.rawQuery(
							"select location from data2 where area =?",
							new String[] { num.substring(1, 4) });
					if (cursor.moveToNext()) {
						String text = cursor.getString(0);
						address = text.substring(0, text.length() - 2);
					}
					cursor.close();
				}
				break;
			}
		}
		return address;

	}
}