/**
 * CopyRight	2014 ZhuYan
 *
 * @author Zhu Yan
 * <p/>
 * All right reserved
 * <p/>
 * Created	on	2014-3-19  ����10:08:36
 */
package com.example.ormtest.ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Zhu Yan
 *
 * Created	on	2014-3-19  ����10:08:36
 */
@DatabaseTable(tableName = "users")
public class User {

    @DatabaseField(generatedId = true, useGetSet = true)
    private int id;

    @DatabaseField(useGetSet = true, columnName = "name")
    private String name;

    @DatabaseField(useGetSet = true, columnName = "phone")
    private String phone;


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }


    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }


    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }


    /**
     *
     */
    public User() {
        super();
    }

}
