package com.example.ormtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.ormtest.activeandroid.UserAndroid;
import com.example.ormtest.contentProvider.MyContentProvider;
import com.example.ormtest.dbflow.UserDbFlow;
import com.example.ormtest.ormlite.User;
import com.example.ormtest.realm.UserRealm;
import com.ormtest.dao.StudentDao;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import greendao.Student;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;

/**
 * User: wanglg
 * Date: 2016-07-29
 * Time: 14:41
 * FIXME
 */
public class RxObservable {

    public static Observable<String> getInsertContentProviderObservable(final int insertCount, final Context context) {

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                long startTime = System.currentTimeMillis();
                for (int i = 0; i < insertCount; i++) {
                    ContentValues values = new ContentValues();
                    values.put("name", "zhuyan");
                    values.put("phone", "18782956011");
                    context.getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
                }
                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "provider插入" + insertCount + "条数据：耗时" + spendTime
                        + "ms   平均耗时:" + (spendTime * 1.0 / insertCount);
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getDeleteContentProviderObservable(final Context context) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                long startTime = System.currentTimeMillis();
                context.getContentResolver().delete(MyContentProvider.CONTENT_URI, null,
                        null);
                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "provider deleteall:耗时"
                        + spendTime;
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getQueryContentProviderObservable(final Context context) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                long startTime = System.currentTimeMillis();
                Cursor cursor = context.getContentResolver().query(
                        MyContentProvider.CONTENT_URI, null, null, null, null);
                ;
                //
                int count = 0;
                if (cursor != null) {
                    cursor.moveToFirst();
                    count = cursor.getCount();
                    List<com.example.ormtest.contentProvider.User> providerList = new ArrayList<>(
                            cursor.getCount());
                    int[] pos = new int[]{cursor.getColumnIndexOrThrow("id"),
                            cursor.getColumnIndexOrThrow("name"),
                            cursor.getColumnIndexOrThrow("phone")};
                    do {
                        com.example.ormtest.contentProvider.User user = new com.example.ormtest.contentProvider.User();
                        user.setId(cursor.getInt(pos[0]));
                        user.setName(cursor.getString(pos[1]));
                        user.setPhone(cursor.getString(pos[2]));

                        providerList.add(user);
                    } while (cursor.moveToNext());
                    cursor.close();
                    cursor = null;
                }

                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "provider query:耗时"
                        + spendTime + " query count " + count;
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getQueryOrmliteObservable(final MainActivity ormLiteBaseActivity) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                long startTime = System.currentTimeMillis();
                List<User> users = Collections.EMPTY_LIST;
                try {
                    users = ormLiteBaseActivity.getHelper().getUserDao().queryForAll();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "ORMLite query:耗时"
                        + spendTime + " query count " + users.size();
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getQueryGreenDaoObservable(final StudentDao studentDao) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                long startTime = System.currentTimeMillis();
                List<Student> studentList = studentDao.loadAll();
                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "GreenDao query:耗时"
                        + spendTime + " query count " + studentList.size();
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getDeleteOrmLiteOb(final MainActivity ormLiteBaseActivity) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                long spendTime = 0;
                try {

                    long startTime = System.currentTimeMillis();
                    ormLiteBaseActivity.getHelper().getUserDao().deleteBuilder().delete();
                    spendTime = System.currentTimeMillis() - startTime;
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    subscriber.onError(e1);
                }

                String msg = "ORMLite deleteall:耗时"
                        + spendTime;
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getDeleteDbFlowOb() {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {


                long startTime = System.currentTimeMillis();
                SQLite.delete().from(UserDbFlow.class).execute();
                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "DbFlow deleteall:耗时"
                        + spendTime;
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getDeleteRealmOb() {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
//                final RealmResults<UserRealm> results = realm.where(UserRealm.class).findAll();
                long startTime = System.currentTimeMillis();
                // All changes to data must happen in a transaction
           /*     realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        // Delete all matches
                        results.deleteAllFromRealm();
                    }
                });*/
                final Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(UserRealm.class);
                    }
                });
                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "Realm deleteall:耗时"
                        + spendTime;
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getQueryRealmOb() {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                long startTime = System.currentTimeMillis();
                final Realm realm = Realm.getDefaultInstance();
                RealmResults<UserRealm> results = realm.where(UserRealm.class).findAll();
                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "Realm query:耗时"
                        + spendTime + " query count " + results.size();
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getDeleteActiveandroidOb() {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {


                long startTime = System.currentTimeMillis();
                new Delete().from(UserAndroid.class).execute();
                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "Activeandroid deleteall:耗时"
                        + spendTime;
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getInsertRealmOb(final int insertCount) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                final Realm realm = Realm.getDefaultInstance();
                final List<UserRealm> userRealms = new ArrayList<>(insertCount);
                long startTime = System.currentTimeMillis();
                try {
                    for (int i = 0; i < insertCount; i++) {
                        UserRealm users = new UserRealm();
                        users.setName("zhuyan" + i);
                        users.setAge(26);
                        userRealms.add(users);
                    }
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.insert(userRealms);
                        }
                    });

                } catch (Exception e) {
                    subscriber.onError(e);
                }
                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "Realm insert " + insertCount + "条数据：耗时" + spendTime
                        + "ms   平均耗时:" + (spendTime * 1.0 / insertCount);
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getInsertDbFlowOb(final int insertCount) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {


                long startTime = System.currentTimeMillis();
                try {
                    FlowManager.getDatabase(MobileDatabase.class).getWritableDatabase().beginTransaction();
                    for (int i = 0; i < insertCount; i++) {
                        UserDbFlow users = new UserDbFlow();
                        users.setName("zhuyan" + i);
                        users.setPhone("18613887192");
                        users.save();
                    }
                    FlowManager.getDatabase(MobileDatabase.class).getWritableDatabase().setTransactionSuccessful();
                } catch (Exception e) {
                    subscriber.onError(e);
                } finally {
                    FlowManager.getDatabase(MobileDatabase.class).getWritableDatabase().endTransaction();
                }
                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "DbFlow insert" + insertCount + "条数据：耗时" + spendTime
                        + "ms   平均耗时:" + (spendTime * 1.0 / insertCount);
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getQueryDbFlowOb() {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                long startTime = System.currentTimeMillis();
                List<UserDbFlow> dbFlows = new com.raizlabs.android.dbflow.sql.language.Select().from(UserDbFlow.class).queryList();
                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "DbFlow query:耗时"
                        + spendTime + " query count " + dbFlows.size();
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getInsertActiveandroidOb(final int insertCount) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {


                long startTime = System.currentTimeMillis();
                ActiveAndroid.beginTransaction();
                try {
                    for (int i = 0; i < insertCount; i++) {
                        UserAndroid users = new UserAndroid();
                        users.name = "zhuyan" + i;
                        users.phone = "18613887192";
                        users.save();
                    }
                    ActiveAndroid.setTransactionSuccessful();
                } catch (Exception e) {
                    subscriber.onError(e);
                } finally {
                    ActiveAndroid.endTransaction();
                }
                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "Activeandroid insert" + insertCount + "条数据：耗时" + spendTime
                        + "ms   平均耗时:" + (spendTime * 1.0 / insertCount);
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getQueryActiveandroidOb() {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {


                long startTime = System.currentTimeMillis();
                List<UserAndroid> activeAndroidUsers = new Select().from(
                        UserAndroid.class).execute();
                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "Activeandroid query:耗时"
                        + spendTime + " query count " + activeAndroidUsers.size();
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getDeleteGreenDaoOb(final StudentDao studentDao) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                long startTime = System.currentTimeMillis();
                studentDao.deleteAll();
                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "GreenDao deleteall:耗时"
                        + spendTime;
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getInsertGreenDaoOb(final int insertCount, final StudentDao studentDao) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {

                long startTime = System.currentTimeMillis();
                for (int i = 0; i < insertCount; i++) {
                    Student student = new Student();
                    student.setId(Long.valueOf(i));
                    student.setSName("leowong");
                    student.setSSex("男");
                    studentDao.insert(student);
                }
                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "GreenDao插入" + insertCount + "条数据：耗时" + spendTime
                        + "ms   平均耗时:" + (spendTime * 1.0 / insertCount);
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<String> getInsertOrmLiteOb(final MainActivity ormLiteBaseActivity, final int insertCount) {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {

                long startTime = System.currentTimeMillis();
                try {
                    for (int i = 0; i < insertCount; i++) {
                        User user = new User();
                        user.setName("zhuyan");
                        user.setPhone("18782956011");
                        ormLiteBaseActivity.getHelper().getUserDao().create(user);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                long spendTime = System.currentTimeMillis() - startTime;
                String msg = "ORMLite插入" + insertCount + "条数据：耗时" + spendTime
                        + "ms   平均耗时:" + (spendTime * 1.0 / insertCount);
                subscriber.onNext(msg);
                subscriber.onCompleted();
            }
        });
    }
}
