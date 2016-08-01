/**
 * CopyRight	2014 ZhuYan
 *
 * @author Zhu Yan
 * <p/>
 * All right reserved
 * <p/>
 * Created	on	2014-2-17  锟斤拷锟斤拷4:58:51
 */
package com.example.ormtest.activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "Users")
public class UserAndroid extends Model {
    @Column(name = "Name")
    public String name;

    public UserAndroid() {
        super();
    }

    @Column(name = "Phone")
    public String phone;

}
