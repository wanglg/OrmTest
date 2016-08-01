package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class GreenDaoGenerator {
    public static void main(String[] args) throws Exception {
        //创建一个用于添加实体的Schema对象，第一个参数表示数据库的版本，第二个参数表示在java-gen目录下自动生成的实体类和DAO类存放的包名
        Schema schema = new Schema(1, "greendao");
        schema.setDefaultJavaPackageDao("com.ormtest.dao");
        addNote(schema);
        //最后通过DaoGenerator对象的generateAll()方法来生成相应的实体类和DAO类，参数分别为Schema对象和java-gen目录路径
        new DaoGenerator().generateAll(schema, "../dbTest/app/src/main/java-gen");
    }

    /**
     * @param schema
     */
    private static void addNote(Schema schema) {
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「Student」（既类名）
        Entity note = schema.addEntity("Student");
        // 你也可以重新给表命名
        // note.setTableName("Student2");

        // greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
        // 接下来你便可以设置表中的字段：
        // 与在 Java 中使用驼峰命名法不同，默认数据库中的命名是使用大写和下划线来分割单词的。
        note.addIdProperty();
        note.addStringProperty("sName").notNull();
        note.addStringProperty("sAge");
        note.addStringProperty("sSex");
        note.addStringProperty("sClass");
    }
}
