package com.nliven.android.airports.biz.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.nliven.android.airports.biz.dao.DaoMaster.OpenHelper;

/**
 * 
 * @author mwoolley59
 *
 */
public class ProdDbOpenHelper extends OpenHelper{

	public ProdDbOpenHelper(Context context, String name, CursorFactory factory) {
		super(context, name, factory);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*
		 *  TODO We really would NOT want to drop user-created data here.  We would use
		 *  version checking and probably ALTER statements to make updates, etc.
		 */
		Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
        DaoMaster.dropAllTables(db, true);
        onCreate(db);	
	}

}
