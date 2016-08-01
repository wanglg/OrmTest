package com.example.ormtest.dbflow;

import com.example.ormtest.MobileDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * User: wanglg
 * Date: 2016-08-01
 * Time: 10:51
 * FIXME
 */
@Table(database = MobileDatabase.class)
public class UserDbFlow extends BaseModel implements Serializable {
    @PrimaryKey(autoincrement = true)
    long id; // package-private recommended, not required

    @Column
    String phone;

    @Column
    @Unique
    private String name; // private with getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
