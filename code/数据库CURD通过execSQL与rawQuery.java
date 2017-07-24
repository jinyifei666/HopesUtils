package com.itheima.sqlite.dao;

import java.util.ArrayList;
import java.util.List;

import com.itheima.sqlite.bean.Person;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PersonDaoClassic {
	private MyOpenHelper helper;

	public PersonDaoClassic(Context context) {
		helper = new MyOpenHelper(context);
	}

	public void insert(Person p) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("INSERT INTO person(name, balance) VALUES(?,?)", new Object[] { p.getName(), p.getBalance() });
		db.close();
	}

	public void delete(int id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("DELETE FROM person WHERE id=?", new Object[] { id });
		db.close();
	}

	public void update(Person p) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("UPDATE person SET name=?, balance=? WHERE id=?", new Object[]{p.getName(), p.getBalance(), p.getId()});
		db.close();
	}

	public Person query(int id) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT name, balance FROM person WHERE id=?", new String[] { id + "" });	// 查询, 得到游标(结果集)	
		Person p = null;
		if (c.moveToNext()) {	// 把游标向后移动一位, 判断是否包含数据
			String name = c.getString(c.getColumnIndex("name"));	// 先根据列名获取索引, 再根据索引获取数据
			int balance = c.getInt(1);								// 直接根据索引获取数据
			p = new Person(id, name, balance);
		}
		c.close();
		db.close();
		return p;
	}
	
	public List<Person> queryAll() {
		List<Person> persons = new ArrayList<Person>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT id, name, balance FROM person", null);
		while (c.moveToNext()) {
			int id = c.getInt(c.getColumnIndex("id"));
			String name = c.getString(c.getColumnIndex("name"));
			int balance = c.getInt(c.getColumnIndex("balance"));	
			persons.add(new Person(id, name, balance));
		}
		c.close();
		db.close();
		return persons;
	}
	
	public List<Person> queryPage(int pageNum, int pageSize) {
		int offset = (pageNum - 1) * pageSize;
		List<Person> persons = new ArrayList<Person>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT id, name, balance FROM person LIMIT ?,?", new String[] {offset + "", pageSize + ""});
		while (c.moveToNext()) {
			int id = c.getInt(c.getColumnIndex("id"));
			String name = c.getString(c.getColumnIndex("name"));
			int balance = c.getInt(c.getColumnIndex("balance"));	
			persons.add(new Person(id, name, balance));
		}
		c.close();
		db.close();
		return persons;
	}
	
	public void remit(int from, int to, int amount) {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.beginTransaction(); 			// 开启事务
			db.execSQL("UPDATE person SET balance=balance+? WHERE id=?", new Object[] { amount, to });
			db.execSQL("UPDATE person SET balance=balance-? WHERE id=?", new Object[] { amount, from });
			db.setTransactionSuccessful(); 	// 设置事务成功
		} finally {
			db.endTransaction();			// 结束事务, 把最后一次成功点之前的提交
			db.close();
		}
	}

}
