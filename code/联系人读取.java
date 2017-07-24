package read.service;

import java.util.ArrayList;
import java.util.List;

import read.domian.ContactPhone;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class ContactService {
	private Context context;
	private ContentResolver cr;
	private ContactPhone contact;

	public ContactService(Context context) {
		super();
		this.context = context;
	}

	public List<ContactPhone> getContact() {
		List<ContactPhone> list = new ArrayList<ContactPhone>();
		cr = context.getContentResolver();
		Uri rawContactsUri = Uri
				.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");
		Cursor rawContactsCursor = cr.query(rawContactsUri,
				new String[] { "_id" }, null, null, null);
		while (rawContactsCursor.moveToNext()) {
			String id = rawContactsCursor.getString(0);
			contact = new ContactPhone();
			contact.setId(id);

			Cursor dataCursor = cr.query(dataUri, new String[] { "data1",
					"mimetype" }, "raw_contact_id=?", new String[] { id + "" },
					null);
			while (dataCursor.moveToNext()) {
				String data1 = dataCursor.getString(0);
				String mimetype = dataCursor.getString(1);
				if ("vnd.android.cursor.item/name".equals(mimetype)) {
					System.out.println("姓名: " + data1);
					contact.setName(data1);
				} else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
					System.out.println("电话: " + data1);
					contact.setPhone(data1);
				}

			}
			list.add(contact);
		}
		return list;
	}
}
