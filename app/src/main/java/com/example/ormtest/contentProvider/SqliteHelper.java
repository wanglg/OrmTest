/**
 * CopyRight	2013 ZhuYan
 *	@author Zhu Yan
 *  
 *	All right reserved
 *	
 *	Created		on		2013-6-3 ����11:03:08
 */
package com.example.ormtest.contentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Zhu Yan
 *
 * Created	on	2013-12-26  ����11:20:06
 */
public class SqliteHelper extends SQLiteOpenHelper{

	protected final static String dropTable = "drop table if exists ";
	
	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public SqliteHelper(Context context) {
		super(context, "provider.db", null, 5);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		System.out.println("db onCreate()");
//		System.out.println("foreign on");
//		db.execSQL("PRAGMA foreign_keys = ON");
		String sql = "create table users (id integer primary key autoincrement,name varchar(32),phone varchar(32))";
		db.execSQL(sql);
		
		
		System.out.println("create tables ok");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println(  " onUpgrade() oldVersion:" + oldVersion
		        + " newVersion:" + newVersion);
		db.execSQL(dropTable + "users");
		onCreate(db);
	}

}
