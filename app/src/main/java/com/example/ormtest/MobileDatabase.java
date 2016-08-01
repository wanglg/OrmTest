package com.example.ormtest;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Description
 * Created by wanglg on 2015/8/11.
 */
@Database(name = MobileDatabase.NAME, version = MobileDatabase.VERSION)
public class MobileDatabase {
    public static final String NAME = "orm_database";
    public static final int VERSION = 1;
}