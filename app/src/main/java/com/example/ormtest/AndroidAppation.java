package com.example.ormtest;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AndroidAppation extends com.activeandroid.app.Application {
  /*  public DaoSession daoSession;
    public SQLiteDatabase db;
    public DaoMaster.DevOpenHelper helper;
    public DaoMaster daoMaster;*/
    private static AndroidAppation mobileApplication;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        setupGreenDaoDatabase();
        setupDbFlow();
        setupRealm();
    }

    private void setupRealm() {
        RealmConfiguration configuration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(configuration);
    }

    private void setupDbFlow() {
        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    public static AndroidAppation getInstance() {
        return mobileApplication;
    }

    public void setupGreenDaoDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
      /*  helper = new DaoMaster.DevOpenHelper(this, "student.db", null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();*/
    }

 /*   public DaoSession getDaoSession() {
        return daoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }*/
}
