package com.example.ormtest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.ormtest.ormlite.OrmHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.ormtest.dao.DaoMaster;
import com.ormtest.dao.StudentDao;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends OrmLiteBaseActivity<OrmHelper> implements
        OnClickListener {

    private Button button;
    private TextView view;
    ProgressDialog dialog = null;
    private final static int INSERT_COUNT = 1000;
    //    private Studen usersDao = null;
    private StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(this);
        view = (TextView) findViewById(R.id.tv);
        findViewById(R.id.read_btn).setOnClickListener(this);
        // greenDao创建数据库
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "greendao.db",
                null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        studentDao = daoMaster.newSession().getStudentDao();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public List<Observable<String>> getDeleteAndInsertObservable() {
        List<Observable<String>> observableList = new ArrayList<>();
        observableList.add(RxObservable.getDeleteContentProviderObservable(MainActivity.this));
        observableList.add(RxObservable.getInsertContentProviderObservable(INSERT_COUNT, MainActivity.this));
        observableList.add(RxObservable.getDeleteOrmLiteOb(MainActivity.this));
        observableList.add(RxObservable.getInsertOrmLiteOb(MainActivity.this, INSERT_COUNT));
        observableList.add(RxObservable.getDeleteGreenDaoOb(studentDao));
        observableList.add(RxObservable.getInsertGreenDaoOb(INSERT_COUNT, studentDao));
        observableList.add(RxObservable.getDeleteActiveandroidOb());
        observableList.add(RxObservable.getInsertActiveandroidOb(INSERT_COUNT));
        observableList.add(RxObservable.getDeleteDbFlowOb());
        observableList.add(RxObservable.getInsertDbFlowOb(INSERT_COUNT));
        observableList.add(RxObservable.getDeleteRealmOb());
        observableList.add(RxObservable.getInsertRealmOb(INSERT_COUNT));
        return observableList;
    }

    public List<Observable<String>> getQueryObservable() {
        List<Observable<String>> observableList = new ArrayList<>();
        observableList.add(RxObservable.getQueryContentProviderObservable(MainActivity.this));
        observableList.add(RxObservable.getQueryOrmliteObservable(MainActivity.this));
        observableList.add(RxObservable.getQueryGreenDaoObservable(studentDao));
        observableList.add(RxObservable.getQueryActiveandroidOb());
        observableList.add(RxObservable.getQueryDbFlowOb());
        observableList.add(RxObservable.getQueryRealmOb());
        return observableList;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn) {
            final StringBuilder builder = new StringBuilder();
            Observable.concat(Observable.from(getDeleteAndInsertObservable()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onStart() {
                            super.onStart();
                            dialog = new ProgressDialog(MainActivity.this);
                            dialog.setCancelable(false);
                            dialog.setMessage("insert test is running");
                            dialog.setTitle("insert test is running");
                            dialog.show();
                        }

                        @Override
                        public void onCompleted() {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {
                            dialog.setMessage(s);
                            builder.append(s).append("\n");
                            view.setText(builder.toString());
                        }
                    });
        } else {
            final StringBuilder builder = new StringBuilder();
            Observable.concat(Observable.from(getQueryObservable()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onStart() {
                            dialog = new ProgressDialog(MainActivity.this);
                            dialog.setCancelable(false);
                            dialog.setMessage("insert test is running");
                            dialog.setTitle("insert test is running");
                            dialog.show();
                        }

                        @Override
                        public void onNext(String s) {
                            dialog.setMessage(s);
                            builder.append(s).append("\n");
                            view.setText(builder.toString());
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onCompleted() {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    });
        }
    }


}
